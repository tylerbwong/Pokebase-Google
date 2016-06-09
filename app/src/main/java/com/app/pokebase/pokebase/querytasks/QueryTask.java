package com.app.pokebase.pokebase.querytasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.example.tylerbwong.pokebase.backend.myApi.MyApi;
import com.example.tylerbwong.pokebase.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * @author Tyler Wong
 */
public class QueryTask extends AsyncTask<Pair<Context, String>, Void, MyBean> {
   private static MyApi myApiService = null;
   private Context context;

   @Override
   protected MyBean doInBackground(Pair<Context, String>... params) {
      if (myApiService == null) {  // Only do this once
         MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
               new AndroidJsonFactory(), null)
               .setRootUrl("https://pokebase-1335.appspot.com/_ah/api/");
         // end options for devappserver

         myApiService = builder.build();
      }

      context = params[0].first;
      String name = params[0].second;

      try {
         return myApiService.sayHi(name).execute();
      }
      catch (IOException e) {
         //return e.getMessage();
         return null;
      }
   }

   @Override
   protected void onPostExecute(MyBean result) {
      Toast.makeText(context, result.getData(), Toast.LENGTH_LONG).show();
   }
}
