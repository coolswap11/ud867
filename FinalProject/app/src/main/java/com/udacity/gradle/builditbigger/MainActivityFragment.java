package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.jokedisplay.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnReceivedListener{

    Context context;
    ProgressDialog progressDialog;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    Button tellAJoke;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        tellAJoke = (Button)root.findViewById(R.id.btn_tellAJoke);
        tellAJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });
        setRetainInstance(true);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        return root;
    }
    public void tellJoke(){
        String endPoint = context.getString(R.string.endpoint_url);
        new EndpointsAsyncTask(endPoint,this).execute();
        progressDialog.show();
    }
    @Override
    public void onReceived(String result) {
        progressDialog.dismiss();
        Intent intent = new Intent(context, JokeActivity.class);
        if(result !=null) {
            intent.putExtra(JokeActivity.JOKE_KEY, result);
        }
        else{
            intent.putExtra(JokeActivity.JOKE_KEY, context.getString(R.string.error_string));
        }
        startActivity(intent);
    }
}
