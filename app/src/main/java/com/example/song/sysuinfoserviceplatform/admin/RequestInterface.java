package com.example.song.sysuinfoserviceplatform.admin;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by song on 2018/6/1.
 */

public interface RequestInterface {
    //网络请求1
    @GET("check_shetuan_or_act?")
    Observable<ListDataModel> getCall_1(@Query("do") String type);

    //网络请求2
    @GET("check_shetuan_or_act?")
    Observable<CardDataModel> getCall_2(@Query("do") String type);

    //网络请求3
    @GET("set_shetuan_or_act?")
    Call<ChangeState> getCall_3(@Query("do") String type, @Query("state") int state, @Query("name") String name);

    //网络请求4
    @GET("admin_log?")
    Call<ChangeState> getCall_4(@Query("name") String name, @Query("passwd") String password);

}
