/*
 * Created by Umberto Ferracci from simone_mancini and published on 27/06/16 16.16
 * email:   umberto.ferracci@gmail.com
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.CalendarWeekViewAdapter
 * File name: CalendarWeekViewAdapter.java
 * Class name: CalendarWeekViewAdapter
 * Last modified: 27/06/16 16.16
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.model_new.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;

import java.util.List;

/**
 * Created by simone_mancini on 27/06/16.
 */
public class CalendarWeekAdapter extends RecyclerView.Adapter<CalendarWeekAdapter.CalendarWeekHolder> {
    private List<DayTrashInfo> lvDayTrashInfo;

    /**
     * Instantiates a new Calendar week adapter.
     *
     * @param lvDayTrashInfo the lv day trash info
     */
    public CalendarWeekAdapter(List<DayTrashInfo> lvDayTrashInfo) {
        this.lvDayTrashInfo = lvDayTrashInfo;
    }

    @Override
    public int getItemCount() {
        return lvDayTrashInfo.size();
    }

    @Override
    public void onBindViewHolder(CalendarWeekHolder calendarWeekHolder, int i) {

        DayTrashInfo dayTrashInfo = lvDayTrashInfo.get(i);
        calendarWeekHolder.tvCardViewDate.setText(dayTrashInfo.getDate());
        calendarWeekHolder.tvCardViewDay.setText(dayTrashInfo.getDay());
        calendarWeekHolder.tvCardViewTrash.setText(dayTrashInfo.getThrash());
        calendarWeekHolder.tvCardViewTrash.setBackgroundColor(dayTrashInfo.getColorOfTheDay());
        }

    @Override
    public CalendarWeekHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.calendar_week_card_view_sample_layout, viewGroup, false);

        return new CalendarWeekHolder(itemView);
    }


    /**
     * The type Calendar week holder.
     */
    public static class CalendarWeekHolder extends RecyclerView.ViewHolder{

        protected TextView tvCardViewDay, tvCardViewDate, tvCardViewTrash, tvColorOfTheDay;

        /**
         * Instantiates a new Calendar week holder.
         *
         * @param v the v
         */
        public CalendarWeekHolder(View v) {
            super(v);
            tvCardViewDate =  (TextView) v.findViewById(R.id.tvCardViewDate);
            tvCardViewDay = (TextView)  v.findViewById(R.id.tvCardViewDay);
            tvCardViewTrash = (TextView)  v.findViewById(R.id.tvCardViewTrash);
            //tvColorOfTheDay = (TextView) v.findViewById(R.id.tvColorOfTheDay);
        }
    }
}

