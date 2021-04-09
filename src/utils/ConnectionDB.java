package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    
String password="";
    String login="root";
    String url="jdbc:mysql://localhost:3306/jnif";
    Connection connection;
    public static ConnectionDB instance;
    ConnectionDB()
    {
       
        try 
        {
            connection=DriverManager.getConnection(url, login, password);
            
            System.out.println("connected");
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
public Connection getCnx()
{
return connection;   
}
public static ConnectionDB getInstance()
{
   if (instance==null)
   {
       instance =new ConnectionDB();
       
   }
   return instance;
}
}
