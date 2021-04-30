/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.teknikindustries.bulksms.SMS;

import entities.post;
import entities.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import utils.ConnectionDB;

/**
 *
 * 
 */
public class PostService {

    Connection c = ConnectionDB.getInstance().getCnx();

    public void ajouterPost(post p) {
        Statement st;
        try {
            st = c.createStatement();
            String req = "INSERT INTO `post` (`id`, `user_id`, `sujet`, `description`, `date`, `categorie`) VALUES "
                    + " (NULL, '" + p.getUser_id() + "', '" + p.getSujet() + "', '" + p.getDescription() + "', '" + p.getDate() + "', '" + p.getCategorie() + "')";
            st.executeUpdate(req);
            System.out.println("post created");
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierPost(int id, post p) {
        try {
            PreparedStatement pt = c.prepareStatement("UPDATE `post` SET `sujet`=?,`description`=?,`date`=?,`categorie`=? WHERE id=?");
            pt.setString(1, p.getSujet());
            pt.setString(2, p.getDescription());
            pt.setDate(3, p.getDate());
            pt.setString(4, p.getCategorie());
            pt.setInt(5, id);
            pt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void afficherPost() {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("select * from post");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Post id :" + rs.getInt(1)
                        + ", User: " + rs.getInt(2)
                        + ", Sujet :" + rs.getString(3)
                        + ", Description : " + rs.getString(4)
                        + ", Date : " + rs.getDate(5)
                        + ", Catégorie : " + rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void recherche(String cat) {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("SELECT * FROM `post` WHERE `categorie`= " + "'" + cat + "'");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Post id :" + rs.getInt(1)
                        + ", User: " + rs.getInt(2)
                        + ", Sujet :" + rs.getString(3)
                        + ", Description : " + rs.getString(4)
                        + ", Date : " + rs.getDate(5)
                        + ", Catégorie : " + rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rechercheparsujet(String cat) {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("SELECT * FROM `post` WHERE `sujet`= " + "'" + cat + "'");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Post id :" + rs.getInt(1)
                        + ", User: " + rs.getInt(2)
                        + ", Sujet :" + rs.getString(3)
                        + ", Description : " + rs.getString(4)
                        + ", Date : " + rs.getDate(5)
                        + ", Catégorie : " + rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerPost(int p) {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("delete from post where id =?");
            pt.setInt(1, p);
            pt.executeUpdate();
            System.out.println("delete successful");
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void like(int user_id, int post_id) {
        Statement st;

        try {
            st = c.createStatement();
            String req = "INSERT INTO `likes` (`post_id`, `users_id`) VALUES "
                    + "('" + post_id + "', '" + user_id + "');";
            st.executeUpdate(req);
            System.out.println("post liked");
            SMS smslike= new SMS();
            smslike.SendSMS("taieb_oueslati", "Esprit2020", "your post has been liked", "21694870758", "https://bulksms.vsms.net/eapi/submission/send_sms/2/2.0");

        } catch (SQLException ex) {
            //Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("post already liked");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Post Already Liked");

            alert.showAndWait();
            
        }
    }

    public void dislike(int user_id, int post_id) {
        Statement st;

        try {
            st = c.createStatement();
            String req = "INSERT INTO `likes` (`post_id`, `users_id`) VALUES "
                    + "('" + post_id + "', '" + user_id + "');";
            st.executeUpdate(req);
            System.out.println("post disliked");

        } catch (SQLException ex) {
            //Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("post already disliked");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Post Already Disliked");

            alert.showAndWait();
        }
    }

    public void triparlike() {

        PreparedStatement pt;

        try {
            pt = c.prepareStatement("select * from post p INNER JOIN likes l on "
                                  + "(p.id=l.post_id) GROUP BY post_id ORDER by COUNT(*) DESC");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Post id :" + rs.getInt(1)
                        + " , Sujet : " + rs.getString(3)
                        + " , Description : " + rs.getString(4)
                        + " , Date : " + rs.getDate(5)
                        + " , Catégorie : " + rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
