package com.app.pokebase.pokebase.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonTeamMemberAdapter;
import com.app.pokebase.pokebase.components.PokemonTeamMember;
import com.github.fabtransitionactivity.SheetLayout;

import java.util.ArrayList;

/**
 * @author Tyler Wong
 */
public class TeamViewActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {

   private Toolbar mToolbar;
   private SheetLayout mSheetLayout;
   private FloatingActionButton mFab;
   private RecyclerView mPokemonList;
   private LinearLayout mEmptyView;

   private PokemonTeamMemberAdapter mPokemonAdapter;
   private ArrayList<PokemonTeamMember> mPokemon;

   private final static int REQUEST_CODE = 1;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_team);

      mToolbar = (Toolbar) findViewById(R.id.toolbar);
      mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheet);
      mPokemonList = (RecyclerView) findViewById(R.id.team_list);
      mFab = (FloatingActionButton) findViewById(R.id.fab);
      mEmptyView = (LinearLayout) findViewById(R.id.empty_layout);

      mFab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            onFabClick();
         }
      });

      mSheetLayout.setFab(mFab);
      mSheetLayout.setFabAnimationEndListener(this);

      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      getSupportActionBar().setTitle(R.string.new_team);

      mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showBackDialog();
         }
      });

      mPokemon = new ArrayList<>();

      LinearLayoutManager llm = new LinearLayoutManager(this);
      llm.setOrientation(LinearLayoutManager.VERTICAL);
      mPokemonList.setLayoutManager(llm);
      mPokemonAdapter = new PokemonTeamMemberAdapter(mPokemon);
      mPokemonList.setAdapter(mPokemonAdapter);

      if (mPokemon.isEmpty()) {
         mPokemonList.setVisibility(View.GONE);
         mEmptyView.setVisibility(View.VISIBLE);
      }
      else {
         mPokemonList.setVisibility(View.VISIBLE);
         mEmptyView.setVisibility(View.GONE);
      }
   }

   public void onFabClick() {
      mSheetLayout.expandFab();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.menu_submit, menu);
      return true;
   }

   @Override
   public void onFabAnimationEnd() {
      Intent intent = new Intent(this, MainActivity.class);
      startActivityForResult(intent, REQUEST_CODE);
   }

   private void showBackDialog() {
      new AlertDialog.Builder(TeamViewActivity.this)
            .setIcon(R.drawable.info)
            .setTitle(R.string.go_back)
            .setMessage(R.string.back_prompt)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  backToMain();
               }

            })
            .setNegativeButton(R.string.no, null)
            .show();
   }

   private void backToMain() {
      Intent mainIntent = new Intent(this, MainActivity.class);
      startActivity(mainIntent);
   }
}
