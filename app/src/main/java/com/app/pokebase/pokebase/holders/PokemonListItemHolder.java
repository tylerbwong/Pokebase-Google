package com.app.pokebase.pokebase.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;

/**
 * Created by brittanyberlanga on 6/8/16.
 */
public class PokemonListItemHolder extends RecyclerView.ViewHolder {
    public final TextView mIdView;
    public final TextView mNameView;
    public final ImageView mIconView;
    public PokemonListItemHolder(View itemView) {
        super(itemView);
        mIdView = (TextView) itemView.findViewById(R.id.id);
        mNameView = (TextView) itemView.findViewById(R.id.name);
        mIconView = (ImageView) itemView.findViewById(R.id.small_icon);
    }
}
