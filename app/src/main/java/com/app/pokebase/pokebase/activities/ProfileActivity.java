package com.app.pokebase.pokebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.TextViewAdapter;

/**
 * Created by brittanyberlanga on 6/4/16.
 */
public class ProfileActivity extends AppCompatActivity {
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
    private TextView mSpeciesView;
    private TextView mRegionView;
    private TextView mHeightView;
    private TextView mWeightView;
    private TextView mExpView;
    private ListView mMovesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProfileImg = (ImageView) findViewById(R.id.profile_image);
        mProfileImg.setClipToOutline(true);
        mProfileImg.setElevation(PROFILE_IMG_ELEVATION);
        mIdView = (TextView) findViewById(R.id.id);
        mNameView = (TextView) findViewById(R.id.name);
        mTypeOneView = (TextView) findViewById(R.id.type_one);
        mTypeTwoView = (TextView) findViewById(R.id.type_two);
        mSpeciesView = (TextView) findViewById(R.id.species);
        mRegionView = (TextView) findViewById(R.id.region);
        mHeightView = (TextView) findViewById(R.id.height);
        mWeightView = (TextView) findViewById(R.id.weight);
        mExpView = (TextView) findViewById(R.id.exp);
        mMovesList = (ListView) findViewById(R.id.moves_list);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        String[] moves = new String[10];
        moves[0] = "blah blah";
        moves[1] = "hello";
        moves[2] = "eevee";
        moves[3] = "shaymin";
        moves[4] = "blah blah";
        moves[5] = "blah blah";
        moves[6] = "hello";
        moves[7] = "eevee";
        moves[8] = "shaymin";
        moves[9] = "blah blah";
        mMovesList.setAdapter(new TextViewAdapter(this, moves));
        setHeightViewText(3);
        setWeightViewText(65);

        actionBar.setTitle("Eevee");

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
}
