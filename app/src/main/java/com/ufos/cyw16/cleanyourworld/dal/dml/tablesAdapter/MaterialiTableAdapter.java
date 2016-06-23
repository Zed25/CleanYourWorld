/*
 * Created by Umberto Ferracci from urania and published on 23/06/16 17.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.MaterialiTableAdapter
 * File name: MaterialiTableAdapter.java
 * Class name: MaterialiTableAdapter
 * Last modified: 23/06/16 17.34
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.MaterialiTableAdapter
 * File name: MaterialiTableAdapter.java
 * Class name: MaterialiTableAdapter
 * Last modified: 09/06/16 16.34
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.32
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.MaterialiTableAdapter
 * File name: MaterialiTableAdapter.java
 * Class name: MaterialiTableAdapter
 * Last modified: 09/06/16 16.32
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Materiali table adapter.
 */
public class MaterialiTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Materiali table adapter.
     *
     * @param context the context
     */
    public MaterialiTableAdapter(Context context) {
        super(context, "materiali");
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
