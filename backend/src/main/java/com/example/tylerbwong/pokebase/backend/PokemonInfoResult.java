package com.example.tylerbwong.pokebase.backend;

import java.util.List;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class PokemonInfoResult extends QueryResult {
    private List<String> mMoves;
    private List<Integer> mValues; //id, height, weight, baseExp
    private List<String> mOtherValues; //name, region
    private int count;
    public PokemonInfoResult(List<Integer> values, List<String> otherValues, List<String> moves) {
        this.mMoves = moves;
        this.mValues = values;
        this.mOtherValues = otherValues;
        this.count = 0;
    }
    @Override
    public List<String> getStringInfo() {
        switch (count) {
            case 0:
                count++;
                return mOtherValues;
            case 1:
                count++;
                return mMoves;
            default:
        }
        return null;
    }

    @Override
    public List<Integer> getIntInfo() {
        return mValues;
    }

    @Override
    public String getType() {
        return QueryResult.SELECTED_POKEMON;
    }
}
