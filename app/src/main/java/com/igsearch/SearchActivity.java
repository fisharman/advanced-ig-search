package com.igsearch;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);

        // TODO 4: implement setting and logout feature listed in menu
    }

    public void startSearch(View view){
        Intent intent = new Intent (this, DisplayPictureActivity.class);

        EditText tagText = (EditText) findViewById(R.id.tagText);
        EditText locationText = (EditText) findViewById(R.id.locationText);
        EditText userText = (EditText) findViewById(R.id.userText);

        if (tagText.getText().length() > 0){
            String tag = tagText.getText().toString();
            intent.putExtra("tag", tag);
            Log.d(TAG, tag);
        }

        if (locationText.getText().length() > 0){
            String location = locationText.getText().toString();
            intent.putExtra("location", location);
            Log.d(TAG, location);
        }

        if (userText.getText().length() > 0){
            String user = userText.getText().toString();
            intent.putExtra("user", user);
            Log.d(TAG, user);
        }

        if (intent.getExtras() != null){
            startActivity(intent);
        }
        else{
            // TODO 3: display at least one input is required
        }



    }


}
