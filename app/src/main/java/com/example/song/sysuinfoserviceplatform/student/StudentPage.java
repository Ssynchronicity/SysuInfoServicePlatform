package com.example.song.sysuinfoserviceplatform.student;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentPage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;

    public void handle_data(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday = "fool'sDay";
        switch (day){
            case Calendar.SUNDAY + 6:
                weekday = "sunday";
                break;

            case Calendar.MONDAY - 1:
                weekday = "monday";
                break;
            case Calendar.TUESDAY - 1:
                weekday = "tuesday";
                break;

            case Calendar.WEDNESDAY - 1:
                weekday = "wednesday";
                break;
            case Calendar.THURSDAY - 1:
                weekday = "thursday";
                break;

            case Calendar.FRIDAY - 1:
                weekday = "friday";
                break;
            case Calendar.SATURDAY - 1:
                weekday = "saturday";
                break;
        }

        List<String> today_course = new ArrayList<>();
        List<String> today_course_section = new ArrayList<>();


        for(myTable table: Data.crs_rsps.data){
            if(table.monday != null && weekday.equals("monday")){
                today_course.add(table.monday);
                today_course_section.add("Monday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.tuesday != null && weekday.equals("tuesday")){
                today_course.add(table.tuesday);
                today_course_section.add("Tuesday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.wednesday != null && weekday.equals("wednesday")){
                today_course.add(table.wednesday);
                today_course_section.add("Wednesday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.thursday != null && weekday.equals("thursday")){
                today_course.add(table.thursday);
                today_course_section.add("Thursday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.friday != null && weekday.equals("friday")){
                today_course.add(table.friday);
                today_course_section.add("Friday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.saturday != null && weekday.equals("saturday")){
                today_course.add(table.saturday);
                today_course_section.add("Saturday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
            if(table.sunday != null && weekday.equals("sunday")){
                today_course.add(table.sunday);
                today_course_section.add("Sunday, 第" + Integer.toString(table.section) + "节");
                Data.has_course = true;
            }
        }

        List<String> all_course = new ArrayList<>();
        List<String> all_course_section = new ArrayList<>();

        List<String> name1 = new ArrayList<>();
        List<String> name2 = new ArrayList<>();
        List<String> name3 = new ArrayList<>();
        List<String> name4 = new ArrayList<>();
        List<String> name5 = new ArrayList<>();
        List<String> name6 = new ArrayList<>();
        List<String> name7 = new ArrayList<>();

        List<String> time1 = new ArrayList<>();
        List<String> time2 = new ArrayList<>();
        List<String> time3 = new ArrayList<>();
        List<String> time4 = new ArrayList<>();
        List<String> time5 = new ArrayList<>();
        List<String> time6 = new ArrayList<>();
        List<String> time7 = new ArrayList<>();



        for(myTable table:Data.crs_rsps.data){
            if(table.monday != null){
                name1.add(table.monday);
                time1.add("Monday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.tuesday != null){
                name2.add(table.tuesday);
                time2.add("Tuesday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.wednesday != null){
                name3.add(table.wednesday);
                time3.add("Wednesday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.thursday != null){
                name4.add(table.thursday);
                time4.add("Thursday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.friday != null){
                name5.add(table.friday);
                time5.add("Friday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.saturday != null){
                name6.add(table.saturday);
                time6.add("Saturday, 第" + Integer.toString(table.section) + "节");
            }
            if(table.sunday != null){
                name7.add(table.sunday);
                time7.add("Sunday, 第" + Integer.toString(table.section) + "节");
            }
        }

        if(!Data.has_course){
            return;
        }

        all_course.clear();
        all_course_section.clear();

        all_course.addAll(name1);
        all_course.addAll(name2);
        all_course.addAll(name3);
        all_course.addAll(name4);
        all_course.addAll(name5);
        all_course.addAll(name6);
        all_course.addAll(name7);

        all_course_section.addAll(time1);
        all_course_section.addAll(time2);
        all_course_section.addAll(time3);
        all_course_section.addAll(time4);
        all_course_section.addAll(time5);
        all_course_section.addAll(time6);
        all_course_section.addAll(time7);


        Data.today_courses_name =  today_course.toArray(new String[today_course.size()]);
        Data.courses_name = all_course.toArray(new String[all_course.size()]);
        Data.today_section = today_course_section.toArray(new String[today_course_section.size()]);
        Data.section = all_course_section.toArray(new String[all_course_section.size()]);

        List<String> s_name = new ArrayList<>();
        List<String> s_gpa = new ArrayList<>();
        List<String> s_rank = new ArrayList<>();
        List<String> s_type = new ArrayList<>();
        List<Boolean> display = new ArrayList<>();
        for(Detail detail:Data.scr_rsps.detail){
            s_name.add(detail.scoCourseName);
            s_gpa.add(Double.toString(detail.scoPoint));
            s_rank.add(detail.teachClassRank);
            s_type.add(detail.scoCourseCategoryName);
            display.add(true);
        }
        Data.score_name = s_name.toArray(new String[s_name.size()]);
        Data.score_gpa = s_gpa.toArray(new String[s_gpa.size()]);
        Data.score_rank = s_rank.toArray(new String[s_rank.size()]);
        Data.score_type = s_type.toArray(new String[s_type.size()]);
        Data.score_display = display.toArray(new Boolean[display.size()]);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///initData_course();
        ///initData_score();
        handle_data();
        setContentView(R.layout.studentdrawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.student_drawer);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_mymain);
        Fragment defaultFrag = null;
        try {
            defaultFrag = (Fragment) StudentMain.class.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.stuMain_contentView, defaultFrag).commit();
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
    public void onStop() {
        Log.i("Activity_stuPage","onStop");
        super.onStop();
    }
    @Override
    public void onDestroy() {
        Log.i("Activity_stuPage","onDestroy");
        super.onDestroy();
    }


    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_mymain:
                fragmentClass = StudentMain.class;
                break;
            case R.id.nav_activity:
                fragmentClass = StudentActivity.class;
                break;
            case R.id.nav_course:
                fragmentClass = Course_detail.class;
                break;
            case R.id.nav_score:
                fragmentClass = Score_detail.class;
                break;
            default:
                fragmentClass = StudentMain.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.stuMain_contentView, fragment).commit();

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

}
