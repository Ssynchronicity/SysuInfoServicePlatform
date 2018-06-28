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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.song.sysuinfoserviceplatform.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment{
    private ListDataModel ldm;
    private ContentAdapter adapter;
    private RecyclerView recyclerView;

    public ContentAdapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ldm = (ListDataModel) getArguments().getSerializable("clubs");
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext(), ldm);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.admin_item_list, parent, false));
            avator = itemView.findViewById(R.id.list_avatar);
            name = itemView.findViewById(R.id.list_title);
            description = itemView.findViewById(R.id.list_desc);

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable{
        // Set numbers of List in RecyclerView.
        private ListDataModel adapter_ldm;
        private List<ListData> listData = new ArrayList<>();
        private List<ListData> filterListData = new ArrayList<>();
        private Context mcontext;

        public ContentAdapter(Context context, ListDataModel ldm) {
            adapter_ldm = ldm;
            listData = adapter_ldm.getData();
            filterListData = adapter_ldm.getData();
            mcontext = context;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.name.setText(listData.get(position).getName());
            holder.description.setText(listData.get(position).getDescription());
            Picasso.with(mcontext).load("http://172.18.217.143:8000/getpic?name="+listData.get(position).getName()).into(holder.avator);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailClub.class);
                    int p = holder.getAdapterPosition();
                    ListData pass = ldm.getData().get(p);

                    //start detail activity
                    intent.putExtra("club",pass);
                    context.startActivity(intent);

                    //remove the selected item
                    listData.remove(p);
                    adapter_ldm.setData(listData);
                    notifyDataSetChanged();
        }
    });
        }

        @Override
        public int getItemCount() {
            return filterListData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    if(charString.isEmpty()) {
                        filterListData = listData;
                    }
                    else {
                        ArrayList<ListData> filteredList = new ArrayList<>();
                        for (ListData data : listData) {
                            if(data.getName().toLowerCase().contains(charString) || data.getDescription().toLowerCase().contains(charString) ||
                               data.getName().contains(charString) || data.getDescription().contains(charString)) {
                                filteredList.add(data);
                            }
                        }
                        filterListData = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterListData;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filterListData = (ArrayList<ListData>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
    }

}
