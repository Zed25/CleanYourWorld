package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.R;

/**
 * Created by simone_mancini on 11/05/16.
 */
public class CalendarFragment extends Fragment{

    CalendarView calendarView;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.calendar_fragment, container, false); //inflate layout

        initializeCalendar(v);
        return v;
    }

    private void initializeCalendar(View v) {
        calendarView = (CalendarView) v.findViewById(R.id.clndrView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), "Date: " + dayOfMonth + " / " + month + " / " + year, Toast.LENGTH_LONG).show();
            }
        });
    }

}