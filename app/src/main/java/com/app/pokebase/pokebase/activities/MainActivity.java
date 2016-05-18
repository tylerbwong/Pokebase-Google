package com.app.pokebase.pokebase.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.pokebase.pokebase.R;

/**
 * @author Tyler Wong
 */
public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      boolean logoTransition = getIntent().getBooleanExtra("logo_transition", false);
      if (logoTransition) {
         overridePendingTransition(R.anim.slow_transition, R.anim.slow_transition);
      }
   }
}
