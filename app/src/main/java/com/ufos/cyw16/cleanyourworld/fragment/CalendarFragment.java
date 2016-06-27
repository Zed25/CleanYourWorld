package com.ufos.cyw16.cleanyourworld.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.CalendarViewType;
import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarWeekAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by simone_mancini on 11/05/16.
 */
public class CalendarFragment extends Fragment{

    private CalendarView calendarView;

    private TextView tvDate, tvTrash;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CalendarViewType viewMode;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //determinate mode view
        pref = getActivity().getSharedPreferences("calendarFragment", Context.MODE_PRIVATE);
        viewMode = getViewMode(pref);

        //initialize calendar fragment's view
        View v = initializeCalendar(viewMode, inflater, container);
        return v;
    }

    private View initializeCalendar(CalendarViewType viewMode, LayoutInflater inflater, ViewGroup container) {
        View v;
        switch(viewMode){
            case MONTH:
                // Inflate the layout for this fragment
                v = inflater.inflate(R.layout.calendar_fragment_month_view, container, false);
                initializeCalendarMonthView(v);
                return v;
            
            case WEEK:
                // Inflate the layout for this fragment
                v = inflater.inflate(R.layout.calendar_fragment_week_view, container,false);
                initializeCalendarWeekView(v);
                return v;

        }
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.calendar_fragment_week_view, container, false);
        return v;
    }

    private void initializeCalendarWeekView(View v) {

        RecyclerView rvWeekList = (RecyclerView) v.findViewById(R.id.rvWeekList);

        //set RecyclerView's size fixed
        rvWeekList.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        rvWeekList.setLayoutManager(linearLayoutManager);

        //create a list of 7 elements
        //theese elements are the 7 days next to the choose one
        Calendar calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        List<DayTrashInfo> dayTrashInfoList = generateWeekDayTrashList(date);

        //create and set the adapter for the recycler view
        CalendarWeekAdapter calendarWeekAdapter = new CalendarWeekAdapter(dayTrashInfoList);
        rvWeekList.setAdapter(calendarWeekAdapter);
    }


    //create a list of DayTrashInfo that include the 7 days next to date
    private List<DayTrashInfo> generateWeekDayTrashList(int date) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < 7; i++){
            DayTrashInfo dayTrashInfo = new DayTrashInfo();
            dayTrashInfo.setDate(date + i);
            String dayName = selectDay(day);
            dayTrashInfo.setDay(dayName);
            dayTrashInfo.setThrash("quello che va buttato oggi!");
        }

    }

    //select DAY_OF_WEEK from the start date
    private String selectDay(int day) {
        int i = day %
        switch (day){
            case
        }
    }

    private void initializeCalendarMonthView(View v) {
        calendarView = (CalendarView) v.findViewById(R.id.clndrView);

        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvTrash = (TextView) v.findViewById(R.id.tvTrash);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //TODO read db and rturn color and name of day's trash

                tvDate.setText(dayOfMonth + " / " + month + " / " + year);

                tvTrash.setText("quella del giorno"); //TODO insert day's trash
                Toast.makeText(getContext(), "Date: " + dayOfMonth + " / " + month + " / " + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    private CalendarViewType getViewMode(SharedPreferences pref) {
        
        int i;
        if (pref != null) {
            i = pref.getInt("calendarMode", 0);
            CalendarViewType type;
            switch (i) {
                case 0:
                    type = CalendarViewType.MONTH;
                    break;
                case 1:
                    type = CalendarViewType.WEEK;
                    break;
                default:
                    type = CalendarViewType.WEEK;
            }
        }
        
        return CalendarViewType.WEEK;
    }

}