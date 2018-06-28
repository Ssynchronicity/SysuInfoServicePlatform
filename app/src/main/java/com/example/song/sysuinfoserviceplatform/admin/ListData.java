package com.example.song.sysuinfoserviceplatform.admin;


import android.content.Intent;

import java.io.Serializable;

/**
 * Created by song on 2018/5/20.
 */

public class ListData implements Serializable {
    private String name;
    private String phone;
    private String description;
    private String danwei;
    private String email;
    private String class_;
    private int state;
    private String teacher;
    private String first;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getDanwei() {
        return danwei;
    }

    public String getEmail() {
        return email;
    }

    public String getClass_() {
        return class_;
    }

    public int getState() {
        return state;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getFirst() {
        return first;
    }

    public void setState(int state) {
        this.state = state;
    }
}

