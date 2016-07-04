/*
 * Created by Umberto Ferracci from simone_mancini and published on 20/06/16 15.47
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.ConfigurationActivity
 * File name: ConfigurationActivity.java
 * Class name: ConfigurationActivity
 * Last modified: 20/06/16 15.47
 */

package com.ufos.cyw16.cleanyourworld;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.model_new.Comune;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.Provincia;
import com.ufos.cyw16.cleanyourworld.model_new.Regione;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapter;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapterDataProvider;
import com.ufos.cyw16.cleanyourworld.config.ConfigStep;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProvinceTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RegioniTableAdapter;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ComuneDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProvinciaDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.RegioneDao;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private Button btnContinue;
    private Button btnBack;
    private TextView chooseTV;
    private ImageView mapIV;
    private ImageView trashIV;
    private SwitchCompat switchRecycle;

    ConfigAdapter adapter;
    private ConfigStep step;
    private ArrayList<ConfigAdapterDataProvider> data;

    private SearchView searchView;

    /* Animation */
    private ProgressBar progressBar;
    private int animationDuration;


    private RegioneDao regioneDao;
    private ProvinciaDao provinciaDao;
    private ComuneDao comuneDao;

    /* chosen regione,provincia and comune; used later to save in shared prefs */
    private Regione regioneChosen = new Regione();
    private Provincia provinciaChosen = new Provincia();
    private Comune comuneChosen = new Comune();

    private List<Regione> regioni_al = null;
    private List<Provincia> province_al = null;
    private List<Comune> comuni_al = null;

    LoadFromDB asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        animationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);


        chooseTV = (TextView) findViewById(R.id.chooseTV);
        mapIV = (ImageView) findViewById(R.id.mapIV);
        trashIV = (ImageView) findViewById(R.id.trashIV);

        switchRecycle = (SwitchCompat) findViewById(R.id.switchRecycle);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        searchView = (SearchView) findViewById(R.id.search_config);

        // start and configure recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnContinue.setEnabled(false);
        btnBack.setVisibility(View.INVISIBLE);

        // set switch and trash Image View invisibile; set visible only when you are choosing
        // a comune
        trashIV.setVisibility(View.INVISIBLE);
        switchRecycle.setVisibility(View.INVISIBLE);

        // add a listener to search view
        // the class itself is the listener because implements interface
        searchView.setOnQueryTextListener(this);


        step = ConfigStep.REGIONE; // first step
        data = new ArrayList<>();

        //updateDBFromServer();

        asyncTask = new LoadFromDB();
        asyncTask.execute();

        //startStep(data);

        /* set adapter and layout manager for recycler view */
        adapter = new ConfigAdapter(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* btnContinue is enabled when you choose an item of the list ;
                * when it's clicked updates the data array and notifies the adapter that data has changed */
                startStep(data);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (step){
                    case PROVINCIA:
                        step = ConfigStep.REGIONE;
                        break;
                    case COMUNE:
                        step = ConfigStep.REGIONE;
                        break;
                    case END:
                        step = ConfigStep.PROVINCIA;
                        break;
                    default:
                        step = ConfigStep.COMUNE;
                        break;
                }

                startStep(data);
            }
        });



        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                /* whenever an item is selected, the regioneCHosen attribute is update as to keep track of selection and later
                * used in SQL queries (to display only related information);
                * */
                ConfigAdapterDataProvider provider = data.get(position);
                setChosenLocation(provider,position);
                Toast.makeText(getApplicationContext(),"You chose "+ provider.getName(),Toast.LENGTH_SHORT).show();

                btnContinue.setEnabled(true);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private class LoadFromDB extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            startLoadingAnimation();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            startStep(data);

            crossfade();
        }

        @Override
        protected Void doInBackground(Void... params) {

            updateDBFromServer();

            return null;

        }
    }

    private void updateDBFromServer() {
        /* downloads all regioni,province,comuni from server and inserts into local DB */
        regioneDao = DaoFactory_def.getInstance(getApplicationContext()).getRegioneDao();
        provinciaDao = DaoFactory_def.getInstance(getApplicationContext()).getProvinciaDao();
        comuneDao = DaoFactory_def.getInstance(getApplicationContext()).getComuneDao();


        try {
            regioneDao.updateFromServer(null,null);
            provinciaDao.updateFromServer(null,null);
            comuneDao.updateFromServer(null,null);

            regioni_al = regioneDao.findAll();
            //province_al = provinciaDao.findAll();
            //comuni_al = comuneDao.findAll();

        } catch (DaoException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    private void startLoadingAnimation() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);


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

    private void setChosenLocation(ConfigAdapterDataProvider provider,int position) {
        switch (step){
            case REGIONE:

                break;
            case PROVINCIA:
                regioneChosen = regioni_al.get(position);
                //regioneChosen.setIdRegione_int(provider.getId());
                //regioneChosen.setName(provider.getName());
                province_al = regioneChosen.getProvince();
                break;
            case COMUNE:
                provinciaChosen = province_al.get(position);
                //provinciaChosen.setIdProvincia(province_al.get(position).getIdProvincia());
                //provinciaChosen.setName(province_al.get(position).getName());
                comuni_al = provinciaChosen.getComuni();
                break;
            case END:

                comuneChosen = comuni_al.get(position);
                //comuneChosen.setName(comuni_al.get(position).getName());
                //comuneChosen.setIdComune(comuni_al.get(position).getIdComune());
                break;
        }
    }

    private void startStep(ArrayList<ConfigAdapterDataProvider> data) {
        
        if(step == ConfigStep.END){
            showRecapDialog();
        }

        //startLoadingAnimation();

        data.clear();
        fillDataArray(data);
        changeChooseTV();
        btnContinue.setEnabled(false);

        if(step == ConfigStep.PROVINCIA) {
            btnBack.setVisibility(View.INVISIBLE);
        } else {
            btnBack.setVisibility(View.VISIBLE);
        }

        //crossfade();
    }

    private void showRecapDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle("Location");
        builder.setMessage("You are in : \n" + comuneChosen.getName() + ","+provinciaChosen.getName() + "," + regioneChosen.getName());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveToSharedPrefs();
                startNewActivity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                step = ConfigStep.COMUNE;
                startStep(data);
            }
        });

        builder.show();
    }

    private void saveToSharedPrefs() {
        SharedPreferences prefs = getSharedPreferences("comune",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // TODO remove comments when finished
        //editor.putBoolean("firstTime",false);
        editor.putInt("regione_id",regioneChosen.getIdRegione_int());
        editor.putInt("provincia_id",provinciaChosen.getIdProvincia());
        editor.putInt("comune_id",comuneChosen.getIdComune());

        editor.apply();
    }

    private void startNewActivity(){

        Intent main = new Intent(getApplicationContext(),MainActivity.class);
        //when the new activity starts, this one calls finish so you can't return to it with the back button
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //create a bundle and fill with a boolean needed in main activity
        //to verify that configuration has been successfully made
        Bundle b = new Bundle();
        b.putBoolean("configDone",true);
        main.putExtras(b);
        startActivity(main);
        //kill activity
        finish();
    }

    private void changeChooseTV() {

        // depending on the config step you are in, the text view changes
        switch (step){
            case REGIONE:
                // do nothing, it's already set by default
                break;
            case PROVINCIA:
                chooseTV.setText("Choose Your Regione");
                break;
            case COMUNE:
                chooseTV.setText("Choose Your Province");
                break;
            case END:
                chooseTV.setText("Choose your Comune");
                break;
        }
    }


    /* fills data array depending on the step of the configuration you are in */
    private void fillDataArray(ArrayList<ConfigAdapterDataProvider> data) {

        switch (step){
            case REGIONE:
                //get all regione from local DB
                /*List<Regione> regioni_al = null;
                try {
                    regioni_al = regioneDao.findAll();
                } catch (DaoException e) {
                    e.printStackTrace();
                }*/

                //converts List of Regione to a class needed to fill recycler view
                convertToDataProviderRegione(data,regioni_al);

                if(adapter != null){
                    //notify the adapter that data has changes
                    adapter.notifyDataSetChanged();
                }
                /* changes next step of config */
                step = ConfigStep.PROVINCIA;
                break;

            case PROVINCIA:
                // get all Provincia from local DB
                /*List<Provincia> province_al = null;
                try {
                    province_al = provinciaDao.getByIdRegion(regioneChosen.getIdRegione_int());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
*/
                //province_al = regioneChosen.getProvince();
                // converts List of Provincia to a class needed by recycler view
                convertToDataProviderProvincia(data,province_al);

                // notify change has been made to array
                adapter.notifyDataSetChanged();
                // update state of config
                step = ConfigStep.COMUNE;
                break;

            case COMUNE:
                // get all Comune from local DB
                /*List<Comune> comuni_al = null;
                try {
                    comuni_al = comuneDao.getByIdProvincia(provinciaChosen.getIdProvincia());
                } catch (DaoException e) {
                    e.printStackTrace();
                }*/

                //comuni_al = provinciaChosen.getComuni();

                convertToDataProviderComune(data,comuni_al);
                adapter.notifyDataSetChanged();

                enableSwitch();

                step = ConfigStep.END;
                break;
        }
    }

    private void enableSwitch() {
        switchRecycle.setVisibility(View.VISIBLE);
        trashIV.setVisibility(View.VISIBLE);
    }

    private void convertToDataProviderComune(ArrayList<ConfigAdapterDataProvider> data, List<Comune> comuni_al) {
        for(Comune r : comuni_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdComune());
            tmp.setName(r.getName());
            data.add(tmp);
        }
    }

    private void convertToDataProviderProvincia(ArrayList<ConfigAdapterDataProvider> data, List<Provincia> province_al) {
        for(Provincia r : province_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdProvincia());
            tmp.setName(r.getName());
            data.add(tmp);
        }
    }

    private void convertToDataProviderRegione(ArrayList<ConfigAdapterDataProvider> data, List<Regione> regioni_al) {

        for(Regione r : regioni_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdRegione_int());
            tmp.setName(r.getName());
            data.add(tmp);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ConfigAdapterDataProvider> filteredModelList = filter(data, newText);
        adapter.animateTo(filteredModelList);
        //adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<ConfigAdapterDataProvider> filter(List<ConfigAdapterDataProvider> models, String query) {
        query = query.toLowerCase();

        final List<ConfigAdapterDataProvider> filteredModelList = new ArrayList<>();
        for (ConfigAdapterDataProvider model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        //adds a listener for every row in recycler view
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
