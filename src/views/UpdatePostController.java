/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PostService;
import utils.ConnectionDB;

/**
 * FXML Controller class
 *
 * @author taieb
 */
public class UpdatePostController implements Initializable {

    @FXML
    private TextField sujet;
    @FXML
    private TextArea desc;
    @FXML
    private ChoiceBox<String> categorie;
    @FXML
    private Button all;
    @FXML
    private Label id_txt;
    @FXML
    private Button upd;

    /**
     * Initializes the controller class.
     */
    ObservableList<String> obs = FXCollections.observableArrayList("Sport", "Nutriton");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        categorie.setItems(obs);
    }

    public void initData(int id) {
        
        id_txt.setText(String.valueOf(id));
    }
    
    
    @FXML
    private void All(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("postShow.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

    @FXML
    private void update(ActionEvent event) throws IOException {
        String id = id_txt.getText();
        int id_post = Integer.parseInt(id);
        String suj = sujet.getText();
        String descr = desc.getText();
        String cat = categorie.getValue();
        Date date = new Date(System.currentTimeMillis());
        /*PreparedStatement pt;
        try {
            Connection c = ConnectionDB.getInstance().getCnx();
            pt = c.prepareStatement("select user_id from post where id=?");
            
            pt.setInt(1, id_post);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
            System.out.println(rs.getInt("user_id"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdatePostController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        post p = new post(suj, descr, date, cat, 2);
        PostService sp = new PostService();
        int i=Integer.parseInt(id_txt.getText());  
        sp.modifierPost(i, p);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("postShow.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

}
