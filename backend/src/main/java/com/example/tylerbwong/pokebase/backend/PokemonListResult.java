package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class PokemonListResult extends QueryResult {
    public final int[] mIds;
    public final String[] mNames;
    private String mType;
    public PokemonListResult(String type, int[] ids, String[] names) {
        this.mIds = ids;
        this.mNames = names;
        this.mType = type;
    }

    @Override
    public String[] getStringInfo() {
        return mNames;
    }

    @Override
    public int[] getIntInfo() {
        return mIds;
    }

    @Override
    public String getType() {
        return mType;
    }
}
