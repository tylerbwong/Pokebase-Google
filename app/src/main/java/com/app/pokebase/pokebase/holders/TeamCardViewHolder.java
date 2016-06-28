package com.app.pokebase.pokebase.holders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.TeamViewActivity;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tyler Wong
 */
public class TeamCardViewHolder extends SwappingHolder implements View.OnClickListener,
      View.OnLongClickListener {
   public View view;
   public TextView mTitleLabel;
   public TextView mDescription;
   public ImageView mPokemonOne;
   public ImageView mPokemonTwo;
   public ImageView mPokemonThree;
   public ImageView mPokemonFour;
   public ImageView mPokemonFive;
   public ImageView mPokemonSix;
   public int mTeamId;

   public List<ImageView> pokemonList;
   private Context mContext;
   private MultiSelector mMultiSelector;
   private ModalMultiSelectorCallback mActionModeCallback;

   public TeamCardViewHolder(View itemView, Context context, MultiSelector multiSelector,
                             ModalMultiSelectorCallback actionModeCallback) {
      super(itemView, multiSelector);
      this.mContext = context;
      this.mMultiSelector = multiSelector;
      this.mActionModeCallback = actionModeCallback;
      this.view = itemView;
      this.mTeamId = 0;

      this.view.setOnClickListener(this);
      this.view.setOnLongClickListener(this);

      mTitleLabel = (TextView) itemView.findViewById(R.id.title_label);
      mDescription = (TextView) itemView.findViewById(R.id.description);
      mPokemonOne = (ImageView) itemView.findViewById(R.id.pokemon_1);
      mPokemonTwo = (ImageView) itemView.findViewById(R.id.pokemon_2);
      mPokemonThree = (ImageView) itemView.findViewById(R.id.pokemon_3);
      mPokemonFour = (ImageView) itemView.findViewById(R.id.pokemon_4);
      mPokemonFive = (ImageView) itemView.findViewById(R.id.pokemon_5);
      mPokemonSix = (ImageView) itemView.findViewById(R.id.pokemon_6);

      pokemonList = new ArrayList<>();

      pokemonList.add(mPokemonOne);
      pokemonList.add(mPokemonTwo);
      pokemonList.add(mPokemonThree);
      pokemonList.add(mPokemonFour);
      pokemonList.add(mPokemonFive);
      pokemonList.add(mPokemonSix);
   }

   @Override
   public void onClick(View v) {
      if (!mMultiSelector.tapSelection(this)) {
         Context cardContext = v.getContext();
         Intent editorIntent = new Intent(cardContext, TeamViewActivity.class);
         Bundle extras = new Bundle();
         extras.putInt(TeamViewActivity.TEAM_ID_KEY, mTeamId);
         extras.putBoolean(TeamViewActivity.UPDATE_KEY, true);
         extras.putString("teamName", mTitleLabel.getText().toString());
         extras.putString("description", mDescription.getText().toString());
         editorIntent.putExtras(extras);
         v.getContext().startActivity(editorIntent);
      }
   }

   @Override
   public boolean onLongClick(View v) {
      if (!mMultiSelector.isSelectable()) {
         ((AppCompatActivity) mContext).startSupportActionMode(mActionModeCallback);
         mMultiSelector.setSelectable(true);
         mMultiSelector.setSelected(this, true);
         return true;
      }
      return false;
   }

   public void setTeamId(int teamId) {
      this.mTeamId = teamId;
   }
}
