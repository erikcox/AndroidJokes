package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.joke.endpoint.backend.myApi.MyApi;

import java.io.IOException;

import rocks.ecox.jokeactivity.JokeActivity.JokeActivity;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context mContext;

    public EndpointsAsyncTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(Void... params){
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Local testing url (emulator url, or local machine url)
                    // .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://androidjokes-155120.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Intent intent = new Intent(mContext, JokeActivity.class);

        intent.putExtra(JokeActivity.JOKE_KEY, result);
        mContext.startActivity(intent);
    }
}