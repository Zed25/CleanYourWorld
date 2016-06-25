/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccoltaTableAdapter
 * Last modified: 26/06/16 1.52
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccoltaTableAdapter
 * File name: TipologiaRaccoltaTableAdapter.java
 * Class name: TipologiaRaccoltaTableAdapter
 * Last modified: 26/06/16 1.48
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccoltaTableAdapter
 * File name: TipologiaRaccoltaTableAdapter.java
 * Class name: TipologiaRaccoltaTableAdapter
 * Last modified: 25/06/16 19.02
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccoltaTableAdapter
 * File name: TipologiaRaccoltaTableAdapter.java
 * Class name: TipologiaRaccoltaTableAdapter
 * Last modified: 09/06/16 16.37
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.18
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.TipologiaRaccolta
 * File name: TipologiaRaccolta.java
 * Class name: TipologiaRaccolta
 * Last modified: 09/06/16 16.18
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Tipologia raccolta table adapter.
 */
@Deprecated
public class TipologiaRaccoltaTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Tipologia raccolta table adapter.
     *
     * @param context the context
     */
    public TipologiaRaccoltaTableAdapter(Context context) {
        super(context, "tipologiaRaccolta");
    }

    @Override
    public int update(String[] key, String[] newValues, String[] whereClauses, String[] whereArgs) throws DaoException {
        throw new DaoException("Operazione non consentita");
    }

    @Override
    public int delete(String[] whereClauses, String[] whereArgs) throws DaoException {
        throw new DaoException("Operazione non consentita");

    }

    @Override
    public long insert(String[] key, String[] value) throws DaoException {
        throw new DaoException("Operazione non consentita");

    }
}
