package com.app.pokebase.pokebase.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.querytasks.QueryTask;
import com.app.pokebase.pokebase.utilities.Typefaces;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

/**
 * @author Tyler Wong
 */
public class SignUpActivity extends AppCompatActivity implements ApiCallback {
   private TextView mTitleLabel;
   private TextInputEditText mNameInput;
   private TextView mNameCount;
   private Button mExitButton;
   private Button mLoginButton;
   private Button mCreateButton;
   private String mUsername;
   private boolean mHasText = false;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";
   final static String DEFAULT_NAME = "Ash Ketchum";
   final static String MAX_LENGTH = "/15";

   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_signup);
      robotoLight = Typefaces.get(this, ROBOTO_PATH);

      mTitleLabel = (TextView) findViewById(R.id.title_label);
      mNameInput = (TextInputEditText) findViewById(R.id.name_input);
      mNameCount = (TextView) findViewById(R.id.name_count);
      mExitButton = (Button) findViewById(R.id.exit_button);
      mLoginButton = (Button) findViewById(R.id.login_button);
      mCreateButton = (Button) findViewById(R.id.create_user);

      mCreateButton.setEnabled(false);

      mExitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            close();
         }
      });

      mLoginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            switchToLogin();
         }
      });

      mCreateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            createUser();
         }
      });

      if (robotoLight != null) {
         mTitleLabel.setTypeface(robotoLight);
         mNameInput.setTypeface(robotoLight);
         mNameCount.setTypeface(robotoLight);
      }

      mNameInput.addTextChangedListener(new TextWatcher() {

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
            String charLeft = s.length() + MAX_LENGTH;

            if (s.toString().trim().length() == 0) {
               mHasText = false;
            } else {
               mHasText = true;
            }
            checkFields();
            mNameCount.setText(charLeft);
         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void afterTextChanged(Editable editable) {
         }
      });
   }

   public String getUsername() {
      if (mHasText) {
         mUsername = mNameInput.getText().toString();
      } else {
         mUsername = DEFAULT_NAME;
      }
      return mUsername;
   }

   private void checkFields() {
      if (mHasText) {
         mCreateButton.setEnabled(true);
      } else {
         mCreateButton.setEnabled(false);
      }
   }

   private void createUser() {
      String[] signUp = new String[2];
      signUp[0] = QueryTask.CHECK_USERNAME;
      signUp[1] = mNameInput.getText().toString();
      new QueryTask().execute(new Pair<Context, String[]>(this, signUp));
   }

   private void close() {
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      finish();
      startActivity(intent);
   }

   private void switchToLogin() {
      Intent loginIntent = new Intent(this, LoginActivity.class);
      startActivity(loginIntent);
   }

   @Override
   public void onApiCallback(QueryResult result) {
      if (result.getCount() == 0) {
         SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
         SharedPreferences.Editor ed = pref.edit();
         ed.putBoolean("loggedIn", true);
         ed.apply();
         Intent genderIntent = new Intent(this, GenderActivity.class);
         genderIntent.putExtra("username", mNameInput.getText().toString());
         startActivity(genderIntent);
      } else {
         showUsedUsernameDialog();
      }
   }

   @Override
   public void onBackPressed() {

   }

   private void showUsedUsernameDialog() {
      new LovelyStandardDialog(this)
            .setIcon(R.drawable.ic_info_white_48dp)
            .setTitle(R.string.taken)
            .setMessage(R.string.new_name).setCancelable(true).setPositiveButton(R.string.ok, null)
            .setTopColor(getResources()
            .getColor(R.color.colorPrimary)).show();
   }
}
