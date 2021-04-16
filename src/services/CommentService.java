/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.commentaire;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionDB;

/**
 *
 * @author taieb
 */
public class CommentService {

    Connection c = ConnectionDB.getInstance().getCnx();

    public void ajouterCommentaire(commentaire co, int user_id, int post_id) {
        Statement st;
        try {
            st = c.createStatement();
            String req = "INSERT INTO `commentaire` (`id`, `post_id`, `user_id`, `date`, `description`) "
                    + "   VALUES ('" + co.getId() + "', '" + post_id + "', '" + user_id + "', '" + co.getDate() + "', '" + co.getDescription() + "')";
            st.executeUpdate(req);
            System.out.println("comment");
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierCommentaire(commentaire co, int id) {

        try {
            PreparedStatement pt = c.prepareStatement("UPDATE `commentaire` SET `description`=? WHERE `id`= ?");
            pt.setString(1, co.getDescription());
            pt.setInt(2, id);
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void afficherCommentaire(int id) {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("select * from commentaire where post_id=?");
            pt.setInt(1, id);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Comment id : " + rs.getInt(1)
                        + ", Post: " + rs.getInt(2)
                        + ", User: " + rs.getInt(3)
                        + ", Date: " + rs.getDate(4)
                        + ", Description: " + rs.getString(5)
                );

            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void supprimerCommentaire(int p) {
        PreparedStatement pt;

        try {
            pt = c.prepareStatement("delete from commentaire where id =?");

            pt.setInt(1, p);
            pt.executeUpdate();
            System.out.println("delete successful");
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
