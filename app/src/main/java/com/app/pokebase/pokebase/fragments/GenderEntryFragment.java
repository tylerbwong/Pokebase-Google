package com.app.pokebase.pokebase.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.utilities.Typefaces;

/**
 * @author Tyler Wong
 */
public class GenderEntryFragment extends Fragment {
   private TextView mTitleLabel;
   private ImageButton mBoyButton;
   private ImageButton mGirlButton;
   private boolean mIsBoy = true;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.gender_fragment, container, false);

      robotoLight = Typefaces.get(getActivity(), ROBOTO_PATH);

      mTitleLabel = (TextView) v.findViewById(R.id.title_label);
      mBoyButton = (ImageButton) v.findViewById(R.id.boy_button);
      mGirlButton = (ImageButton) v.findViewById(R.id.girl_button);

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

      return v;
   }

   public boolean isBoy() {
      return mIsBoy;
   }
}
