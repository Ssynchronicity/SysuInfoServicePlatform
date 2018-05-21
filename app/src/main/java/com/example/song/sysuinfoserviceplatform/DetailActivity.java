package com.example.song.sysuinfoserviceplatform;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_TYPE = "type";

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

        int postion = bundle.getInt(EXTRA_POSITION, 0);
        int type = bundle.getInt(EXTRA_TYPE);
        int resName = 0,resPlace = 0,resDetail = 0,resTime = 0,resPic = 0;
        if(type == 1){
            resName =R.array.places;
            resPlace = R.array.place_locations;
            resDetail=R.array.place_details;
            resTime=R.array.service_time;
            resPic = R.array.places_picture;
        }
        else if(type == 2){
            resName =R.array.sports;
            resPlace = R.array.place_locations;
            resDetail=R.array.sport_details;
            resTime=R.array.service_time;
            resPic = R.array.sport_pictures;
        }
        Resources resources = getResources();
        String[] places = resources.getStringArray(resName);
        collapsingToolbar.setTitle(places[postion % places.length]);

        String[] placeDetails = resources.getStringArray(resDetail);
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(placeDetails[postion % placeDetails.length]);

        String[] placeLocations = resources.getStringArray(resPlace);
        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText(placeLocations[postion % placeLocations.length]);

        String[] ActivityTimes = resources.getStringArray(resTime);
        TextView ActivityTime = (TextView) findViewById(R.id.activity_time);
        ActivityTime.setText(ActivityTimes[postion % placeDetails.length]);

        TypedArray placePictures = resources.obtainTypedArray(resPic);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));

        placePictures.recycle();
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
