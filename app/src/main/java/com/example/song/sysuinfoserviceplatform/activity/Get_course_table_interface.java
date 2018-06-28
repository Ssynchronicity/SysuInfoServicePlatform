package com.example.song.sysuinfoserviceplatform.activity;

import com.example.song.sysuinfoserviceplatform.student.Course_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Get_course_table_interface {
    @GET("nor_class?")
    Call<Course_response> getCall(@Query("user") String name, @Query("passwd") String pswd);
}
