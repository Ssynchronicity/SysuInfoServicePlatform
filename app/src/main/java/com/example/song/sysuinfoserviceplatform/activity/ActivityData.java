package com.example.song.sysuinfoserviceplatform.activity;

import java.io.Serializable;

public class ActivityData implements Serializable{
    private String slogon;
    private String shetuan;
    private String signupurl;
    private int people;
    private String description;
    private String value;
    private String timestart;
    private String class_;
    private int state;
    private int valid;
    private String place;
    private String jpg;
    private String way;
    private String name;

    public String getAct_slogan(){return slogon;}
    public String getAct_organiser(){return shetuan;}
    public String getHire_link(){return signupurl;}
    public int getHire_num(){return people;}
    public String getAct_detail(){return description;}


    public String getAct_name(){return name;}
    public String getAct_type(){return class_;}

    public String getAct_bonus(){return value;}

    public String getAct_time(){return timestart;}
    public String getAct_place(){return place;}

    public String getHire_way(){return way;}

    public int getAct_state(){return state;}

    public String getAct_pic(){return jpg;}
    public int getAct_valid(){return valid;}
}
