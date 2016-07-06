/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.ProductScannInfo
 * Last modified: 05/07/16 17.16
 */

/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.model_new.ProductScannInfo
 * Last modified: 05/07/16 17.09
 */

package com.ufos.cyw16.cleanyourworld.model_new;


import android.graphics.Color;

/**
 * Created by simone_mancini on 05/07/16.
 */
public class ProductScanInfo {

    private String barcode, productName, materialProduct, collectionDay, trashColorCode, scannDate;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMaterialProduct() {
        return materialProduct;
    }

    public void setMaterialProduct(String materialProduct) {
        this.materialProduct = materialProduct;
    }

    public String getCollectionDay() {
        return collectionDay;
    }

    public void setCollectionDay(String collectionDay) {
        this.collectionDay = collectionDay;
    }

    public int getTrashColorCode() {
        int color = Color.parseColor(trashColorCode);
        return color;
    }

    public void setTrashColorCode(String trashColorCode) {
        this.trashColorCode = trashColorCode;
    }

    public String getScannDate() {
        return scannDate;
    }

    public void setScannDate(String scannDate) {
        this.scannDate = scannDate;
    }
}
