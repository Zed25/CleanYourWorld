/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ProductOfMaterialListAdapter
 * Last modified: 04/07/16 14.37
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.content.Context;
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
public class ProductOfMaterialListAdapter extends BaseAdapter {

    private List list;
    private static LayoutInflater inflater = null;

    //Constructor.
    public ProductOfMaterialListAdapter(Context context, List list) {
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        //if (convertView == null)
            view = inflater.inflate(R.layout.product_search_row, null);

        ImageView bucket = (ImageView) view.findViewById(R.id.product_search_icon);
        TextView productName = (TextView) view.findViewById(R.id.product_search_tv);

        if (list != null) {
            if (!list.isEmpty()) {
                ProductType productType = (ProductType) list.get(position);

                if (productType != null){
                    if(productType.getName() != null){
                        productName.setText(productType.getName());
                    }else {
                        Message4Debug.log("product name doesn't exist");
                    }
                }
            }
        }

        return view;
    }
}
