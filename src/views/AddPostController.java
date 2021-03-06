/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.post;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PostService;

/**
 * FXML Controller class
 *
 */
public class AddPostController implements Initializable {

    @FXML
    private TextField sujet;
    @FXML
    private TextArea desc;
    @FXML
    private ChoiceBox<String> categorie;
    @FXML
    private Button add;

    /**
     * Initializes the controller class.
     */
    ObservableList<String> obs = FXCollections.observableArrayList("Sport", "Nutriton");
    @FXML
    private Button all;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categorie.setItems(obs);
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {

        String suj = sujet.getText();
        String descr = desc.getText();
        String cat = categorie.getValue();
        Date date = new Date(System.currentTimeMillis());

        post p = new post(suj, descr, date, cat, 2);
        PostService sp = new PostService();
        sp.ajouterPost(p);
        
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("postShow.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
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

}
