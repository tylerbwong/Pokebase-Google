package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class CheckResult extends QueryResult{
    private boolean mResult;
    private int mCount;
    private String mType;
    public CheckResult(String type, boolean result) {
        this.mResult = result;
    }
    public CheckResult(String type, boolean result, int count) {
        this.mResult = result;
        this.mCount = count;
    }
    @Override
    public boolean getBoolean() {
        return mResult;
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public int getCount() {
        return mCount;
    }
}
