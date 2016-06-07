/*
 * Created by Umberto Ferracci from urania and published on 07/06/16 5.17
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * File name: DbFragment.java
 * Class name: DbFragment
 * Last modified: 07/06/16 5.14
 */

/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * File name: DbFragment.java
 * Class name: DbFragment
 * Last modified: 04/06/16 20.16
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RegioniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaProdottiTableAdapter;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

/**
 * The type Db fragment.
 */
public class DbFragment extends Fragment {

    private Button btn_insertRegion, btn_viewAllRegion, btn_aggiorna, btn_prodType, btn_comuni;
    private ArrayList<Button> buttons;
    private SubFragmentButtonManager subFragmentButtonManager;
    private SubListViewOnItemClick subListViewOnItemClick;
    private ListView listView;
    private ProgressBar progressBar;
    private boolean init = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.db_fragment, container, false);
        createFragment(v);
        return v;
    }

    /**
     * Create fragment.
     *
     * @param v the v
     */
    public void createFragment(View v) {

        /* Buttons */
        buttons = new ArrayList<Button>();
        buttons.add(btn_viewAllRegion = (Button) v.findViewById(R.id.btn_viewAllRegion));
        buttons.add(btn_aggiorna = (Button) v.findViewById(R.id.btn_aggiorna));
        buttons.add(btn_prodType = (Button) v.findViewById(R.id.btn_typeProd));        // TODO: 01/06/16
        buttons.add(btn_comuni = (Button) v.findViewById(R.id.btn_comuni));        // TODO: 02/06/16
        subFragmentButtonManager = new SubFragmentButtonManager();
        for (Button b : buttons) {
            b.setOnClickListener(subFragmentButtonManager);
        }
         /* Listview */
        listView = (ListView) v.findViewById(R.id.listView);
        subListViewOnItemClick = new SubListViewOnItemClick();
        listView.setOnItemClickListener(subListViewOnItemClick);
        /* ProgressBar */
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

    }

    /**
     * The type Sub fragment button manager.
     */
    public class SubFragmentButtonManager implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            Context context = v.getContext();
            Message4Debug.log("SubFragmentButtonManager.oncClick()");
            RegioniTableAdapter regioniTableAdapter = new RegioniTableAdapter(context);
            TipologiaProdottiTableAdapter tipologiaProdottiTableAdapter = new TipologiaProdottiTableAdapter(context);
            ComuniTableAdapter comuniTableAdapter = new ComuniTableAdapter(context);

            switch (v.getId()) {
                case R.id.btn_viewAllRegion:
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        regioniTableAdapter.getData(null, null);
                        ArrayList<String> list = new ArrayList<>();
                        list.add("ciao cioa 1");
                        list.add("ciao cioa 2");
                        list.add("ciao cioa 3");
                        list.add("ciao cioa 4");
                        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(itemAdapter);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
                case R.id.btn_aggiorna:
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        regioniTableAdapter.queryServer2(null, null);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    } catch (InterruptedException e) {
                        Message4Debug.log(e.toString());
                    }
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        list.add("ciao dioa 1");
                        list.add("ciao dioa 2");
                        list.add("ciao dioa 3");
                        list.add("ciao dioa 4");
                    }
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
                    listView.setAdapter(itemAdapter);
                    break;
                case R.id.btn_typeProd:
                    try {
                        tipologiaProdottiTableAdapter.queryServer2(null, null);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    } catch (InterruptedException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
                case R.id.btn_comuni:
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        comuniTableAdapter.queryServer2(new String[]{"province_id"}, new String[]{"12"});
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    } catch (InterruptedException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
            }
        }
    }

    public class SubListViewOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Message4Debug.log("hai cliccato su: " + String.valueOf(position));
            Message4Debug.log("id: " + String.valueOf(id));
            Message4Debug.log("qualcosa: " + parent.getItemAtPosition(position).toString());
        }

    }
}
