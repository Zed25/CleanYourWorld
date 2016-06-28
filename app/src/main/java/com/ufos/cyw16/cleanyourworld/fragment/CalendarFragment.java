package com.ufos.cyw16.cleanyourworld.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.CalendarViewType;
import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarWeekAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public  static CalendarWeekAdapter calendarWeekAdapter;

    private RecyclerView rvWeekList;

    private List<DayTrashInfo> dayTrashInfoList;

    public static int[] dayChoosen;

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
        //initialize components
        rvWeekList = (RecyclerView) v.findViewById(R.id.rvWeekList);
        FloatingActionButton fabDayChooser = (FloatingActionButton) v.findViewById(R.id.fabChooseDay);
        dayTrashInfoList = new ArrayList<>();

        //setOnClickListener on the flay
        fabDayChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(); //this function return void but there is setDayChoosen() in it, so dayChoosen
                // is setted after this function return
                //another control on dayChoosen variable
                if ( dayChoosen != null){
                    generateWeekDayTrashList(dayTrashInfoList, dayChoosen);
                }
            }
        });


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
        if(dayChoosen == null) {
            dayChoosen = computeToday();
        }
        generateWeekDayTrashList(dayTrashInfoList, dayChoosen);

        //create and set the adapter for the recycler view
        calendarWeekAdapter = new CalendarWeekAdapter(dayTrashInfoList);
        rvWeekList.setAdapter(calendarWeekAdapter);
    }


    //choose a day and set dayChoosen variable
    private void showCalendarDialog(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getActivity().getSupportFragmentManager(), "datePicker");
    }

    private int[] computeToday() {
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int[] dateInformations = {day, month, year};

        return dateInformations;
    }


    //create a list of DayTrashInfo that include the 7 days next to date
    private void generateWeekDayTrashList(List<DayTrashInfo> list, int[] date) {
        list.clear();
        GregorianCalendar calendar = new GregorianCalendar(date[2], date[1], date[0]);
        for (int i = 0; i < 7; i++){
            DayTrashInfo dayTrashInfo = new DayTrashInfo();
            String dayDate = computeDateString(calendar);
            dayTrashInfo.setDate(dayDate);
            String dayName = selectDay(calendar);
            dayTrashInfo.setDay(dayName);
            dayTrashInfo.setThrash("quello che va buttato oggi!");
            list.add(dayTrashInfo);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        if(calendarWeekAdapter != null) {
            calendarWeekAdapter.notifyDataSetChanged();
        }


    }

    private String selectDay(GregorianCalendar calendar) {
        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String str;
        switch(dayOfWeekNumber) {
            case 1:
                str = getResources().getString(R.string.strSunday);
                return str;
            case 2:
                str = getResources().getString(R.string.strMonday);
                return str;
            case 3:
                str = getResources().getString(R.string.strTuesday);
                return str;
            case 4:
                str = getResources().getString(R.string.strWednesday);
                return str;
            case 5:
                str = getResources().getString(R.string.strThursday);
                return str;
            case 6:
                str = getResources().getString(R.string.strFriday);
                return str;
            case 7:
                str = getResources().getString(R.string.strSaturday);
                return str;
            default:
                str = getResources().getString(R.string.strMonday);
                return str;
        }
    }


    //it creates the string dd/mm/yyyy
    private String computeDateString(GregorianCalendar calendar) {

        String date;

        date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/"
                + Integer.toString(calendar.get(Calendar.MONTH)) + "/" +
                Integer.toString(calendar.get(Calendar.YEAR));

        return date;
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
        CalendarViewType type;
        if (pref != null) {
            i = pref.getInt("calendarMode", 1);
            switch (i) {
                case 0:
                    type = CalendarViewType.MONTH;
                    return type;
                case 1:
                    type = CalendarViewType.WEEK;
                    return type;
                default:
                    type = CalendarViewType.WEEK;
                    return type;
            }
        }
        return CalendarViewType.WEEK;
    }

    public static int[] getDayChoosen() {
        return dayChoosen;
    }

    public static void setDayChoosen(int[] dayChoosen) {
        CalendarFragment.dayChoosen = dayChoosen;
    }

    public static CalendarWeekAdapter getCalendarWeekAdapter() {
        return calendarWeekAdapter;
    }

    public static void setCalendarWeekAdapter(CalendarWeekAdapter calendarWeekAdapter) {
        CalendarFragment.calendarWeekAdapter = calendarWeekAdapter;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year, month, day;

            if (dayChoosen != null) {
                //Use the last date choosen to initialize date picker
                final GregorianCalendar calendar = new GregorianCalendar(dayChoosen[2], dayChoosen[1], dayChoosen[0]);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }else {

                //Use the current date to initialize date picker
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            int[] date = {dayOfMonth, monthOfYear, year};

            setDayChoosen(date);


        }
    }

}