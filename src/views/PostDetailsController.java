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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.CommentService;
import services.PostService;
import utils.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author taieb
 */
public class PostDetailsController implements Initializable {

    @FXML
    private TableView<commentaire> tab_com;
    @FXML
    private TableColumn<commentaire, Date> col_date;
    @FXML
    private TableColumn<commentaire, String> col_contenu;
    @FXML
    private TextArea commentaire;
    @FXML
    private Button com_btn;
    @FXML
    private Label lab_suj;
    @FXML
    private Label lab_cat;
    @FXML
    private Label lab_dat;
    @FXML
    private Label lab_des;
    @FXML
    private Label suj;
    @FXML
    private Label cat;
    @FXML
    private Label dat;
    @FXML
    private Label des;
    @FXML
    private Button back;
    @FXML
    private Label lab_id;
    @FXML
    private Label id_p;

    /**
     * Initializes the controller class.
     */
    private post selectedPost;
    @FXML
    private Button like_btn;
    @FXML
    private Button dislike_btn;

    public void initData(post p) {
        selectedPost = p;
        id_p.setText(String.valueOf(selectedPost.getId()));
        suj.setText(selectedPost.getSujet());
        cat.setText(selectedPost.getCategorie());
        dat.setText(selectedPost.getDate().toString());
        des.setText(selectedPost.getDescription());
        System.out.println(selectedPost.getId());
    }
    ObservableList<commentaire> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        PreparedStatement pt;
        try {
            Connection c = ConnectionDB.getInstance().getCnx();
            
            ResultSet rs = c.createStatement().executeQuery("select * from commentaire");

            while (rs.next()) {
               /* oblist.add(new commentaire(rs.getString("description"),
                        rs.getDate("date")
                ));*/
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_contenu.setCellValueFactory(new PropertyValueFactory<>("description"));

        tab_com.setItems(oblist);
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("postShow.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void comment(ActionEvent event) {
        refreshtable();
        String com = commentaire.getText();

        Date date = new Date(System.currentTimeMillis());
        commentaire c = new commentaire(com, date);
        CommentService cs = new CommentService();
        cs.ajouterCommentaire(c, 5, selectedPost.getId());
        refreshtable();

    }

    
    public void refreshtable(){
        oblist.clear();
        PreparedStatement pt;
        try {
            Connection c = ConnectionDB.getInstance().getCnx();
            pt = c.prepareStatement("select * from commentaire where post_id=?");
            pt.setInt(1, selectedPost.getId());
            ResultSet rs = pt.executeQuery();
            //ResultSet rs = c.createStatement().executeQuery("select * from commentaire");

            while (rs.next()) {
                oblist.add(new commentaire(rs.getString("description"),
                                           rs.getDate("date")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void like(ActionEvent event) {
        PostService ps = new PostService();
        ps.like(4, selectedPost.getId() );
    }

    @FXML
    private void dislike(ActionEvent event) {
        PostService ps = new PostService();
        ps.dislike(4, selectedPost.getId() );
    }
    
}
