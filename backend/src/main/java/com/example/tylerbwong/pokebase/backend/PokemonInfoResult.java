package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class PokemonInfoResult extends QueryResult {
    private String[] mMoves;
    private int[] mValues; //id, height, weight, baseExp
    private String[] mOtherValues; //name, region
    private int count;
    public PokemonInfoResult(int[] values, String[] otherValues, String[] moves) {
        this.mMoves = moves;
        this.mValues = values;
        this.mOtherValues = otherValues;
        this.count = 0;
    }
    @Override
    public String[] getStringInfo() {
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
    public int[] getIntInfo() {
        return mValues;
    }
}
