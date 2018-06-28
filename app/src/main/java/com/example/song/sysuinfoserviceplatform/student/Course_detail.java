package com.example.song.sysuinfoserviceplatform.student;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;

public class Course_detail extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("hi", "onClick: here2");
        View view = inflater.inflate(R.layout.detail_recycle_view, container, false);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbar);
        ((android.support.v7.app.AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = ((android.support.v7.app.AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        RecyclerView recyclerView = view.findViewById(R.id.detail_recycle_list);
        ///RecyclerView recyclerView = (RecyclerView) inflater.inflate(
        ///        R.layout.recycle_view_course_detail, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("hi", "onClick: here3");


        return view;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView course;
        public TextView time;
        public TextView place;
        public viewHolder(View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.class_table_name);
            time = itemView.findViewById(R.id.class_table_time);
            //place = itemView.findViewById(R.id.class_table_place);
        }
    }


    public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public static final int CARD = 0;

        int LENGTH = Data.courses_name.length; // NEEDS TO BE OVERWROTE.
        public final String[] class_name = Data.courses_name;
        public final String[] class_time = Data.section;


        public ContentAdapter(Context context){

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder holder = null;
            Log.i("hi", "onClick: here4");
            if (viewType == CARD){
                View v = inflater.inflate(R.layout.card_course_table, parent, false);
                holder = new viewHolder(v);
                Log.i("hi", "onClick: here5");
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof viewHolder){
                Log.i("hi", "onClick: here8");
                ((viewHolder) holder).course.setText(class_name[position]);
                ((viewHolder) holder).time.setText(class_time[position]);
            }

        }

        /// @Override
        /// public int getItemViewType(int position) {
        ///    return 0;
        ///}

        @Override
        public int getItemCount() {
            Log.i("hi", "onClick: here7");
            return LENGTH;
        }
    }

}
