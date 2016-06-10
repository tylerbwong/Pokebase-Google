/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.tylerbwong.pokebase.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Named;

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

    @ApiMethod(name = "queryByType")
    public QueryResult queryByType(@Named("type") String type) {
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
            if (resultSet.next()) {
                ids[2] = resultSet.getInt("id");
                names[2] = resultSet.getString("name");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            names[2] = e.toString();
            names[1] = e.getLocalizedMessage();
        }
        return new PokemonListResult(ids, names);
    }

    @ApiMethod(name = "queryByRegion")
    public QueryResult queryByRegion(@Named("region") String region) {
        //SELECT P.id, P.name
        //FROM Pokemon P
        //JOIN Regions R ON R.id = P.region
        //WHERE R.name = (?)

        //do da query
        return new PokemonListResult(null, null);
    }

    @ApiMethod(name = "queryByTypeAndRegion")
    public QueryResult queryByTypeAndRegion(@Named("type") String type, @Named("region") String region) {
        //SELECT P.id, P.name
        //FROM Pokemon P
        //JOIN PokemonTypes T ON P.id = T.pokemon
        //JOIN Types Y ON Y.id = T.type
        //JOIN Regions R ON R.id = P.region
        //WHERE Y.name = (?) AND R.name = (?)

        //do da query
        return new PokemonListResult(null, null);
    }

    @ApiMethod(name = "queryByName")
    public QueryResult queryByName(@Named("name") String name) {
        //SELECT P.id, P.name
        //FROM Pokemon P
        //WHERE P.name = (?)

        //do da query
        return new PokemonListResult(null, null);
    }

    @ApiMethod(name = "queryAll")
    public QueryResult queryAll() {
        //SELECT P.id, P.name
        //FROM Pokemon P

        //do da query
        return new PokemonListResult(null, null);
    }

    @ApiMethod(name = "querySelected")
    public QueryResult querySelected(@Named("id") int id) {
        //SELECT P.id, P.name, P.height, P.weight, P.baseExp, R.name
        //FROM Pokemon P
        //JOIN Region R ON P.region = R.id
        //WHERE P.id = (?)


        //query for types

        //query for moves

        return new PokemonInfoResult(null, null, null);
    }

    @ApiMethod(name = "queryEvolutions")
    public QueryResult queryEvolutions(@Named("id") int id) {
        //SELECT P.id, P.name
        //FROM Pokemon P
        //JOIN PokemonEvolutions E ON E.id = P.id
        //WHERE E.evolves_from = (?)

        return new PokemonListResult(null, null);
    }

    @ApiMethod(name = "queryCheckUser")
    public QueryResult queryCheckUser(@Named("name") String name) {
        //SELECT COUNT(*)
        //FROM Users U
        //WHERE U.name = (?)

        return new CheckResult(false);
    }

    @ApiMethod(name = "newUser")
    public QueryResult newUser(@Named("name") String name) {
        //INSERT INTO Users
        //VALUES (?) --ensure autoincrement

        return new CheckResult(false);
    }

    @ApiMethod(name = "newTeam")
    public QueryResult newTeam(@Named("userId") int userId, @Named("name") String name,
                               @Named("description") String description) {

        //INSERT INTO Teams
        //VALUES (?), (?) --ensure autoincrement

        //INSERT INTO UserTeams
        //VALUES (?), (?)

        return new CheckResult(true);
    }

    @ApiMethod(name = "newPokemonTeam")
    public QueryResult newPokemonTeam(@Named("userId") int userId, @Named("teamId") int teamId,
                                      @Named("pokemonId") int pokemonId) {

        //QUERY THE NAME OF THE POKEMON

        //INSERT INTO TeamPokemon
        //VALUES (teamId?), (pokemonId?), (POKEMON NAME), (1), (null), (null), (null), (null)

        return new CheckResult(true);
    }

    @ApiMethod(name = "updateTeam")
    public QueryResult updateTeam(@Named("teamId") int teamId, @Named("name") String name,
                                  @Named("description") String description) {

        //QUERY THE NAME OF THE POKEMON

        //INSERT INTO TeamPokemon
        //VALUES (teamId?), (pokemonId?), (POKEMON NAME), (1), (null), (null), (null), (null)

        return new CheckResult(true);
    }

    @ApiMethod(name = "queryAllTeams")
    public QueryResult queryAllTeams(@Named("userId") int userId) {
        //QUERY ALL TEAMS

        //SELECT *
        //FROM Users U
        //JOIN UserTeams UT ON U.id = UT.userId
        //JOIN Teams T ON UT.teamId = T.id
        //JOIN TeamPokemon TP ON TP.teamId = T.id
        //JOIN Pokemon P ON P.id = TP.pokemonId
        //WHERE U.id = (userId ?)

        return null; //new TeamListResult();
    }

    @ApiMethod(name = "deleteTeam")
    public QueryResult deleteTeam(@Named("userId") int userId, @Named("teamId") int teamId) {
        //DELETE SPECIFIC TEAM

        //DELETE FROM TeamPokemon TP
        //JOIN UserTeams UT ON UT.teamId = UT.teamId
        //JOIN Teams T ON T.id = UT.teamId
        //JOIN Users U ON U.id = UT.userId
        //WHERE U.id = (userId ?)
        //AND T.id = (teamId ?)

        return new CheckResult(true);
    }

    @ApiMethod(name = "deleteTeamPokemon")
    public QueryResult deleteTeamPokemon(@Named("userId") int userId, @Named("teamId") int teamId,
                                         @Named("pokemonId") int pokemonId) {
        // DELETE POKEMON FROM TEAM

        //DELETE FROM TeamPokemon TP
        //JOIN UserTeams UT ON UT.teamId = UT.teamId
        //JOIN Teams T ON T.id = UT.teamId
        //JOIN Users U ON U.id = UT.userId
        //WHERE U.id = (userId ?)
        //AND T.id = (teamId ?)
        //AND TP.pokemonId = (pokemonId ?)

        return new CheckResult(true);
    }

    @ApiMethod(name = "updateTeamPokemon")
    public QueryResult updateTeamPokemon(@Named("userId") int userId, @Named("teamId") int teamId,
                                         @Named("pokemonId") int pokemonId,
                                         @Named("nickname") String nickname, @Named("level") int level,
                                         @Named("moveOne") String moveOne, @Named("moveTwo") String moveTwo,
                                         @Named("moveThree") String moveThree, @Named("moveFour") String moveFour) {
        // UPDATE POKEMON ON TEAM

        //UPDATE TeamPokemon TP
        //JOIN UserTeams UT ON UT.teamId = UT.teamId
        //JOIN Teams T ON T.id = UT.teamId
        //JOIN Users U ON U.id = UT.userId
        //SET TP.nickname = (nickname ?), TP.level = (level ?),
        //TP.moveOne = (moveOne ?), TP.moveTwo = (moveTwo ?),
        //TP.moveThree = (moveThree ?), TP.moveFour = (moveFour ?)
        //WHERE U.id = userId
        //AND T.id = teamId
        //AND TP.pokemonId = pokemonId


        return new CheckResult(true);
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
