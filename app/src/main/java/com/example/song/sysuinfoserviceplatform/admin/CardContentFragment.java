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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.song.sysuinfoserviceplatform.R;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {

    private CardDataModel cdm;
    private ContentAdapter adapter;

    public ContentAdapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cdm = (CardDataModel) getArguments().getSerializable("acts");
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext(),cdm);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.admin_item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);

        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable{

        private CardDataModel adapter_cdm;
        private List<CardData> cardData = new ArrayList<>();
        private List<CardData> filterCardData = new ArrayList<>();
        private Context mcontext;

        public ContentAdapter(Context context, CardDataModel ldm) {
            adapter_cdm = cdm;
            cardData = adapter_cdm.getData();
            filterCardData = adapter_cdm.getData();
            mcontext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.name.setText(cardData.get(position).getName());
            holder.description.setText(cardData.get(position).getSlogon());
            Picasso.with(mcontext).load(cardData.get(position).getJpg()).into(holder.picture);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    int p = holder.getAdapterPosition();
                    CardData pass = cdm.getData().get(p);

                    //start detail activity
                    intent.putExtra("act",pass);
                    context.startActivity(intent);

                    //remove the selected item
                    cardData.remove(p);
                    cdm.setData(cardData);
                    notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return filterCardData.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String charString = constraint.toString();
                    Log.d("查询的字符串",charString);
                    if(charString.isEmpty()) {
                        filterCardData = cardData;
                    }
                    else {
                        ArrayList<CardData> filteredList = new ArrayList<>();
                        for (CardData data : cardData) {
                            if(data.getName().toLowerCase().contains(charString) || data.getSlogon().toLowerCase().contains(charString) ||
                               data.getName().contains(charString) || data.getSlogon().contains(charString)) {
                                filteredList.add(data);
                            }
                        }
                        filterCardData = filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = filterCardData;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filterCardData = (ArrayList<CardData>) results.values;
                    notifyDataSetChanged();
                }
            };
        }
    }
}
