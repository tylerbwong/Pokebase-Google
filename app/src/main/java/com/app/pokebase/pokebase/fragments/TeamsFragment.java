package com.app.pokebase.pokebase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.NewTeamActivity;
import com.github.fabtransitionactivity.SheetLayout;

/**
 * @author Tyler Wong
 */
public class TeamsFragment extends Fragment implements SheetLayout.OnFabAnimationEndListener {

   private SheetLayout mSheetLayout;
   private FloatingActionButton mFab;

   private final static int REQUEST_CODE = 1;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.teams_fragment, container, false);

      mSheetLayout = (SheetLayout) v.findViewById(R.id.bottom_sheet);
      mFab = (FloatingActionButton) v.findViewById(R.id.fab);

      mFab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            onFabClick();
         }
      });

      mSheetLayout.setFab(mFab);
      mSheetLayout.setFabAnimationEndListener(this);

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
