/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.MaterialsSearchFragment
 * Last modified: 03/07/16 12.06
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.CustomLinearLayoutManager;
import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.Models.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarMonthAdapter;
import com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter;
import com.ufos.cyw16.cleanyourworld.config.ConfigStep;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.Day;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductType;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class MaterialsSearchFragment extends Fragment {

    private RecyclerView recyclerView;

    private MaterialRecyclerViewAdapter materialRecyclerViewAdapter;

    private List<MaterialTrashInfo> materialTrashInfoList;

    private MaterialSearchAsyncTask materialSearchAsyncTask;

    private ProgressDialog waitingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.materials_search_subfragment, container, false); //inflate layout

        createFragment(v);
        return v;
    }

    private void createFragment(View v) {

        initializerecyclerView(v);

        materialSearchAsyncTask = new MaterialSearchAsyncTask();

        materialSearchAsyncTask.execute(1865);

        openWaitingDialog();

    }

    private void openWaitingDialog() {
        waitingDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setMessage("I'm computing material's cards");
        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.show();
    }


    /**
     * initialize Recycler View
     * <p>
     * Called in either in week view or in month view
     **/
    private void initializerecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rvMaterials);


        materialTrashInfoList = new ArrayList<>();

        //set RecyclerView's size fixed
        recyclerView.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private class MaterialSearchAsyncTask extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... ints) {

            computeMaterialCards(materialTrashInfoList, ints[0]);

            return null;
        }

        private void computeMaterialCards(List<MaterialTrashInfo> materialTrashInfoList, int comuneID) {
            materialTrashInfoList.clear();
            List<Material> materials = null;

            MaterialDao materialDao = DaoFactory_def.getInstance(getContext()).getMaterialDao();

            try {
                materials = materialDao.getMaterialsFromIdComune(comuneID);

            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }

            if (materials != null) {
                for (Material m : materials) {
                    MaterialTrashInfo materialTrashInfo;
                    materialTrashInfo = computeCardStructure(m);

                    if(materialTrashInfo != null) {
                        materialTrashInfoList.add(materialTrashInfo);
                    }else {
                        Toast.makeText(getContext(), getResources().getString(R.string.strNoRecycle) + ": " + m.getName(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

        private MaterialTrashInfo computeCardStructure(Material material){

            MaterialTrashInfo materialTrashInfo = new MaterialTrashInfo();
            String dayName = selectDay(material.getDays());
            materialTrashInfo.setDay(dayName);
            String trashName = material.getName();
            String trashColor = material.getColor().getColorCode();

            materialTrashInfo.setThrash(trashName);
            materialTrashInfo.setColorOfTheTrash(trashColor);

            ProductTypeDao productTypeDao = DaoFactory_def.getInstance(getContext()).getProtuctTypeDao();
            /*List<ProductType> productTypes = null;

            try {
                productTypes = productTypeDao.getProductsByIdMaterial(material.getIdMaterial());
            } catch (DaoException e) {
                Message4Debug.log("problem in productTypeDao.getProductsByIdMaterial()");
            }
            if(productTypes != null){*/
                materialTrashInfo.setProductTypes(material.getProdutctTypes());

            return materialTrashInfo;
        }

        private String selectDay(List<Day> days) {
            String str = "";
            if(days.size() == 1){
                str += days.get(0).getName();
            }else {
                for (int i = 0; i < days.size() - 1; i++) {
                    str += days.get(i).getName();
                    str += "/";
                }
                str += days.get(days.size() - 1).getName();
            }
            return str;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            materialRecyclerViewAdapter = new MaterialRecyclerViewAdapter(getContext(), materialTrashInfoList);
            recyclerView.setAdapter(materialRecyclerViewAdapter);

            if(waitingDialog != null){
                waitingDialog.dismiss();
            }else{
                Message4Debug.log("waitingDialog == null");
            }
        }
    }
}