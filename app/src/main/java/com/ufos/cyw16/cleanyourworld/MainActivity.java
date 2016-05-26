package com.ufos.cyw16.cleanyourworld;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.ufos.cyw16.cleanyourworld.fragment.CalendarFragment;
import com.ufos.cyw16.cleanyourworld.fragment.GeolocFragment;
import com.ufos.cyw16.cleanyourworld.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private String[] mPlanetTitles = {"prova", "prova2", "prova3"};
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    //private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp); // set menu icon

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);


        getSupportActionBar().setTitle(""); // delete app title

        createFragment(); //set ViewPager and TabLayout

        // it manages the left navigation bar
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar,
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);

    }


    // Defines the number of tabs by setting appropriate fragment and tab name
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CalendarFragment(), "Calendar");
        adapter.addFragment(new GeolocFragment(), "Geoloc");
        adapter.addFragment(new SearchFragment(), "Search");
        viewPager.setAdapter(adapter);
    }

    public void createFragment() {

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setTabTextColors(Color.parseColor("#e62b1e"), Color.parseColor("#e62b1e"));
        tabLayout.setupWithViewPager(viewPager); //Assigns the ViewPager to TabLayout

        setupTabIcons(tabLayout);

    }

    // set tab icons (calendar, geolocalization and research) to fragments to improve user interface
    private void setupTabIcons(TabLayout tabLayout) {

        // set calendar fragment icon
        ImageView tabCalendar = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabCalendar.setImageResource(R.drawable.ic_calendar_tab_24dp);
        tabLayout.getTabAt(0).setCustomView(tabCalendar);

        // set geolocalization fragment icon
        ImageView tabGeoloc = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabGeoloc.setImageResource(R.drawable.ic_geoloc_tab_24dp);
        tabLayout.getTabAt(1).setCustomView(tabGeoloc);

        // set research fragment icon
        ImageView tabSearch = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSearch.setImageResource(R.drawable.ic_search_tab_24dp);
        tabLayout.getTabAt(2).setCustomView(tabSearch);
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