package com.example.song.sysuinfoserviceplatform.activity;

import com.example.song.sysuinfoserviceplatform.log_in_suyr3.Login_response;
import com.example.song.sysuinfoserviceplatform.student.Course_response;
import com.example.song.sysuinfoserviceplatform.student.Score_response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Stu_enter {
    @GET("nor_class?")
    Observable<Course_response> getCall_course(@Query("user") String name, @Query("passwd") String pswd);

    @GET("nor_login?")
    Observable<Login_response> getCall_login(@Query("user") String name, @Query("passwd") String pswd);

    @GET("nor_score?")
    Observable<Score_response> getCall_score(@Query("user") String name, @Query("passwd") String pswd);

    //http://172.18.217.143:8000/club_act_detail?club_type=公益类
    @GET("club_act_detail")
    Observable<ActivityDataModel> getStudentList_bene(@Query("club_type") String type);

    @GET("club_act_detail")
    Observable<ActivityDataModel> getStudentList_sp(@Query("club_type") String type);


}
