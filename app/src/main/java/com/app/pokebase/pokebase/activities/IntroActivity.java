package com.app.pokebase.pokebase.activities;

import android.content.Intent;
import android.os.Bundle;
import com.app.pokebase.pokebase.fragments.GenderEntryFragment;
import com.app.pokebase.pokebase.fragments.IntroTeamFragment;
import com.app.pokebase.pokebase.fragments.IntroPokebaseFragment;
import com.app.pokebase.pokebase.fragments.NameEntryFragment;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * @author Tyler Wong
 */
public class IntroActivity extends AppIntro {
   private IntroPokebaseFragment mIntroPokebaseFragment;
   private IntroTeamFragment mIntroTeamFragment;
   private NameEntryFragment mNameEntryFragment;
   private GenderEntryFragment mGenderEntryFragment;

   @Override
   public void init(Bundle savedInstanceState) {
      mIntroPokebaseFragment = new IntroPokebaseFragment();
      mIntroTeamFragment = new IntroTeamFragment();
      mNameEntryFragment = new NameEntryFragment();
      mGenderEntryFragment = new GenderEntryFragment();

      addSlide(mIntroPokebaseFragment);
      addSlide(mIntroTeamFragment);
      addSlide(mNameEntryFragment);
      addSlide(mGenderEntryFragment);

      showSkipButton(false);
   }

   @Override
   public void onSkipPressed() {

   }

   @Override
   public void onDonePressed() {
      Intent loadingIntent = new Intent(this, LoadingActivity.class);
      loadingIntent.putExtra("username", mNameEntryFragment.getUsername());
      loadingIntent.putExtra("is_boy", mGenderEntryFragment.isBoy());
      startActivity(loadingIntent);
   }

   @Override
   public void onSlideChanged() {

   }

   @Override
   public void onNextPressed() {

   }

   @Override
   public void onBackPressed() {

   }
}
