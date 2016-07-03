/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.MaterialsSearchFragment
 * Last modified: 03/07/16 12.06
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufos.cyw16.cleanyourworld.R;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class MaterialsSearchFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.materials_search_subfragment, container, false); //inflate layout

        //createFragment(v); //set ViewPager and TabLayout
        return v;
    }
}
