package com.example.song.sysuinfoserviceplatform.admin;

import java.io.Serializable;

/**
 * Created by song on 2018/5/27.
 */

public class CardData implements Serializable{
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

    public String getClass_() {
        return class_;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getPeople() {
        return people;
    }

    public int getState() {
        return state;
    }

    public int getValid() {
        return valid;
    }

    public String getJpg() {
        return jpg;
    }

    public String getPlace() {
        return place;
    }

    public String getShetuan() {
        return shetuan;
    }

    public String getSignupurl() {
        return signupurl;
    }

    public String getSlogon() {
        return slogon;
    }

    public String getTimestart() {
        return timestart;
    }

    public String getValue() {
        return value;
    }

    public String getWay() {
        return way;
    }

    public void setState(int state) {
        this.state = state;
    }
}
