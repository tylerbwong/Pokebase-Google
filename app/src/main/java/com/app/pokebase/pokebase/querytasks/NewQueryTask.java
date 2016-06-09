package com.app.pokebase.pokebase.querytasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.app.pokebase.pokebase.interfaces.ApiCallback;
import com.example.tylerbwong.pokebase.backend.myApi.MyApi;
import com.example.tylerbwong.pokebase.backend.myApi.model.QueryResult;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by brittanyberlanga on 6/9/16.
 */
public class NewQueryTask extends AsyncTask<Pair<Context, String[]>, Void, QueryResult> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected QueryResult doInBackground(Pair<Context, String[]>... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://pokebase-1335.appspot.com/_ah/api/");
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String[] queryInfo = params[0].second;

        try {
            return myApiService.queryByType(Arrays.asList(queryInfo)).execute();
        }
        catch (IOException e) {
            //return e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(QueryResult queryResult) {
        if (context instanceof ApiCallback)
        {
            ApiCallback callback = (ApiCallback) context;
            callback.onApiCallback(queryResult);
        }
    }
}
