package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public abstract class QueryResult {
    public static final String ALL_POKEMON = "all";
    public static final String POKEMON_BY_TYPE = "by_type";
    public static final String POKEMON_BY_REGION = "by_region";
    public static final String POKEMON_BY_TYPE_AND_REGION = "by_type_and_region";
    public static final String SELECTED_POKEMON = "selected";
    public static final String SELECTED_POKEMON_EVOLUTIONS = "selected_evolutions";
    public static final String POKEMON_BY_NAME = "by_name";
    public static final String CHECK_USERNAME = "check_username";
    public static final String NEW_USER = "new_user";
    public static final String NEW_TEAM = "new_team";
    public static final String NEW_POKEMON_ON_TEAM = "new_team_pokemon";
    public static final String UPDATE_TEAM = "update_team";
    public static final String UPDATE_POKEMON = "update_pokemon";
    public static final String DELETE_POKEMON = "delete_pokemon";
    public static final String DELETE_TEAM = "delete_team";
    public String[] getStringInfo() {
        return new String[0];
    }
    public int[] getIntInfo() {
        return new int[0];
    }
    public boolean getBoolean() {
        return false;
    }
    public String getType() {
        return "";
    }
}
