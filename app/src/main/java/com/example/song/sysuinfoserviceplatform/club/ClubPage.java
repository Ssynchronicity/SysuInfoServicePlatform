package com.example.song.sysuinfoserviceplatform.club;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.ClubData;
import com.example.song.sysuinfoserviceplatform.activity.ClubDataModel;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.example.song.sysuinfoserviceplatform.club.ClubApply;
import com.example.song.sysuinfoserviceplatform.club.ClubFinish;
import com.example.song.sysuinfoserviceplatform.club.ClubInfo;
import com.example.song.sysuinfoserviceplatform.club.ClubOngoing;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubPage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private globalClub app;
    private static ArrayList<ClubData> clubdata;
    private ClubData data;
    private CircleImageView icon;
    private TextView clubname;
    private View view;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubdrawer_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.club_drawer);
        navView = (NavigationView) findViewById(R.id.nav_view);
        icon = navView.getHeaderView(0).findViewById(R.id.icon_image);
        clubname = navView.getHeaderView(0).findViewById(R.id.school_num);
        loadJSON();
    }

    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_ongoing:
                fragmentClass = ClubOngoing.class;
                break;
            case R.id.nav_finish:
                fragmentClass = ClubFinish.class;
                break;
            case R.id.nav_apply:
                fragmentClass = ClubApply.class;
                break;
            case R.id.nav_clubinfo:
                fragmentClass = ClubInfo.class;
                break;
            default:
                fragmentClass = ClubOngoing.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.clubMain_contentView, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.log_out:
                Toast.makeText(this, "已退出", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent("android.intent.action.MAIN");
                startActivity(intent);*/
                finish();
                break;
            default:
        }
        return true;
    }

    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        app = (globalClub)getApplication();
        Call<ClubDataModel> call = request.getClubInfo(app.getName());
        call.enqueue(new Callback<ClubDataModel>(){
            @Override
            public void onResponse(Call<ClubDataModel> call, Response<ClubDataModel> response) {
                ClubDataModel activitymodel = response.body();
                clubdata = new ArrayList<>(Arrays.asList(activitymodel.getData()));
                data = clubdata.get(0);
                clubname.setText(data.getClub_name());
                Picasso.with(ClubPage.this).load("http://172.18.217.143:8000/getpic?name="+data.getClub_name()).into(icon);
                navView.setCheckedItem(R.id.nav_ongoing);
                Fragment defaultFrag = null;
                try {
                    defaultFrag = (Fragment) ClubOngoing.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.clubMain_contentView, defaultFrag).commit();
                navView.setNavigationItemSelectedListener(
                        new NavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                selectDrawerItem(item);

                                return true;
                            }
                        }
                );


            }

            @Override
            public void onFailure(Call<ClubDataModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
                //Toast.makeText(getActivity(), "网络异常，请稍后重试", Toast.LENGTH_LONG).show();
                Toast.makeText(ClubPage.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
