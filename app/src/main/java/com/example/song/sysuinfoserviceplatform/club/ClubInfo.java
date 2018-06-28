package com.example.song.sysuinfoserviceplatform.club;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.ClubData;
import com.example.song.sysuinfoserviceplatform.activity.ClubDataModel;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class ClubInfo extends Fragment {

    private Toolbar toolbar;
    private View view;
    private static ArrayList<ClubData> clubdata;
    private ClubData data;
    private static int LENGTH;
    private globalClub app;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clubinfo, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        Button change = (Button)view.findViewById(R.id.submit_button);
        change.setVisibility(GONE);
        /*change.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent("android.intent.action.Info_Change");
                startActivity(intent);
            }
        });*/
        loadJSON();
        return view;
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        app = (globalClub)getActivity().getApplication();
        Call<ClubDataModel> call = request.getClubInfo(app.getName());
        call.enqueue(new Callback<ClubDataModel>(){
            @Override
            public void onResponse(Call<ClubDataModel> call, Response<ClubDataModel> response) {
                ClubDataModel activitymodel = response.body();
                clubdata = new ArrayList<>(Arrays.asList(activitymodel.getData()));
                LENGTH = clubdata.size();
                data = clubdata.get(0);

                TextView name = view.findViewById(R.id.name);
                name.setText(data.getClub_name());

                TextView type = view.findViewById(R.id.type);
                type.setText(data.getClub_type());

                TextView detail = view.findViewById(R.id.detail);
                detail.setText(data.getClub_detail());

                TextView teacher = view.findViewById(R.id.teacher);
                teacher.setText(data.getClub_teacher());

                TextView governor = view.findViewById(R.id.governor);
                governor.setText(data.getClub_governor());

                TextView leader = view.findViewById(R.id.leader);
                leader.setText(data.getClub_leader());

                TextView mail = view.findViewById(R.id.mail);
                mail.setText(data.getClub_mail());

                TextView phone = view.findViewById(R.id.phone);
                phone.setText(data.getClub_phone());

                ImageView logo = view.findViewById(R.id.logo);
                Picasso.with(getActivity()).load("http://172.18.217.143:8000/getpic?name="+data.getClub_name()).into(logo);
            }

            @Override
            public void onFailure(Call<ClubDataModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
                //Toast.makeText(getActivity(), "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
