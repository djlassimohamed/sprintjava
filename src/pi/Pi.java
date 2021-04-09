/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import entities.commentaire;
import entities.post;
import entities.user;
import java.time.LocalDateTime;
import java.sql.Date;
import services.CommentService;
import services.PostService;

/**
 *
 * @author taieb
 */
public class Pi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
       

       post p = new post("jariya", "esprit", Date.valueOf("2021-04-09"), "sport", 5);
       PostService sp = new PostService();
       // sp.ajouterPost(p);
       //sp.modifierPost(10, p);
       //sp.afficherPost();
       //sp.supprimerPost(10);
       sp.like(5, 4);
        //commentaire com = new commentaire("commentaire", Date.valueOf("2021-04-10"));
       //CommentService cs = new CommentService();
        //cs.ajouterCommentaire(com, 2, 9);
        //cs.modifierCommentaire(com, 5);
        //cs.afficherCommentaire(9);
        //cs.supprimerCommentaire(5);
    }
    
    
}
