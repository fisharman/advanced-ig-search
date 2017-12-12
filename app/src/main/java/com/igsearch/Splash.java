package com.igsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Splash extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        /*
        // temp, to skip log in during development
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setAccessToken("");
        Intent intent = new Intent(this, SearchActivity.class);
        */

        Intent intent = new Intent(this, Login.class);


        startActivity(intent);

        finish();

    }

}
