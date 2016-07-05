/*
 * Created by Umberto Ferracci from ovidiudanielbarba and published on 6/15/16 12:37 PM
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.config.ConfigAdapter
 * File name: ConfigAdapter.java
 * Class name: ConfigAdapter
 * Last modified: 6/15/16 12:37 PM
 */

package com.ufos.cyw16.cleanyourworld.config;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovidiudanielbarba on 15/06/16.
 */
// adapter used in configuration recycler view
public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.MyViewHolder>{

    private ArrayList<ConfigAdapterDataProvider> data;

    public ConfigAdapter(ArrayList<ConfigAdapterDataProvider> regioni) {
        // it needs to be new object,because it needs to filter later with the original ArrayList
        this.data = new ArrayList<>(regioni);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflates layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.config_row,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    public ArrayList<ConfigAdapterDataProvider> getData() {
        return data;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ConfigAdapterDataProvider element = data.get(position);

        holder.nameTv.setText(element.getName());

        // sets green text
        holder.nameTv.setTextColor(Color.parseColor("#4CAF50"));

        // make icon invisible
        holder.icon.setVisibility(View.INVISIBLE);

        // if it's a comune
        if(element.HasCollection() != null){
            holder.icon.setVisibility(View.VISIBLE);
            // if no collection available,set RED text and RED icon
            if(!element.HasCollection()){
                holder.nameTv.setTextColor(Color.parseColor("#F44336"));
                holder.icon.setImageResource(R.drawable.no_recycle_icon_config);

            } else {
                // else,green icon (green text already set by default)
                holder.icon.setImageResource(R.drawable.recycle_icon_config);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void animateTo(List<ConfigAdapterDataProvider> models) {
        // animates removal,addition and moving of items with new List
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<ConfigAdapterDataProvider> newModels) {
        // if newModels doesn't contain a dataProvider, remove it
        for (int i = data.size() - 1; i >= 0; i--) {
            final ConfigAdapterDataProvider model = data.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ConfigAdapterDataProvider> newModels) {
        // if data doesn't include a newModel, isert it
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final ConfigAdapterDataProvider model = newModels.get(i);
            if (!data.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ConfigAdapterDataProvider> newModels) {
        // move from a position to a new one
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ConfigAdapterDataProvider model = newModels.get(toPosition);
            final int fromPosition = data.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ConfigAdapterDataProvider removeItem(int position) {
        // remove and item from data
        final ConfigAdapterDataProvider model = data.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, ConfigAdapterDataProvider model) {
        // insert an item into data
        data.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        // move in data from a position to another
        final ConfigAdapterDataProvider model = data.remove(fromPosition);
        data.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTv;
        public ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.config_icon);
        }
    }


}
