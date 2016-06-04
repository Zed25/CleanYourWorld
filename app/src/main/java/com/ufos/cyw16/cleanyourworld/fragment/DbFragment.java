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
import android.widget.Button;

import com.ufos.cyw16.cleanyourworld.R;
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
                    Message4Debug.log("regioni ha " + regioniTableAdapter.getCount() + " elementi");
                    Message4Debug.log("TipologiaProdotto ha " + tipologiaProdottiTableAdapter.getCount() + " elementi");
                    Message4Debug.log("Comuni ha " + comuniTableAdapter.getCount() + " elementi");
                    break;
                case R.id.btn_aggiorna:
                    regioniTableAdapter.updateFromRemoteServer();
                    break;
                case R.id.btn_typeProd:
                    tipologiaProdottiTableAdapter.updateFromRemoteServer();
                    break;
                case R.id.btn_comuni:
                    comuniTableAdapter.updateFromRemoteServer();
            }
        }
    }
}
