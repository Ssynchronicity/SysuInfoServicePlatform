package com.example.song.sysuinfoserviceplatform;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class StudentPage extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentdrawer_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.student_drawer);
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

                break;
            case R.id.nav_score:
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
            case R.id.my_activity:
                Toast.makeText(this, "我的活动", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_notification:
                Toast.makeText(this, "我的通知",Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_time:
                Toast.makeText(this, "我的公益时", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
