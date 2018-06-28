/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.song.sysuinfoserviceplatform.admin;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.song.sysuinfoserviceplatform.R;


/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private boolean isSelected = false;
    private boolean isSelected1 = false;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar1));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final CardData act = (CardData) getIntent().getSerializableExtra("act");
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle(act.getName());

        EditText type = findViewById(R.id.editText1);
        type.setText(act.getClass_());
        type.setEnabled(false);

        EditText desc = findViewById(R.id.editText3);
        desc.setText(act.getDescription());
        desc.setEnabled(false);

        EditText value = findViewById(R.id.editText11);
        value.setText(act.getValue());
        value.setEnabled(false);

        final EditText club = findViewById(R.id.editText10);
        club.setText(act.getShetuan());
        club.setEnabled(false);

        EditText time = findViewById(R.id.editText4);
        time.setText(act.getTimestart());
        time.setEnabled(false);

        EditText place = findViewById(R.id.editText5);
        place.setText(act.getPlace());
        place.setEnabled(false);

        EditText people = findViewById(R.id.editText6);
        people.setText(""+act.getPeople());
        people.setEnabled(false);

        EditText way = findViewById(R.id.editText7);
        way.setText(act.getWay());
        way.setEnabled(false);

        EditText link = findViewById(R.id.editText8);
        link.setText(act.getSignupurl());
        link.setEnabled(false);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(DetailActivity.this,"您已成功将名为\""+act.getName()+"\"的活动的状态修改为通过审核",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,0);
                toast.show();
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                isSelected = true;
                act.setState(1);
                SetState(act);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(DetailActivity.this,"您已成功将名为\""+act.getName()+"\"的活动的状态修改为未通过审核",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,0);
                toast.show();
                btn2.setEnabled(false);
                btn1.setEnabled(false);
                isSelected1 = true;
                act.setState(-1);
                SetState(act);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && !isSelected && !isSelected1){
            Toast toast = Toast.makeText(DetailActivity.this, "请选择该活动是否通过审核", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void SetState(CardData act) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ChangeState> call = request.getCall_3("activity",act.getState(),act.getName());
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
