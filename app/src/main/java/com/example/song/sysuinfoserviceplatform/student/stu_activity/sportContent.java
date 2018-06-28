package com.example.song.sysuinfoserviceplatform.student.stu_activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.activity.ActivityData;
import com.example.song.sysuinfoserviceplatform.activity.ActivityDataModel;
import com.example.song.sysuinfoserviceplatform.student.stu_activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class sportContent extends Fragment {
    private View view;
    private static ActivityData[] data;
    private sportContent.ContentAdapter adapter;
    private static int LENGTH;
    private RecyclerView activityList;
    private ActivityDataModel sportData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sportData = (ActivityDataModel) getArguments().getSerializable("sport");
        activityList = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        //activityList = (RecyclerView)view.findViewById(R.id.activity_list);
        adapter = new sportContent.ContentAdapter(activityList.getContext(), sportData);
        activityList.setAdapter(adapter);
        activityList.setHasFixedSize(true);
        activityList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return activityList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_sport, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<serviceContent.ViewHolder> {
        // Set numbers of List in RecyclerView.
        private Context mcontext;
        public ContentAdapter(Context context, ActivityDataModel sportData) {
            mcontext = context;
            data = sportData.getData();
            LENGTH = data.length;
        }

        @Override
        public serviceContent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new serviceContent.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final serviceContent.ViewHolder holder, int position) {
            holder.name.setText(data[position].getAct_name());
            holder.description.setText(data[position].getAct_detail());
            Picasso.with(mcontext).load(data[position].getAct_pic()).into(holder.picture);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    int p = holder.getAdapterPosition();
                    ActivityData pass = data[p];
                    bundle.putInt("type", 2);
                    bundle.putSerializable("sport", pass);
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

}
