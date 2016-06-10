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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.pokebase.pokebase.R;
import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.app.pokebase.pokebase.utilities.Typefaces;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;

/**
 * @author Tyler Wong
 */
public class LoginActivity extends AppCompatActivity implements ApiCallback {
   private TextView mTitleLabel;
   private TextInputEditText mNameInput;
   private TextView mNameCount;
   private Button mExitButton;
   private Button mCreateButton;
   private Button mLoginButton;
   private String mUsername;
   private boolean mHasText = false;

   Typeface robotoLight;

   final static String ROBOTO_PATH = "fonts/roboto-light.ttf";
   final static String DEFAULT_NAME = "Ash Ketchum";
   final static String MAX_LENGTH = "/15";

   @Override
   public void onCreate(Bundle savedInstanceBundle) {
      super.onCreate(savedInstanceBundle);
      setContentView(R.layout.activity_login);

      robotoLight = Typefaces.get(this, ROBOTO_PATH);

      mTitleLabel = (TextView) findViewById(R.id.title_label);
      mNameInput = (TextInputEditText) findViewById(R.id.name_input);
      mNameCount = (TextView) findViewById(R.id.name_count);
      mExitButton = (Button) findViewById(R.id.exit_button);
      mCreateButton = (Button) findViewById(R.id.create_user);
      mLoginButton = (Button) findViewById(R.id.login_button);

      mExitButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            close();
         }
      });

      mCreateButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            switchToCreate();
         }
      });

      mLoginButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            login();
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

   private void login() {
      //new QueryTask.execute();
   }

   @Override
   public void onApiCallback(QueryResult result) {
      SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
      SharedPreferences.Editor ed = pref.edit();
      ed.putBoolean("loggedIn", true);
      ed.putInt("userId", result.getIntInfo().get(0));
      ed.apply();
      Intent loadingIntent = new Intent(this, LoadingActivity.class);
      loadingIntent.putExtra("username", mNameInput.getText().toString());
      startActivity(loadingIntent);
   }

   private void close() {
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      finish();
      startActivity(intent);
   }

   private void switchToCreate() {
      Intent createIntent = new Intent(this, SignUpActivity.class);
      startActivity(createIntent);
   }
}
