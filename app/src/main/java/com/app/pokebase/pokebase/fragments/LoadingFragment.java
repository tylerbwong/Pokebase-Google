package com.app.pokebase.pokebase.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.MainActivity;
import com.app.pokebase.pokebase.utilities.Typefaces;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Tyler Wong
 */
public class LoadingFragment extends Fragment {
   private TextView mTitlelabel;
   private String mUsername;
   private boolean mIsBoy;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";
   final static int LOADING_TIME = 3000;

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.loading_fragment, container, false);

      robotoLight = Typefaces.get(getActivity(), ROBOTO_PATH);

      mTitlelabel = (TextView) v.findViewById(R.id.title_label);
      mUsername = getArguments().getString("username");
      mIsBoy = getArguments().getBoolean("is_boy");

      if (robotoLight != null) {
         mTitlelabel.setTypeface(robotoLight);
      }

      TimerTask timerTask = new TimerTask() {
         @Override
         public void run() {
            switchToMain();
         }
      };

      Timer timer = new Timer();
      timer.schedule(timerTask, LOADING_TIME);

      return v;
   }

   private void switchToMain() {
      Intent mainIntent = new Intent(getActivity(), MainActivity.class);
      mainIntent.putExtra("username", mUsername);
      mainIntent.putExtra("is_boy", mIsBoy);
      startActivity(mainIntent);
   }
}
