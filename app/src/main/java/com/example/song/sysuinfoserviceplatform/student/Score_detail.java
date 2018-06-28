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

import java.util.ArrayList;
import java.util.List;

public class Score_detail extends Fragment {

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
        ///        R.layout.recycle_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("hi", "onClick: here3");


        return view;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView gpa;
            public TextView rank;
            public viewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.score_name);
                gpa = itemView.findViewById(R.id.score_value);
                rank = itemView.findViewById(R.id.score_rank);
            }
    }


    public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public static final int CARD = 0;

        int LENGTH = Data.score_display.length; // NEEDS TO BE OVERWROTE.
        public String[] name_arr;
        public String[] gpa_arr;
        public String[] rank_arr;


        public ContentAdapter(Context context){
            List<String> s_name = new ArrayList<>();
            List<String> s_gpa = new ArrayList<>();
            List<String> s_rank = new ArrayList<>();
            for(int i = 0; i < Data.score_display.length; i++){
                if (Data.score_display[i] == true){
                    s_name.add(Data.score_name[i]);
                    s_gpa.add(Data.score_gpa[i]);
                    s_rank.add(Data.score_rank[i]);
                }
            }
            name_arr = s_name.toArray(new String[s_name.size()]);
            gpa_arr = s_gpa.toArray(new String[s_gpa.size()]);
            rank_arr = s_rank.toArray(new String[s_rank.size()]);
            LENGTH = name_arr.length;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder holder = null;
            Log.i("hi", "onClick: here4");
            if (viewType == CARD){
                View v = inflater.inflate(R.layout.card_score_table, parent, false);
                holder = new viewHolder(v);
                Log.i("hi", "onClick: here5");
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof viewHolder){
                Log.i("hi", "onClick: here8");

                ((viewHolder) holder).name.setText(name_arr[position]);
                ((viewHolder) holder).gpa.setText(gpa_arr[position]);
                ((viewHolder) holder).rank.setText(rank_arr[position]);

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
