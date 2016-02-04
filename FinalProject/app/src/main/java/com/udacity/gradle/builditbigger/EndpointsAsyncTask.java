package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.backend.tellJoke.TellJoke;


import java.io.IOException;

/**
 * Created by 597753 on 04-02-2016.
 */
public class EndpointsAsyncTask extends AsyncTask<String, Void, Integer> {
    private static TellJoke myApiService = null;
    String result;
    @Override
    protected Integer doInBackground(String... params) {
        System.out.println("executing do in background");
        if(myApiService == null) {  // Only do this once
            TellJoke.Builder builder = new TellJoke.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.10.5.127:8080/_ah/api/").
                            setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });;
            builder.setApplicationName("Some crazy App Name");
            myApiService = builder.build();
        }


        try {
            result = myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Integer result) {
        System.out.println("Response is "+this.result);
    }
}