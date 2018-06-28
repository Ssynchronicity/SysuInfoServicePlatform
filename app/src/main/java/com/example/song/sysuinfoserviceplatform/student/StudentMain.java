package com.example.song.sysuinfoserviceplatform.student;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;
import com.example.song.sysuinfoserviceplatform.student.Data;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentMain extends Fragment {

    public String get_stu_name(){
        return "李四咸";
    }
    public String get_stu_id(){
        return "15331111";
    }
    public String get_stu_school(){
        return "数据科学与挖掘机学院";
    }
    public String[] get_class_name(){
        return new String[]{"软件工程导论", "实用挖掘机技术"};
    }
    public String[] get_class_time(){
        return new String[]{"周一 1-2节", "周二 1-2节"};
    }
    public String[] get_class_place(){
        return new String[]{"公共教学楼", "公共挖掘区"};
    }
    public String[] get_act_name(){
        return new String[]{"琶洲义教"};
    }
    public String[] get_act_time(){
        return new String[]{"2018-08-30"};
    }
    public String[] get_act_club(){
        return new String[]{"爱心同盟"};
    }


    private View view;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_studentmain, container, false);
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

    public class viewHolder_personalInfo extends RecyclerView.ViewHolder{
        public CircleImageView icon;
        public TextView name;
        public TextView id;
        public TextView school;
        public viewHolder_personalInfo(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon_image);
            name = itemView.findViewById(R.id.person_info_name);
            id = itemView.findViewById(R.id.person_info_id);
            school = itemView.findViewById(R.id.person_info_school);
        }
    }

    public class viewHolder_todayClass extends RecyclerView.ViewHolder{
        public TextView course;
        public TextView time;
        public TextView place;
        public viewHolder_todayClass(View itemView) {
            super(itemView);
            course = itemView.findViewById(R.id.today_class_name);
            time = itemView.findViewById(R.id.today_class_time);
            /// place = itemView.findViewById(R.id.today_class_place);
        }
    }

    public class viewHolder_actRecent extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView club;
        public TextView time;
        public viewHolder_actRecent(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.act_recent_name);
            club = itemView.findViewById(R.id.act_recent_club);
            time = itemView.findViewById(R.id.act_recent_time);
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public static final int PERSON = 1;
        public static final int CLASS = 2;
        public static final int ACT = 3;

        public int class_index = 0;
        public int act_index = 0;

        /// int LENGTH = Data.today_courses_name.length + 2; // NEEDS TO BE OVERWROTE.
        int LENGTH = 2; // NEEDS TO BE OVERWROTE.

        public final String stu_name;
        public final String stu_id;
        public final String stu_school;
        public final String[] class_name;
        public final String[] class_time;
        /// public final String[] class_place;
        public final String bn_act_name;
        public final String bn_act_time;
        public final String bn_act_club;

        public final String sp_act_name;
        public final String sp_act_time;
        public final String sp_act_club;

        public Boolean is_bn;

        public ContentAdapter(Context context){
            stu_name = "李四咸";
            stu_id = "15336085";
            stu_school = get_stu_school();
            class_name = Data.today_courses_name;
            class_time = Data.today_section;
            /// class_time = get_class_time();
            /// class_place = get_class_place();
            bn_act_name = Data.one_bn_act.getAct_name();
            bn_act_time = Data.one_bn_act.getAct_time();
            bn_act_club = Data.one_bn_act.getAct_organiser();

            sp_act_name = Data.one_bn_act.getAct_name();
            sp_act_time = Data.one_bn_act.getAct_time();
            sp_act_club = Data.one_bn_act.getAct_organiser();

            is_bn = true;


            class_index = 0;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RecyclerView.ViewHolder holder = null;
            if (viewType == PERSON){
                View v = inflater.inflate(R.layout.card_main_personal_info, parent, false);
                holder = new viewHolder_personalInfo(v);
            }
            else if (viewType == CLASS){
                View v = inflater.inflate(R.layout.card_main_today_class, parent, false);
                holder = new viewHolder_todayClass(v);
            }
            else if (viewType == ACT){
                View v = inflater.inflate(R.layout.card_main_act_recent, parent, false);
                holder = new viewHolder_actRecent(v);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof viewHolder_personalInfo){
                ((viewHolder_personalInfo) holder).name.setText(Data.NetId);
                ((viewHolder_personalInfo) holder).id.setText(stu_id);
                ((viewHolder_personalInfo) holder).school.setText(stu_school);
            }
            else if(holder instanceof viewHolder_todayClass){
                ((viewHolder_todayClass) holder).course.setText(class_name[class_index]);
                ((viewHolder_todayClass) holder).time.setText(class_time[class_index]);
                /// ((viewHolder_todayClass) holder).place.setText(class_place[class_index]);
                class_index = class_index + 1;
            }
            else if(holder instanceof viewHolder_actRecent && is_bn){
                ((viewHolder_actRecent) holder).name.setText(bn_act_name);
                ((viewHolder_actRecent) holder).club.setText(bn_act_club);
                ((viewHolder_actRecent) holder).time.setText(bn_act_time);
                act_index = act_index + 1;
                is_bn = false;
            }

            else if(holder instanceof viewHolder_actRecent && !is_bn){
                ((viewHolder_actRecent) holder).name.setText(sp_act_name);
                ((viewHolder_actRecent) holder).club.setText(sp_act_club);
                ((viewHolder_actRecent) holder).time.setText(sp_act_time);
                act_index = act_index + 1;
                is_bn = true;
            }

        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return PERSON;
            }else if (class_name != null && position <= class_name.length){
                return CLASS;
            }
            else {
                return ACT;
            }
        }

        @Override
        public int getItemCount() {
            if(!Data.has_course)
                return LENGTH;
            else return Data.today_courses_name.length + 2;
        }
    }

}
