/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import entities.commentaire;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author taieb
 */
public class PostDetailsController implements Initializable {

    @FXML
    private TextField suj;
    @FXML
    private TextField cat;
    @FXML
    private TextField date;
    @FXML
    private TextArea desc;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    
    public void showInformation(String sujet, String d, String categ, String descr){
        suj.setText(sujet);
        date.setText(d);
        cat.setText(categ);
        desc.setText(descr);
    
    }
    
}
