package com.example.tylerbwong.pokebase.backend;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class CheckResult extends QueryResult{
    private boolean mResult;
    private String mType;
    public CheckResult(String type, boolean result) {
        this.mResult = result;
    }
    @Override
    public boolean getBoolean() {
        return mResult;
    }

    @Override
    public String getType() {
        return mType;
    }
}
