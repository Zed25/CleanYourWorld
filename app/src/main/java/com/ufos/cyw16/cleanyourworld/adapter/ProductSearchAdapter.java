/*
 * Created by UFOS from ovidiudanielbarba
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ProductSearchAdapter
 * Last modified: 7/2/16 12:54 PM
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ovidiudanielbarba on 02/07/16.
 */
public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder> {

    private List<ProductType> productTypes;

    public ProductSearchAdapter(List<ProductType> productTypes) {
        this.productTypes = new ArrayList<>(productTypes);
    }

    @Override
    public ProductSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_search_row,parent,false);

        return new ProductSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductSearchViewHolder holder, int position) {
        ProductType selectedProd = productTypes.get(position);
        holder.textView.setText(selectedProd.getName());

        int matID = selectedProd.getMaterial().getIdMaterial();

        holder.textView.setTextColor(Color.parseColor(getColorByMaterilID(matID)));

        setMaterialIcon(holder.imageView,matID);




    }

    private void setMaterialIcon(ImageView imageView, int matID) {
        switch (matID){
            case 1:
                imageView.setImageResource(R.drawable.glass);
                break;
            case 2:
                imageView.setImageResource(R.drawable.paper);
                break;
            case 3:
                imageView.setImageResource(R.drawable.plastics);
                break;
            //metals
            case 5:
                imageView.setImageResource(R.drawable.metals);
                break;
            //non recycling
            case 6:
                imageView.setImageResource(R.drawable.no_recycle);
                break;
            //electronics
            case 8:
                imageView.setImageResource(R.drawable.electronics);
                break;
            //batteries
            case 9:
                imageView.setImageResource(R.drawable.batteries);
                break;
            //household
            case 10:
                imageView.setImageResource(R.drawable.household);
                break;
            //oil
            case 11:
                imageView.setImageResource(R.drawable.oil);
                break;
            //garden
            case 12:
                imageView.setImageResource(R.drawable.garden);
                break;
            //clothes
            case 13:
                imageView.setImageResource(R.drawable.clothes);
                break;
            //toner
            case 14:
                imageView.setImageResource(R.drawable.toner);
                break;
            //medicinals
            case 15:
                imageView.setImageResource(R.drawable.drugs);
                break;
            default:
                imageView.setImageResource(R.drawable.recycle);
                break;

        }
    }

    private String getColorByMaterilID(int idMaterial) {

        switch (idMaterial){
            //Glass
            case 1:
                return "#4caf50";
            //Paper
            case 2:
                return "#2196f3";
            //Plastic
            case 3:
                return "#ffeb3b";
            //organic
            case 4:
                return "#795548";
            //nonrecycling
            case 6:
                return "#9e9e9e";
            default:
                return "#000000";
        }
    }

    @Override
    public int getItemCount() {
        return productTypes.size();
    }

    public void animateTo(List<ProductType> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<ProductType> newModels) {
        for (int i = productTypes.size() - 1; i >= 0; i--) {
            final ProductType model = productTypes.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ProductType> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final ProductType model = newModels.get(i);
            if (!productTypes.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ProductType> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ProductType model = newModels.get(toPosition);
            final int fromPosition = productTypes.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ProductType removeItem(int position) {
        final ProductType model = productTypes.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, ProductType model) {
        productTypes.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ProductType model = productTypes.remove(fromPosition);
        productTypes.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


    public class ProductSearchViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        public ProductSearchViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.product_search_tv);
            imageView = (ImageView) itemView.findViewById(R.id.product_search_icon);

        }
    }
}
