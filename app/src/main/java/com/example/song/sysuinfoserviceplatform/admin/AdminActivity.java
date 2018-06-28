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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.song.sysuinfoserviceplatform.R;

/**
 * Provides UI for the main screen.
 */
public class AdminActivity extends AppCompatActivity {
    private ListContentFragment List;
    private CardContentFragment Card;
    private ListContentFragment.ContentAdapter adapter;
    private CardContentFragment.ContentAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);

         //Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadJSON();
    }



    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(List, "待审核的社团");
        adapter.addFragment(Card, "待审核的活动");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                if (adapter1 != null) {
                    adapter1.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

    private void loadJSON(){
        Observable<ListDataModel> observable1;
        Observable<CardDataModel> observable2;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        observable1 = request.getCall_1("shetuan").subscribeOn(Schedulers.io());
        observable2 = request.getCall_2("activity").subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<ListDataModel, CardDataModel, ListAndCard>() {
            @Override
            public ListAndCard apply(ListDataModel listDataModel, CardDataModel cardDataModel) throws Exception {
                return new ListAndCard(listDataModel,cardDataModel);
            }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ListAndCard>() {
            @Override
            public void accept(ListAndCard listAndCard) throws Exception {
                ListDataModel clubs = listAndCard.getListDataModel();
                CardDataModel acts = listAndCard.getCardDataModel();
                List = new ListContentFragment();
                Card = new CardContentFragment();
                Bundle lb = new Bundle();
                lb.putSerializable("clubs",clubs);
                List.setArguments(lb);
                Bundle cb = new Bundle();
                cb.putSerializable("acts",acts);
                Card.setArguments(cb);

                // Setting ViewPager for each Tabs
                ViewPager viewPager = findViewById(R.id.viewpager);
                setupViewPager(viewPager);
                // Set Tabs inside Toolbar
                TabLayout tabs = findViewById(R.id.tabs);
                tabs.setupWithViewPager(viewPager);

                //获取Adapter
                adapter = List.getAdapter();
                adapter1 = Card.getAdapter();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("Error","获取失败");
            }
        });
    }
}
