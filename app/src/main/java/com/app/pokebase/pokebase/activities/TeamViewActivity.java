package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonTeamMemberAdapter;
import com.app.pokebase.pokebase.components.PokemonTeamMember;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.github.fabtransitionactivity.SheetLayout;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

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
   private TextInputEditText mNameInput;
   private TextInputEditText mDescriptionInput;

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
      mNameInput = (TextInputEditText) findViewById(R.id.name_input);
      mDescriptionInput = (TextInputEditText) findViewById(R.id.description_input);

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

      mPokemon = new ArrayList<>();

      LinearLayoutManager llm = new LinearLayoutManager(this);
      llm.setOrientation(LinearLayoutManager.VERTICAL);
      mPokemonList.setLayoutManager(llm);
      mPokemonAdapter = new PokemonTeamMemberAdapter(mPokemon);
      mPokemonList.setAdapter(mPokemonAdapter);

      if (mPokemon.isEmpty()) {
         mPokemonList.setVisibility(View.GONE);
         mEmptyView.setVisibility(View.VISIBLE);
      } else {
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
   public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
         case android.R.id.home:
            onBackPressed();
            break;
         case R.id.submit_action:
            addTeam();
            break;
         default:
            break;
      }
      return true;
   }

   private void addTeam() {
      String[] newTeam = new String[4];
      newTeam[0] = QueryTask.NEW_TEAM;
      newTeam[1] = mNameInput.getText().toString();
      newTeam[2] = mDescriptionInput.getText().toString();
      new QueryTask().execute(new Pair<Context, String[]>(this, newTeam));
   }

   @Override
   public void onFabAnimationEnd() {
      Intent intent = new Intent(this, MainActivity.class);
      startActivityForResult(intent, REQUEST_CODE);
   }

   private void showBackDialog() {
      new LovelyStandardDialog(this)
            .setIcon(R.drawable.ic_info_white_48dp)
            .setTitle(R.string.go_back)
            .setMessage(R.string.back_prompt).setCancelable(true)
            .setPositiveButton(R.string.yes, new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  backToMain();
               }
            }).setNegativeButton(R.string.no, null).setTopColor(getResources()
            .getColor(R.color.colorPrimary)).show();
   }

   private void backToMain() {
      Intent mainIntent = new Intent(this, MainActivity.class);
      startActivity(mainIntent);
   }

   @Override
   public void onBackPressed() {
      showBackDialog();
   }
}
