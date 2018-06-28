package com.example.song.sysuinfoserviceplatform.activity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import io.reactivex.Observable;

public interface RequestInterface {
    @GET("club_act_detail")
    Call<ActivityDataModel> getApplyList(@Query("club_name") String name, @Query("club_check") String check);

    //http://172.18.217.143:8000/club_set_act?act_name=xxx&valid=2
    //http://172.18.217.143:8000/club_set_act?act_name=xxx&valid=3
    @GET("club_set_act")
    Call<ValidModel> setValid(@Query("act_name") String name, @Query("valid") int valid);

    //http://172.18.217.143:8000/club_act_detail?club_name=xxx
    @GET("club_act_detail")
    Call<ActivityDataModel> getOngoingList(@Query("club_name") String name);

    //http://172.18.217.143:8000/club_act_detail?club_name=xxx&club_end=end
    @GET("club_act_detail")
    Call<ActivityDataModel> getFinishList(@Query("club_name") String name, @Query("club_end") String end);

    //http://172.18.217.143:8000/club_act_detail?club_type=公益类
    @GET("club_act_detail")
    Observable<ActivityDataModel> getStudentList(@Query("club_type") String type);

    //http://172.18.217.143:8000/club_detail?club=xxx
    @GET("club_detail")
    Call<ClubDataModel> getClubInfo(@Query("club") String name);

    //http://172.18.217.143:8000/club_act_register?
    @GET("club_act_register")
    Call<ResponseBody>applyActivity(@Query("name") String name, @Query("class_") String type,
                                         @Query("description") String detail, @Query("value") String bonus,
                                         @Query("shetuan") String organiser, @Query("timestart") String time,
                                         @Query("place") String place, @Query("people") int num,
                                         @Query("way") String way, @Query("signupurl") String link,
                                         @Query("state") int state, @Query("slogon") String slogan,
                                         @Query("jpg") String piclink, @Query("valid") int valid);

    //http://172.18.217.143:8000/club_register?
    @GET("club_register")
    Call<ResponseBody>clubRegister(@Query("name") String name, @Query("club_type") String type,
                                    @Query("club_detail") String detail, @Query("club_teacher") String teacher,
                                    @Query("club_governor") String governor, @Query("club_leader") String leader,
                                    @Query("club_mail") String mail, @Query("club_phone") String phone,
                                    @Query("club_password") String password);

    @Multipart
    @POST("club_upload")
    Call<ResponseBody>uploadPic(@Query("name")String name, @Part MultipartBody.Part image);

    ///club_login?name=xxx&passwd=123
    @GET("club_login")
    Call<ResponseBody> clubSignIn(@Query("name") String name, @Query("passwd")String pass);


}
