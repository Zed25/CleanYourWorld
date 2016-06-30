/*
 * Created by Umberto Ferracci from urania and published on 09/06/16 12.16
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ListOfListAdapter
 * File name: ListOfListAdapter.java
 * Class name: ListOfListAdapter
 * Last modified: 09/06/16 12.16
 */

/*
 * Created by Umberto Ferracci from urania and published on 07/06/16 7.11
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.ListOfListAdapter
 * File name: ListOfListAdapter.java
 * Class name: ListOfListAdapter
 * Last modified: 07/06/16 7.11
 */

/*
 * Created by Umberto Ferracci from urania and published on 07/06/16 5.21
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.ListAdapter
 * File name: ListAdapter.java
 * Class name: ListAdapter
 * Last modified: 07/06/16 5.21
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.R;

import java.util.ArrayList;

/**
 *
 */
public class ListOfListAdapter extends BaseAdapter {
    private ArrayList<ArrayList<String>> lists;
    private Context context;
    private int resources;

    public ListOfListAdapter(Context context, ArrayList<ArrayList<String>> lists, int resources) {
        this.context = context;
        this.lists = lists;
        this.resources = resources;
    }

    @Override
    public final int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return Long.parseLong(lists.get(position).get(0));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = getItem(position).toString();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resources, parent, false);
        }

        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_iconStar);

        tv_title.setText(s);
//        if (getItemId(position) % 2 == 0)
//            imageView.setVisibility(View.GONE);


        return convertView;
    }

    public final Context getContext() {
        return context;
    }

}
