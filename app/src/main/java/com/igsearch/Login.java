package com.igsearch;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.concurrent.Callable;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        checkForInstagramData();

        if (globalVariable.getAccessToken() != null){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        else
        {
            // open webview
            setContentView(R.layout.activity_login);

            final Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .authority("api.instagram.com")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", "949a895ee1054b129b2e666b7c19216e")
                    .appendQueryParameter("redirect_uri", "https://localhost")
                    .appendQueryParameter("response_type", "token")
                    .appendQueryParameter("scope", "public_content");

            Log.d(TAG, "Opening Webview");
            WebView myWebView = (WebView) findViewById(R.id.webview);
            // myWebView.loadUrl("https://api.instagram.com/oauth/authorize/?client_id=949a895ee1054b129b2e666b7c19216e&redirect_uri=https://localhost&response_type=token");
            myWebView.loadUrl(uriBuilder.build().toString());
        }

    }

    private void checkForInstagramData() {
        final Uri data = this.getIntent().getData();

        Log.d(TAG, "in checkForInstagramData");

        if(data != null && data.getScheme().equals("https")){
            // valid sign in, go to next activity
            if(data.getFragment() != null){
                final String accessToken = data.getFragment().replaceFirst("access_token=", "");
                Log.d(TAG, "Log In Success");

                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                globalVariable.setAccessToken(accessToken);
            }
            // user cancel
            // https://localhost/?error_reason=user_denied&error=access_denied&error_description=The+user+denied+your+request.
            else if (data.toString().contains("error_reason=user_denied")){
                Log.d(TAG, "User Cancelled");
                // TODO 1: implement method where users are told they need to log in, they will then press a button to proceed
                // currently reloading the sign up screen...
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "INSIDE: onResume");

        //TODO 2: add button that brings user to next activity. otherwise when user will press back to a blank page

    }

}