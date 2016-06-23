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


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView chooseTV;
    private ImageView mapIV;

    ConfigAdapter adapter;
    private ConfigStep step;
    private ArrayList<ConfigAdapterDataProvider> data;

    private Regione regioneChosen = new Regione(0,"tmp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        chooseTV = (TextView) findViewById(R.id.chooseTV);
        mapIV = (ImageView) findViewById(R.id.mapIV);

        // start and configure recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        if (btnContinue != null) {
            btnContinue.setEnabled(false);
        }

        step = ConfigStep.REGIONE; // first step
        data = new ArrayList<>();

        startStep(data);

        for(ConfigAdapterDataProvider tmp : data){
            System.out.println("DATA : " + tmp.getName());
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startStep(data);
            }
        });

        /* set adapter and layout manager for recycler view*/
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
                regioneChosen.setId(provider.getId());
                regioneChosen.setName(provider.getName());
                Toast.makeText(getApplicationContext(),"You chose "+ provider.getName(),Toast.LENGTH_SHORT).show();

                btnContinue.setEnabled(true);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void startStep(ArrayList<ConfigAdapterDataProvider> data) {

        data.clear();
        fillDataArray(data);
        changeChooseTV();
        btnContinue.setEnabled(false);
    }

    private void changeChooseTV() {

        switch (step){
            case REGIONE:
                break;
            case PROVINCIA:
                chooseTV.setText("Choose Your Province");
                break;
            case COMUNE:
                chooseTV.setText("Choose your Comune");
                break;
        }
    }


    /* fills data array depending on the step of the configuration you are in */
    private void fillDataArray(ArrayList<ConfigAdapterDataProvider> data) {

        switch (step){
            case REGIONE:
                RegioniTableAdapter regioni = new RegioniTableAdapter(getApplicationContext());
                try {
                    regioni.updateFromServer(null,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // TODO change return type of .getData()
                ArrayList<ArrayList<String>> regioni_al = new ArrayList<>();
                try {
                    regioni_al = regioni.getData(null,null,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }
                convertToDataProvider(ConfigStep.REGIONE,data,regioni_al);
                step = ConfigStep.PROVINCIA;
                break;
            case PROVINCIA:
                ProvinceTableAdapter provinceTableAdapter = new ProvinceTableAdapter(getApplicationContext());
                try {
                    provinceTableAdapter.updateFromServer(null,null );
                } catch (DaoException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ArrayList<ArrayList<String>> province_al = new ArrayList<>();
                String[] selection = {"regioni_id"};
                String[] values = {String.valueOf(regioneChosen.getId())};
                try {
                    province_al = provinceTableAdapter.getData(selection,values,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                convertToDataProvider(ConfigStep.PROVINCIA,data,province_al);

                adapter.notifyDataSetChanged();
                step = ConfigStep.COMUNE;
                break;
            case COMUNE:
                ComuniTableAdapter comuni = new ComuniTableAdapter(getApplicationContext());

                try {
                    comuni.updateFromServer(null,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<ArrayList<String>> comuni_al = new ArrayList<>();
                String[] select_comuni = {"province_id"};
                String[] values_comuni = {String.valueOf(regioneChosen.getId())};

                try {
                    comuni_al = comuni.getData(select_comuni,values_comuni,null);
                } catch (DaoException e) {
                    e.printStackTrace();
                }

                convertToDataProvider(ConfigStep.COMUNE,data,comuni_al);
                adapter.notifyDataSetChanged();


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
