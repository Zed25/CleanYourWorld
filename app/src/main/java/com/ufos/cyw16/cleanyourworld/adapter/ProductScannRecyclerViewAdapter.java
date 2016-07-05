/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ProductScannRecyclerViewAdapter
 * Last modified: 05/07/16 18.17
 */

package com.ufos.cyw16.cleanyourworld.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.model_new.ProductScannInfo;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by simone_mancini on 05/07/16.
 */
public class ProductScannRecyclerViewAdapter extends RecyclerView.Adapter<ProductScannRecyclerViewAdapter.ProductScannHolder> {
    private List<ProductScannInfo> lvProductScannInfo;

    public ProductScannRecyclerViewAdapter(List<ProductScannInfo> lvProductScannInfo) {
        this.lvProductScannInfo = lvProductScannInfo;
    }

    @Override
    public int getItemCount() {
        return lvProductScannInfo.size();
    }

    @Override
    public void onBindViewHolder(ProductScannHolder calendarWeekHolder, int i) {

        ProductScannInfo productScannInfo = lvProductScannInfo.get(i);
    }

    @Override
    public ProductScannHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.scann_card_semple_layout, viewGroup, false);

        return new ProductScannHolder(itemView);
    }



    public static class ProductScannHolder extends RecyclerView.ViewHolder{

        protected TextView tvProductName, tvMaterialProduct, tvBarCode, tvCollectionDay, tvScannDate;

        public ProductScannHolder(View v) {
            super(v);
            tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            tvMaterialProduct = (TextView) v.findViewById(R.id.tvMaterialProduct);
            tvBarCode = (TextView) v.findViewById(R.id.tvBarCode);
            tvCollectionDay = (TextView) v.findViewById(R.id.tvCollectionDay);
            tvScannDate = (TextView) v.findViewById(R.id.tvScannDate);
        }
    }
}

