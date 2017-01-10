package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.joke.endpoint.backend.myApi.MyApi;

import java.io.IOException;

// AsyncTask for getting jokes from GCE
public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    final private OnTaskCompleted mListener;

    public EndpointsAsyncTask (OnTaskCompleted listener) {
        this.mListener = listener;
    }

    // Grabs a joke in a background task
    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    // Local testing url (emulator is 10.0.2.2 or use local machine ip)
                    // .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setRootUrl("https://androidjokes-155120.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        // Grab a joke
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    // Return result when done
    @Override
    protected void onPostExecute(String result) {
        mListener.onTaskCompleted(result);
    }
}