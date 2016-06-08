package com.app.pokebase.pokebase.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.components.PokemonListItem;
import com.app.pokebase.pokebase.holders.PokemonListItemHolder;

/**
 * Created by brittanyberlanga on 6/8/16.
 */
public class PokemonRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private PokemonListItem[] mItems;

    public PokemonRecyclerViewAdapter(Context context, PokemonListItem[] items){
        this.mContext = context;
        this.mItems = items;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_pokemon, parent, false);
        return new PokemonListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PokemonListItemHolder holder = (PokemonListItemHolder) viewHolder;
        PokemonListItem item = mItems[position];
        holder.mIdView.setText(String.valueOf(item.mId));
        holder.mNameView.setText(item.mName);
        int imageResourceId = mContext.getResources().getIdentifier("icon_" + item.mId, "drawable", mContext.getPackageName());
        holder.mIconView.setImageResource(imageResourceId);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }
}
