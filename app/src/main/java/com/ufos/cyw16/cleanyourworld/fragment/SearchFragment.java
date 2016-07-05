package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.fragment.subfragment.*;

/**
 * Created by Sasha on 11/05/16.
 */

@Deprecated
public class SearchFragment extends Fragment {

    private ButtonBarLayout buttonBarLayout;
    //private ViewPagerAdapter adapter;

    private Button btnMaterials, btnBarCode, btnProducts;

    private SubFragmentButtonManager subFragmentButtonManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.search_fragment, container, false); //inflate layout

        createFragment(v);

        //createFragment(v); //set ViewPager and TabLayout
        return v;
    }

    public void createFragment(View v) {

        //ViewPager viewPager = (ViewPager) v.findViewById(R.id.vpSearch);

        btnBarCode = (Button) v.findViewById(R.id.btnBarCode);
        btnMaterials = (Button) v.findViewById(R.id.btnMaterials);
        btnProducts = (Button) v.findViewById(R.id.btnProducts);

        subFragmentButtonManager = new SubFragmentButtonManager();

        btnBarCode.setOnClickListener(subFragmentButtonManager);
        btnProducts.setOnClickListener(subFragmentButtonManager);
        btnMaterials.setOnClickListener(subFragmentButtonManager);

        //setupViewPager(viewPager);

        //buttonBarLayout.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {

        //    }
        //});
        //btnBarCode.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        adapter.getItem(0).getLayoutInflater(null).inflate(R.layout.barcode_search_subfragment, null, false);
        //    }
        //});
    }

    public class SubFragmentButtonManager implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            switch (v.getId()){
                case R.id.btnBarCode:
                    ft.replace(R.id.showFragment, new BarCodeSearchFragment(), "fragment_screen");
                    ft.commit();
                    break;
                case R.id.btnMaterials:
                    ft.replace(R.id.showFragment, new MaterialsSearchFragment(), "fragment_screen");
                    ft.commit();
                    break;
                case R.id.btnProducts:
                    ft.replace(R.id.showFragment, new ProductsSearchSubFragment(), "fragment_screen");
                    ft.commit();
                    break;
            }
        }
    }
}
