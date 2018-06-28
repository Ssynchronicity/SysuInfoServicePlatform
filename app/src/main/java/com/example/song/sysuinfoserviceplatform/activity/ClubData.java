package com.example.song.sysuinfoserviceplatform.activity;

import java.io.Serializable;

public class ClubData implements Serializable {
    private String name;
    private String phone;
    private String description;
    private String danwei;
    private String email;
    private String class_;
    private int state;
    private String teacher;
    private String first;

    public String getClub_name(){return name;}
    public String getClub_phone(){return phone;}
    public String getClub_detail(){return description;}
    public String getClub_governor(){return danwei;}
    public String getClub_mail(){return email;}
    public String getClub_type(){return class_;}
    public int getClub_state(){return state;}
    public String getClub_teacher(){return teacher;}
    public String getClub_leader(){return first;}
}
