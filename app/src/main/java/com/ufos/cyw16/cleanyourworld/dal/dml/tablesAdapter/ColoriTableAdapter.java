/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter
 * Last modified: 26/06/16 1.52
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter
 * File name: ColoriTableAdapter.java
 * Class name: ColoriTableAdapter
 * Last modified: 26/06/16 1.48
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter
 * File name: ColoriTableAdapter.java
 * Class name: ColoriTableAdapter
 * Last modified: 25/06/16 19.01
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter
 * File name: ColoriTableAdapter.java
 * Class name: ColoriTableAdapter
 * Last modified: 09/06/16 16.37
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.37
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.ColoriTableAdapter
 * File name: ColoriTableAdapter.java
 * Class name: ColoriTableAdapter
 * Last modified: 09/06/16 16.37
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Colori table adapter.
 */
@Deprecated
public class ColoriTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Colori table adapter.
     *
     * @param context the context
     */
    public ColoriTableAdapter(Context context) {
        super(context, "colori");
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
