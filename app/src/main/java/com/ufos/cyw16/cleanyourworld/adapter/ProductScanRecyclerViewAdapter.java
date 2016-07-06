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
import com.ufos.cyw16.cleanyourworld.model_new.ProductScanInfo;

import java.util.List;

/**
 * Created by simone_mancini on 05/07/16.
 */
public class ProductScanRecyclerViewAdapter extends RecyclerView.Adapter<ProductScanRecyclerViewAdapter.ProductScanHolder> {
    private List<ProductScanInfo> lvProductScanInfo;

    public ProductScanRecyclerViewAdapter(List<ProductScanInfo> lvProductScanInfo) {
        this.lvProductScanInfo = lvProductScanInfo;
    }

    @Override
    public int getItemCount() {
        return lvProductScanInfo.size();
    }

    @Override
    public void onBindViewHolder(ProductScanHolder productScanHolder, int i) {

        ProductScanInfo productScanInfo = lvProductScanInfo.get(i);

        productScanHolder.tvProductName.setText(productScanInfo.getProductName());
        productScanHolder.tvProductName.setBackgroundColor(productScanInfo.getTrashColorCode());
        productScanHolder.tvBarCode.setText(productScanInfo.getBarcode());
        productScanHolder.tvCollectionDay.setText(productScanInfo.getCollectionDay());
        productScanHolder.tvMaterialProduct.setText(productScanInfo.getMaterialProduct());
        productScanHolder.tvScannDate.setText(productScanInfo.getScannDate());
    }

    @Override
    public ProductScanHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.scann_card_semple_layout, viewGroup, false);

        return new ProductScanHolder(itemView);
    }



    public static class ProductScanHolder extends RecyclerView.ViewHolder{

        protected TextView tvProductName, tvMaterialProduct, tvBarCode, tvCollectionDay, tvScannDate;

        public ProductScanHolder(View v) {
            super(v);
            tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            tvMaterialProduct = (TextView) v.findViewById(R.id.tvMaterialProduct);
            tvBarCode = (TextView) v.findViewById(R.id.tvBarCode);
            tvCollectionDay = (TextView) v.findViewById(R.id.tvCollectionDay);
            tvScannDate = (TextView) v.findViewById(R.id.tvScannDate);
        }
    }
}

