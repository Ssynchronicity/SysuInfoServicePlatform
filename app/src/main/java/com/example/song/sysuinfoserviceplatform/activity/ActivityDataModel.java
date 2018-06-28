package com.example.song.sysuinfoserviceplatform.activity;

import com.example.song.sysuinfoserviceplatform.activity.ActivityData;

import java.io.Serializable;

public class ActivityDataModel implements Serializable {
    private ActivityData[] data;
    public ActivityData[] getData(){ return data;}
    public void setData(ActivityData[] data){this.data = data;}
}
