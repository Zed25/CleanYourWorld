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
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.MaterialRecyclerViewHolder> {
    private List<MaterialTrashInfo> lvMaterilTrashInfo;

    public MaterialRecyclerViewAdapter(List<MaterialTrashInfo> lvMaterilTrashInfo) {
        this.lvMaterilTrashInfo = lvMaterilTrashInfo;
    }

    @Override
    public int getItemCount() {
        return lvMaterilTrashInfo.size();
    }

    @Override
    public void onBindViewHolder(MaterialRecyclerViewHolder materialRecyclerViewHolder, int i) {
        MaterialTrashInfo materialTrashInfo = lvMaterilTrashInfo.get(i);

        materialRecyclerViewHolder.tvMaterialAndColor.setText(materialTrashInfo.getThrash());
        materialRecyclerViewHolder.tvMaterialAndColor.setBackgroundColor(materialTrashInfo.getColorOfTheTrash());
        materialRecyclerViewHolder.tvDay.setText(materialTrashInfo.getDay());
    }

    @Override
    public MaterialRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.materials_card_semple_layout, viewGroup, false);

        return new MaterialRecyclerViewHolder(itemView);
    }



    public static class MaterialRecyclerViewHolder extends RecyclerView.ViewHolder{

        protected TextView tvMaterialAndColor, tvDay;
        protected ImageButton ibtnToMaterialListView;

        public MaterialRecyclerViewHolder(View v) {
            super(v);
            tvMaterialAndColor = (TextView) v.findViewById(R.id.tvMaterialAndColor);
            tvDay = (TextView) v.findViewById(R.id.tvDay);
            ibtnToMaterialListView = (ImageButton) v.findViewById(R.id.ibtnToMateriaListView);


            ibtnToMaterialListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message4Debug.log("Image Button selected");
                }
            });
        }
    }
}
