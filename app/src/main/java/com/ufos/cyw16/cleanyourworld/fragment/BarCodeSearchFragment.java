
/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.BarCodeSearchFragment
 * Last modified: 05/07/16 11.24
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ufos.cyw16.cleanyourworld.adapter.MaterialRecyclerViewAdapter;
import com.ufos.cyw16.cleanyourworld.adapter.ProductScannRecyclerViewAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Day;
import com.ufos.cyw16.cleanyourworld.model_new.Material;
import com.ufos.cyw16.cleanyourworld.model_new.MaterialTrashInfo;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductScannInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.MaterialDao;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductTypeDao;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class BarCodeSearchFragment extends Fragment{

    private final int USER_CAMERA_PERMISSION = 12; //check int for camera permission: 0 equals to PERMISSION_GRANTED
    private RecyclerView rvScann;
    private List<ProductScannInfo> productScannInfoList;
    private ProgressDialog waitingDialog;
    private ProductScannRecyclerViewAdapter productScannRecyclerViewAdapter;
    private TextView tvSuggest;
    private Boolean permission;
    private FloatingActionButton fabScannProduct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.barcode_search_subfragment, container, false); //inflate layout

        createFragment(v);
        return v;
    }


    //runtime camera permission request
    private void checkCameraPermission() {
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA);
        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                setPermission(false);
                showMessageOKCancel(getResources().getString(R.string.strNeedCamera),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.CAMERA},
                                        USER_CAMERA_PERMISSION);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setPermission(false);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.CAMERA},
                    USER_CAMERA_PERMISSION);
            return;
        }else{
            setPermission(true);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", cancelListener)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case USER_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    permission = true;
                } else {
                    // Permission Denied
                    permission = false;
                    Toast.makeText(getContext(), "CAMERA Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    private void createFragment(View v) {
        initializerecyclerView(v);
        tvSuggest = (TextView) v.findViewById(R.id.tvSuggest);
        fabScannProduct = (FloatingActionButton) v.findViewById(R.id.fabScannProduct);
        checkCameraPermission();
        if(permission != null) {
            if (permission) {

                BarCodeSearchAsyncTask barCodeSearchAsyncTask = new BarCodeSearchAsyncTask();
                barCodeSearchAsyncTask.execute();

                openWaitingDialog();
            } else {
                if (tvSuggest.getVisibility() != View.VISIBLE) {
                    tvSuggest.setVisibility(View.VISIBLE);
                }

                tvSuggest.setText(getResources().getString(R.string.strNeedCameraForFragment));

            }
        }

        fabScannProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permission !=null){
                    if(permission){
                        scan();
                    }else{
                        checkCameraPermission();
                    }
                }
            }
        });
    }

    /**
     * initialize Recycler View
     * <p>
     * Called in either in week view or in month view
     **/
    private void initializerecyclerView(View v) {

        rvScann = (RecyclerView) v.findViewById(R.id.rvBarCodeScann);


        productScannInfoList = new ArrayList<>();

        //set RecyclerView's size fixed
        rvScann.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        rvScann.setLayoutManager(linearLayoutManager);



    }

    private void openWaitingDialog() {
        waitingDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setMessage("I'm computing material's cards");
        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.show();
    }

    private void scan() {
            IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            intentIntegrator.setPrompt("Scan a barcode");
            intentIntegrator.setCameraId(0);  // Use a specific camera of the device
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            String scanFormat = scanningResult.getFormatName();

            //adaptBarcodeAndEAN(scanContent, scanFormat, tvEAN);
        } else {
            Toast.makeText(this.getActivity(), "No scan data received!", Toast.LENGTH_LONG).show();
        }
    }


    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    private class BarCodeSearchAsyncTask extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... ints) {

            computeBarCodeCards(productScannInfoList);

            return null;
        }

        private void computeBarCodeCards(List<ProductScannInfo> productScannInfoList) {
            /*materialTrashInfoList.clear();
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
            }*/

            productScannInfoList.clear();

            //TODO crea Lista di ProductScann

            //TODO istanzia ProductScann dao

            //TODO try catch con l'operazione get all (se tabella vuota set text sulla text view)

            //TODO se la lista non è null fai un for
                //elabora card con ProductscanInfo
                //add a productScannInfoList

            //TODO TextView gone e RecyclerView visible



        }

        private ProductScannInfo computeCardStructure(){

            /*MaterialTrashInfo materialTrashInfo = new MaterialTrashInfo();
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
            if(productTypes != null){/*
            /*materialTrashInfo.setProductTypes(material.getProdutctTypes());

            return materialTrashInfo;*/

            ProductScannInfo productScannInfo = new ProductScannInfo();
            return null;
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

            productScannRecyclerViewAdapter = new ProductScannRecyclerViewAdapter(productScannInfoList);
            rvScann.setAdapter(productScannRecyclerViewAdapter);

            if(waitingDialog != null){
                waitingDialog.dismiss();
            }else{
                Message4Debug.log("waitingDialog == null");
            }
        }
    }

}
