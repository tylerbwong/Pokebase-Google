package com.app.pokebase.pokebase.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.MainActivity;
import com.app.pokebase.pokebase.activities.TeamViewActivity;
import com.app.pokebase.pokebase.adapters.TeamAdapter;
import com.app.pokebase.pokebase.components.PokemonTeamItem;
import com.app.pokebase.pokebase.components.Team;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;
import com.github.fabtransitionactivity.SheetLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class TeamsFragment extends Fragment implements SheetLayout.OnFabAnimationEndListener, ApiCallback {

   private SheetLayout mSheetLayout;
   private FloatingActionButton mFab;
   private RecyclerView mTeamList;
   private LinearLayout mEmptyView;

   private TeamAdapter mTeamAdapter;
   private ArrayList<Team> mTeams;
   private MultiSelector mMultiSelector;
   private ModalMultiSelectorCallback mActionModeCallback;

   private final static int REQUEST_CODE = 1;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.teams_fragment, container, false);

      mSheetLayout = (SheetLayout) v.findViewById(R.id.bottom_sheet);
      mTeamList = (RecyclerView) v.findViewById(R.id.team_list);
      mFab = (FloatingActionButton) v.findViewById(R.id.fab);
      mEmptyView = (LinearLayout) v.findViewById(R.id.empty_layout);

      mFab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            onFabClick();
         }
      });

      mSheetLayout.setFab(mFab);
      mSheetLayout.setFabAnimationEndListener(this);

      final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      if (actionBar != null) {
         actionBar.setTitle(R.string.teams);
      }

      mMultiSelector = ((MainActivity) getActivity()).getMultiSelector();
      mActionModeCallback = ((MainActivity) getActivity()).getActionModeCallback();

      mTeams = new ArrayList<>();

      LinearLayoutManager llm = new LinearLayoutManager(getContext());
      llm.setOrientation(LinearLayoutManager.VERTICAL);
      mTeamList.setLayoutManager(llm);
      mTeamAdapter = new TeamAdapter(getContext(), mMultiSelector, mActionModeCallback, mTeams, null);
      mTeamList.setAdapter(mTeamAdapter);

      if (mTeams.isEmpty()) {
         mTeamList.setVisibility(View.GONE);
         mEmptyView.setVisibility(View.VISIBLE);
      }
      else {
         mTeamList.setVisibility(View.VISIBLE);
         mEmptyView.setVisibility(View.GONE);
      }

      loadTeams();
      return v;
   }

   public void onFabClick() {
      mSheetLayout.expandFab();
   }

   @Override
   public void onFabAnimationEnd() {
      Intent intent = new Intent(getContext(), TeamViewActivity.class);
      Bundle extras = new Bundle();
      extras.putInt(TeamViewActivity.TEAM_ID_KEY, 0);
      extras.putBoolean(TeamViewActivity.UPDATE_KEY, false);
      intent.putExtras(extras);
      startActivityForResult(intent, REQUEST_CODE);
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if(requestCode == REQUEST_CODE){
         mSheetLayout.contractFab();
      }
   }

   public void loadTeams() {
      SharedPreferences pref = getActivity().getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
      String[] loadTeams = new String[2];
      loadTeams[0] = QueryTask.ALL_TEAMS;
      loadTeams[1] = pref.getString("username", "");
      new QueryTask().execute(new Pair<Context, String[]>(getActivity(), loadTeams));
   }

   @Override
   public void onApiCallback(QueryResult result) {
      if (result.getType().equals(QueryTask.ALL_TEAMS)) {
         List<String> names = result.getStringInfo();
         List<String> descriptions = result.getMoreStringInfo();
         List<List<Integer>> pokemonIds = result.getListOfLists();
         List<Integer> teamIds = result.getIntInfo();

         mTeams = new ArrayList<>();
         if (teamIds != null) {
            for (int i = 0; i < teamIds.size(); i++) {
               List<PokemonTeamItem> members = new ArrayList<>();
               if (pokemonIds != null && pokemonIds.size()  > 0) {
                  List<Integer> teamPokemon = pokemonIds.remove(0);

                  for (Integer pokemon : teamPokemon) {
                     if (pokemon != -1) {
                        members.add(new PokemonTeamItem(pokemon));
                     }
                  }
               }
               Team newTeam = new Team(teamIds.get(i), names.get(i), descriptions.get(i), members);
               mTeams.add(newTeam);
            }
         }

         mTeamAdapter = new TeamAdapter(getContext(), mMultiSelector, mActionModeCallback, mTeams, teamIds);
         mTeamList.setAdapter(mTeamAdapter);

         if (mTeams.isEmpty()) {
            mTeamList.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
         }
         else {
            mTeamList.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
         }
      }
   }
}
