/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.tylerbwong.pokebase.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * An endpoint class we are exposing
 */
@Api(
      name = "myApi",
      version = "v1",
      namespace = @ApiNamespace(
            ownerDomain = "backend.pokebase.tylerbwong.example.com",
            ownerName = "backend.pokebase.tylerbwong.example.com",
            packagePath = ""
      )
)
public class MyEndpoint {
   private String url;
   private final static String query = "SELECT P.id, P.name FROM Pokemon P WHERE P.name = ?";
   private final static String urlLocalProperty = "ae-cloudsql.local-database-url";
   private final static String urlCloudProperty = "ae-cloudsql.cloudsql-database-url";

   /**
    * A simple endpoint method that takes a name and says Hi back
    */
   @ApiMethod(name = "sayHi")
   public MyBean sayHi(@Named("name") String name) {
      MyBean response = new MyBean();
      response.setData("Hi " + name);
      return response;
   }

   @ApiMethod(name = "queryByType")
   public QueryResult queryByType(@Named("info") String[] info) {
      instantiateDriver();

      //do da query
      int[] ids = new int[3];
      ids[0] = 1;
      ids[1] = 25;
      String[] names = new String[3];
      names[0] = "Bulbasaur";
      names[1] = "Pikachu";
      try {
         Connection connection = DriverManager.getConnection(url);
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setString(1, "Eevee");
         ResultSet resultSet = preparedStatement.executeQuery();
         if  (resultSet.next()) {
            ids[2] = resultSet.getInt("id");
            names[2] = resultSet.getString("name");
         }
         connection.close();
      }
      catch (Exception e) {
         e.printStackTrace();
         names[2] = e.toString();
         names[1] = e.getLocalizedMessage();
      }
      return new PokemonListResult(ids, names);
   }

   private void instantiateDriver() {
      if (System
            .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
         // Check the System properties to determine if we are running on appengine or not
         // Google App Engine sets a few system properties that will reliably be present on a remote
         // instance.
         url = System.getProperty("ae-cloudsql.cloudsql-database-url");
         try {
            // Load the class that provides the new "jdbc:google:mysql://" prefix.
            Class.forName("com.mysql.jdbc.GoogleDriver");
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
         }
      } else {
         // Set the url with the local MySQL database connection url when running locally
         url = System.getProperty("ae-cloudsql.local-database-url");
      }
   }
}
