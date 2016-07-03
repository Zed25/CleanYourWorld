/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter
 * Last modified: 03/07/16 12.32
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;

import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.MaterialRecyclerViewHolder> {
    private List<DayTrashInfo> lvDayTrashInfo;

    public MaterialRecyclerViewAdapter(List<DayTrashInfo> lvDayTrashInfo) {
        this.lvDayTrashInfo = lvDayTrashInfo;
    }

    @Override
    public int getItemCount() {
        return lvDayTrashInfo.size();
    }

    @Override
    public void onBindViewHolder(MaterialRecyclerViewHolder materialRecyclerViewHolder, int i) {
        DayTrashInfo dayTrashInfo = lvDayTrashInfo.get(i);
    }

    @Override
    public MaterialRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.calendar_card_view_sample_layout, viewGroup, false);

        return new MaterialRecyclerViewHolder(itemView);
    }



    public static class MaterialRecyclerViewHolder extends RecyclerView.ViewHolder{

        public MaterialRecyclerViewHolder(View v) {
            super(v);
        }
    }
}
