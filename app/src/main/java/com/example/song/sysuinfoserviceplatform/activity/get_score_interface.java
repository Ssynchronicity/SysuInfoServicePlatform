package com.example.song.sysuinfoserviceplatform.activity;

import com.example.song.sysuinfoserviceplatform.student.Score_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface get_score_interface {
    @GET("nor_score?")
    Call<Score_response> getCall(@Query("user") String name, @Query("passwd") String pswd);

}
