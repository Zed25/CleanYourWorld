/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.MaterialsSearchFragment
 * Last modified: 03/07/16 12.06
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarMonthAdapter;
import com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class MaterialsSearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private MaterialRecyclerViewAdapter materialRecyclerViewAdapter;

    private List<MaterialTrashInfo> materialTrashInfoList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.materials_search_subfragment, container, false); //inflate layout

        createFragment(v);
        return v;
    }

    private void createFragment(View v) {

        initializerecyclerView(v);

        computeMaterialCards(materialTrashInfoList, 1865);

    }

    private void computeMaterialCards(List<MaterialTrashInfo> materialTrashInfoList, int comuneID) {

        List<Material> materials = null;

        MaterialDao materialDao = DaoFactory_def.getInstance(getContext()).getMaterialDao();

    }


    /**initialize Recycler View
     *
     * Called in either in week view or in month view **/
    private void initializerecyclerView(View v){
        recyclerView = (RecyclerView) v.findViewById(R.id.rvMaterials);


        materialTrashInfoList = new ArrayList<>();

        //set RecyclerView's size fixed
        recyclerView.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        recyclerView.setLayoutManager(linearLayoutManager);

        //create and set the adapter for the recycler view
        materialRecyclerViewAdapter = new MaterialRecyclerViewAdapter(materialTrashInfoList);
        recyclerView.setAdapter(materialRecyclerViewAdapter);
    }
}
