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
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.Models.Comune;
import com.ufos.cyw16.cleanyourworld.Models.Provincia;
import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapter;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapterDataProvider;
import com.ufos.cyw16.cleanyourworld.config.ConfigStep;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProvinceTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RegioniTableAdapter;

import java.util.ArrayList;

public class ConfigurationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnContinue;
    private Button btnBack;
    private TextView chooseTV;
    private ImageView mapIV;

    ConfigAdapter adapter;
    private ConfigStep step;
    private ArrayList<ConfigAdapterDataProvider> data;

    /* Animation */
    private ProgressBar progressBar;
    private int animationDuration;

    // FIXME: 02/07/16 [DAO - MODELS] togliere i tablesAdapter e mettere le DAO
    private RegioniTableAdapter regioni;
    private ProvinceTableAdapter province;
    private ComuniTableAdapter comuni;

    /* chosen regione,provincia and comune; used later to save in shared prefs */
    private Regione regioneChosen = new Regione(0,"tmp");
    private Provincia provinciaChosen = new Provincia("tmp",0,0);
    private Comune comuneChosen = new Comune("tmp",0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        animationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);


        chooseTV = (TextView) findViewById(R.id.chooseTV);
        mapIV = (ImageView) findViewById(R.id.mapIV);

        progressBar = (ProgressBar) findViewById(R.id.progress);

        // start and configure recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnContinue.setEnabled(false);
        btnBack.setVisibility(View.INVISIBLE);


        step = ConfigStep.REGIONE; // first step
        data = new ArrayList<>();

        updateDBFromServer();

        startStep(data);

        for(ConfigAdapterDataProvider tmp : data){
            System.out.println("DATA : " + tmp.getName());
        }

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

        /* set adapter and layout manager for recycler view */
        adapter = new ConfigAdapter(data);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                /* whenever an item is selected, the regioneCHosen attribute is update as to keep track of selection and later
                * used in SQL queries (to display only related information);
                * */
                ConfigAdapterDataProvider provider = data.get(position);
                setChosenLocation(provider);
                Toast.makeText(getApplicationContext(),"You chose "+ provider.getName(),Toast.LENGTH_SHORT).show();

                btnContinue.setEnabled(true);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void updateDBFromServer() {
        /* downloads all regioni,province,comuni from server and inserts into local DB */
        // FIXME: 02/07/16 [DAO] Mettere le DAO
        regioni = new RegioniTableAdapter(getApplicationContext());
        province = new ProvinceTableAdapter(getApplicationContext());
        comuni = new ComuniTableAdapter(getApplicationContext());
        try {
            regioni.updateFromServer(null,null);
            province.updateFromServer(null,null );
            comuni.updateFromServer(null,null);

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

    private void setChosenLocation(ConfigAdapterDataProvider provider) {
        // FIXME: 02/07/16 [MODELS] Sostituire con nuovi model
        switch (step){
            case REGIONE:

                break;
            case PROVINCIA:
                regioneChosen.setId(provider.getId());
                regioneChosen.setName(provider.getName());

                break;
            case COMUNE:
                provinciaChosen.setId(provider.getId());
                provinciaChosen.setName(provider.getName());
                provinciaChosen.setIdRegione(regioneChosen.getId());

                break;
            case END:
                comuneChosen.setName(provider.getName());
                comuneChosen.setId(provider.getId());
                comuneChosen.setIdProvincia(provinciaChosen.getId());
                break;
        }
    }

    private void startStep(ArrayList<ConfigAdapterDataProvider> data) {
        
        if(step == ConfigStep.END){
            showRecapDialog();
        }

        startLoadingAnimation();

        data.clear();
        fillDataArray(data);
        changeChooseTV();
        btnContinue.setEnabled(false);

        if(step == ConfigStep.PROVINCIA) {
            btnBack.setVisibility(View.INVISIBLE);
        } else {
            btnBack.setVisibility(View.VISIBLE);
        }

        crossfade();
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
        // FIXME: 02/07/16 [MODELS] sostituire con nuovi model
        editor.putInt("regione_id",regioneChosen.getId());
        editor.putInt("provincia_id",provinciaChosen.getId());
        editor.putInt("comune_id",comuneChosen.getId());

        editor.apply();
    }

    private void startNewActivity(){

        Intent main = new Intent(getApplicationContext(),MainActivity.class);
        //when the new activity starts, this one calls finish so you can't return to it with the back button
        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putBoolean("configDone",true);
        main.putExtras(b);
        startActivity(main);
        finish();
    }

    private void changeChooseTV() {

        switch (step){
            case REGIONE:

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
        // FIXME: 02/07/16 [DAO - MODELS] controllare i nuovi models e le chiamate ai tablesAdapter
        switch (step){
            case REGIONE:

                // TODO change return type of .getData()
                ArrayList<ArrayList<String>> regioni_al = new ArrayList<>();
                try {
                    /* returns all data from local db (previously updated from server)*/
                    regioni_al = regioni.getData(null,null,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                convertToDataProvider(ConfigStep.REGIONE,data,regioni_al);

                if(adapter != null){
                    adapter.notifyDataSetChanged();
                }
                /* changes next step of config */
                step = ConfigStep.PROVINCIA;
                break;

            case PROVINCIA:

                ArrayList<ArrayList<String>> province_al = new ArrayList<>();
                /* SELECT * FROM province WHERE regioni_id = (id of previously chosen regione) */
                String[] selection = {"regioni_id"};
                String[] values = {String.valueOf(regioneChosen.getId())};
                try {
                    province_al = province.getData(selection,values,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                convertToDataProvider(ConfigStep.PROVINCIA,data,province_al);

                adapter.notifyDataSetChanged();
                step = ConfigStep.COMUNE;
                break;

            case COMUNE:

                ArrayList<ArrayList<String>> comuni_al = new ArrayList<>();
                /* SELECT * from comuni WHERE province_id = (id of previously chosen province)*/
                String[] select_comuni = {"province_id"};
                String[] values_comuni = {String.valueOf(provinciaChosen.getId())};

                try {
                    comuni_al = comuni.getData(select_comuni,values_comuni,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                convertToDataProvider(ConfigStep.COMUNE,data,comuni_al);
                adapter.notifyDataSetChanged();

                step = ConfigStep.END;
                break;
        }
    }

    private void convertToDataProvider(ConfigStep step, ArrayList<ConfigAdapterDataProvider> data, ArrayList<ArrayList<String>> db_data) {

        int i;

        for (i = 0; i < db_data.size(); i++){
            ConfigAdapterDataProvider tmp = new ConfigAdapterDataProvider();
            tmp.setName(db_data.get(i).get(1));
            tmp.setId(Integer.valueOf(db_data.get(i).get(0)));
            data.add(tmp);
            System.out.println(tmp.getName() + tmp.getId());
        }

    }


    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

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
