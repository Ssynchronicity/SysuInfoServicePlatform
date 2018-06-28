package com.example.song.sysuinfoserviceplatform.student;

import com.example.song.sysuinfoserviceplatform.activity.ActivityData;

public class Data {
    public static String NetId;
    public static String pswd;
    public static String url;

    public static Course_response crs_rsps;
    public static Score_response scr_rsps;

    public static ActivityData one_bn_act;
    public static ActivityData one_spt_act;

    public static String[] courses_name;

    public int today_course_num;
    public int course_num;
    public int score_num;

    public static int semester = 1;
    public static int schoolyear;

    public static String[] today_courses_name;
    public static String[] today_courses_week;
    public static Boolean has_course = false;

    public static String[] section;
    public static String[] today_section;


    public static String[] score_name;
    public static String[] score_gpa;
    public static String[] score_rank;
    public static String[] score_type;
    public static Boolean[] score_display;

    public static String[] recent_activity_name;
    public static String[] recent_activity_club;
    public static String[] recent_activity_time;

    public int get_course_num(){
        return crs_rsps.data.length;
    }

    public static Boolean if_continue_1;
    public static Boolean if_continue_2;
    public static Boolean if_continue_3;


}
