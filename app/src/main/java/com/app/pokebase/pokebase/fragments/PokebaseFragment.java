package com.app.pokebase.pokebase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;

/**
 * @author Tyler Wong
 */
public class PokebaseFragment extends Fragment {
   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.pokebase_fragment, container, false);

      return v;
   }
}
