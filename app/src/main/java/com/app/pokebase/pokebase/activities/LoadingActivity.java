package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.utilities.Typefaces;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tyler Wong
 */
public class LoadingActivity extends AppCompatActivity implements ApiCallback {
   private TextView mTitlelabel;
   private String mUsername;
   private boolean mIsBoy;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";
   final static int LOADING_TIME = 3000;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_loading);
      robotoLight = Typefaces.get(this, ROBOTO_PATH);

      mTitlelabel = (TextView) findViewById(R.id.title_label);

      if (robotoLight != null) {
         mTitlelabel.setTypeface(robotoLight);
      }

      Intent introIntent = getIntent();
      mUsername = introIntent.getStringExtra("username");

      TimerTask timerTask = new TimerTask() {
         @Override
         public void run() {
            switchToMain();
         }
      };

      Timer timer = new Timer();
      timer.schedule(timerTask, LOADING_TIME);
   }

   @Override
   public void onBackPressed() {

   }

   private void switchToMain() {
      Intent mainIntent = new Intent(this, MainActivity.class);
      mainIntent.putExtra("username", mUsername);
      mainIntent.putExtra("is_boy", mIsBoy);
      startActivity(mainIntent);
   }

   @Override
   public void onApiCallback(QueryResult result) {
      // get id and add to preferences
      SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
      SharedPreferences.Editor ed = pref.edit();
      ed.putBoolean("loggedIn", true);
      ed.apply();
   }
}
