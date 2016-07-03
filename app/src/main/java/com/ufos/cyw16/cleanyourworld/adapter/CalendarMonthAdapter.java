/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.adapter.CalendarMonthAdapter
 * Last modified: 03/07/16 18.02
 */

package com.ufos.cyw16.cleanyourworld.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;

import java.util.List;

/**
 * Created by simone_mancini on 03/07/16.
 */
public class CalendarMonthAdapter extends RecyclerView.Adapter<CalendarMonthAdapter.CalendarMonthHolder> {
    private List<DayTrashInfo> lvDayTrashInfo;

    public CalendarMonthAdapter(List<DayTrashInfo> lvDayTrashInfo) {
        this.lvDayTrashInfo = lvDayTrashInfo;
    }

    @Override
    public int getItemCount() {
        return lvDayTrashInfo.size();
    }

    @Override
    public void onBindViewHolder(CalendarMonthHolder calendarWeekHolder, int i) {

        DayTrashInfo dayTrashInfo = lvDayTrashInfo.get(i);
        calendarWeekHolder.tvCardViewDate.setText(dayTrashInfo.getDate());
        calendarWeekHolder.tvCardViewDay.setText(dayTrashInfo.getDay());
        calendarWeekHolder.tvCardViewTrash.setText(dayTrashInfo.getThrash());
        calendarWeekHolder.tvColorOfTheDay.setBackgroundColor(dayTrashInfo.getColorOfTheDay());
    }

    @Override
    public CalendarMonthHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.calendar_month_card_view_sample_layout, viewGroup, false);

        return new CalendarMonthHolder(itemView);
    }



    public static class CalendarMonthHolder extends RecyclerView.ViewHolder{

        protected TextView tvCardViewDay, tvCardViewDate, tvCardViewTrash, tvColorOfTheDay;

        public CalendarMonthHolder(View v) {
            super(v);
            tvCardViewDate =  (TextView) v.findViewById(R.id.tvCardViewDate);
            tvCardViewDay = (TextView)  v.findViewById(R.id.tvCardViewDay);
            tvCardViewTrash = (TextView)  v.findViewById(R.id.tvCardViewTrash);
            tvColorOfTheDay = (TextView) v.findViewById(R.id.tvColorOfTheDay);
        }
    }
}
