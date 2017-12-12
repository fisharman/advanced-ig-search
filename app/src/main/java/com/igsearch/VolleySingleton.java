package com.igsearch;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class VolleySingleton {
    private static VolleySingleton instance;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static final String TAG = "VolleySingleton";

    private VolleySingleton(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();

        // copied from training guide
        imageLoader = new ImageLoader(this.requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                        Log.d(TAG, String.valueOf(cache.size()));
                    }
                });
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null){
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue (Request<T> req){
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }

}
