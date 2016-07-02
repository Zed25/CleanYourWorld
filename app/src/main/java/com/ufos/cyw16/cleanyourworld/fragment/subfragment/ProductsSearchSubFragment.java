package com.ufos.cyw16.cleanyourworld.fragment.subfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.ProductSearchAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.fragment.SearchFragment;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class ProductsSearchSubFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private RecyclerView recyclerView;

    private List<ProductType> productTypes;
    private ProductSearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onPause() {
        super.onPause();
        // set search view to gone (only used here)
        searchView.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.products_search_subfragment, container, false); //inflate layout

        searchView = (SearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.product_search_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

/*        try {
            productTypes = DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().findAll();

        } catch (DaoException e) {
            e.printStackTrace();
        }*/

        ProductType productType1 = new ProductType();
        productType1.setName("penna");

        ProductType productType2 = new ProductType();
        productType2.setName("quaderno");

        productTypes = new ArrayList<>();
        productTypes.add(productType1);
        productTypes.add(productType2);

        adapter = new ProductSearchAdapter(productTypes);
        recyclerView.setAdapter(adapter);


        //createFragment(v); //set ViewPager and TabLayout
        return v;
    }


    private List<ProductType> filter(List<ProductType> models, String query) {
        query = query.toLowerCase();

        final List<ProductType> filteredModelList = new ArrayList<>();
        for (ProductType model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<ProductType> filteredModelList = filter(productTypes, newText);
        adapter.animateTo(filteredModelList);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return true;

    }
}
