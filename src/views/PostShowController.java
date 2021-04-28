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
import java.sql.PreparedStatement;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.CommentService;
import services.PostService;
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

    ObservableList<post> oblist = FXCollections.observableArrayList();
    private Label desclabel;
    @FXML
    private Button viewmore;
    @FXML
    private Button create_post;
    @FXML
    private Button delete_btn;
    @FXML
    private Pagination pagination;
    @FXML
    private Button tri;

    @FXML
    public void changeSceneToDetailedPersonView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("postDetails.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        PostDetailsController controller = loader.getController();
        controller.initData(table.getSelectionModel().getSelectedItem());

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Connection c = ConnectionDB.getInstance().getCnx();

            ResultSet rs = c.createStatement().executeQuery("select * from post");

            while (rs.next()) {

                oblist.add(new post(rs.getInt("id"),
                        rs.getString("sujet"),
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

        table.setItems(oblist);

        int pagina = 1;
        if (oblist.size() % filaPorPagina() == 0) {
            pagina = oblist.size() / filaPorPagina();
        } else if (oblist.size() > filaPorPagina()) {
            pagina = oblist.size() / filaPorPagina();

        }
        pagination.setPageCount(pagina);
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(this::createPagination);

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

    @FXML
    private void create(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("add post.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void delete(ActionEvent event) {
        ObservableList<post> selectedRows, allPost;
        allPost = table.getItems();
        selectedRows = table.getSelectionModel().getSelectedItems();
        post p = table.getSelectionModel().getSelectedItem();
        PostService sp = new PostService();
        System.out.println(p.getId());
        sp.supprimerPost(p.getId());
        refreshtable();
    }

    public void refreshtable() {
        oblist.clear();
        try {
            Connection c = ConnectionDB.getInstance().getCnx();
            ResultSet rs = c.createStatement().executeQuery("select * from post");

            while (rs.next()) {

                oblist.add(new post(rs.getInt("id"),
                        rs.getString("sujet"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("categorie"),
                        rs.getInt("user_id")
                ));

            }

        } catch (SQLException ex) {
            Logger.getLogger(PostShowController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void triparlike() {
        oblist.clear();

        PreparedStatement pt;

        try {
            Connection c = ConnectionDB.getInstance().getCnx();
            pt = c.prepareStatement("select * from post p INNER JOIN likes l on "
                    + "(p.id=l.post_id) GROUP BY post_id ORDER by COUNT(*) DESC");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                oblist.add(new post(rs.getInt("id"),
                        rs.getString("sujet"),
                        rs.getString("description"),
                        rs.getDate("date"),
                        rs.getString("categorie"),
                        rs.getInt("user_id")
                ));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int filaPorPagina() {
        return 3;
    }

    private Node createPagination(int pageIndex) {
        int fromIndex = pageIndex * filaPorPagina();
        int toIndex = Math.min(fromIndex + filaPorPagina(), oblist.size());
        table.setItems(FXCollections.observableArrayList(oblist.subList(fromIndex, toIndex)));
        return new BorderPane(table);
    }

}
