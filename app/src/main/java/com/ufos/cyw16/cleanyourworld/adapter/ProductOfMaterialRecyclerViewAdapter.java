/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ProductOfMaterialListAdapter
 * Last modified: 04/07/16 14.37
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 04/07/16.
 */
public class ProductOfMaterialRecyclerViewAdapter extends RecyclerView.Adapter<ProductOfMaterialRecyclerViewAdapter.ProductOfMaterialRecyclerViewHolder> {

    private List<ProductType> productTypes;

    /**
     * Instantiates a new Product of material recycler view adapter.
     *
     * @param productTypes the product types
     */
    public ProductOfMaterialRecyclerViewAdapter(List<ProductType> productTypes) {
        this.productTypes = new ArrayList<>(productTypes);
    }

    @Override
    public ProductOfMaterialRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_search_row,parent,false);

        return new ProductOfMaterialRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductOfMaterialRecyclerViewHolder holder, int position) {
        ProductType selectedProd = productTypes.get(position);
        holder.textView.setText(selectedProd.getName());

        int matID = selectedProd.getMaterial().getIdMaterial();

        holder.textView.setTextColor(Color.parseColor(getColorByMaterilID(matID)));

        setMaterialIcon(holder.imageView,matID);
    }

    /**
     * Sets material icon.
     *
     * @param imageView the image view
     * @param matID     the mat id
     */
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

    /**
     * Gets color by materil id.
     *
     * @param idMaterial the id material
     * @return the color by materil id
     */
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


    /**
     * The type Product of material recycler view holder.
     */
    public class ProductOfMaterialRecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        /**
         * Instantiates a new Product of material recycler view holder.
         *
         * @param itemView the item view
         */
        public ProductOfMaterialRecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.product_search_tv);
            imageView = (ImageView) itemView.findViewById(R.id.product_search_icon);

        }
    }
}