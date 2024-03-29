package com.app.pokebase.pokebase.querytasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.example.tylerbwong.pokebase.backend.myApi.MyApi;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class QueryTask extends AsyncTask<Pair<Context, String[]>, Void, QueryResult> {
    public static final String ALL_POKEMON = "all";
    public static final String POKEMON_BY_TYPE = "by_type";
    public static final String POKEMON_BY_REGION = "by_region";
    public static final String POKEMON_BY_TYPE_AND_REGION = "by_type_and_region";
    public static final String SELECTED_POKEMON = "selected";
    public static final String SELECTED_POKEMON_EVOLUTIONS = "selected_evolutions";
    public static final String CHECK_USERNAME = "check_username";
    public static final String NEW_USER = "new_user";
    public static final String NEW_TEAM = "new_team";
    public static final String NEW_POKEMON_ON_TEAM = "new_team_pokemon";
    public static final String UPDATE_TEAM = "update_team";
    public static final String UPDATE_POKEMON = "update_pokemon";
    public static final String DELETE_POKEMON = "delete_pokemon";
    public static final String DELETE_TEAM = "delete_team";
    public static final String ALL_TYPES_REGIONS = "all_types_and_regions";
    public static final String ALL_TEAMS = "all_teams";
    public static final String TEAM_BY_ID = "team_by_id";
    public static final String TEAM_NAMES = "team_names";
    public static final String POKEMON_MOVES = "pokemon_moves";

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected QueryResult doInBackground(Pair<Context, String[]>... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://pokebase-1335.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String[] queryInfo = params[0].second;
        List<String> queryList = Arrays.asList(queryInfo);
        //the first item is the command
        String command = queryList.get(0);
        try {
            switch (command) {
                case ALL_POKEMON:
                    return myApiService.queryAll().execute();
                case POKEMON_BY_TYPE:
                    return myApiService.queryByType(queryList.get(1)).execute();
                case POKEMON_BY_REGION:
                    return myApiService.queryByRegion(queryList.get(1)).execute();
                case POKEMON_BY_TYPE_AND_REGION:
                    return myApiService.queryByTypeAndRegion(queryList.get(1), queryList.get(2)).execute();
                case SELECTED_POKEMON:
                    return myApiService.querySelected(Integer.valueOf(queryList.get(1))).execute();
                case SELECTED_POKEMON_EVOLUTIONS:
                    return myApiService.queryEvolutions(Integer.valueOf(queryList.get(1))).execute();
                case CHECK_USERNAME:
                    return myApiService.queryCheckUser(queryList.get(1)).execute();
                case NEW_USER:
                    return myApiService.newUser(queryList.get(1), queryList.get(2)).execute();
                case NEW_TEAM:
                    return myApiService.newTeam(queryList.get(1), queryList.get(2), queryList.get(3)).execute();
                case NEW_POKEMON_ON_TEAM:
                    return myApiService.newPokemonTeam(Integer.valueOf(queryList.get(1)),
                          Integer.valueOf(queryList.get(2)), queryList.get(3),
                          Integer.valueOf(queryList.get(4)), Integer.valueOf(queryList.get(5)), Integer.valueOf(queryList.get(6)),
                          Integer.valueOf(queryList.get(7)), Integer.valueOf(queryList.get(8))).execute();
                case UPDATE_TEAM:
                    return myApiService.updateTeam(Integer.valueOf(queryList.get(1)), queryList.get(2), queryList.get(3)).execute();
                case UPDATE_POKEMON:
                    return myApiService.updateTeamPokemon(Integer.valueOf(queryList.get(1)), queryList.get(2), Integer.valueOf(queryList.get(3)),
                          queryList.get(4), queryList.get(5), queryList.get(6), queryList.get(7)).execute();
                case DELETE_POKEMON:
                case DELETE_TEAM:
                    return null;
                case TEAM_NAMES:
                    return myApiService.queryTeamNames(queryList.get(1)).execute();
                case ALL_TYPES_REGIONS:
                    return myApiService.queryAllTypesRegions().execute();
                case ALL_TEAMS:
                    return myApiService.queryAllTeams(queryList.get(1)).execute();
                case TEAM_BY_ID:
                    return myApiService.queryTeamId(Integer.valueOf(queryList.get(1))).execute();
                default:
                    return null;
            }
        }
        catch (IOException e) {
            //return e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(QueryResult queryResult) {
        if (context instanceof ApiCallback)
        {
            ApiCallback callback = (ApiCallback) context;
            callback.onApiCallback(queryResult);
        }
    }
}
