package com.example.song.sysuinfoserviceplatform.log_in_suyr3;

import android.content.Context;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.admin.RequestInterface;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login_tab_admin extends Fragment {
    private View view;
    private android.widget.EditText admin_name;
    private android.widget.EditText password;
    private android.widget.Button signin;
    private String name = "";
    private String pass = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_login_tab_admin, null);
        admin_name = view.findViewById(R.id.admin_name);
        password = view.findViewById(R.id.password);
        signin = view.findViewById(R.id.admin_sign_in_button);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = admin_name.getText().toString();
                pass = password.getText().toString();
                if(name.equals("")||pass.equals("")){
                    android.widget.Toast.makeText(getContext(), "请输入账号密码", android.widget.Toast.LENGTH_SHORT).show();
                }
                else{
                    signIn(name, pass);
                }
            }
        });

        return view;
    }

    private void signIn(final String name, String pass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<com.example.song.sysuinfoserviceplatform.admin.ChangeState> call = request.getCall_4(name, pass);
        call.enqueue(new Callback<com.example.song.sysuinfoserviceplatform.admin.ChangeState>() {
            @Override
            public void onResponse(Call<com.example.song.sysuinfoserviceplatform.admin.ChangeState> call, Response<com.example.song.sysuinfoserviceplatform.admin.ChangeState> response) {
                String rsps = response.body().getState();
                if(rsps.equals("loginSuccess")) {
                    Intent intent = new Intent("android.intent.action.Admin_Activity");
                    startActivity(intent);
                }
                else {
                    android.widget.Toast.makeText(getContext(), "账号或密码错误",android.widget.Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.song.sysuinfoserviceplatform.admin.ChangeState> call, Throwable t) {
                android.util.Log.d("Network request error", "Error! Network request error. ");
            }
        });

    }
}
