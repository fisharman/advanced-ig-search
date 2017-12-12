package com.igsearch;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class DisplayPictureActivity extends AppCompatActivity {
    private static final String TAG = "DisplayActivity";

    private Map<String, String> tagResult;
    private Map<String, String> userResult;
    private Map<String, String> locationResult;

    private AtomicInteger responseRxd = new AtomicInteger(0);
    private ImageAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_display_picture);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // TODO 8: implement up button to return to search activity
        // TODO 9: show the search terms. see design sketch

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        String accessToken = globalVariable.getAccessToken();

        VolleyCallback callback = new JSONCallback();

        if (getIntent().hasExtra("tag")){
            final String tag = getIntent().getExtras().getString("tag");
            tagResult = new HashMap<>();
            Log.d(TAG, "searching tag");
            searchByTag(tag, accessToken, callback);
        }

        if (getIntent().hasExtra("location")){
            final String tag = getIntent().getExtras().getString("location");
            locationResult = new HashMap<>();
            Log.d(TAG, "searching location");
            searchByLocation(tag, accessToken, callback);
        }

        if (getIntent().hasExtra("user")){
            final String tag = getIntent().getExtras().getString("user");
            userResult = new HashMap<>();
            Log.d(TAG, "searching user");
            searchUserID(tag, accessToken, callback);
        }

        GridView gridview = (GridView) findViewById(R.id.gridview);
        iAdapter = new ImageAdapter(this);
        gridview.setAdapter(iAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(DisplayPictureActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void searchByTag (String tag, String accessToken, final VolleyCallback callback){

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        // https://api.instagram.com/v1/tags/{tag}/media/recent?access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("v1")
                .appendPath("tags")
                .appendPath(tag)
                .appendPath("media")
                .appendPath("recent")
                .appendQueryParameter("access_token", accessToken);

        String url = uriBuilder.toString();

        // String url = "https://api.instagram.com/v1/tags/dougong/media/recent?access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("volley", "starting callback");
                        callback.getImageThumb(response, tagResult);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "searchByTag response error");
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void searchUserID (String user, final String accessToken, final VolleyCallback callback){

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // VolleyCallback tagCallback = new JSONCallback();

        // for testing ONLY!
        //
        //
        //
        //user = "celineliew";
        //

        // https://api.instagram.com/v1/tags/{tag}/media/recent?access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("v1")
                .appendPath("users")
                .appendPath("search")
                .appendQueryParameter("q", user)
                .appendQueryParameter("access_token", accessToken);

        String url = uriBuilder.toString();

        // String url = https://api.instagram.com/v1/users/search?q=fisharman&access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("volley", "starting callback");
                        searchByUser(callback.getID(response),accessToken, callback);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "searchUserID response error");
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    private void searchByUser (String user, String accessToken, final VolleyCallback callback){

        RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        // https://api.instagram.com/v1/tags/{tag}/media/recent?access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5
        final Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority("api.instagram.com")
                .appendPath("v1")
                .appendPath("users")
                .appendPath(user)
                .appendPath("media")
                .appendPath("recent")
                .appendQueryParameter("access_token", accessToken);

        String url = uriBuilder.toString();

        // String url = "https://api.instagram.com/v1/tags/dougong/media/recent?access_token=9438970.949a895.4ce6e9fa1ce445938a6eea770e7c5eb5";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("volley", "starting callback");
                        callback.getImageThumb(response, userResult);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("volley", "searchByUser response error");
                    }
                });

        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    public interface VolleyCallback{
        // TODO 7: move to separate class
        void getImageThumb(JSONObject response, Map<String, String> map);
        String getID(JSONObject response);
    }

    class JSONCallback implements VolleyCallback{
        // TODO 7: move to separate class
        @Override
        public void getImageThumb(JSONObject response, Map<String, String> map){

            try {
                JSONArray jArray = response.getJSONArray("data");

                for (int i = 0 ; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);

                    String thumbNailURL = jObject
                            .getJSONObject("images")
                            .getJSONObject("thumbnail")
                            .getString("url");

                    map.put(jObject.getString("id"), thumbNailURL);

                    Log.d("Callback", jObject.getString("id"));
                    Log.d("Callback", thumbNailURL);
                }
                responseRxd.getAndIncrement();
            }
            catch (Exception e){
                Log.d("Callback", "tag JSON error");
            }

        }

        @Override
        public String getID(JSONObject response){

            try {
                JSONArray jArray = response.getJSONArray("data");
                JSONObject jObject = jArray.getJSONObject(0);
                return jObject.getString("id");
            }
            catch (Exception e){
                Log.d("Callback", "tag JSON error");
            }
            return null;
        }
    }


    private void searchByLocation(String location, String accessToken, final VolleyCallback callback){
        /* TODO 6 : implement location name to coordinate translation using google map API
           require google maps API for location coordinates
        */
    }

    private class CombineMap extends AsyncTask<Map<String, String>, Void, Map<String, String>>{

        @Override
        protected Map<String, String> doInBackground(Map<String, String>... maps) {

            // make sure only valid maps (!=null) are passed to recursiveMap
            // valid map include map of size 0
            // asList is immutable
            List<Map<String, String>> mapList = new ArrayList<>(Arrays.asList(maps));
            while(mapList.remove(null));

            if (mapList.size() == 1)
                return maps[0];

            // TODO 5: show loading screen while search thread is underway

            // waits for all maps to be ready
            while (responseRxd.get() < mapList.size());

            responseRxd.set(0);

            Map<String, String> combinedMap = new HashMap<>();
            combinedMap = recursiveCombineMap(mapList.subList(0, mapList.size()-1), mapList.get(mapList.size()-1));

            for (int i = 0; i < maps.length; i++){
                if (maps[i] != null)
                    maps[i].clear();
            }
            return combinedMap;
        }

        @Override
        protected void onPostExecute(Map<String, String> combinedMap){
            // adapter update
            iAdapter.setImgURLs(combinedMap);
            iAdapter.notifyDataSetChanged();

        }

        private Map<String, String> recursiveCombineMap (List<Map<String, String>> mapList, Map<String,String> map){
            Map<String, String> rtMap;
            Map<String, String> cbMap = new HashMap<>();

            if (mapList.size() > 1){
                // call function again with smaller set
                rtMap = recursiveCombineMap(mapList.subList(0, mapList.size() - 1), mapList.get(mapList.size() - 1));
            }
            else{
                rtMap = mapList.get(0);
            }

            for (Map.Entry<String,String> entry : rtMap.entrySet()){
                if (map.containsKey(entry.getKey())){
                    cbMap.put(entry.getKey(), entry.getValue());
                }
            }
            return cbMap;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "INSIDE: onResume");

        new CombineMap().execute(tagResult,userResult,locationResult);
    }


}
