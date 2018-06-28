package com.example.song.sysuinfoserviceplatform.activity;

import com.example.song.sysuinfoserviceplatform.log_in_suyr3.Login_response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Get_login_state_interface {
    @GET("nor_login?")
    Call<Login_response> getCall(@Query("user") String name, @Query("passwd") String pswd);
};
