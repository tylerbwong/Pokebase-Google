package com.app.pokebase.pokebase.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.pokebase.pokebase.R;

/**
 * @author Tyler Wong
 */
public class NewTeamActivity extends AppCompatActivity {

   private Toolbar mToolbar;

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_team);

      mToolbar = (Toolbar) findViewById(R.id.toolbar);

      setSupportActionBar(mToolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      getSupportActionBar().setTitle(R.string.new_team);

      mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showBackDialog();
         }
      });
   }

   private void showBackDialog() {
      new AlertDialog.Builder(NewTeamActivity.this)
            .setIcon(R.drawable.info)
            .setTitle(R.string.go_back)
            .setMessage(R.string.back_prompt)
            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  backToMain();
               }

            })
            .setNegativeButton(R.string.no, null)
            .show();
   }

   private void backToMain() {
      Intent mainIntent = new Intent(this, MainActivity.class);
      startActivity(mainIntent);
   }
}
