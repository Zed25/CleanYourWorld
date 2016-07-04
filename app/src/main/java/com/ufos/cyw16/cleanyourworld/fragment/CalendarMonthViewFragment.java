/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.CalendarMonthViewFragment
 * Last modified: 02/07/16 16.07
 */

/*
 * Created by UFOS from simone_mancini
 * Project: CleanYourWorld
 * Package: com.ufos.cyw16.cleanyourworld.fragment.subfragment.CalendarMonthViewSubFragment
 * Last modified: 01/07/16 9.53
 */

package com.ufos.cyw16.cleanyourworld.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import com.ufos.cyw16.cleanyourworld.CalendarViewType;
import com.ufos.cyw16.cleanyourworld.DAO.MaterialiDAO;
import com.ufos.cyw16.cleanyourworld.Models.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarMonthAdapter;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarWeekAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.utlity.Message4Debug;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by simone_mancini on 01/07/16.
 */
public class CalendarMonthViewFragment extends Fragment {

    private CalendarView calendarView;

    private TextView tvDate, tvTrash;

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private CalendarViewType viewMode;

    private RecyclerView rvWeekList;

    private CalendarMonthAdapter calendarMonthAdapter;

    private List<DayTrashInfo> dayTrashInfoList;

    private int[] dayChoosen;

