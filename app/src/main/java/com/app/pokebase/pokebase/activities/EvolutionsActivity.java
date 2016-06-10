package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonRecyclerViewAdapter;
import com.app.pokebase.pokebase.components.PokemonListItem;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

import java.util.List;

/**
 * Created by brittanyberlanga on 6/6/16.
 */
public class EvolutionsActivity extends AppCompatActivity implements ApiCallback{
    private Toolbar mToolbar;
    private RecyclerView mEvolutionList;
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int pokemonId = extras.getInt(ProfileActivity.POKEMON_ID_KEY);
        String pokemonName = extras.getString(ProfileActivity.POKEMON_NAME_KEY);
        String[] evolutionsArray = new String[2];
        evolutionsArray[0] = QueryTask.SELECTED_POKEMON_EVOLUTIONS;
        evolutionsArray[1] = String.valueOf(pokemonId);
        new QueryTask().execute(new Pair<Context, String[]>(this, evolutionsArray));
        setContentView(R.layout.activity_evolutions);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Evolutions of " + pokemonName);

        mEvolutionList = (RecyclerView) findViewById(R.id.evolutions_list);
        mEvolutionList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onApiCallback(QueryResult result) {
        if (result.getType().equals(QueryTask.SELECTED_POKEMON_EVOLUTIONS)) {
            List<Integer> ids = result.getIntInfo();
            List<String> names = result.getStringInfo();
            if (ids != null && ids.size() > 0) {
                PokemonListItem[] items = new PokemonListItem[ids.size()];
                for (int i = 0; i < ids.size(); i++) {
                    items[i] = new PokemonListItem(ids.get(i), names.get(i));
                }
                mEvolutionList.setAdapter(new PokemonRecyclerViewAdapter(this, items));
            }
            else {
                mEvolutionList.setAdapter(new PokemonRecyclerViewAdapter(this, new PokemonListItem[0]));
            }
        }
    }
}
