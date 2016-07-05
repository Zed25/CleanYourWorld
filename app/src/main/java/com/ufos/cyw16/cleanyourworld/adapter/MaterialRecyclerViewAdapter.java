/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter
 * Last modified: 03/07/16 12.32
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.CustomLinearLayoutManager;
import com.ufos.cyw16.cleanyourworld.MultilevelRecyclerView;
import com.ufos.cyw16.cleanyourworld.model_new.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;

import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.MaterialRecyclerViewHolder> {
    private List<MaterialTrashInfo> lvMaterilTrashInfo;
    private Context context;
    private MultilevelRecyclerView parentRecyclerView;

    public MaterialRecyclerViewAdapter(Context context, List<MaterialTrashInfo> lvMaterilTrashInfo, MultilevelRecyclerView parentRecyclerView) {
        this.lvMaterilTrashInfo = lvMaterilTrashInfo;
        this.context = context;
        this.parentRecyclerView = parentRecyclerView;
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

        ProductOfMaterialRecyclerViewAdapter productOfMaterialRecyclerViewAdapter = new ProductOfMaterialRecyclerViewAdapter(materialTrashInfo.getProductTypes());
        materialRecyclerViewHolder.rvProductsOfMaterial.setAdapter(productOfMaterialRecyclerViewAdapter);


    }

    @Override
    public MaterialRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.materials_card_semple_layout, viewGroup, false);

        return new MaterialRecyclerViewHolder(context, itemView, parentRecyclerView);
    }



    public static class MaterialRecyclerViewHolder extends RecyclerView.ViewHolder{

        protected TextView tvMaterialAndColor, tvDay;
        protected MultilevelRecyclerView rvProductsOfMaterial;
        protected ImageButton ibtnToMaterialListView;
        protected Context context;
        protected MultilevelRecyclerView parentRecyclerView;

        public MaterialRecyclerViewHolder(Context context, View v, MultilevelRecyclerView parentRecyclerView) {
            super(v);
            tvMaterialAndColor = (TextView) v.findViewById(R.id.tvMaterialAndColor);
            tvDay = (TextView) v.findViewById(R.id.tvDay);
            ibtnToMaterialListView = (ImageButton) v.findViewById(R.id.ibtnToMateriaListView);
            rvProductsOfMaterial = (MultilevelRecyclerView) v.findViewById(R.id.rvProductsOfMaterial);

            //set RecyclerView's size fixed
            rvProductsOfMaterial.setHasFixedSize(true);

            //create the layout manager to insert into the recycler view
            //linear layout manager is similar to layout manager for the list view
            LinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            //set the layout manager in recycler view
            rvProductsOfMaterial.setLayoutManager(linearLayoutManager);

            rvProductsOfMaterial.setParentRecyclerView(parentRecyclerView);


            ibtnToMaterialListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tvMaterialAndColor.getVisibility() == View.VISIBLE){
                        tvMaterialAndColor.setVisibility(View.GONE);
                        rvProductsOfMaterial.setVisibility(View.VISIBLE);
                    }else {
                        rvProductsOfMaterial.setVisibility(View.GONE);
                        tvMaterialAndColor.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
