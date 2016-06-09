package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class PokemonListResult extends QueryResult {
    public final int[] mIds;
    public final String[] mNames;
    private int mCount = 0;
    public PokemonListResult(int[] ids, String[] names) {
        this.mIds = ids;
        this.mNames = names;
    }

    @Override
    public String[] getStringInfo() {
        return mNames;
    }

    @Override
    public int[] getIntInfo() {
        return mIds;
    }
}
