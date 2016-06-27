package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.fragment.subfragment.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasha on 11/05/16.
 */
public class SearchFragment extends Fragment {

    private ButtonBarLayout buttonBarLayout;
    private ViewPagerAdapter adapter;

    private Button btnMaterials, btnBarCode, btnProducts;

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

        createFragment(v); //set ViewPager and TabLayout
        return v;
    }

    public void createFragment(View v) {

        ViewPager viewPager = (ViewPager) v.findViewById(R.id.vpSearch);

        buttonBarLayout = (ButtonBarLayout) v.findViewById(R.id.btnBarLayout);

        btnBarCode = (Button) v.findViewById(R.id.btnBarCode);
        btnMaterials = (Button) v.findViewById(R.id.btnMaterials);
        btnProducts = (Button) v.findViewById(R.id.btnProducts);

        setupViewPager(viewPager);

        buttonBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //btnBarCode.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        adapter.getItem(0).getLayoutInflater(null).inflate(R.layout.barcode_search_subfragment, null, false);
        //    }
        //});
    }

    // Defines the number of tabs by setting appropriate fragment and tab name
    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new BarCodeSearchSubFragment(), "BarCode" );
        adapter.addFragment(new MaterialsSearchSubFragment(), "Material" );
        adapter.addFragment(new ProductsSearchSubFragment(), "Products");

        viewPager.setAdapter(adapter);
    }


    // Custom adapter class provides fragments required for the view pager
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
