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
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.fragment.CalendarMonthViewFragment;
import com.ufos.cyw16.cleanyourworld.fragment.CalendarWeekViewFragment;
import com.ufos.cyw16.cleanyourworld.fragment.DbFragment;
import com.ufos.cyw16.cleanyourworld.fragment.GeolocalizationActivity;
import com.ufos.cyw16.cleanyourworld.fragment.subfragment.BarCodeSearchSubFragment;
import com.ufos.cyw16.cleanyourworld.fragment.MaterialsSearchFragment;
import com.ufos.cyw16.cleanyourworld.fragment.subfragment.ProductsSearchSubFragment;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionTypeDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ColorDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ComuneDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.DayDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.IsolaEcologicaDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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


    private CollectionDao collectionDao;
    private CollectionTypeDao collectionTypeDao;
    private ColorDao colorDao;
    private ComuneDao comuneDao;
    private IsolaEcologicaDao isolaEcologicaDao;
    private MaterialDao materialDao;
    private ProductDao productDao;
    private ProductTypeDao productTypeDao;
    private ProvinciaDao provinciaDao;
    private RegioneDao regioneDao;
    private DayDao dayDao;


    //private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Message4Debug.log("MainActivity.onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateServer();



        parent = (RelativeLayout) findViewById(R.id.parentView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp); // set menu icon

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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

            createFragment(); //set initial fragment on the view

            // it manages the left navigation bar
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                    toolbar,
                    R.string.app_name, // nav drawer open - description for accessibility
                    R.string.app_name // nav drawer close - description for accessibility
            );

            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.lmSideBar);
            navigationView.setNavigationItemSelectedListener(this);

        }

    }

    private void updateServer() {

         /* DAOFactory */
        DaoFactory_def daoFactory = DaoFactory_def.getInstance(getBaseContext());

        /* Factories */
        List<EntityDao> entities = new ArrayList<>();
        entities.add((collectionDao = daoFactory.getCollectionDao()));
        entities.add((collectionTypeDao = daoFactory.getCollectionTypeDao()));
        entities.add((colorDao = daoFactory.getColorDao()));
        entities.add((comuneDao = daoFactory.getComuneDao()));
        entities.add((isolaEcologicaDao = daoFactory.getIsolaEcologicaDao()));
        entities.add((materialDao = daoFactory.getMaterialDao()));
        entities.add((productDao = daoFactory.getProductDao()));
        entities.add((productTypeDao = daoFactory.getProtuctTypeDao()));
        entities.add((provinciaDao = daoFactory.getProvinciaDao()));
        entities.add((regioneDao = daoFactory.getRegioneDao()));
        entities.add((dayDao = daoFactory.getDayDao()));
        Message4Debug.log("inizio aggiornamento del database interno...");
        long start = System.currentTimeMillis();
        for (EntityDao entity : entities) {
            try {
                Message4Debug.log(entity.getClass().getSimpleName());
                entity.updateFromServer(null, null);
            } catch (DaoException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message4Debug.log("aggiornamento completato in: " + (System.currentTimeMillis() - start));
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContent, new CalendarMonthViewFragment(), "fragment_screen");
        ft.commit();

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (item.getItemId()){
            case R.id.srchScan:
                ft.replace(R.id.fragmentContent, new BarCodeSearchSubFragment(), "fragment_screen");
                ft.commit();
                break;
            case R.id.srchProduct:
                ft.replace(R.id.fragmentContent, new ProductsSearchSubFragment(), "fragment_screen");
                ft.commit();
                break;
            case R.id.srchMaterial:
                ft.replace(R.id.fragmentContent, new MaterialsSearchFragment(), "fragment_screen");
                ft.commit();
                break;
            case R.id.geolocalizzation:
                Intent intent = new Intent(getBaseContext(), GeolocalizationActivity.class);
                startActivity(intent);
                break;
            case R.id.calendarMonthView:
                ft.replace(R.id.fragmentContent, new CalendarMonthViewFragment(), "fragment_screen");
                ft.commit();
                break;
            case R.id.calendarWeekView:
                ft.replace(R.id.fragmentContent, new CalendarWeekViewFragment(), "fragment_screen");
                ft.commit();
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(getBaseContext(), ConfigurationActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.dbFragment:
                ft.replace(R.id.fragmentContent, new DbFragment(), "fragment_screen");
                ft.commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

