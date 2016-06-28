package com.app.pokebase.pokebase.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Pair;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.pokebase.pokebase.components.PokemonListItem;
import com.app.pokebase.pokebase.components.PokemonProfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
   private SQLiteDatabase mDatabase;
   private Context mContext;

   private final static String DB_PATH = "/data/data/com.app.pokebase.pokebase/databases/";
   private final static String DB_NAME = "Pokebase.db";
   private final static int DB_SIZE = 6144;
   private final static String TEAM_POKEMON_TABLE = "TeamPokemon";
   private final static String TEAMS_TABLE = "Teams";
   private final static String DESCRIPTION_COL = "description";
   private final static String TEAM_ID_COL = "teamId";
   private final static String POKEMON_ID_COL = "pokemonId";
   private final static String NICKNAME_COL = "nickname";
   private final static String LEVEL_COL = "level";
   private final static String MOVE_ONE_COL = "moveOne";
   private final static String MOVE_TWO_COL = "moveTwo";
   private final static String MOVE_THREE_COL = "moveThree";
   private final static String MOVE_FOUR_COL = "moveFour";
   private final static String ROW_ID_COL = "_id";
   private final static String GENDER_COL = "gender";
   private final static String ID_COL = "id";
   private final static String NAME_COL = "name";
   private final static String HEIGHT_COL = "height";
   private final static String WEIGHT_COL = "weight";
   private final static String BASEEXP_COL = "baseExp";
   private final static String REGION_COL = "region";

   private final static String ALL =
         "SELECT P.id, P.name " +
               "FROM Pokemon P";
   private final static String ALL_TYPES =
         "SELECT T.name FROM Types T";
   private final static String ALL_REGIONS =
         "SELECT R.name FROM Regions R";
   private final static String TYPE_QUERY =
         "SELECT P.id, P.name " +
               "FROM Pokemon P " +
               "JOIN PokemonTypes T ON P.id = T.pokemonId " +
               "JOIN Types Y ON Y.id = T.typeId " +
               "WHERE Y.name = ?";
   private final static String REGION_QUERY =
         "SELECT P.id, P.name " +
               "FROM Pokemon P " +
               "JOIN Regions R ON R.id = P.region " +
               "WHERE R.name = ?";
   private final static String TYPE_REGION_QUERY =
         "SELECT P.id, P.name " +
               "FROM Pokemon P " +
               "JOIN PokemonTypes T ON P.id = T.pokemonId " +
               "JOIN Types Y ON Y.id = T.typeId " +
               "JOIN Regions R ON R.id = P.region " +
               "WHERE Y.name = ? " +
               "AND R.name = ?";
   private final static String SELECTED_INFO_QUERY =
         "SELECT P.id, P.name, P.height, P.weight, P.baseExp, R.name AS region " +
               "FROM Pokemon P " +
               "JOIN Regions R ON P.region = R.id " +
               "WHERE P.id = ?";
   private final static String SELECTED_TYPES_QUERY =
         "SELECT T.name " +
               "FROM Pokemon P " +
               "JOIN PokemonTypes Y ON Y.pokemonId = P.id " +
               "JOIN Types T ON Y.typeId = T.id " +
               "WHERE P.id = ?";
   private final static String SELECTED_MOVES_QUERY =
         "SELECT DISTINCT M.name " +
               "FROM Pokemon P " +
               "JOIN PokemonMoves O ON P.id = O.pokemonId " +
               "JOIN Moves M ON O.moveId = M.id " +
               "WHERE P.id = ?";
   private final static String SELECTED_EVOLUTIONS_QUERY =
         "SELECT K.id, K.name " +
               "FROM Pokemon P " +
               "JOIN PokemonEvolutions E ON E.evolvesFrom = P.id " +
               "JOIN Pokemon K ON E.id = K.id " +
               "WHERE P.id = ?";
   private final static String ALL_TEAM_INFO =
         "SELECT T.id, T.name, T.description " +
               "FROM Teams T " +
               "ORDER BY T.id";
   private final static String ALL_TEAM_POKEMON =
         "SELECT T.id, K.pokemonId " +
               "FROM Teams T " +
               "JOIN TeamPokemon K ON K.teamId = T.id " +
               "ORDER BY T.id";
   private final static String POKEMON_BY_TEAM =
         "SELECT P.id, P.pokemonId, P.nickname, P.level, P.moveOne, P.moveTwo, P.moveThree, P.moveFour " +
               "FROM TeamPokemon P " +
               "WHERE P.teamId = ?";
   private final static String NEW_TEAM =
         "INSERT INTO Teams Values (0, ?, ?)";
   private final static String UPDATE_TEAM =
         "UPDATE Teams SET name = ?, description = ? WHERE id = ?";
   private final static String TEAM_NAMES = "SELECT T._id, T.name FROM Teams T";
   private final static String MAX_TEAM_ID =
         "SELECT MAX(T.id) AS maxId FROM Teams T";
   private final static String NEW_USER_TEAM =
         "INSERT INTO UserTeams VALUES (?, ?)";
   private final static String NEW_POKEMON_TEAM =
         "INSERT INTO TeamPokemon Values (0, ?, ?, ?, ?, ?, ?, ?, ?)";
   private final static String POKEMON_MOVES =
         "SELECT M.name FROM Moves M WHERE M.id = ?";
   private final static String UPDATE_POKEMON =
         "UPDATE TeamPokemon SET nickname = ?, level = ?, moveOne = ?, moveTwo = ?, " +
               "moveThree = ?, moveFour = ? WHERE id = ?";
   private final static String QUERY_MOVE_ID =
         "SELECT M.id FROM Moves M WHERE M.name = ?";

   public DatabaseOpenHelper(Context context) {
      super(context, DB_NAME, null, 1);
      this.mContext = context;
   }

   public void createDatabase() {
      boolean databaseExists = checkDatabase();

      if (!databaseExists) {
         getReadableDatabase();

         try {
            copyDatabase();
         }
         catch(IOException e) {

         }
      }
   }

   private boolean checkDatabase() {
      SQLiteDatabase checkDatabase = null;

      try {
         String path = DB_PATH + DB_NAME;
         checkDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
      }
      catch (SQLiteException e) {

      }

      if (checkDatabase != null) {
         checkDatabase.close();
      }

      return checkDatabase != null;
   }

   private void copyDatabase() throws IOException {
      InputStream inputStream = mContext.getAssets().open(DB_NAME);
      String outFileName = DB_PATH + DB_NAME;

      OutputStream outputStream = new FileOutputStream(outFileName);

      byte[] buffer = new byte[DB_SIZE];
      int length;

      while ((length = inputStream.read(buffer)) > 0) {
         outputStream.write(buffer, 0, length);
      }

      outputStream.flush();
      outputStream.close();
      inputStream.close();
   }

   public void openDatabase() throws SQLException {
      String path = DB_PATH + DB_NAME;
      mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
   }

   @Override
   public synchronized void close() {
      if (mDatabase != null) {
         mDatabase.close();
      }
      super.close();
   }

   @Override
   public void onCreate(SQLiteDatabase database) {

   }

   @Override
   public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

   }

   public List<String> queryAllTypes() {
      List<String> typeList = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(ALL_TYPES, null);
      cursor.moveToFirst();

      while(!cursor.isAfterLast()) {
         typeList.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
         cursor.moveToNext();
      }
      cursor.close();
      return typeList;
   }

   public List<String> queryAllRegions() {
      List<String> regionList = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(ALL_REGIONS, null);
      cursor.moveToFirst();

      while(!cursor.isAfterLast()) {
         regionList.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
         cursor.moveToNext();
      }
      cursor.close();
      return regionList;
   }

   public List<PokemonListItem> queryByType(String type) {
      List<PokemonListItem> typePokemon = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(TYPE_QUERY, new String[] {type});
      cursor.moveToFirst();

      PokemonListItem tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonListItem(cursor.getColumnIndex(ID_COL),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         typePokemon.add(tempPokemon);
         cursor.moveToNext();
      }
      cursor.close();
      return typePokemon;
   }

   public List<PokemonListItem> queryByRegion(String region) {
      List<PokemonListItem> regionPokemon = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(REGION_QUERY, new String[] {region});
      cursor.moveToFirst();

      PokemonListItem tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonListItem(cursor.getInt(cursor.getColumnIndex(ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         regionPokemon.add(tempPokemon);
         cursor.moveToNext();
      }
      cursor.close();
      return regionPokemon;
   }

   public List<PokemonListItem> queryByTypeAndRegion(String type, String region) {
      List<PokemonListItem> typeRegionPokemon = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(TYPE_REGION_QUERY, new String[] {type, region});
      cursor.moveToFirst();

      PokemonListItem tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonListItem(cursor.getInt(cursor.getColumnIndex(ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         typeRegionPokemon.add(tempPokemon);
         cursor.moveToNext();
      }
      cursor.close();
      return typeRegionPokemon;
   }

   public List<PokemonListItem> queryAll() {
      List<PokemonListItem> pokemon = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(ALL, null);
      cursor.moveToFirst();

      PokemonListItem tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonListItem(cursor.getInt(cursor.getColumnIndex(ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         pokemon.add(tempPokemon);
         cursor.moveToNext();
      }
      cursor.close();
      return pokemon;
   }

   public List<String> querySelectedPokemonTypes(int id) {
      List<String> pokemonTypes = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(SELECTED_TYPES_QUERY, new String[] {String.valueOf(id)});
      cursor.moveToFirst();

      while(!cursor.isAfterLast()) {
         pokemonTypes.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
         cursor.moveToNext();
      }
      cursor.close();
      return pokemonTypes;
   }

   public List<String> querySelectedPokemonMoves(int id) {
      List<String> pokemonMoves = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(SELECTED_MOVES_QUERY, new String[] {String.valueOf(id)});
      cursor.moveToFirst();

      while(!cursor.isAfterLast()) {
         pokemonMoves.add(cursor.getString(cursor.getColumnIndex(NAME_COL)));
         cursor.moveToNext();
      }
      cursor.close();
      return pokemonMoves;
   }

   public List<PokemonProfile> querySelectedPokemonProfile(int id) {
      List<PokemonProfile> pokemon = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(SELECTED_INFO_QUERY, new String[] {String.valueOf(id)});
      cursor.moveToFirst();

      PokemonProfile tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonProfile(cursor.getInt(cursor.getColumnIndex(ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)),
               cursor.getInt(cursor.getColumnIndex(HEIGHT_COL)),
               cursor.getInt(cursor.getColumnIndex(WEIGHT_COL)),
               cursor.getInt(cursor.getColumnIndex(BASEEXP_COL)),
               cursor.getString(cursor.getColumnIndex(REGION_COL)),
               querySelectedPokemonTypes(id), querySelectedPokemonMoves(id));
         cursor.moveToNext();
      }
      cursor.close();
      return pokemon;
   }

   public List<PokemonListItem> queryPokemonEvolutions(int id) {
      List<PokemonListItem> pokemonEvolutions = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(SELECTED_EVOLUTIONS_QUERY, new String[] {String.valueOf(id)});
      cursor.moveToFirst();

      PokemonListItem tempPokemon;

      while(!cursor.isAfterLast()) {
         tempPokemon = new PokemonListItem(cursor.getInt(cursor.getColumnIndex(ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         pokemonEvolutions.add(tempPokemon);
         cursor.moveToNext();
      }
      cursor.close();
      return pokemonEvolutions;
   }

   public boolean insertTeam(String name, String description) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(NAME_COL, name);
      contentValues.put(DESCRIPTION_COL, description);
      mDatabase.insert(TEAMS_TABLE, null, contentValues);
      return true;
   }

   public boolean insertTeamPokemon(int teamId, int pokemonId, String nickname, int level,
                                    int moveOne, int moveTwo, int moveThree, int moveFour) {
      ContentValues contentValues = new ContentValues();
      contentValues.put(TEAM_ID_COL, teamId);
      contentValues.put(POKEMON_ID_COL, pokemonId);
      contentValues.put(NICKNAME_COL, nickname);
      contentValues.put(LEVEL_COL, level);
      contentValues.put(MOVE_ONE_COL, moveOne);
      contentValues.put(MOVE_TWO_COL, moveTwo);
      contentValues.put(MOVE_THREE_COL, moveThree);
      contentValues.put(MOVE_FOUR_COL, moveFour);
      mDatabase.insert(TEAM_POKEMON_TABLE, null, contentValues);
      return true;
   }

   public boolean updateTeam(int teamId, String name, String description) {
      mDatabase.rawQuery(UPDATE_TEAM, new String[] {name, description, String.valueOf(teamId)});
      return true;
   }

   public List<Pair<Integer, String>> queryTeamIdsAndNames() {
      List<Pair<Integer, String>> teamNames = new ArrayList<>();
      Cursor cursor = mDatabase.rawQuery(TEAM_NAMES, null);
      cursor.moveToFirst();

      Pair<Integer, String> tempPair;

      while(!cursor.isAfterLast()) {
         tempPair = new Pair<>(cursor.getInt(cursor.getColumnIndex(ROW_ID_COL)),
               cursor.getString(cursor.getColumnIndex(NAME_COL)));
         teamNames.add(tempPair);
         cursor.moveToNext();
      }
      cursor.close();
      return teamNames;
   }

   // TODO queryAllTeams
   // TODO queryTeamId
   // TODO deleteTeam
   // TODO deleteTeamPokemon
   // TODO updateTeamPokemon
}
