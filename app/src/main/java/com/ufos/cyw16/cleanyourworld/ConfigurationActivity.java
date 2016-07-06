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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.config.ConfigAdapter;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapterDataProvider;
import com.ufos.cyw16.cleanyourworld.config.ConfigStep;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Comune;
import com.ufos.cyw16.cleanyourworld.model_new.Provincia;
import com.ufos.cyw16.cleanyourworld.model_new.Regione;
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

    List<ConfigAdapterDataProvider> comuniWithCollection = new ArrayList<>();


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
        searchView.setVisibility(View.INVISIBLE);


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

        // when switch is available,filters list with comuni with available collection
        switchRecycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    adapter.animateTo(comuniWithCollection);
                } else {
                    adapter.animateTo(data);
                    recyclerView.scrollToPosition(0);
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* btnContinue is enabled when you choose an item of the list ;
                * when it's clicked updates the data array and notifies the adapter that data has changed */
                startStep(data);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {

            //change the step of the configuration
            @Override
            public void onClick(View v) {
                // change step of config 1 step before
                // when back button is pressed
                switch (step){
                    case PROVINCIA:
                        step = ConfigStep.REGIONE;
                        break;
                    case COMUNE:
                        step = ConfigStep.REGIONE;
                        break;
                    case END:
                        step = ConfigStep.PROVINCIA;
                        comuniWithCollection.clear();
                        disableSwitch();

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

                /* whenever an item is selected, the regioneChosen attribute is update as to keep track of selection and later
                * used in SQL queries (to display only related information);
                * */
                ConfigAdapterDataProvider provider = adapter.getData().get(position);
                setChosenLocation(provider,position);
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.you_chose) + " " +provider.getName(),Toast.LENGTH_SHORT).show();


                //checks if selected comune has collection available in local db
                TextView tv = (TextView) view.findViewById(R.id.name);
                if(step == ConfigStep.END) {
                    int comuniWithCollectionSize = comuniWithCollection.size();
                    boolean hasCollection = false;
                    for(int i = 0;i < comuniWithCollectionSize; i++){
                        if(comuniWithCollection.get(i).getName().equals(tv.getText())){
                            hasCollection = true;
                        }
                    }

                    // if it doesn't , set it to null
                    if(!hasCollection){
                        comuneChosen = null;
                    }
                }

                btnContinue.setEnabled(true);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void disableSwitch() {
        //make it unchecked
        switchRecycle.setChecked(false);
        switchRecycle.setVisibility(View.INVISIBLE);
        trashIV.setVisibility(View.INVISIBLE);

    }

    // private async task that gets from local db all regioni,province and comuni
    private class LoadFromDB extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //shows progress bar and makes recycler view invisible
            startLoadingAnimation();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            // when async task finishes (downloads from server and loads from local DB)
            // starts showing data to recycler view
            startStep(data);

            // animate from progress bar to recycler view
            crossfade();
            searchView.setVisibility(View.VISIBLE);
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
            //update all tables from remote db once configuration starts
            regioneDao.updateFromServer(null,null);
            provinciaDao.updateFromServer(null,null);
            comuneDao.updateFromServer(null,null);

            //get list of all regioni (includes also all province and comuni)
            regioni_al = regioneDao.findAllLazy();


        } catch (DaoException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    //show indeterminate progress bar
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
                int regione_id = adapter.getData().get(position).getId();

                //checks which regione has selected id
                for(Regione r : regioni_al){
                    if(provider.getId() == r.getIdRegione_int()){
                        regioneChosen = r;
                        break;
                    }
                }

                //get list of all province once you chose a regione
//                province_al = regioneChosen.getProvince(); // FIXME: 06/07/16
                try {
                    province_al = provinciaDao.getByIdRegionLazy(regioneChosen.getIdRegione_int());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                break;
            case COMUNE:
                int provincia_id = adapter.getData().get(position).getId();

                //find provincia with selected id
                for(Provincia p : province_al){
                    if(provider.getId() == p.getIdProvincia()){
                        provinciaChosen = p;
                        break;
                    }
                }

                //get all comuni once you chose a provincia
//                comuni_al = provinciaChosen.getComuni(); //// FIXME: 06/07/16
                try {
                    comuni_al = comuneDao.getByIdProvinciaLazy(provinciaChosen.getIdProvincia());
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                break;
            case END:

                //get selected comune
                for(Comune c : comuni_al){
                    if(provider.getId() == c.getIdComune()){
                        comuneChosen = c;
                        break;
                    }
                }
                break;
        }
    }

    private void startStep(ArrayList<ConfigAdapterDataProvider> data) {
        
        if(step == ConfigStep.END){
            //if you are at the end of configuration and you chose a comune with collection, you can continue
            if(comuneChosen != null) {
                showRecapDialog();
            //else show dialog to choose another comune
            } else {
                showChooseAnotherComuneDialog();
            }
        }

        //empty array to later fill it
        data.clear();
        fillDataArray(data);
        changeChooseTV();
        btnContinue.setEnabled(false);

        //set back button visibility depending where you are in the configuration step
        if(step == ConfigStep.PROVINCIA) {
            btnBack.setVisibility(View.INVISIBLE);
        } else {
            btnBack.setVisibility(View.VISIBLE);
        }

    }

    private void showChooseAnotherComuneDialog() {
        //show dialog to choose another comune because this one doesn't have collection in out db

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.RedAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.not_available));
        builder.setMessage(getResources().getString(R.string.choose_another_comune));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        builder.show();

    }

    private void showRecapDialog() {
        //once you chose a comune, shows position IF collection is available in our db

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.location));
        builder.setMessage(getResources().getString(R.string.you_are_in) + "\n" + comuneChosen.getName() + ", "+provinciaChosen.getName() + ", " + regioneChosen.getName());
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save chosen comune to sharedPrefs
                saveToSharedPrefs();
                //start main activity
                startNewActivity();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //go back one step and choose another comune
                step = ConfigStep.COMUNE;
                startStep(data);
            }
        });

        builder.show();
    }

    private void saveToSharedPrefs() {
        //saves ids to later use in main activity
        SharedPreferences prefs = getSharedPreferences("CYW", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("configDone",true);
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
                chooseTV.setText(R.string.choose_regione);
                break;
            case COMUNE:
                chooseTV.setText(R.string.choose_prov);
                break;
            case END:
                chooseTV.setText(R.string.choose_comune);
                break;
        }
    }


    /* fills data array depending on the step of the configuration you are in */
    private void fillDataArray(ArrayList<ConfigAdapterDataProvider> data) {

        switch (step){
            case REGIONE:
                //converts List of Regione to a class needed to fill recycler view
                convertToDataProviderRegione(data,regioni_al);

                if(adapter != null){
                    //notify the adapter that data has changes
                    adapter.animateTo(data);
                }
                /* changes next step of config */
                step = ConfigStep.PROVINCIA;
                break;

            case PROVINCIA:

                // converts List of Provincia to a class needed by recycler view
                convertToDataProviderProvincia(data,province_al);

                // notify change has been made to array
                adapter.animateTo(data);
                // update state of config
                step = ConfigStep.COMUNE;
                break;

            case COMUNE:

                convertToDataProviderComune(data,comuni_al);
                adapter.animateTo(data);

                //switch used only when you need to choose a comune
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
        //converts Comune class to ConfigAdapterDataProvider class used to fill recycler view
        for(Comune r : comuni_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdComune());
            tmp.setName(r.getName());
            //if comune has collection available
            if(r.getCollections() != null) {
                tmp.setHasCollection(true);
                //keep an array of comuni with collection
                comuniWithCollection.add(tmp);
                System.out.println(r.getIdComune());
            } else {
                tmp.setHasCollection(false);
            }
            data.add(tmp);
        }
    }

    private void convertToDataProviderProvincia(ArrayList<ConfigAdapterDataProvider> data, List<Provincia> province_al) {
        //converts Provincia class to ConfigAdapterDataProvider used by adapter to fill list
        // it's the same adapter and recycler view for REGIONI,PROVINCE and COMUNI
        for(Provincia r : province_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdProvincia());
            tmp.setName(r.getName());
            data.add(tmp);
        }
    }

    private void convertToDataProviderRegione(ArrayList<ConfigAdapterDataProvider> data, List<Regione> regioni_al) {
        //converts Regione class to ConfigAdapterDataProvider needed to fill list
        for(Regione r : regioni_al){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setId(r.getIdRegione_int());
            tmp.setName(r.getName());
            data.add(tmp);
        }
    }


    //the activity itself is the listener (implements interface)
    @Override
    public boolean onQueryTextSubmit(String query) {
        //not used
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //filters list by new inserted text
        List<ConfigAdapterDataProvider> filteredModelList;
        if(switchRecycle.isChecked()){
            filteredModelList = filter(comuniWithCollection,newText);
        } else {
            filteredModelList = filter(data, newText);
        }
        //shows only filtered rows
        adapter.animateTo(filteredModelList);
        //scroll to top
        recyclerView.scrollToPosition(0);
        return true;
    }

    private List<ConfigAdapterDataProvider> filter(List<ConfigAdapterDataProvider> models, String query) {
        query = query.toLowerCase();

        final List<ConfigAdapterDataProvider> filteredModelList = new ArrayList<>();
        for (ConfigAdapterDataProvider model : models) {
            //fills array only with matching rows
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        //OnItemTouchListener for recycler view

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

    //Click listener interface used in recycler view on item listener
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
