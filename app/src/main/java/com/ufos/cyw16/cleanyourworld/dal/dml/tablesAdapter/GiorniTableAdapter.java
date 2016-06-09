/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.GiorniTableAdapter
 * File name: GiorniTableAdapter.java
 * Class name: GiorniTableAdapter
 * Last modified: 09/06/16 16.37
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.34
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.GiorniTableAdapter
 * File name: GiorniTableAdapter.java
 * Class name: GiorniTableAdapter
 * Last modified: 09/06/16 16.34
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Giorni table adapter.
 */
public class GiorniTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Giorni table adapter.
     *
     * @param context the context
     */
    public GiorniTableAdapter(Context context) {
        super(context, "giorni");
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
