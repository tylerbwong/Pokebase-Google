package com.app.pokebase.pokebase.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.utilities.Typefaces;

/**
 * @author Tyler Wong
 */
public class NameEntryFragment extends Fragment {
   private TextView mTitleLabel;
   private TextInputEditText mNameInput;
   private TextView mNameCount;
   private String mUsername;
   private boolean mHasText = false;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";
   final static String DEFAULT_NAME = "Ash Ketchum";
   final static String MAX_LENGTH = "/15";

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.name_fragment, container, false);

      robotoLight = Typefaces.get(getActivity(), ROBOTO_PATH);

      mTitleLabel = (TextView) v.findViewById(R.id.title_label);
      mNameInput = (TextInputEditText) v.findViewById(R.id.name_input);
      mNameCount = (TextView) v.findViewById(R.id.name_count);

      if (robotoLight != null) {
         mTitleLabel.setTypeface(robotoLight);
         mNameInput.setTypeface(robotoLight);
         mNameCount.setTypeface(robotoLight);
      }

      mNameInput.addTextChangedListener(new TextWatcher() {

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            String charLeft = s.length() + MAX_LENGTH;

            if (s.toString().trim().length() == 0) {
               mHasText = false;
            }
            else {
               mHasText = true;
            }
            mNameCount.setText(charLeft);
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
         }
      });

      return v;
   }

   public String getUsername() {
      if (mHasText) {
         mUsername = mNameInput.getText().toString();
      }
      else {
         mUsername = DEFAULT_NAME;
      }
      return mUsername;
   }
}
