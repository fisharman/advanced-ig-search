package com.igsearch;

import android.app.Application;

// for storage and retrieval of IG API access info
public class GlobalClass extends Application {
    private String accessToken;

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

}
