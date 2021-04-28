/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import javafx.scene.control.Button;

/**
 *
 * @author taieb
 */
public class post {
    
    private int id;
    private String sujet;
    private String description;
    private Date date;
    private String categorie;
    private int user_id;

    

  
    public post() {
    }

    public post(int id, String sujet, String description, Date date, String categorie, int user_id) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.date = date;
        this.categorie = categorie;
        this.user_id = user_id;
    }

    public post(String sujet, String description, Date date, String categorie, int user_id) {
        this.sujet = sujet;
        this.description = description;
        this.date = date;
        this.categorie = categorie;
        this.user_id = user_id;
    }

    public post(int user_id) {
        this.user_id = user_id;
    }


    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

  
}
