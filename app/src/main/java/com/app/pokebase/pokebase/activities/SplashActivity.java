package com.app.pokebase.pokebase.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.app.pokebase.pokebase.R;

/**
 * @author Brittany Berlanga
 */
public class SplashActivity extends AppCompatActivity {
   private static final int SPLASH_DISPLAY_LENGTH = 1000;

   Typeface gotham;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_splash);
   }

   @Override
   protected void onResume() {
      super.onResume();

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            mainIntent.putExtra("logo_transition", true);
            LinearLayout logo = (LinearLayout) findViewById(R.id.logo);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                  makeSceneTransitionAnimation(SplashActivity.this, logo, getString(R.string.logo_transition));
            SplashActivity.this.startActivity(mainIntent, options.toBundle());
         }
      }, SPLASH_DISPLAY_LENGTH);
   }
}
