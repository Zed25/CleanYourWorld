
/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.BarCodeSearchFragment
 * Last modified: 05/07/16 11.24
 */

package com.ufos.cyw16.cleanyourworld.fragment;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.ufos.cyw16.cleanyourworld.R;

/**
 * Created by simone_mancini on 19/05/16.
 */
public class BarCodeSearchFragment extends Fragment{

    private final int USER_CAMERA_PERMISSION = 12; //check int for camera permission: 0 equals to PERMISSION_GRANTED
    private final String TAG = "recycleDebug";
    private TextView tvEAN;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        checkCameraPermission();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.barcode_search_subfragment, container, false); //inflate layout

        tvEAN = (TextView) v.findViewById(R.id.tvEAN);

        //createFragment(v); //set ViewPager and TabLayout
        return v;
    }

    //runtime camera permission request
    private void checkCameraPermission() {
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA);
        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                showMessageOKCancel(getResources().getString(R.string.strNeedCamera),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.CAMERA},
                                        USER_CAMERA_PERMISSION);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.CAMERA},
                    USER_CAMERA_PERMISSION);
            return;
        }
        scan();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case USER_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    scan();
                } else {
                    // Permission Denied
                    Toast.makeText(getContext(), "CAMERA Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

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
            Log.v(TAG, scanContent);
            String scanFormat = scanningResult.getFormatName();
            Log.v(TAG, scanFormat);

            adaptBarcodeAndEAN(scanContent, scanFormat, tvEAN);
        } else {
            Toast toast = Toast.makeText(this.getActivity(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void adaptBarcodeAndEAN(String scanContent, String scanFormat, TextView tvEAN) {
        String tvText = getResources().getString(R.string.barCode) + ": " + scanContent + " " + scanFormat;

        tvEAN.setText(tvText);
    }


}
