package com.app.pokebase.pokebase.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.PokemonRecyclerViewAdapter;
import com.app.pokebase.pokebase.components.PokemonListItem;

/**
 * Created by brittanyberlanga on 6/6/16.
 */
public class EvolutionsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private RecyclerView mEvolutionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolutions);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEvolutionList = (RecyclerView) findViewById(R.id.evolutions_list);
        mEvolutionList.setLayoutManager(new LinearLayoutManager(this));
        PokemonListItem[] items = new PokemonListItem[2];
        items[0] = new PokemonListItem(25, "Pikachu");
        items[1] = new PokemonListItem(1, "Bulbasaur");
        mEvolutionList.setAdapter(new PokemonRecyclerViewAdapter(this, items));

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
}
