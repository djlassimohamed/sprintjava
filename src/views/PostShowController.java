/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.commentaire;
import entities.post;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.CommentService;
import utils.ConnectionDB;

/**
 * FXML Controller class
 *
 */
public class PostShowController implements Initializable {

    @FXML
    private TableView<post> table;
    @FXML
    private TableColumn<post, String> col_suj;
    @FXML
    private TableColumn<post, String> col_cat;
    @FXML
    private TableColumn<post, Date> col_date;
    @FXML
    private TableColumn<post, String> col_desc;
    @FXML
    private TextField search;
    @FXML
    private TableColumn col_details;

    ObservableList<post> oblist = FXCollections.observableArrayList();
    @FXML
    private Label des;
    @FXML
    private Label desclabel;
    @FXML
    private TextArea comment;
    @FXML
    private Button com_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection c = ConnectionDB.getInstance().getCnx();

            ResultSet rs = c.createStatement().executeQuery("select * from post");

            while (rs.next()) {

                oblist.add(new post(rs.getString("sujet"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("categorie"),
                        rs.getInt("user_id")
                ));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PostShowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        col_suj.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        col_cat.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        Callback<TableColumn<post, String>, TableCell<post, String>> cellFactory
                = (param) -> {
                    final TableCell<post, String> cell = new TableCell<post, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final Button detailsButton = new Button("Details");
                        detailsButton.setOnAction(event -> {

                            /*Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("Vous avez choisi \n" + p.getSujet());
                            a.show();*/
                            post p = table.getSelectionModel().selectedItemProperty().get();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("postDetails.fxml"));

                            try {
                                Parent root;
                                root = loader.load();
                                Scene scene = new Scene(root);

                                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                window.setScene(scene);
                                window.show();

                            } catch (IOException ex) {
                                Logger.getLogger(PostShowController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        setGraphic(detailsButton);
                        setText(null);

                    }

                }

            };
                    return cell;
                };

        col_details.setCellFactory(cellFactory);

        table.setItems(oblist);

        FilteredList<post> filteredData = new FilteredList<>(oblist, b -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(post -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (post.getSujet().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; 
                } else if (post.getCategorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; 
                } else {
                    return false; 
                }
            });
        });
        SortedList<post> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

    }

    @FXML
    private void mouseClick(MouseEvent event) {
        if (event.getClickCount() > 1) {
            onClicked();
        }
    }

    private void onClicked() {

        if (table.getSelectionModel().getSelectedItem() != null) {
            post postt = table.getSelectionModel().getSelectedItem();
            desclabel.setText(postt.getDescription());
        }

    }

    
    private void comment(ActionEvent event) {
       /* post p = table.getSelectionModel().getSelectedItem();
        
        int i=p.getId();
        System.out.println(i);
        String com=comment.getText();
        Date date = new Date(System.currentTimeMillis());
        commentaire c=new commentaire(com, date);
        CommentService cs= new CommentService();*/
        //cs.ajouterCommentaire(c, 5, i);
        
    }

    
    
    
    
}
