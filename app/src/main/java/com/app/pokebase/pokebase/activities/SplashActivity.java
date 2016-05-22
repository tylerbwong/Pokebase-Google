package com.app.pokebase.pokebase.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.utilities.Typefaces;

/**
 * @author Brittany Berlanga
 */
public class SplashActivity extends AppCompatActivity {
   private TextView mTitleLabel;

   Typeface robotoLight;

   final static int SPLASH_DISPLAY_LENGTH = 1000;
   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      robotoLight = Typefaces.get(this, ROBOTO_PATH);

      setContentView(R.layout.activity_splash);

      mTitleLabel = (TextView) findViewById(R.id.title_label);

      if (robotoLight != null) {
         mTitleLabel.setTypeface(robotoLight);
      }

//      //  Declare a new thread to do a preference check
//      Thread t = new Thread(new Runnable() {
//         @Override
//         public void run() {
//            //  Initialize SharedPreferences
//            SharedPreferences getPrefs = PreferenceManager
//                  .getDefaultSharedPreferences(getBaseContext());
//
//            //  Create a new boolean and preference and set it to true
//            boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
//
//            //  If the activity has never started before...
//            if (isFirstStart) {
//
//               //  Launch app intro
//               Intent startIntent = new Intent(SplashActivity.this, StartActivity.class);
//               startActivity(startIntent);
//
//               //  Make a new preferences editor
//               SharedPreferences.Editor e = getPrefs.edit();
//
//               //  Edit preference to make it false because we don't want this to run again
//               e.putBoolean("firstStart", false);
//
//               //  Apply changes
//               e.apply();
//            }
//            else {
//               Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//               startActivity(mainIntent);
//            }
//         }
//      });
//
//      // Start the thread
//      t.start();
   }

   @Override
   protected void onResume() {
      super.onResume();

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            Intent mainIntent = new Intent(SplashActivity.this, StartActivity.class);
            mainIntent.putExtra("logo_transition", true);
            LinearLayout logo = (LinearLayout) findViewById(R.id.logo);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                  makeSceneTransitionAnimation(SplashActivity.this, logo, getString(R.string.logo_transition));
            SplashActivity.this.startActivity(mainIntent, options.toBundle());
         }
      }, SPLASH_DISPLAY_LENGTH);
   }
}
