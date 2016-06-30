
/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * Last modified: 26/06/16 1.55
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ufos.cyw16.cleanyourworld.Models.dao.DaoFactory;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.CollectionTypeDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ColorsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ComuneDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.IsolaEcologicaDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.MaterialsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProductsDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.ProvinciaDao;
import com.ufos.cyw16.cleanyourworld.Models.dao.factories.RegioneDao;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Db fragment.
 */
public class DbFragment extends Fragment {

    private Button btn_update, btn_regioni, btn_viewAll, btn_prodType, btn_comuni;
    private ArrayList<Button> buttons;
    private SubFragmentButtonManager subFragmentButtonManager;
    private SubListViewOnItemClick subListViewOnItemClick;
    private ListView listView;
    private ProgressBar progressBar;
    private boolean init = true;
    private Context context;

    /* DAOFactory */
    private DaoFactory daoFactory;
    private CollectionDao collectionDao;
    private CollectionTypeDao collectionTypeDao;
    private ColorsDao colorsDao;
    private ComuneDao comuneDao;
    private IsolaEcologicaDao isolaEcologicaDao;
    private MaterialsDao materialsDao;
    private ProductsDao productsDao;
    private ProductTypeDao productTypeDao;
    private ProvinciaDao provinciaDao;
    private RegioneDao regioneDao;
    private List<EntityDao> entities;

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
        buttons.add(btn_regioni = (Button) v.findViewById(R.id.btn_regioni));
        buttons.add(btn_update = (Button) v.findViewById(R.id.btn_update));
        buttons.add(btn_viewAll = (Button) v.findViewById(R.id.btn_viewAll));
        buttons.add(btn_prodType = (Button) v.findViewById(R.id.btn_typeProd));
        buttons.add(btn_comuni = (Button) v.findViewById(R.id.btn_comuni));
        subFragmentButtonManager = new SubFragmentButtonManager();
        for (Button b : buttons) {
            b.setOnClickListener(subFragmentButtonManager);
        }
         /* Listview */
        listView = (ListView) v.findViewById(R.id.listView);
        subListViewOnItemClick = new SubListViewOnItemClick();
        listView.setOnItemClickListener(subListViewOnItemClick);


        /* inizializzazione dell'app */
//        tables = new ArrayList<>();
//        tables.add(regioniTableAdapter = new RegioniTableAdapter(context));
//        tables.add(provinceTableAdapter = new ProvinceTableAdapter(context));
//        tables.add(comuniTableAdapter = new ComuniTableAdapter(context));
//        tables.add(coloriTableAdapter = new ColoriTableAdapter(context));
//        tables.add(giorniTableAdapter = new GiorniTableAdapter(context));
//        tables.add(isolaEcologicaTableAdapter = new IsolaEcologicaTableAdapter(context));
//        tables.add(materialiTableAdapter = new MaterialiTableAdapter(context));
//        tables.add(preferitiTableAdapter = new PreferitiTableAdapter(context));
//        tables.add(prodottiTableAdapter = new ProdottiTableAdapter(context));
//        tables.add(raccoltaTableAdapter = new RaccoltaTableAdapter(context));
//        tables.add(tipologiaProdottiTableAdapter = new TipologiaProdottiTableAdapter(context));
//        tables.add(tipologiaRaccoltaTableAdapter = new TipologiaRaccoltaTableAdapter(context));


        /* aggiornamento di tutte le tabelle */


//        for (TableAdapter t : tables) {
//            try {
//                t.updateFromServer(null, null);
//            } catch (DaoException e) {
//                Message4Debug.log(e.toString());
//            } catch (InterruptedException e) {
//                Message4Debug.log(e.toString());
//            }
//        }


        /* DAOFactory */
        daoFactory = DaoFactory.getInstance(context);

        /* Factories */
        entities = new ArrayList<>();
        entities.add((collectionDao = daoFactory.getCollectionDao()));
        entities.add((collectionTypeDao = daoFactory.getCollectionTypeDao()));
        entities.add((colorsDao = daoFactory.getColorsDao()));
        entities.add((comuneDao = daoFactory.getComuneDao()));
        entities.add((isolaEcologicaDao = daoFactory.getIsolaEcologicaDao()));
        entities.add((materialsDao = daoFactory.getMaterialsDao()));
        entities.add((productsDao = daoFactory.getProductsDao()));
        entities.add((productTypeDao = daoFactory.getProtuctTypeDao()));
        entities.add((provinciaDao = daoFactory.getProvinciaDao()));
        entities.add((regioneDao = daoFactory.getRegioneDao()));

    }

    /**
     * The type Sub fragment button manager.
     */
    public class SubFragmentButtonManager implements View.OnClickListener {

        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.btn_update:
                    Message4Debug.log("inizio aggiornamento del database interno...");
                    long start = System.currentTimeMillis();
                    for (EntityDao entity : entities) {
                        try {
                            Message4Debug.log(entity.getClass().getSimpleName());
                            entity.findFromServer(null, null);
                        } catch (DaoException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Message4Debug.log("aggiornamento completato in: " + (System.currentTimeMillis() - start));
                    break;
                case R.id.btn_viewAll:
                    for (EntityDao entity : entities) {
                        try {
                            Message4Debug.log(entity.getClass().getSimpleName());
                            entity.findAll();
                        } catch (DaoException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case R.id.btn_regioni:

                    break;
                case R.id.btn_typeProd:

                    break;
                case R.id.btn_comuni:

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
