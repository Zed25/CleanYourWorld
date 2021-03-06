
/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.BarCodeSearchFragment
 * Last modified: 05/07/16 11.24
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.ufos.cyw16.cleanyourworld.NotFoundElementException;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.ProductScanRecyclerViewAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Day;
import com.ufos.cyw16.cleanyourworld.model_new.Product;
import com.ufos.cyw16.cleanyourworld.model_new.ProductScan;
import com.ufos.cyw16.cleanyourworld.model_new.ProductScanInfo;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.ProductScanDao;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class BarCodeSearchFragment extends Fragment{

    private final int USER_CAMERA_PERMISSION = 12; //check int for camera permission: 0 equals to PERMISSION_GRANTED
    private RecyclerView rvScann;
    private List<ProductScanInfo> productScanInfoList;
    private ProgressDialog waitingDialog;
    private ProductScanRecyclerViewAdapter productScanRecyclerViewAdapter;
    private TextView tvSuggest;
    private Boolean permission;
    private FloatingActionButton fabScannProduct;
    private BarCodeSearchAsyncTask barCodeSearchAsyncTask;
    private ProductScan productScan;

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


    /**
     * Check camera permission.
     */
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
            tvSuggest.setText(getResources().getString(R.string.strClickOnFABToScann));
            if(tvSuggest.getVisibility()!=View.VISIBLE){
                tvSuggest.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Show message ok cancel.
     *
     * @param message        the message
     * @param okListener     the ok listener
     * @param cancelListener the cancel listener
     */
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


    /**
     * Create fragment.
     *
     * @param v the v
     */
    private void createFragment(View v) {
        initializerecyclerView(v);
        tvSuggest = (TextView) v.findViewById(R.id.tvSuggest);
        fabScannProduct = (FloatingActionButton) v.findViewById(R.id.fabScannProduct);
        setPermission(false);
        checkCameraPermission();
        if(permission != null) {
            if (permission) {
                if(barCodeSearchAsyncTask == null) {
                    barCodeSearchAsyncTask = new BarCodeSearchAsyncTask();
                }
                barCodeSearchAsyncTask.execute("getAll");

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
                        if(productScanInfoList != null){
                            if(productScanInfoList.size() == 0){
                                if(tvSuggest.getVisibility() != View.VISIBLE){
                                    tvSuggest.setText(getResources().getString(R.string.strClickOnFABToScann));
                                    tvSuggest.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }else{
                        checkCameraPermission();
                    }
                }
            }
        });
    }

    /**
     * initialize Recycler View
     * <p/>
     * Called in either in week view or in month view
     *
     * @param v the v
     */
    private void initializerecyclerView(View v) {

        rvScann = (RecyclerView) v.findViewById(R.id.rvBarCodeScann);


        productScanInfoList = new ArrayList<>();

        //set RecyclerView's size fixed
        rvScann.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        rvScann.setLayoutManager(linearLayoutManager);

        if(productScanInfoList != null) {
            productScanRecyclerViewAdapter = new ProductScanRecyclerViewAdapter(productScanInfoList);
            rvScann.setAdapter(productScanRecyclerViewAdapter);
        }



    }

    /**
     * Open waiting dialog.
     */
    private void openWaitingDialog() {
        waitingDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        waitingDialog.setIndeterminate(true);
        waitingDialog.setMessage("I'm computing material's cards");
        waitingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        waitingDialog.show();
    }

    /**
     * Scan.
     */
    private void scan() {
            IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(this);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            intentIntegrator.setPrompt("Scan a barcode");
            intentIntegrator.setCameraId(0);  // Use a specific camera of the device
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.initiateScan();
    }

    public void onActivityResult (int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();

            String scanFormat = scanningResult.getFormatName();


            try {
                productScan = createProductScan(scanContent);
            } catch (NotFoundElementException e) {
                showAllertDialogSendToServer(scanContent);
            } catch (Exception e) {
                return;
            }
            if(productScan != null) {
                barCodeSearchAsyncTask = new BarCodeSearchAsyncTask();
                barCodeSearchAsyncTask.execute("Scan");
            }
        } else {
            Toast.makeText(this.getActivity(), "No scan data received!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Show allert dialog send to server.
     *
     * @param barcode the barcode
     */
    private void showAllertDialogSendToServer(final String barcode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext(), R.style.RedAlertDialogStyle);
        builder.setTitle(getResources().getString(R.string.strNotInDB));
        builder.setMessage(getResources().getString(R.string.strShouldSendAllertMessage));
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isNetworkAvailable()) {
                    try {
                        DaoFactory_def.getInstance(getContext()).getProtuctScanDao().sendToServer(barcode);
                        dialog.dismiss();
                        Toast.makeText(getContext(), R.string.strMessageSent, Toast.LENGTH_LONG).show();
                    } catch (DaoException e) {
                        Message4Debug.log(e.getMessage());
                    }
                }else{
                    showNoNetworkDialog(barcode);
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * Create product scan product scan.
     *
     * @param scanContent the scan content
     * @return the product scan
     * @throws NotFoundElementException the not found element exception
     */
    private ProductScan createProductScan(String scanContent) throws NotFoundElementException {
        Product product = null;
        try {
            product = DaoFactory_def.getInstance(getContext()).getProductDao().getProductByBarCode(scanContent);
        } catch (DaoException e) {
            Message4Debug.log(e.getMessage());
        }
        if(product == null){
            throw new NotFoundElementException("No element in db");
        }

       return new ProductScan(product, computeReverseToday());

    }

    /**
     * this method determinates the current date aaaa/mm/dd @return the string
     */
    private String computeReverseToday() {
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        return Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day);
    }

    /**
     * Is network available boolean.
     *
     * @return the boolean
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Show no network dialog.
     *
     * @param barcode the barcode
     */
    private void showNoNetworkDialog(final String barcode) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext(),R.style.YellowAlertDialogStyle);
        builder.setTitle(getString(R.string.no_internet));
        builder.setMessage(getString(R.string.open_internet));

        String positiveText = getString(R.string.try_again);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // check if internet connect and try again
                        if(isNetworkAvailable()){
                            try {
                                DaoFactory_def.getInstance(getContext()).getProtuctScanDao().sendToServer(barcode);
                                dialog.dismiss();
                                Toast.makeText(getContext(), R.string.strMessageSent, Toast.LENGTH_LONG);
                            } catch (DaoException e) {
                                Message4Debug.log(e.getMessage());
                            }
                        }else {
                            showNoNetworkDialog(barcode);
                        }

                    }
                });

        String negativeText = getString(R.string.exit);
        builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });


        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }

    /**
     * Is permission boolean.
     *
     * @return the boolean
     */
    public boolean isPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    /**
     * The type Bar code search async task.
     */
    private class BarCodeSearchAsyncTask extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... strings) {

            switch (strings[0]){
                case "getAll":
                    computeBarCodeCards(productScanInfoList);
                    break;
                case "Scan":
                    insertNewScan(productScan);
                    computeBarCodeCards(productScanInfoList);
                    break;
            }



            return null;
        }

        /**
         * Insert new scan.
         *
         * @param productScan the product scan
         */
        private void insertNewScan(ProductScan productScan) {
            try {
                DaoFactory_def.getInstance(getContext()).getProtuctScanDao().insertOrUpdate(productScan);
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }
        }

        /**
         * Compute bar code cards.
         *
         * @param productScanInfoList the product scan info list
         */
        private void computeBarCodeCards(List<ProductScanInfo> productScanInfoList) {

            productScanInfoList.clear();

            List<ProductScan> productScanList = null;

            ProductScanDao productScanDao = DaoFactory_def.getInstance(getContext()).getProtuctScanDao();

            try {
                productScanList = productScanDao.findAll();
            } catch (DaoException e) {
                Message4Debug.log(e.getMessage());
            }

            if (productScanList != null) {
                if (productScanList.size() == 0) {
                    tvSuggest.setText(getResources().getString(R.string.strClickOnFABToScann));
                } else {

                    for (ProductScan p : productScanList) {
                        ProductScanInfo productScanInfo = computeCardStructure(p);
                        productScanInfoList.add(productScanInfo);
                    }
                }
            }else{
                tvSuggest.setText(getResources().getString(R.string.strClickOnFABToScann));
            }
        }

        /**
         * Compute card structure product scan info.
         *
         * @param productScan the product scan
         * @return the product scan info
         */
        private ProductScanInfo computeCardStructure(ProductScan productScan){

            ProductScanInfo productScanInfo = new ProductScanInfo();

            productScanInfo.setProductName(productScan.getProduct().getName());
            productScanInfo.setMaterialProduct(productScan.getProduct().getProductType().getMaterial().getName());
            productScanInfo.setTrashColorCode(productScan.getProduct().getProductType().getMaterial().getColor().getColorCode());
            productScanInfo.setBarcode(productScan.getProduct().getEAN());
            productScanInfo.setCollectionDay(selectDay(productScan.getProduct().getProductType().getMaterial().getDays()));
            productScanInfo.setScannDate(productScan.getDate());

            return productScanInfo;
        }

        /**
         * Select day string.
         *
         * @param days the days
         * @return the string
         */
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

            if (productScanRecyclerViewAdapter != null) {
                productScanRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                productScanRecyclerViewAdapter = new ProductScanRecyclerViewAdapter(productScanInfoList);
                rvScann.setAdapter(productScanRecyclerViewAdapter);
            }if(productScanInfoList != null){
                if(productScanInfoList.size() == 0){
                    tvSuggest.setVisibility(View.VISIBLE);
                    rvScann.setVisibility(View.GONE);
                }else {
                    tvSuggest.setVisibility(View.GONE);
                    rvScann.setVisibility(View.VISIBLE);
                }
            }


            if(waitingDialog != null){
                waitingDialog.dismiss();
            }else{
                Message4Debug.log("waitingDialog == null");
            }
        }
    }

}
