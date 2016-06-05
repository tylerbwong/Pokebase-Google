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
   public TextView mName;
   public ImageView mType;
   public TextView mLevel;
   public TextView mMove;
   public ImageView mPicture;

   public TeamCardViewHolder(View itemView) {
      super(itemView);

      mPicture = (ImageView) itemView.findViewById(R.id.photo);
      mName = (TextView) itemView.findViewById(R.id.event_title);
      mMove = (TextView) itemView.findViewById(R.id.description);
      mLevel = (TextView) itemView.findViewById(R.id.street);
   }
}
