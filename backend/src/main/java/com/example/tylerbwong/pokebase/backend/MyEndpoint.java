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
import java.sql.Statement;
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
      ResultSet results;
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
         Statement statement = connection.createStatement();
         results = statement.executeQuery("SELECT P.id, P.name FROM Pokemon P WHERE P.name = 'Eevee'");
         ids[2] = results.getInt("id");
         names[2] = results.getString("name");
         connection.close();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return new PokemonListResult(ids, names);
   }

   private void instantiateDriver() {
      if (System.getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
         url = System.getProperty(urlCloudProperty);

         try {
            Class.forName("com.mysql.jdbc.GoogleDriver");
         }
         catch (ClassNotFoundException e) {

         }
      }
      else {
         url = System.getProperty(urlLocalProperty);

         try {
            Class.forName("com.mysql.jdbc.Driver");
         }
         catch (ClassNotFoundException e) {

         }
      }
   }
}
