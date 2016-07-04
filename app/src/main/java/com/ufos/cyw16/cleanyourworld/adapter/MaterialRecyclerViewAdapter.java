/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter
 * Last modified: 03/07/16 12.32
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.CustomLinearLayoutManager;
import com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.MaterialRecyclerViewHolder> {
    private List<MaterialTrashInfo> lvMaterilTrashInfo;
    private Context context;

    public MaterialRecyclerViewAdapter(Context context, List<MaterialTrashInfo> lvMaterilTrashInfo) {
        this.lvMaterilTrashInfo = lvMaterilTrashInfo;
        this.context = context;
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

        return new MaterialRecyclerViewHolder(context, itemView);
    }



    public static class MaterialRecyclerViewHolder extends RecyclerView.ViewHolder{

        protected TextView tvMaterialAndColor, tvDay;
        protected RecyclerView rvProductsOfMaterial;
        protected CoordinatorLayout svProducTypes;
        //protected ScrollView svProducTypes;
        protected ImageButton ibtnToMaterialListView;
        protected Context context;

        public MaterialRecyclerViewHolder(Context context, View v) {
            super(v);
            tvMaterialAndColor = (TextView) v.findViewById(R.id.tvMaterialAndColor);
            tvDay = (TextView) v.findViewById(R.id.tvDay);
            ibtnToMaterialListView = (ImageButton) v.findViewById(R.id.ibtnToMateriaListView);
            rvProductsOfMaterial = (RecyclerView) v.findViewById(R.id.rvProductsOfMaterial);
            svProducTypes = (CoordinatorLayout) v.findViewById(R.id.svProductTypes);
            //svProducTypes = (ScrollView) v.findViewById(R.id.svProductTypes);

            //set RecyclerView's size fixed
            rvProductsOfMaterial.setHasFixedSize(true);

            //create the layout manager to insert into the recycler view
            //linear layout manager is similar to layout manager for the list view
            LinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            //set the layout manager in recycler view
            rvProductsOfMaterial.setLayoutManager(linearLayoutManager);


            ibtnToMaterialListView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tvMaterialAndColor.getVisibility() == View.VISIBLE){
                        tvMaterialAndColor.setVisibility(View.GONE);
                        svProducTypes.setVisibility(View.VISIBLE);
                    }else {
                        svProducTypes.setVisibility(View.GONE);
                        tvMaterialAndColor.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
