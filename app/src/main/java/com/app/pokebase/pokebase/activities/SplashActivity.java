package com.app.pokebase.pokebase.activities;

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

//      Thread preferenceThread = new Thread(new Runnable() {
//         @Override
//         public void run() {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
//                  getBaseContext());
//
//            boolean isFirstStart = preferences.getBoolean("firstStart", true);
//
//            if (!isFirstStart) {
//               Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
//               startActivity(mainIntent);
//            }
//            else {
//               SharedPreferences.Editor editor = preferences.edit();
//               editor.putBoolean("firstStart", false);
//               editor.apply();
//               Intent startIntent = new Intent(SplashActivity.this, StartActivity.class);
//               startActivity(startIntent);
//            }
//         }
//      });
//      preferenceThread.start();
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
