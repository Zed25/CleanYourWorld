/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.subfragment.ProductsSearchSubFragment
 * Last modified: 05/07/16 5.12
 */

package com.ufos.cyw16.cleanyourworld.fragment.subfragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.ProductSearchAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class ProductsSearchSubFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private List<ProductType> productTypes;
    private ProductSearchAdapter adapter;

    private int animationDuration;

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
        // when resumes, makes search bar visible
        searchView.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.products_search_subfragment, container, false); //inflate layout

        //get short animation duration from resources
        animationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        // load all views
        searchView = (SearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVisibility(View.VISIBLE);
        searchView.setOnQueryTextListener(this);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_products);
        recyclerView = (RecyclerView) v.findViewById(R.id.product_search_recycler_view);

        // set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //make progress bar visible and recycler visible (by default loading)
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        //start asynk task to load product types from db and not block the main UI thread
        new LoadFromDB().execute();

        return v;
    }

    private void crossfade() {


        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        recyclerView.setAlpha(0f);
        recyclerView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        recyclerView.animate()
                .alpha(1f)
                .setDuration(animationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        progressBar.animate()
                .alpha(0f)
                .setDuration(animationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private List<ProductType> filter(List<ProductType> models, String query) {
        query = query.toLowerCase();

        // creates new array with filtered elements by query
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

        // the class itself is a listener to text change in search view
        final List<ProductType> filteredModelList = filter(productTypes, newText);
        // animate adapter to new filtered list
        adapter.animateTo(filteredModelList);
        adapter.notifyDataSetChanged();
        // scroll at top to show first elements
        recyclerView.scrollToPosition(0);
        return true;

    }

    private class LoadFromDB extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // loads all products from local DB (downloaded before from server)
                productTypes = DaoFactory_def.getInstance(getContext()).getProtuctTypeDao().findAllLazy();

            } catch (DaoException e) {
                e.printStackTrace();
            }

            // set adapter on newly created list
            adapter = new ProductSearchAdapter(productTypes);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // set adapter on recycler view
            recyclerView.setAdapter(adapter);

            //animate from progress bar to recycler view
            crossfade();

        }
    }
}
