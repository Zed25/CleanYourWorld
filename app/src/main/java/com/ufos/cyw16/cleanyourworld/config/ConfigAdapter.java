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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.R;

import java.util.ArrayList;

/**
 * Created by ovidiudanielbarba on 15/06/16.
 */
public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.MyViewHolder>{

    private ArrayList<ConfigAdapterDataProvider> data;

    public ConfigAdapter(ArrayList<ConfigAdapterDataProvider> regioni) {
        this.data = regioni;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.config_row,parent,false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ConfigAdapterDataProvider element = data.get(position);

        holder.nameTv.setText(element.getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.name);
        }
    }


}
