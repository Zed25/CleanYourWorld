/*
 * Created by Umberto Ferracci from urania and published on 04/06/16 20.27
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaProdottiTableAdapter
 * File name: TipologiaProdottiTableAdapter.java
 * Class name: TipologiaProdottiTableAdapter
 * Last modified: 04/06/16 20.15
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Tipologia prodotti table adapter.
 */
public class TipologiaProdottiTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Tipologia prodotti table adapter.
     *
     * @param context the context
     */
    public TipologiaProdottiTableAdapter(Context context) {
        super(context, "tipologiaProdotto");
    }
}
