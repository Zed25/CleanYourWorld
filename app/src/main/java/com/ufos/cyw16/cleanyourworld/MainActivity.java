/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.MainActivity
 * File name: MainActivity.java
 * Class name: MainActivity
 * Last modified: 02/06/16 23.48
 */

package com.ufos.cyw16.cleanyourworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.fragment.CalendarFragment;
import com.ufos.cyw16.cleanyourworld.fragment.DbFragment;
import com.ufos.cyw16.cleanyourworld.fragment.GeolocFragment;
import com.ufos.cyw16.cleanyourworld.fragment.SearchFragment;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout parent;

    private Toolbar toolbar;
    private String[] mPlanetTitles = {"prova", "prova2", "prova3"};
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private FrameLayout mainFrame;
    private FrameLayout loadFrame;
    private FrameLayout configFrame;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ComuniTableAdapter comuniTableAdapter;


    //private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Message4Debug.log("MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = (RelativeLayout) findViewById(R.id.parentView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp); // set menu icon

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);
        loadFrame = (FrameLayout) findViewById(R.id.loadFrame);


        mainFrame.setVisibility(View.INVISIBLE);
        loadFrame.setVisibility(View.VISIBLE);

        if(configurationDone()){

            mainFrame.setVisibility(View.VISIBLE);
            loadFrame.setVisibility(View.INVISIBLE);

            printSharedPrefsAfterConfig();
        } else {

                if(!isNetworkAvailable()){

                    showNoNetworkDialog();
                } else {
                    // prepare for configuration as to choose your COMUNE
                    prepareForConfiguration();
                }

        }

        ImageView backgroud = (ImageView) findViewById(R.id.backgraound);
        if (backgroud != null) {
            backgroud.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.clean_your_world));
        }


        getSupportActionBar().setTitle(""); // delete app title

        if(isNetworkAvailable()) {

            createFragment(); //set ViewPager and TabLayout

            // it manages the left navigation bar
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    toolbar,
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    // calling onPrepareOptionsMenu() to showToast action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            };
            drawerLayout.addDrawerListener(drawerToggle);

        }

    }

    private void showNoNetworkDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.YellowAlertDialogStyle);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Open an Internet connection and try again.");

        String positiveText = "TRY AGAIN";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // check if internet connect and try again
                        if(isNetworkAvailable()){
                            prepareForConfiguration();
                        }else {
                            showNoNetworkDialog();
                        }

                    }
                });

        String negativeText = "EXIT";
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });


        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }

    // TODO remove before deploy; used only for debug
    private void printSharedPrefsAfterConfig() {

        SharedPreferences prefs = getSharedPreferences("comune",MODE_PRIVATE);
        System.out.println("CHOSEN REGIONE ID: " + prefs.getInt("regione_id",0));
        System.out.println("CHOSEN PROV ID: " + prefs.getInt("provincia_id",0));
        System.out.println("CHOSEN COMUNE ID: " + prefs.getInt("comune_id",0));
    }

    private boolean configurationDone() {

        /* if configuration process completed successfully, a configDone Bool variable (set to TRUE)
         * is passed by the config activity
         */
        if(getIntent().hasExtra("configDone")) {

            Bundle b = getIntent().getExtras();
            Boolean configDone = b.getBoolean("configDone");
            return configDone;
        }

        return false;

    }


    private void prepareForConfiguration() {
        /* if it's the first time you open the app or you haven't previously chosen a COMUNE,
        *  a configuration activity starts */
        Intent configurationIntent = new Intent(this.getBaseContext(), ConfigurationActivity.class);
        startActivity(configurationIntent);

    }


    // Defines the number of tabs by setting appropriate fragment and tab name
    private void setupViewPager(ViewPager viewPager) {
        Message4Debug.log("MainActivity.setupViewPager()");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CalendarFragment(), "Calendar");
        adapter.addFragment(new GeolocFragment(), "Geoloc");
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new DbFragment(), "Database");
        viewPager.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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

        // set database fragment icon
        ImageView tabDb = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSearch.setImageResource(R.drawable.ic_search_tab_24dp);
        tabLayout.getTabAt(3).setCustomView(tabDb);
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

