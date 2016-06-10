package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.TextViewAdapter;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

import java.util.List;

/**
 * Created by brittanyberlanga on 6/4/16.
 */
public class ProfileActivity extends AppCompatActivity implements ApiCallback{
    public static final String POKEMON_ID_KEY = "pokemon_id";
    private static final double FT_PER_DM = 0.32808399;
    private static final double LB_PER_HG = 0.22046226218;
    private static final int KG_PER_HG = 10;
    private static final int IN_PER_FT = 12;
    private static final int DM_PER_M = 10;
    private static final int PROFILE_IMG_ELEVATION = 40;
    private Toolbar mToolbar;
    private ImageView mProfileImg;
    private TextView mIdView;
    private TextView mNameView;
    private TextView mTypeOneView;
    private TextView mTypeTwoView;
    private TextView mRegionView;
    private TextView mHeightView;
    private TextView mWeightView;
    private TextView mExpView;
    private ListView mMovesList;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int pokemonId = extras.getInt(POKEMON_ID_KEY);
        String[] pokemonArray = new String[2];
        pokemonArray[0] = QueryTask.SELECTED_POKEMON;
        pokemonArray[1] = String.valueOf(pokemonId);
        new QueryTask().execute(new Pair<Context, String[]>(this, pokemonArray));
        setContentView(R.layout.activity_profile);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProfileImg = (ImageView) findViewById(R.id.profile_image);
        mProfileImg.setClipToOutline(true);
        mProfileImg.setElevation(PROFILE_IMG_ELEVATION);
        mIdView = (TextView) findViewById(R.id.id);
        mNameView = (TextView) findViewById(R.id.name);
        mTypeOneView = (TextView) findViewById(R.id.type_one);
        mTypeTwoView = (TextView) findViewById(R.id.type_two);
        mRegionView = (TextView) findViewById(R.id.region);
        mHeightView = (TextView) findViewById(R.id.height);
        mWeightView = (TextView) findViewById(R.id.weight);
        mExpView = (TextView) findViewById(R.id.exp);
        mMovesList = (ListView) findViewById(R.id.moves_list);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void setHeightViewText(int decimeters)
    {
        int feet = (int) Math.floor(decimeters * FT_PER_DM);
        int inches = (int) Math.round((decimeters * FT_PER_DM - feet) * IN_PER_FT);
        if (inches == IN_PER_FT)
        {
            feet++;
            inches = 0;
        }
        double millimeters = (double) decimeters / DM_PER_M;
        String heightText = feet + "'" + inches + "'' (" + String.format("%.2f", millimeters)
                + " m)";
        mHeightView.setText(heightText);
    }

    private void setWeightViewText(int hectograms)
    {
        double pounds = hectograms * LB_PER_HG;
        double kilograms = (double) hectograms / KG_PER_HG;
        String weightText = String.format("%.1f", pounds) + " lbs (" +
                String.format("%.1f", kilograms) + " kg)";
        mWeightView.setText(weightText);
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

    public void showEvolutions(View view)
    {
        Intent evolutionsIntent = new Intent(this, EvolutionsActivity.class);
        startActivity(evolutionsIntent);
    }

    @Override
    public void onApiCallback(QueryResult result) {
        if (result.getType().equals(QueryTask.SELECTED_POKEMON)) {
            List<Integer> intStuff = result.getIntInfo();
            List<String> strStuff = result.getStringInfo();
            List<String> moves = result.getMoreStringInfo();
            mMovesList.setAdapter(new TextViewAdapter(this, moves.toArray(new String[moves.size()])));

            mIdView.setText(String.valueOf(intStuff.get(0)));
            String idValue = String.valueOf(intStuff.get(0));
            int imageResourceId = this.getResources().getIdentifier("sprites_" + idValue, "drawable", this.getPackageName());
            mProfileImg.setImageResource(imageResourceId);

            setHeightViewText(intStuff.get(1));
            setWeightViewText(intStuff.get(2));
            mExpView.setText(String.valueOf(intStuff.get(3)));

            mActionBar.setTitle(strStuff.get(0));
            mNameView.setText(strStuff.get(0));
            mRegionView.setText(strStuff.get(1));
            //IF there is only one type
            if (strStuff.size() == 3) {
                mTypeTwoView.setVisibility(View.GONE);
                mTypeOneView.setText(strStuff.get(2));
            }
            else {
                mTypeOneView.setText(strStuff.get(2));
                mTypeTwoView.setVisibility(View.VISIBLE);
                mTypeTwoView.setText(strStuff.get(3));
            }
        }
    }
}
