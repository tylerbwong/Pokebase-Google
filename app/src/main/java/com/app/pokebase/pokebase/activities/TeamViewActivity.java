package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonTeamMemberAdapter;
import com.app.pokebase.pokebase.components.PokemonTeamMember;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class TeamViewActivity extends AppCompatActivity implements ApiCallback {
   public static final String TEAM_ID_KEY = "team_id_key";
   public static final String UPDATE_KEY = "update_key";
   private Toolbar mToolbar;
   private RelativeLayout mLayout;
   private RecyclerView mPokemonList;
   private LinearLayout mEmptyView;
   private TextInputEditText mNameInput;
   private TextInputEditText mDescriptionInput;

   private PokemonTeamMemberAdapter mPokemonAdapter;
   private ArrayList<PokemonTeamMember> mPokemon;

   private boolean mUpdateKey;
   private int mTeamId;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_team);

      mToolbar = (Toolbar) findViewById(R.id.toolbar);
      mLayout = (RelativeLayout) findViewById(R.id.layout);
      mPokemonList = (RecyclerView) findViewById(R.id.team_list);
      mEmptyView = (LinearLayout) findViewById(R.id.empty_layout);
      mNameInput = (TextInputEditText) findViewById(R.id.name_input);
      mDescriptionInput = (TextInputEditText) findViewById(R.id.description_input);

      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      getSupportActionBar().setTitle(R.string.new_team);

      Intent intent = getIntent();
      Bundle extras = intent.getExtras();
      mTeamId = extras.getInt(TEAM_ID_KEY);
      mUpdateKey = extras.getBoolean(UPDATE_KEY, false);
      String teamTitle = extras.getString("teamName");
      String description = extras.getString("description");

      mNameInput.setText(teamTitle);
      mDescriptionInput.setText(description);

      //if you are updating an existing team
      if (mUpdateKey) {
         String[] teamPokemon = new String[2];
         teamPokemon[0] = QueryTask.TEAM_BY_ID;
         teamPokemon[1] = String.valueOf(mTeamId);
         new QueryTask().execute(new Pair<Context, String[]>(this, teamPokemon));
      }

      mPokemon = new ArrayList<>();

      LinearLayoutManager llm = new LinearLayoutManager(this);
      llm.setOrientation(LinearLayoutManager.VERTICAL);
      mPokemonList.setLayoutManager(llm);

      if (mPokemon.isEmpty()) {
         mPokemonList.setVisibility(View.GONE);
         mEmptyView.setVisibility(View.VISIBLE);
      }
      else {
         mPokemonList.setVisibility(View.VISIBLE);
         mEmptyView.setVisibility(View.GONE);
      }
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
      SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
      String[] newTeam;
      if (!mUpdateKey) {
         newTeam = new String[4];
         newTeam[0] = QueryTask.NEW_TEAM;
         newTeam[1] = pref.getString("username", "");
         newTeam[2] = mNameInput.getText().toString();
         newTeam[3] = mDescriptionInput.getText().toString();
         new QueryTask().execute(new Pair<Context, String[]>(this, newTeam));

         Toast.makeText(this, "Added new team " + mNameInput.getText().toString() + "!",
               Toast.LENGTH_LONG).show();
      }
      else {
         newTeam = new String[4];
         newTeam[0] = QueryTask.UPDATE_TEAM;
         newTeam[1] = String.valueOf(mTeamId);
         newTeam[2] = mNameInput.getText().toString();
         newTeam[3] = mDescriptionInput.getText().toString();
         new QueryTask().execute(new Pair<Context, String[]>(this, newTeam));
         Toast.makeText(this, "Updated team!", Toast.LENGTH_LONG).show();
      }
      backToMain();
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

   @Override
   public void onApiCallback(QueryResult result) {
      if (result.getType().equals(QueryTask.TEAM_BY_ID)) {
         List<Integer> memberIds = result.getMoreMoreIntInfo();
         List<Integer> pokemonIds = result.getIntInfo();
         List<String> nicknames = result.getStringInfo();
         List<Integer> levels = result.getMoreIntInfo();
         List<List<String>> moves = result.getListOfStringLists();

         mPokemon = new ArrayList<>();
         for (int index = 0; index < pokemonIds.size(); index++) {
            mPokemon.add(new PokemonTeamMember(pokemonIds.get(index), nicknames.get(index),
                  levels.get(index), moves.get(index)));
         }
         mPokemonAdapter = new PokemonTeamMemberAdapter(this, mPokemon, memberIds);
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
   }
}
