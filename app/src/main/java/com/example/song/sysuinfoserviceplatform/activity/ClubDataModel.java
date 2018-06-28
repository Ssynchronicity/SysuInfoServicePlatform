package com.example.song.sysuinfoserviceplatform.activity;

import java.io.Serializable;

public class ClubDataModel implements Serializable{
    private ClubData[] data;
    public ClubData[] getData(){ return data;}
    public void setData(ClubData[] data){this.data = data;}
}
