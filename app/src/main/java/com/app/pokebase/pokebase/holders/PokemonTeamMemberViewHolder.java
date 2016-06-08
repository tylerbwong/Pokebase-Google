package com.app.pokebase.pokebase.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.activities.PokemonEditorActivity;

/**
 * @author Tyler Wong
 */
public class PokemonTeamMemberViewHolder extends RecyclerView.ViewHolder {
   public View view;
   public ImageView mPokemon;
   public TextView mName;
   public TextView mLevel;
   public TextView mMoveset;

   public PokemonTeamMemberViewHolder(View itemView) {
      super(itemView);
      this.view = itemView;

      this.view.setOnClickListener(new View.OnClickListener() {
         @Override public void onClick(View v) {
            Context cardContext = v.getContext();
            Intent editorIntent = new Intent(cardContext, PokemonEditorActivity.class);
            v.getContext().startActivity(editorIntent);
         }
      });

      mPokemon = (ImageView) itemView.findViewById(R.id.pokemon);
      mName = (TextView) itemView.findViewById(R.id.name);
      mLevel = (TextView) itemView.findViewById(R.id.level);
      mMoveset = (TextView) itemView.findViewById(R.id.moveset);
   }
}