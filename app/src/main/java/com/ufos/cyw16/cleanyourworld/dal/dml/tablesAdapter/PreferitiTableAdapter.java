/*
 * Created by Umberto Ferracci from urania and published on 23/06/16 17.49
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.PreferitiTableAdapter
 * File name: PreferitiTableAdapter.java
 * Class name: PreferitiTableAdapter
 * Last modified: 23/06/16 17.34
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 18.13
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.PreferitiTableAdapter
 * File name: PreferitiTableAdapter.java
 * Class name: PreferitiTableAdapter
 * Last modified: 09/06/16 16.37
 */

/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 16.24
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter.PreferitiTableAdapter
 * File name: PreferitiTableAdapter.java
 * Class name: PreferitiTableAdapter
 * Last modified: 09/06/16 16.24
 */

package com.ufos.cyw16.cleanyourworld.dal.dml.tablesAdapter;

import android.content.Context;

import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.dal.dml.TableAdapter;

/**
 * The type Preferiti table adapter.
 */
public class PreferitiTableAdapter extends TableAdapter {
    /**
     * Instantiates a new Preferiti table adapter.
     *
     * @param context the context
     */
    public PreferitiTableAdapter(Context context) {
        super(context, "preferiti");
    }

    @Override
    public void updateFromServer(String[] keys, String[] values) throws DaoException, InterruptedException {
        throw new DaoException("operazione non consentita");
    }
}
