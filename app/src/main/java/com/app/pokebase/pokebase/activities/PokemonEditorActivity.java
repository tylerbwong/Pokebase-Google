package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.adapters.TextViewSpinnerAdapter;
import com.app.pokebase.pokebase.querytasks.QueryTask;

/**
 * Created by brittanyberlanga on 6/6/16.
 */
public class PokemonEditorActivity extends AppCompatActivity {
    private static final int PROFILE_IMG_ELEVATION = 40;
    private static final int NUM_SPINNERS = 4;
    private static final int MIN_LEVEL = 1;
    private static final int MAX_LEVEL = 100;
    private Toolbar mToolbar;
    private ImageView mProfileImg;
    private TextInputEditText mNickNameView;
    private Spinner mLevelSpinner;
    private Spinner[] mMoveSpinners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_editor);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Oddish");
        mProfileImg = (ImageView) findViewById(R.id.profile_image);
        mProfileImg.setClipToOutline(true);
        mProfileImg.setElevation(PROFILE_IMG_ELEVATION);
        mNickNameView = (TextInputEditText) findViewById(R.id.nickname_input);
        mLevelSpinner = (Spinner) findViewById(R.id.level_spinner);
        mMoveSpinners = new Spinner[NUM_SPINNERS];
        mMoveSpinners[0] = (Spinner) findViewById(R.id.move_one_spinner);
        mMoveSpinners[1] = (Spinner) findViewById(R.id.move_two_spinner);
        mMoveSpinners[2] = (Spinner) findViewById(R.id.move_three_spinner);
        mMoveSpinners[3] = (Spinner) findViewById(R.id.move_four_spinner);


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

        String[] levels = new String[MAX_LEVEL];
        for (int lvl = MIN_LEVEL; lvl < levels.length; lvl++) {
            levels[lvl - MIN_LEVEL] = String.valueOf(lvl);
        }

        mMoveSpinners[0].setAdapter(new TextViewSpinnerAdapter(this, moves));
        mMoveSpinners[1].setAdapter(new TextViewSpinnerAdapter(this, moves));
        mMoveSpinners[2].setAdapter(new TextViewSpinnerAdapter(this, moves));
        mMoveSpinners[3].setAdapter(new TextViewSpinnerAdapter(this, moves));
        mLevelSpinner.setAdapter(new TextViewSpinnerAdapter(this, levels, 18));
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
                //UPDATE database
                new QueryTask().execute(new Pair<Context, String>(this, "Your mom."));
                break;
            default:
                break;
        }
        return true;
    }
}
