/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.post;
import entities.user;
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
public class PostService {
    
     Connection c=ConnectionDB.getInstance().getCnx();
     
     public void ajouterPost(post p){
         Statement st;
         try {
             st = c.createStatement();
             String req = "INSERT INTO `post` (`id`, `user_id`, `sujet`, `description`, `date`, `categorie`) VALUES "
                     + " (NULL, '"+ p.getUser_id()+"', '"+p.getSujet()+"', '"+p.getDescription()+"', '"+p.getDate()+"', '"+p.getCategorie()+"')";
            st.executeUpdate(req);
         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     
     }
     
     public void modifierPost(int id, post p){
         try {
             PreparedStatement pt=c.prepareStatement
             ("UPDATE `post` SET `sujet`=?,`description`=?,`date`=?,`categorie`=? WHERE id=?");
             pt.setString(1, p.getSujet());
             pt.setString(2, p.getDescription());
             pt.setDate(3, p.getDate());
             pt.setString(4, p.getCategorie());
             pt.setInt(5,id);
             pt.executeUpdate();

         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     
     }
     
     public void afficherPost(){
             PreparedStatement pt;
             
         try {
             pt = c.prepareStatement("select * from post");
             ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                System.out.println("Post id :" + rs.getInt(1) + 
                                   ", User: "+rs.getInt(2)+
                                   ", Sujet :" + rs.getString(3) + 
                                   ", Description : " + rs.getString(4) +
                                   ", Date : "+rs.getDate(5)+
                                   ", Catégorie : " +rs.getString(6)
                                   
                                   );
            }
         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
    
     public void supprimerPost (int p){
             PreparedStatement pt;
                  
         try {
             pt=c.prepareStatement("delete from post where id =?");
             pt.setInt(1, p);
             pt.executeUpdate();
             System.out.println("delete successful");
         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     
     public void like (int user_id, int post_id){
     Statement st;
         
         try {
             st = c.createStatement();
             String req = "INSERT INTO `likes` (`post_id`, `users_id`) VALUES "
                     + "('"+post_id+"', '"+user_id+"');";
             st.executeUpdate(req);
             System.out.println("post liked");

         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
     
     
     public void dislike (int user_id, int post_id){
     Statement st;
         
         try {
             st = c.createStatement();
             String req = "INSERT INTO `likes` (`post_id`, `users_id`) VALUES "
                     + "('"+post_id+"', '"+user_id+"');";
             st.executeUpdate(req);
             System.out.println("post disliked");

         } catch (SQLException ex) {
             Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
