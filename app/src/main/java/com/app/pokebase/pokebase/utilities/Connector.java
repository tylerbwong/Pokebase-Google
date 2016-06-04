package com.app.pokebase.pokebase.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Tyler Wong
 */
public class Connector {
   /**
    * Get a connection to the database.
    */
   public static Connection getConnection() throws Exception {
      // Instantiate Driver
      Class.forName("com.mysql.jdbc.Driver");

      String dbUser = "admin";
      String dbPass = "Babnv115";
      String host = "pokebase.ckvcseuewxvg.us-west-1.rds.amazonaws.com";
      String dbName = "pokebase";

      String dbUrl = String.format("jdbc:mysql://%s/%s?autoReconnect=true", host, dbName);

      return DriverManager.getConnection(dbUrl, dbUser, dbPass);
   }

   /**
    * Close the connection.
    */
   public static void close(Connection conn) {
      try {
         if (conn != null) {
            conn.close();
            conn = null;
         }
      }
      catch (Exception ex) {
         System.err.println("Unable to close DBConnection.");
         System.exit(1);
      }
   }

   public static void main(String[] args) throws Exception {
      Connection conn = getConnection();
      Statement statement = null;
      ResultSet results = null;

      String query = "SELECT * FROM Teachers";

      try {
         // Get a statement from the connection
         statement = conn.createStatement();

         // Execute the query
         results = statement.executeQuery(query);

         while (results.next()) {
            String last = results.getString(1);
            String first = results.getString("first");
            int room = results.getInt(3);

            System.out.println(String.format("%s, %s -- %d", last, first, room));
         }
      }
      catch (SQLException sqlEx) {
         System.err.println("Error doing query: " + sqlEx);
         sqlEx.printStackTrace(System.err);
      }
      finally {
         try {
            if (results != null) {
               results.close();
               results = null;
            }

            if (statement != null) {
               statement.close();
               statement = null;
            }
         }
         catch (Exception ex) {
            System.err.println("Error closing query: " + ex);
            ex.printStackTrace(System.err);
         }

         close(conn);
      }
   }
}