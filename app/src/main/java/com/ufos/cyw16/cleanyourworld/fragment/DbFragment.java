
/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.DbFragment
 * Last modified: 03/07/16 20.14
 */

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

import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.dal.dao.EntityDao;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.Comune;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
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
    private DaoFactory_def daoFactory;
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

        /* DAOFactory */
        daoFactory = DaoFactory_def.getInstance(context);

        /* Factories */
        entities = new ArrayList<>();
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
                            entity.updateFromServer(null, null);
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
                    try {
                        Long a = System.currentTimeMillis();
                        DaoFactory_def daoFactorynew = DaoFactory_def.getInstance(context);
                        com.ufos.cyw16.cleanyourworld.model_new.Regione regione5 = daoFactorynew.getRegioneDao().findById(12);
                        regione5.getName();
                        Message4Debug.log("fine in: " + (System.currentTimeMillis() - a) + " msec");
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_typeProd:
                    DaoFactory_def daoFactory_def = DaoFactory_def.getInstance(context);
                    try {
                        List<Collection> collections = daoFactory_def.getCollectionDao().getCollectionsByIdComune(1865);
                        collections.get(0).getIdCollection();
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_comuni:
                    MaterialDao materialDao = daoFactory.getMaterialDao();
                    try {
                        List<Material> materials = materialDao.getMaterialsFromIdComune(1865);
                        for (Material m : materials) {
                            System.out.println(m.getName());
                        }
                    } catch (DaoException e) {
                        e.printStackTrace();
                    }
                    ComuneDao comuneDao = daoFactory.getComuneDao();
                    try {
                        List<Comune> comunes = comuneDao.getComuniThatProvideCollection();
                        for (Comune c : comunes) {
                            System.out.println(c.getName());
                        }
                    } catch (DaoException e) {
                        e.printStackTrace();
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
