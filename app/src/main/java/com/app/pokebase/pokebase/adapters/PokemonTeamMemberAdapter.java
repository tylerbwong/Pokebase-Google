package com.app.pokebase.pokebase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.components.PokemonTeamMember;
import com.app.pokebase.pokebase.holders.PokemonTeamMemberViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class PokemonTeamMemberAdapter extends RecyclerView.Adapter<PokemonTeamMemberViewHolder> {

   private ArrayList<PokemonTeamMember> mPokemon;
   private Context mContext;

   public PokemonTeamMemberAdapter(Context context, ArrayList<PokemonTeamMember> pokemon) {
      this.mContext = context;
      this.mPokemon = pokemon;
   }

   public ArrayList<PokemonTeamMember> getPokemon() {
      return mPokemon;
   }

   @Override
   public PokemonTeamMemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
      View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.pokemon_team_member_card, parent, false);

      PokemonTeamMemberViewHolder vh = new PokemonTeamMemberViewHolder(v);
      return vh;
   }

   @Override
   public void onBindViewHolder(final PokemonTeamMemberViewHolder holder, int position) {
      PokemonTeamMember curPokemon = mPokemon.get(position);
      holder.mName.setText(curPokemon.mNickname);
      holder.mLevel.setText(curPokemon.mLevel);
      int imageResourceId = mContext.getResources().getIdentifier("sprites_" + curPokemon.mPokemonId, "drawable", mContext.getPackageName());
      holder.mPokemon.setImageResource(imageResourceId);
      List<String> moves = curPokemon.mMoves;
      String moveList = "";
      for (String move : moves) {
         moveList += move + "\n";
      }
      holder.mMoveset.setText(moveList);
   }

   @Override
   public int getItemCount() {
      return mPokemon.size();
   }
}
