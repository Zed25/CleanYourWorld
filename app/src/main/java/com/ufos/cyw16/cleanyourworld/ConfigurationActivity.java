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

import android.app.Fragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.Models.Regione;
import com.ufos.cyw16.cleanyourworld.config.ConfigAdapter;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

public class ConfigurationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        // start and configure recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnContinue = (Button) findViewById(R.id.btnContinue);

        if (btnContinue != null) {
            btnContinue.setEnabled(false);
        }

        final ArrayList<Regione> regioni = new ArrayList<>();
        regioni.add(new Regione(1,"Lazio"));
        regioni.add(new Regione(2,"Umbria"));
        ConfigAdapter adapter = new ConfigAdapter(regioni);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Regione regione = regioni.get(position);
                Toast.makeText(getApplicationContext(),"You chose "+ regione.getName(),Toast.LENGTH_SHORT).show();

                btnContinue.setEnabled(true);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
