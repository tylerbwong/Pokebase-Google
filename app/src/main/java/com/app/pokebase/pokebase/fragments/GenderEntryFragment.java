package com.app.pokebase.pokebase.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
   private Button mBackButton;
   private Button mFinishButton;
   private String mUsername;
   private boolean mIsBoy = false;

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
      mBackButton = (Button) v.findViewById(R.id.back_button);
      mFinishButton = (Button) v.findViewById(R.id.next_button);

      mUsername = getArguments().getString("username");

      mFinishButton.setEnabled(false);

      mBoyButton.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            mIsBoy = true;
            mFinishButton.setEnabled(true);
            mBoyButton.setPressed(true);
            mGirlButton.setPressed(false);
            return true;
         }
      });

      mGirlButton.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
            mIsBoy = false;
            mFinishButton.setEnabled(true);
            mGirlButton.setPressed(true);
            mBoyButton.setPressed(false);
            return true;
         }
      });

      if (robotoLight != null) {
         mTitleLabel.setTypeface(robotoLight);
         mBackButton.setTypeface(robotoLight);
         mFinishButton.setTypeface(robotoLight);
      }

      mBackButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            backAction();
         }
      });

      mFinishButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            nextAction();
         }
      });

      return v;
   }

   private void backAction() {
      NameEntryFragment nameEntryFragment = new NameEntryFragment();
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
      fragmentTransaction.replace(R.id.start_frame, nameEntryFragment);
      fragmentTransaction.commit();
   }

   private void nextAction() {
      Bundle fragmentBundle = new Bundle();
      fragmentBundle.putString("username", mUsername);
      fragmentBundle.putBoolean("is_boy", mIsBoy);
      LoadingFragment loadingFragment = new LoadingFragment();
      loadingFragment.setArguments(fragmentBundle);
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      fragmentTransaction.replace(R.id.start_frame, loadingFragment);
      fragmentTransaction.commit();
   }
}
