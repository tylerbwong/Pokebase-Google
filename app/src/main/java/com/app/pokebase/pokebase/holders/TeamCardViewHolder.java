package com.app.pokebase.pokebase.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.PokemonEditorActivity;
import com.app.pokebase.pokebase.activities.TeamViewActivity;

/**
 * @author Tyler Wong
 */
public class TeamCardViewHolder extends RecyclerView.ViewHolder {
   public View view;
   public TextView mTitleLabel;
   public TextView mDescription;
   public ImageView mPokemonOne;
   public ImageView mPokemonTwo;
   public ImageView mPokemonThree;
   public ImageView mPokemonFour;
   public ImageView mPokemonFive;
   public ImageView mPokemonSix;

   public TeamCardViewHolder(View itemView) {
      super(itemView);
      this.view = itemView;

      this.view.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            Context cardContext = v.getContext();
            Intent editorIntent = new Intent(cardContext, TeamViewActivity.class);
            v.getContext().startActivity(editorIntent);
         }
      });

      mTitleLabel = (TextView) itemView.findViewById(R.id.title_label);
      mDescription = (TextView) itemView.findViewById(R.id.description);
      mPokemonOne = (ImageView) itemView.findViewById(R.id.pokemon_1);
      mPokemonTwo = (ImageView) itemView.findViewById(R.id.pokemon_2);
      mPokemonThree = (ImageView) itemView.findViewById(R.id.pokemon_3);
      mPokemonFour = (ImageView) itemView.findViewById(R.id.pokemon_4);
      mPokemonFive = (ImageView) itemView.findViewById(R.id.pokemon_5);
      mPokemonSix = (ImageView) itemView.findViewById(R.id.pokemon_6);
   }
}
