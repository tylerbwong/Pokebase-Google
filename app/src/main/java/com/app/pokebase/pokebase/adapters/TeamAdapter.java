package com.app.pokebase.pokebase.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.holders.TeamCardViewHolder;
import com.app.pokebase.pokebase.utilities.Team;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamCardViewHolder> {

   private ArrayList<Team> mTeams;

   public TeamAdapter(ArrayList<Team> teams) {
      this.mTeams = teams;
   }

   public ArrayList<Team> getTeams() {
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

   }

   @Override
   public int getItemCount() {
      return 0;
   }

   @Override
   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
      super.onAttachedToRecyclerView(recyclerView);
   }
}
