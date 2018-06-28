package com.example.song.sysuinfoserviceplatform.student;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.song.sysuinfoserviceplatform.R;

public class StudentCourse extends Fragment {
    private View view;
    private Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_studentcourse, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_list);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public EditText school_year;
        public EditText semester;
        public CheckBox major;
        public CheckBox vice;
        public CheckBox profession_2;
        public CheckBox degree_2;
        public viewHolder(View itemView) {
            super(itemView);
            school_year = itemView.findViewById(R.id.school_tear);
            semester = itemView.findViewById(R.id.editText2);
            major = itemView.findViewById(R.id.checkBox1);
            vice = itemView.findViewById(R.id.checkBox2);
            profession_2 = itemView.findViewById(R.id.checkBox4);
            degree_2 = itemView.findViewById(R.id.checkBox3);

            Button btn_go = itemView.findViewById(R.id.button_go);
            btn_go.setOnClickListener(new RecyclerView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class fragmentClass = Course_detail.class;
                    Fragment frag = null;
                    try{
                        frag = (Fragment) fragmentClass.newInstance();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    FragmentManager fragmentManager = getFragmentManager();
                    Log.i("hi", "onClick: here1");
                    fragmentManager.beginTransaction().replace(R.id.stuMain_contentView, frag).commit();
                }
            });
        }
    }


    public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public static final int CARD = 0;


        int LENGTH = 1; // NEEDS TO BE OVERWROTE.


        public ContentAdapter(Context context){

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder holder = null;
            if (viewType == CARD){
                View v = inflater.inflate(R.layout.card_course_quiry, parent, false);
                holder = new viewHolder(v);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof viewHolder){

            }

        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }

}
