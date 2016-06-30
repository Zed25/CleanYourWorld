/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProdottiTableAdapter
 * File name: ProdottiTableAdapter.java
 * Class name: ProdottiTableAdapter
 * Last modified: 09/06/16 16.32
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.26
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ProdottiTableAdapter
 * File name: ProdottiTableAdapter.java
 * Class name: ProdottiTableAdapter
 * Last modified: 09/06/16 16.26
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Prodotti table adapter.
 */
public class ProdottiTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Prodotti table adapter.
     *
     * @param context the context
     */
    public ProdottiTableAdapter(Context context) {
        super(context, "prodotti");
    }

    @Override
    public long insert(String[] key, String[] value) throws DaoException {
        throw new DaoException("operazione non consentita");
    }

    @Override
    public int update(String[] key, String[] newValues, String[] whereClauses, String[] whereArgs) throws DaoException {
        throw new DaoException("operazione non consentita");
    }

    @Override
    public int delete(String[] whereClauses, String[] whereArgs) throws DaoException {
        throw new DaoException("operazione non consentita");

    }
}
