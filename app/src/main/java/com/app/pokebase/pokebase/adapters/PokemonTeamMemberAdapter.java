package com.app.pokebase.pokebase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.components.PokemonTeamMember;
import com.app.pokebase.pokebase.holders.PokemonTeamMemberViewHolder;
import com.app.pokebase.pokebase.holders.TeamCardViewHolder;

import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class PokemonTeamMemberAdapter extends RecyclerView.Adapter<PokemonTeamMemberViewHolder> {

   private ArrayList<PokemonTeamMember> mPokemon;

   public PokemonTeamMemberAdapter(ArrayList<PokemonTeamMember> pokemon) {
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
//      holder.mTitleLabel.setText(curPokemon.getName());
      //holder.mPokemonOne.setImageDrawable(c);
   }

   @Override
   public int getItemCount() {
      return mPokemon.size();
   }

   @Override
   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
   }
}
