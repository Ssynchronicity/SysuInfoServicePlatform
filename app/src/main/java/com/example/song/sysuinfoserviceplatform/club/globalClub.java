package com.example.song.sysuinfoserviceplatform.club;

import android.app.Application;

public class globalClub extends Application {
    private String name;
    public String getName(){
        return this.name;
    }
    public void setName(String ame){
        this.name = ame;
    }

    @Override
    public void onCreate(){
        setName("中大青协");
        super.onCreate();
    }
}
