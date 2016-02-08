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

import com.udacity.gradle.jokedisplay.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements OnReceivedListener{

    Context context;
    ProgressDialog progressDialog;
    boolean isTaskRunning = false;
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isTaskRunning) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    Button tellAJoke;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
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
        isTaskRunning = true;
        progressDialog.show();
    }
    @Override
    public void onReceived(String result) {
        isTaskRunning = false;
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
