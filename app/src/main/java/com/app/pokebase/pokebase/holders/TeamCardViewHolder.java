package com.app.pokebase.pokebase.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;

/**
 * @author Tyler Wong
 */
public class TeamCardViewHolder extends RecyclerView.ViewHolder {
   public TextView mTitleLabel;
   public ImageView mPokemonOne;
   public ImageView mPokemonTwo;
   public ImageView mPokemonThree;
   public ImageView mPokemonFour;
   public ImageView mPokemonFive;
   public ImageView mPokemonSix;

   public TeamCardViewHolder(View itemView) {
      super(itemView);

      mTitleLabel = (TextView) itemView.findViewById(R.id.title_label);
      mPokemonOne = (ImageView) itemView.findViewById(R.id.pokemon_1);
      mPokemonTwo = (ImageView) itemView.findViewById(R.id.pokemon_2);
      mPokemonThree = (ImageView) itemView.findViewById(R.id.pokemon_3);
      mPokemonFour = (ImageView) itemView.findViewById(R.id.pokemon_4);
      mPokemonFive = (ImageView) itemView.findViewById(R.id.pokemon_5);
      mPokemonSix = (ImageView) itemView.findViewById(R.id.pokemon_6);
   }
}
