package com.example.song.sysuinfoserviceplatform.student.stu_activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.activity.ActivityData;
import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.example.song.sysuinfoserviceplatform.activity.ValidModel;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_TYPE = "type";
    public static ActivityData act;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //final ActivityData act;
        if(bundle.getInt("type") == 1)
            act = (ActivityData)getIntent().getSerializableExtra("service");
        if(bundle.getInt("type") == 2)
            act = (ActivityData)getIntent().getSerializableExtra("sport");
        if(bundle.getInt("type") == 3)
            act = (ActivityData)getIntent().getSerializableExtra("ongoing");
        if(bundle.getInt("type") == 4)
            act = (ActivityData)getIntent().getSerializableExtra("apply");
        if(bundle.getInt("type") == 5)
            act = (ActivityData)getIntent().getSerializableExtra("finish");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(act.getAct_name());
        collapsingToolbar.setTitle(act.getAct_name());

        ImageView image = findViewById(R.id.image);
        Picasso.with(DetailActivity.this).load(act.getAct_pic()).into(image);

        TextView type = findViewById(R.id.activity_type);
        type.setText(act.getAct_type());

        TextView detail = findViewById(R.id.activity_detail);
        detail.setText(act.getAct_detail());

        TextView bonus = findViewById(R.id.activity_bonus);
        bonus.setText(act.getAct_bonus());

        TextView organiser = findViewById(R.id.activity_organiser);
        organiser.setText(act.getAct_organiser());

        TextView time = findViewById(R.id.activity_time);
        time.setText(act.getAct_time());

        TextView place = findViewById(R.id.activity_place);
        place.setText(act.getAct_place());

        TextView num = findViewById(R.id.hire_num);
        num.setText(""+act.getHire_num());

        TextView way = findViewById(R.id.hire_way);
        way.setText(act.getHire_way());

        TextView link = findViewById(R.id.hire_link);
        link.setText(act.getHire_link());



        Button btn = findViewById(R.id.public_button);
        int state  = act.getAct_state();
        int valid = act.getAct_valid();
        TextView status = findViewById(R.id.activity_state);
        if(state == -1)
            status.setText("未通过");
        if(state == 0)
            status.setText("未审批");
        if(state == 1 && valid == 0)
            status.setText("未发布");
        if(valid == 2)
            status.setText("招募中");
        if(valid == 3)
            status.setText("进行中（已停止报名）");
        if(valid == 4)
            status.setText("已结束");
        if(state == 0 || state == -1 || valid != 0)
            btn.setVisibility(GONE);


        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                setValid(act);
            }
        });
    }
    private void setValid(ActivityData act) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<ValidModel> call = request.setValid(act.getAct_name(),2);
        call.enqueue(new Callback<ValidModel>() {
            @Override
            public void onResponse(Call<ValidModel> call, Response<ValidModel> response) {
                /*String res = "";
                res = res+response.body().getValid();
                if(res.equals("setValidSuccese")) {
                    Log.d("修改状态","成功");
                    Toast.makeText(DetailActivity.this, "已成功发布",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Log.d("修改状态","失败");
                    Toast.makeText(DetailActivity.this, "发布失败",Toast.LENGTH_SHORT).show();
                }*/
                Toast.makeText(DetailActivity.this, "已成功发布",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ValidModel> call, Throwable t) {
                Log.d("Network request error", "Error! Network request error. ");
                Toast.makeText(DetailActivity.this, "网络异常，请稍后重试",Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {//点击back键finish当前activity
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
