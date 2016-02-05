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
    Context context;
    ProgressDialog progressDialog;
    public EndpointsAsyncTask(Context context,OnReceivedListener listner){
        this.listener = listner;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.progress_dialog_text));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("executing do in background");
        if(myApiService == null) {  // Only do this once
            TellJoke.Builder builder = new TellJoke.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(context.getString(R.string.endpoint_url)).
                            setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });;
            builder.setApplicationName(context.getString(R.string.app_name));
            myApiService = builder.build();

        }


        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return context.getString(R.string.error_string);
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println("Response is "+result);
        progressDialog.dismiss();
        listener.onReceived(result);
    }
}