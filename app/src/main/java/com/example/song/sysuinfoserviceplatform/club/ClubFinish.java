package com.example.song.sysuinfoserviceplatform.club;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.ActivityData;
import com.example.song.sysuinfoserviceplatform.activity.ActivityDataModel;
import com.example.song.sysuinfoserviceplatform.activity.RequestInterface;
import com.example.song.sysuinfoserviceplatform.student.stu_activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClubFinish extends Fragment {

    private Toolbar toolbar;
    private View view;
    private static ArrayList<ActivityData> data;
    private ContentAdapter adapter;
    private RecyclerView activityList;
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
        view = inflater.inflate(R.layout.fragment_clubfinish, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        loadJSON();
        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatar;
        public TextView name;
        public TextView time;
        public TextView status;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_clubactlist, parent, false));
            avatar = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            time = (TextView) itemView.findViewById(R.id.list_time);
            status = (TextView) itemView.findViewById(R.id.list_status);
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{
        // Set numbers of List in RecyclerView.
        private Context mcontext;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mcontext = context;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            holder.name.setText(data.get(position).getAct_name());
            holder.time.setText(data.get(position).getAct_time());
            holder.status.setText("已结束");
            Picasso.with(mcontext).load(data.get(position).getAct_pic()).into(holder.avatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    int p = holder.getAdapterPosition();
                    ActivityData pass = data.get(p);
                    bundle.putInt("type", 5);
                    bundle.putSerializable("finish", pass);
                    //start detail activity
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.217.143:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        app = (globalClub)getActivity().getApplication();
        Call<ActivityDataModel> call = request.getFinishList(app.getName(), "end");
        call.enqueue(new Callback<ActivityDataModel>(){
            @Override
            public void onResponse(Call<ActivityDataModel> call, Response<ActivityDataModel> response) {
                ActivityDataModel activitymodel = response.body();
                data = new ArrayList<>(Arrays.asList(activitymodel.getData()));
                LENGTH = data.size();
                activityList = (RecyclerView)view.findViewById(R.id.activity_list);

                adapter = new ClubFinish.ContentAdapter(activityList.getContext());
                activityList.setAdapter(adapter);
                activityList.setHasFixedSize(true);
                activityList.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<ActivityDataModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
                //Toast.makeText(getActivity(), "获取失败，请稍后重试", Toast.LENGTH_LONG).show();
                Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }

        });
    }
}
