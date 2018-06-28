package com.example.song.sysuinfoserviceplatform.admin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.song.sysuinfoserviceplatform.R;

/**
 * Created by song on 2018/5/19.
 */

public class DetailClub extends AppCompatActivity {
    private Toolbar toolbar;
    private boolean isSelected = false;
    private boolean isSelected1 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_club_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar1));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final ListData club = (ListData) getIntent().getSerializableExtra("club");
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(club.getName());

        EditText property = findViewById(R.id.editText1);
        property.setText(club.getClass_());
        property.setEnabled(false);

        EditText desc = findViewById(R.id.editText3);
        desc.setText(club.getDescription());
        desc.setEnabled(false);

        EditText teacher = findViewById(R.id.editText4);
        teacher.setText(club.getTeacher());
        teacher.setEnabled(false);

        EditText institution = findViewById(R.id.editText5);
        institution.setText(club.getDanwei());
        institution.setEnabled(false);

        EditText first = findViewById(R.id.editText6);
        first.setText(club.getFirst());
        first.setEnabled(false);

        EditText email = findViewById(R.id.editText7);
        email.setText(club.getEmail());
        email.setEnabled(false);

        EditText phone = findViewById(R.id.editText8);
        phone.setText(club.getPhone());
        phone.setEnabled(false);

        final Button btn1 = findViewById(R.id.button1);
        final Button btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(DetailClub.this,"您已成功将名为\""+club.getName()+"\"的社团的状态修改为通过审核",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,0);
                toast.show();
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                isSelected = true;
                club.setState(1);
                SetState(club);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(DetailClub.this,"您已成功将名为\""+club.getName()+"\"的社团的状态修改为未通过审核",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,0);
                toast.show();
                btn2.setEnabled(false);
                btn1.setEnabled(false);
                isSelected1 = true;
                club.setState(-1);
                SetState(club);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && !isSelected && !isSelected1){
            Toast toast = Toast.makeText(DetailClub.this, "请选择该社团是否通过审核", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void SetState(ListData club) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ChangeState> call = request.getCall_3("shetuan",club.getState(),club.getName());
        call.enqueue(new Callback<ChangeState>() {
            @Override
            public void onResponse(Call<ChangeState> call, Response<ChangeState> response) {
                String rsps = response.body().getState();
                if(rsps.equals("setStateSuccess")) {
                    Log.d("修改状态","成功");
                }
                else {
                    Log.d("修改状态","失败");
                }
            }

            @Override
            public void onFailure(Call<ChangeState> call, Throwable t) {
                Log.d("Network request error", "Error! Network request error. ");
            }
        });
    }
}
