package com.app.pokebase.pokebase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.components.Team;
import com.app.pokebase.pokebase.holders.TeamCardViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamCardViewHolder> {

   private List<Team> mTeams;
   private Context mContext;

   public TeamAdapter(Context context, ArrayList<Team> teams) {
      this.mContext = context;
      this.mTeams = teams;
   }

   public List<Team> getTeams() {
      return mTeams;
   }

   @Override
   public TeamCardViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
      View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.team_card, parent, false);

      TeamCardViewHolder vh = new TeamCardViewHolder(v);
      return vh;
   }

   @Override
   public void onBindViewHolder(final TeamCardViewHolder holder, int position) {
      Team curTeam = mTeams.get(position);
      holder.mTitleLabel.setText(curTeam.mName);
      holder.mDescription.setText(curTeam.mDescription);
      int teamSize = curTeam.mTeam.size();

      for (int i = 0; i < teamSize; i++) {
         holder.pokemonList.get(i).setImageResource(mContext.getResources()
                 .getIdentifier("icon_" + curTeam.mTeam.get(i).mPokemonId,
                         "drawable", mContext.getPackageName()));
      }
   }

   @Override
   public int getItemCount() {
      return mTeams.size();
   }

   @Override
   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
   }
}
