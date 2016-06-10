package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.app.pokebase.pokebase.utilities.Typefaces;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

/**
 * @author Tyler Wong
 */
public class GenderActivity extends AppCompatActivity implements ApiCallback {
   private TextView mTitleLabel;
   private ImageButton mBoyButton;
   private ImageButton mGirlButton;
   private Button mGoButton;
   private boolean mIsBoy = true;
   private String mUsername;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_gender);

      robotoLight = Typefaces.get(this, ROBOTO_PATH);

      mTitleLabel = (TextView) findViewById(R.id.title_label);
      mBoyButton = (ImageButton) findViewById(R.id.boy_button);
      mGirlButton = (ImageButton) findViewById(R.id.girl_button);
      mGoButton = (Button) findViewById(R.id.gender_select);

      Intent mainIntent = getIntent();
      mUsername = mainIntent.getStringExtra("username");

      mGoButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            submitGender();
         }
      });

      mBoyButton.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            mIsBoy = true;
            mBoyButton.setPressed(true);
            mGirlButton.setPressed(false);
            return true;
         }
      });

      mGirlButton.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            mIsBoy = false;
            mGirlButton.setPressed(true);
            mBoyButton.setPressed(false);
            return true;
         }
      });

      if (robotoLight != null) {
         mTitleLabel.setTypeface(robotoLight);
      }
   }

   @Override
   public void onApiCallback(QueryResult result) {
      Intent loadingIntent = new Intent(this, LoadingActivity.class);
      loadingIntent.putExtra("username", mUsername);
      startActivity(loadingIntent);
   }

   @Override
   public void onBackPressed() {

   }

   public boolean isBoy() {
      return mIsBoy;
   }

   private void submitGender() {
      String[] newUser = new String[3];
      newUser[0] = QueryTask.NEW_USER;
      newUser[1] = mUsername;
      if (mIsBoy) {
         newUser[2] = "M";
      }
      else {
         newUser[2] = "F";
      }
      SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
      SharedPreferences.Editor ed = pref.edit();
      ed.putBoolean("loggedIn", true);
      ed.putString("username", mUsername);
      ed.putString("gender", newUser[2]);
      ed.apply();
      new QueryTask().execute(new Pair<Context, String[]>(this, newUser));
   }
}
