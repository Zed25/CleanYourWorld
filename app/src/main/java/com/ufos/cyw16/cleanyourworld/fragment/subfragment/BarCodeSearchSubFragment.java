/*
 * Created by Umberto Ferracci from urania on 04/06/16 18.06
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.subfragment.BarCodeSearchSubFragment
 * File name: BarCodeSearchSubFragment.java
 * Class name: BarCodeSearchSubFragment
 * Last modified: 04/06/16 18.04
 */

package com.ufos.cyw16.cleanyourworld.fragment.subfragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
public class BarCodeSearchSubFragment extends Fragment{

    private final int USER_CAMERA_PERMISSION = 0; //check int for camera permission: 0 equals to PERMISSION_GRANTED
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
        int permissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            scan();
        } else {
            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {


                // Should we toast an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.CAMERA)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {


                    ActivityCompat.requestPermissions(this.getActivity(),
                            new String[]{Manifest.permission.CAMERA}, USER_CAMERA_PERMISSION);

                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case USER_CAMERA_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    scan();

                } else {

                }
                return;
            }
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
