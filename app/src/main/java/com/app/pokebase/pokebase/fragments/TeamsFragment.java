package com.app.pokebase.pokebase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.NewTeamActivity;
import com.app.pokebase.pokebase.adapters.TeamAdapter;
import com.app.pokebase.pokebase.utilities.Team;
import com.github.fabtransitionactivity.SheetLayout;

import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class TeamsFragment extends Fragment implements SheetLayout.OnFabAnimationEndListener {

   private SheetLayout mSheetLayout;
   private FloatingActionButton mFab;
   private RecyclerView mTeamList;
   private LinearLayout mEmptyView;

   private TeamAdapter mTeamAdapter;
   private ArrayList<Team> mTeams;

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

      ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      if (actionBar != null) {
         actionBar.setTitle(R.string.teams);
      }

      mTeams = new ArrayList<>();

      LinearLayoutManager llm = new LinearLayoutManager(getContext());
      llm.setOrientation(LinearLayoutManager.VERTICAL);
      mTeamList.setLayoutManager(llm);
      mTeamAdapter = new TeamAdapter(mTeams);
      mTeamList.setAdapter(mTeamAdapter);

      if (mTeams.isEmpty()) {
         mTeamList.setVisibility(View.GONE);
         mEmptyView.setVisibility(View.VISIBLE);
      }
      else {
         mTeamList.setVisibility(View.VISIBLE);
         mEmptyView.setVisibility(View.GONE);
      }

      return v;
   }

   public void onFabClick() {
      mSheetLayout.expandFab();
   }

   @Override
   public void onFabAnimationEnd() {
      Intent intent = new Intent(getContext(), NewTeamActivity.class);
      startActivityForResult(intent, REQUEST_CODE);
   }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if(requestCode == REQUEST_CODE){
         mSheetLayout.contractFab();
      }
   }
}