    public CalendarMonthViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //initialize calendar fragment's view
        View v = initializeMonthCalendar(inflater, container);
        return v;
    }

    /**this method prepare fragment for the configuration**/
    private View initializeMonthCalendar(LayoutInflater inflater, ViewGroup container) {
        View v;
        // Inflate the layout for Calendarfragment's default view (week view)
        v = inflater.inflate(R.layout.calendar_subfragment_month_view, container, false);
        initializeCalendarMonthView(v);
        return v;
    }

    /**this method initialize CalendarView's month view**/
    private void initializeCalendarMonthView(View v) {
        //initialize components
        initializerecyclerView(v);
        calendarView = (CalendarView) v.findViewById(R.id.clndrView);

        //if(dayTrashInfoList != null){
            generateDayTrashCard(dayTrashInfoList, dayChoosen);
        //}

        //manage the reaction of the view to a date change
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int date[] = {dayOfMonth, month, year};
                setDayChoosen(date);
                if (dayTrashInfoList != null) {
                    generateDayTrashCard(dayTrashInfoList, dayChoosen);
                }
            }
        });
    }

    /**initialize Recycler View
     *
     * Called in either in week view or in month view **/
    private void initializerecyclerView(View v){
        rvWeekList = (RecyclerView) v.findViewById(R.id.rvMonthList);

        //if dayTrashInfoList is equals to null initialized it
        //if (dayTrashInfoList == null) {
            dayTrashInfoList = new ArrayList<>();
        //}

        //set RecyclerView's size fixed
        rvWeekList.setHasFixedSize(true);

        //create the layout manager to insert into the recycler view
        //linear layout manager is similar to layout manager for the list view
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        //set the layout manager in recycler view
        rvWeekList.setLayoutManager(linearLayoutManager);

        //if dayChoosen is equals to null initialize it whit the current day
        //if(dayChoosen == null) {
            dayChoosen = computeToday();
        //}
        //create and set the adapter for the recycler view
        //if(calendarWeekAdapter == null) {
            calendarMonthAdapter = new CalendarMonthAdapter(dayTrashInfoList);
        //}

        rvWeekList.setAdapter(calendarMonthAdapter);
    }

    private void generateDayTrashCard(List<DayTrashInfo> list, int[] date) {
        //clear the list because if create another one the recycler view's adapter changes the riferiment list, so notifyDataSetChanged() doesen't work
        list.clear();

        GregorianCalendar calendar = new GregorianCalendar(date[2], date[1], date[0]);

        //create base structure for card view
        DayTrashInfo dayTrashInfo = createCardStructure(calendar);
        list.add(dayTrashInfo);

        //if calendarWeekAdapter isn't equals to null notyfy data set changed and rebuild recycler view
        //if(calendarWeekAdapter != null) {
            calendarMonthAdapter.notifyDataSetChanged();
        //}
    }

    /**this method determinates the current date**/
    private int[] computeToday() {
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int[] dateInformations = {day, month, year};

        return dateInformations;
    }

    private DayTrashInfo createCardStructure(GregorianCalendar calendar) {
        DayTrashInfo dayTrashInfo = new DayTrashInfo();
        String dayDate = computeDateString(calendar);
        dayTrashInfo.setDate(dayDate);
        String dayName = selectDay(calendar);
        dayTrashInfo.setDay(dayName);
        // FIXME: 02/07/16 [DAO] sostituire le DAO ed implementare getCollectionByDayOfWeek(..) in CollectionDao
        //MaterialiDAO materialiDAO = new MaterialiDAO(getContext());
        //Collection collection = materialiDAO.getCollectionByDayOfWeek(1865, calendar.get(Calendar.DAY_OF_WEEK));
        CollectionDao collectionDao = DaoFactory_def.getInstance(getContext()).getCollectionDao();
        List<Collection> collectionList = null;
        try {
            collectionList = collectionDao.getCollectionByDayOfWeek(1865, calendar.get(Calendar.DAY_OF_WEEK));
        } catch (DaoException e) {
            Message4Debug.log("Problems in collectionDao.getCollectionByDayOfWeek()");
        }
        String trash = "", trashColor = "";
        System.out.println("CREATE CARD QUERY DAY OF WEEK = " + calendar.get(Calendar.DAY_OF_WEEK));

        if(collectionList != null) {
            for (int i = 0; i < collectionList.size(); i++) {
                Collection collection = collectionList.get(i);
                // no trash that day
                if (collection.getMaterial() == null) {
                    //if (true) { // FIXME: 01/07/16
                    trash = "Nulla";
                    trashColor = "#29d96a";
                } else {
                    trash += collection.getMaterial().getName() + "\n";
                    trashColor = collection.getColor().getColorCode();
                }
            }
        }else{
            trash = "Nulla";
            trashColor = "#29d96a";
        }


        System.out.println("TRASH : " +trash + "TRASH COLOR :" + trashColor);
        dayTrashInfo.setThrash(trash);
        dayTrashInfo.setColorOfTheDay(trashColor);

        return dayTrashInfo;
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
        }

        str = getResources().getString(R.string.strProblem);
        return str;
    }


    /**this method creates the string dd/mm/yyyy**/
    private String computeDateString(GregorianCalendar calendar) {

        String date;

        date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/"
                + Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" +
                Integer.toString(calendar.get(Calendar.YEAR));

        return date;
    }

    /**GETTER AND SETTER**/
    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    public TextView getTvTrash() {
        return tvTrash;
    }

    public void setTvTrash(TextView tvTrash) {
        this.tvTrash = tvTrash;
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public CalendarViewType getViewMode() {
        return viewMode;
    }

    public void setViewMode(CalendarViewType viewMode) {
        this.viewMode = viewMode;
    }

    public RecyclerView getRvWeekList() {
        return rvWeekList;
    }

    public void setRvWeekList(RecyclerView rvWeekList) {
        this.rvWeekList = rvWeekList;
    }

    public CalendarMonthAdapter getCalendarMonthAdapter() {
        return calendarMonthAdapter;
    }

    public void setCalendarMonthAdapter(CalendarMonthAdapter calendarMonthAdapter) {
        this.calendarMonthAdapter = calendarMonthAdapter;
    }

    public List<DayTrashInfo> getDayTrashInfoList() {
        return dayTrashInfoList;
    }

    public void setDayTrashInfoList(List<DayTrashInfo> dayTrashInfoList) {
        this.dayTrashInfoList = dayTrashInfoList;
    }

    public int[] getDayChoosen() {
        return dayChoosen;
    }

    public void setDayChoosen(int[] dayChoosen) {
        this.dayChoosen = dayChoosen;
    }
}
