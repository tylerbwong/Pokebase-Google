package com.example.tylerbwong.pokebase.backend;

import java.util.List;

/**
 * Created by brittanyberlanga on 6/10/16.
 */
public class TypeRegionResult extends QueryResult {
    private List<String> mTypes;
    private List<String> mRegions;
    private int count;

    public TypeRegionResult(List<String> types, List<String> regions) {
        this.mTypes = types;
        this.mRegions = regions;
        count = 0;
    }
    @Override
    public List<String> getStringInfo() {
        switch (count) {
            case 0:
                count++;
                return mTypes;
            case 1:
                count++;
                return mRegions;
            default:
                break;
        }
        return null;
    }

    @Override
    public String getType() {
        return QueryResult.ALL_TYPES_REGIONS;
    }
}
