package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
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
public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {
    private static TellJoke myApiService = null;
    OnReceivedListener listener;
    String endPoint;
    public EndpointsAsyncTask(String endPoint,OnReceivedListener listener){
        this.listener = listener;
        this.endPoint = endPoint;
    }


    @Override
    protected String doInBackground(String... params) {
        System.out.println("executing do in background");
        if(myApiService == null) {  // Only do this once
            TellJoke.Builder builder = new TellJoke.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(endPoint).
                            setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();

        }


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("Response is " + result);
        listener.onReceived(result);
    }
}