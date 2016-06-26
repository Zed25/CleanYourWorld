package com.ufos.cyw16.cleanyourworld.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufos.cyw16.cleanyourworld.R;

/**
 * Created by simone_mancini on 11/05/16.
 */
public class CalendarFragment extends Fragment{

    private CalendarView calendarView;

    private TextView tvDate, tvTrash;

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

}