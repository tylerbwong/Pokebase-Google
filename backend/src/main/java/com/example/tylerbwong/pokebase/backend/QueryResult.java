package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public abstract class QueryResult {
    public String[] getStringInfo() {
        return new String[0];
    }
    public int[] getIntInfo() {
        return new int[0];
    }
    public boolean getBoolean() {
        return false;
    }
}
