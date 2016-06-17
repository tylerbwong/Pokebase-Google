package com.app.pokebase.pokebase.components;

import java.util.List;

/**
 * @author Tyler Wong
 */
public class PokemonTeamMember {
    public final int mPokemonId;
    public final int mLevel;
    public final String mNickname;
    public final List<String> mMoves;

    public PokemonTeamMember(int id, int level, String nickname, List<String> moves) {
        this.mPokemonId = id;
        this.mLevel = level;
        this.mNickname = nickname;
        this.mMoves = moves;
    }
}
