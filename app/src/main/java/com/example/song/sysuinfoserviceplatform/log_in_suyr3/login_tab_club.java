package com.example.song.sysuinfoserviceplatform.log_in_suyr3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.example.song.sysuinfoserviceplatform.club.globalClub;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login_tab_club extends Fragment {
    private EditText clubname;
    private EditText password;
    private Button signin;
    private Button signup;
    private String name = "";
    private String pass = "";
    private View view;
    private globalClub app;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_login_tab_club, null);
        clubname = view.findViewById(R.id.clubname);
        password = view.findViewById(R.id.password);
        signin = view.findViewById(R.id.club_sign_in_button);
        signup = view.findViewById(R.id.club_sign_up_button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = clubname.getText().toString();
                pass = password.getText().toString();
                if(name.equals("")||pass.equals("")){
                    Toast.makeText(getContext(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                }
                else{
                    signIn(name, pass);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.Club_Register");
                startActivity(intent);
            }
        });
        return view;
    }

    private void signIn(final String name, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ResponseBody> call = request.clubSignIn(name, pass);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //Log.i("state", response.body().string());
                    String res = response.body().string();
                    if(res.contains("error")){
                        Toast.makeText(getContext(), "账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                    if(res.contains("loginSuccess")){
                        Toast.makeText(getContext(), "登陆成功",Toast.LENGTH_SHORT).show();
                        app = (globalClub)getActivity().getApplication();
                        app.setName(name);
                        Intent intent = new Intent("android.intent.action.Club_Page");
                        startActivity(intent);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Network request error", "Error! Network request error. ");
                Toast.makeText(getContext(), "Error:"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private OkHttpClient getOkHttpClient() {
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("获取","OkHttp====Message:"+message);
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return okHttpClient;
    }
}
