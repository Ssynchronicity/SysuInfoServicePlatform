package com.example.song.sysuinfoserviceplatform.student;

import com.example.song.sysuinfoserviceplatform.activity.ActivityDataModel;
import com.example.song.sysuinfoserviceplatform.log_in_suyr3.Login_response;

public class myResponse {
    public Course_response cr;
    public Login_response lr;
    public Score_response sr;
    public ActivityDataModel be;

    public myResponse(Course_response c, Login_response l, Score_response s,
                      ActivityDataModel b, ActivityDataModel sp){
        this.cr = c;
        this.lr = l;
        this.sr = s;
        this.be = b;
    }
}
