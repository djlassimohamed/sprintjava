/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author taieb
 */
public class commentaire {
    
    private int id;
    private int post_id;
    private int user_id;
    private String description;
    private Date date;

    public commentaire(int id, int post_id, int user_id, String description, Date date) {
        this.id = id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.description = description;
        this.date = date;
    }

    public commentaire(int post_id, int user_id, String description, Date date) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.description = description;
        this.date = date;
    }

    public commentaire(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    
    
    public commentaire() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
    
    
    
}
