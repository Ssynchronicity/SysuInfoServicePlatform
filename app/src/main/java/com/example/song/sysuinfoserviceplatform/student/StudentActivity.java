package com.example.song.sysuinfoserviceplatform.student;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.ActivityData;
import com.example.song.sysuinfoserviceplatform.activity.ActivityDataModel;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.example.song.sysuinfoserviceplatform.activity.ServiceAndSport;
import com.example.song.sysuinfoserviceplatform.club.ApplyActivity;
import com.example.song.sysuinfoserviceplatform.student.stu_activity.serviceContent;
import com.example.song.sysuinfoserviceplatform.student.stu_activity.sportContent;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentActivity extends Fragment {
    private Toolbar toolbar;
    private View view;
    public static ArrayList<ActivityData> serviceData, sportData;
    private serviceContent Service;
    private sportContent Sport;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_studentactivity, container, false);
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        loadJSON();
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(((AppCompatActivity) getActivity()).getSupportFragmentManager());
        adapter.addFragment(Service, "公益活动");
        adapter.addFragment(Sport, "体育活动");
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
    }

    private void loadJSON(){
        Observable<ActivityDataModel> observable1;
        Observable<ActivityDataModel> observable2;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        observable1 = request.getStudentList("公益类").subscribeOn(Schedulers.io());
        observable2 = request.getStudentList("体育类").subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<ActivityDataModel, ActivityDataModel, ServiceAndSport>() {
            @Override
            public ServiceAndSport apply(ActivityDataModel serviceModel, ActivityDataModel sportModel) throws Exception {
                return new ServiceAndSport(serviceModel,sportModel);
            }
        }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<ServiceAndSport>() {
                @Override
                public void accept(ServiceAndSport serviceAndsport) throws Exception {
                    ActivityDataModel service = serviceAndsport.getServiceModel();
                    ActivityDataModel sport = serviceAndsport.getSportModel();
                    Service = new serviceContent();
                    Sport = new sportContent();
                    Bundle lb = new Bundle();
                    lb.putSerializable("service",service);
                    Service.setArguments(lb);
                    Bundle cb = new Bundle();
                    cb.putSerializable("sport",sport);
                    Sport.setArguments(cb);
                    // Setting ViewPager for each Tabs
                    ViewPager viewPager = view.findViewById(R.id.viewpager);
                    setupViewPager(viewPager);
                    // Set Tabs inside Toolbar
                    TabLayout tabs = view.findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);
                }
            }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("Error","获取失败");
                Toast.makeText(getContext(), "获取失败，稍后重试", Toast.LENGTH_SHORT).show();
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

}
