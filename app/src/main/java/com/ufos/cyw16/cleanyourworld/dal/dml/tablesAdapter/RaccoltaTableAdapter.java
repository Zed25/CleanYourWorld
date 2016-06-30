/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RaccoltaTableAdapter
 * Last modified: 26/06/16 1.52
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RaccoltaTableAdapter
 * File name: RaccoltaTableAdapter.java
 * Class name: RaccoltaTableAdapter
 * Last modified: 26/06/16 1.48
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RaccoltaTableAdapter
 * File name: RaccoltaTableAdapter.java
 * Class name: RaccoltaTableAdapter
 * Last modified: 25/06/16 19.01
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.RaccoltaTableAdapter
 * File name: RaccoltaTableAdapter.java
 * Class name: RaccoltaTableAdapter
 * Last modified: 09/06/16 16.43
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Raccolta table adapter.
 */
@Deprecated
public class RaccoltaTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Raccolta table adapter.
     *
     * @param context the context
     */
    public RaccoltaTableAdapter(Context context) {
        super(context, "raccolta");
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
