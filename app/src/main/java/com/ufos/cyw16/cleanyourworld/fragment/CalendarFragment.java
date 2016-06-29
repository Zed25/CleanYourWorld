package com.ufos.cyw16.cleanyourworld.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.ufos.cyw16.cleanyourworld.ConfigurationActivity;
import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarWeekAdapter;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by simone_mancini on 11/05/16.
 */
public class CalendarFragment extends Fragment{

    private CalendarView calendarView;

    private TextView tvDate, tvTrash;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CalendarViewType viewMode;

    public static CalendarWeekAdapter calendarWeekAdapter;

    private RecyclerView rvWeekList;

    public static List<DayTrashInfo> dayTrashInfoList;

    public static int[] dayChoosen;

    public static CalendarFragment calendarFragmentInstance;

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

        if (calendarFragmentInstance == null){
            setCalendarFragmentInstance(this);
        }
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
        if (dayTrashInfoList == null) {
            dayTrashInfoList = new ArrayList<>();
        }
        FloatingActionButton fabDayChooser = (FloatingActionButton) v.findViewById(R.id.fabChooseDay);

        //set RecyclerView's size fixed
        rvWeekList.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        rvWeekList.setLayoutManager(linearLayoutManager);



        //setOnClickListener on the flay
        fabDayChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(); //this function return void but there is setDayChoosen() in it, so dayChoosen is setted after this function return
            }
        });

        //create a list of 7 elements
        //theese elements are the 7 days next to the choose one
        if(dayChoosen == null) {
            dayChoosen = computeToday();
        }
        if (dayTrashInfoList != null) {
            generateWeekDayTrashList(dayTrashInfoList, dayChoosen);
        }
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


    //To select the correct language day this function doesen't use getResurces() and R class
    // because with the date picker this function is called before the onAttach() function
    private String selectDay(GregorianCalendar calendar) {
        int dayOfWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String str = null;
        String phoneLanguage, italian;
        phoneLanguage = Locale.getDefault().getDisplayLanguage();
        Message4Debug.log("default value is: " + phoneLanguage);
        italian = Locale.ITALIAN.getDisplayLanguage();
        Message4Debug.log("italian's value is: " + italian);
        switch(dayOfWeekNumber) {
            case 1:
                if(phoneLanguage.equals(italian)){
                    str = "Domenica";

                }else{
                    str = "Sunday";
                }
                break;
            case 2:
                if(phoneLanguage.equals(italian)){
                    str = "Lunedì";

                }else{
                    str = "Monday";
                }
                break;
            case 3:
                if(phoneLanguage.equals(italian)){
                    str = "Martedì";

                }else{
                    str = "Tuesday";
                }
                break;
            case 4:
                if(phoneLanguage.equals(italian)){
                str = "Mercoledi";

                }else{
                    str = "Wednesday";
                }
                break;
            case 5:
                if(phoneLanguage.equals(italian)){
                    str = "Giovedì";

                }else{
                    str = "Thursday";
                }
                break;
            case 6:
                if(phoneLanguage.equals(italian)){
                    str = "Venerdì";

                }else{
                    str = "Friday";
                }
                break;
            case 7:
                if(phoneLanguage.equals(italian)){
                    str = "Sabato";

                }else{
                    str = "Saturday";
                }
                break;
        }
        if (str == null) {
            if (phoneLanguage.equals(italian)) {
                str = "Un problema è occorso";
            }else {
                str = "There is a problem";
            }
        }
        return str;
    }


    //it creates the string dd/mm/yyyy
    private String computeDateString(GregorianCalendar calendar) {

        String date;

        date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/"
                + Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" +
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

    public static List<DayTrashInfo> getDayTrashInfoList() {
        return dayTrashInfoList;
    }

    public static void setDayTrashInfoList(List<DayTrashInfo> dayTrashInfoList) {
        CalendarFragment.dayTrashInfoList = dayTrashInfoList;
    }

    public static CalendarFragment getCalendarFragmentInstance() {
        if (calendarFragmentInstance != null) {
            return calendarFragmentInstance;
        }
        CalendarFragment calendarFragment = new CalendarFragment();
        return calendarFragment;
    }

    public static void setCalendarFragmentInstance(CalendarFragment calendarFragmentInstance) {
        if (calendarFragmentInstance == null) {
            CalendarFragment.calendarFragmentInstance = calendarFragmentInstance;
        }
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

            if (getDayTrashInfoList() != null && getDayChoosen() != null && getCalendarWeekAdapter() != null && getCalendarFragmentInstance() != null) {
                getCalendarFragmentInstance().generateWeekDayTrashList(getDayTrashInfoList(), getDayChoosen());
            }


        }
    }

}