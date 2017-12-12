package com.igsearch;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;
import java.util.Map;


public class ImageAdapter extends BaseAdapter {
    private Context iContext;
    private String[] imgURLs;

    public ImageAdapter(Context iContext){
        this.iContext = iContext;
    }

    @Override
    public int getCount() {
        // return size of map
        if (imgURLs == null)
            return 0;
        return imgURLs.length;
    }

    public void setImgURLs (Map<String, String> map){
        imgURLs = map.values().toArray(new String[map.size()]);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ImageLoader imageLoader;
        NetworkImageView imageView;

        //ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            DisplayMetrics metrics = iContext.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels;

            imageView = new NetworkImageView(iContext);
            imageView.setLayoutParams(new GridView.LayoutParams(screenWidth/3, screenWidth/3));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (NetworkImageView) convertView;
        }

        imageLoader = VolleySingleton.getInstance(iContext).getImageLoader();
        imageView.setImageUrl(imgURLs[position], imageLoader);

        return imageView;
    }




}
