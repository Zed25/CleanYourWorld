/*
 * Created by UFOS from urania
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologicaTableAdapter
 * Last modified: 26/06/16 1.52
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologicaTableAdapter
 * File name: IsolaEcologicaTableAdapter.java
 * Class name: IsolaEcologicaTableAdapter
 * Last modified: 26/06/16 1.48
 */

/*
 * Created by Umberto Ferracci from urania and published on 26/06/16 1.48
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologicaTableAdapter
 * File name: IsolaEcologicaTableAdapter.java
 * Class name: IsolaEcologicaTableAdapter
 * Last modified: 25/06/16 19.01
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologicaTableAdapter
 * File name: IsolaEcologicaTableAdapter.java
 * Class name: IsolaEcologicaTableAdapter
 * Last modified: 09/06/16 16.37
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.20
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.IsolaEcologica
 * File name: IsolaEcologica.java
 * Class name: IsolaEcologica
 * Last modified: 09/06/16 16.20
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Isola ecologica table adapter.
 */
@Deprecated
public class IsolaEcologicaTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Isola ecologica table adapter.
     *
     * @param context the context
     */
    public IsolaEcologicaTableAdapter(Context context) {
        super(context, "isolaEcologica");
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
