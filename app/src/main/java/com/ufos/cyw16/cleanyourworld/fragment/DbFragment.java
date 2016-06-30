/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * File name: DbFragment.java
 * Class name: DbFragment
 * Last modified: 09/06/16 16.50
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 15.40
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * File name: DbFragment.java
 * Class name: DbFragment
 * Last modified: 09/06/16 15.38
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 12.16
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * File name: DbFragment.java
 * Class name: DbFragment
 * Last modified: 09/06/16 12.06
 */

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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.ListOfListAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ComuniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.GiorniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologicaTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.MaterialiTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.PreferitiTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProdottiTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProvinceTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RaccoltaTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RegioniTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaProdottiTableAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccoltaTableAdapter;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;

/**
 * The type Db fragment.
 */
public class DbFragment extends Fragment {

    private Button btn_setup, btn_viewAllRegion, btn_aggiorna, btn_prodType, btn_comuni;
    private ArrayList<Button> buttons;
    private SubFragmentButtonManager subFragmentButtonManager;
    private SubListViewOnItemClick subListViewOnItemClick;
    private ListView listView;
    private ProgressBar progressBar;
    private boolean init = true;
    private Context context;
    private ProgressDialog progressDialog;
    /* Tables Adapter*/
    private ArrayList<TableAdapter> tables;
    private RegioniTableAdapter regioniTableAdapter;
    private ProvinceTableAdapter provinceTableAdapter;
    private ComuniTableAdapter comuniTableAdapter;
    private ColoriTableAdapter coloriTableAdapter;
    private GiorniTableAdapter giorniTableAdapter;
    private IsolaEcologicaTableAdapter isolaEcologicaTableAdapter;
    private MaterialiTableAdapter materialiTableAdapter;
    private PreferitiTableAdapter preferitiTableAdapter;
    private ProdottiTableAdapter prodottiTableAdapter;
    private RaccoltaTableAdapter raccoltaTableAdapter;
    private TipologiaProdottiTableAdapter tipologiaProdottiTableAdapter;
    private TipologiaRaccoltaTableAdapter tipologiaRaccoltaTableAdapter;


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


        context = v.getContext();

        /* Buttons */
        buttons = new ArrayList<Button>();
        buttons.add(btn_viewAllRegion = (Button) v.findViewById(R.id.btn_viewAllRegion));
        buttons.add(btn_setup = (Button) v.findViewById(R.id.btn_setup));
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

        /* Waiting dialog */
        progressDialog = new ProgressDialog(context);

        /* inizializzazione dell'app */
        tables = new ArrayList<>();
        tables.add(regioniTableAdapter = new RegioniTableAdapter(context));
        tables.add(provinceTableAdapter = new ProvinceTableAdapter(context));
        tables.add(comuniTableAdapter = new ComuniTableAdapter(context));
        tables.add(coloriTableAdapter = new ColoriTableAdapter(context));
        tables.add(giorniTableAdapter = new GiorniTableAdapter(context));
        tables.add(isolaEcologicaTableAdapter = new IsolaEcologicaTableAdapter(context));
        tables.add(materialiTableAdapter = new MaterialiTableAdapter(context));
        tables.add(preferitiTableAdapter = new PreferitiTableAdapter(context));
        tables.add(prodottiTableAdapter = new ProdottiTableAdapter(context));
        tables.add(raccoltaTableAdapter = new RaccoltaTableAdapter(context));
        tables.add(tipologiaProdottiTableAdapter = new TipologiaProdottiTableAdapter(context));
        tables.add(tipologiaRaccoltaTableAdapter = new TipologiaRaccoltaTableAdapter(context));


        /* aggiornamento di tutte le tabelle */
        progressDialog.setMessage("Download of database");
        progressDialog.show();
        Message4Debug.log("inizio aggiornamento del database interno...");
        long start = System.currentTimeMillis();
        for (TableAdapter t : tables) {
            try {
                t.updateFromServer(null, null);
            } catch (DaoException e) {
                Message4Debug.log(e.toString());
            } catch (InterruptedException e) {
                Message4Debug.log(e.toString());
            }
        }
        Message4Debug.log("aggiornamento completato in: " + (System.currentTimeMillis() - start));
        progressDialog.dismiss();


    }

    /**
     * The type Sub fragment button manager.
     */
    public class SubFragmentButtonManager implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();


            switch (v.getId()) {
                case R.id.btn_setup:
                    /* INIZIALIZZAZIONE DELL'APP */

                    break;
                case R.id.btn_viewAllRegion:
                    try {
                        ListOfListAdapter listOfListAdapter = new ListOfListAdapter(context, regioniTableAdapter.getData(null, null, null), R.layout.item_list_simple);
                        listView.setAdapter(listOfListAdapter);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
                case R.id.btn_aggiorna:
                    break;
                case R.id.btn_typeProd:
                    try {
                        ListOfListAdapter listOfListAdapter = new ListOfListAdapter(context, tipologiaProdottiTableAdapter.getData(null, null, null), R.layout.item_list_simple);
                        listView.setAdapter(listOfListAdapter);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
                case R.id.btn_comuni:
                    try {
                        ListOfListAdapter listOfListAdapter = new ListOfListAdapter(context, comuniTableAdapter.getData(null, null, "comune"), R.layout.item_list_simple_whit_icon);
                        listView.setAdapter(listOfListAdapter);
                    } catch (DaoException e) {
                        Message4Debug.log(e.toString());
                    }
                    break;
            }
        }
    }

    public class SubListViewOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Message4Debug.log("hai cliccato sula posizione " + String.valueOf(position));
            Message4Debug.log("che ha l'id: " + String.valueOf(id));
        }

    }
}
