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
import com.ufos.cyw16.cleanyourworld.R;
import com.ufos.cyw16.cleanyourworld.adapter.CalendarMonthAdapter;
import com.ufos.cyw16.cleanyourworld.dal.dml.DaoException;
import com.ufos.cyw16.cleanyourworld.model_new.Collection;
import com.ufos.cyw16.cleanyourworld.model_new.DayTrashInfo;
import com.ufos.cyw16.cleanyourworld.model_new.dao.DaoFactory_def;
import com.ufos.cyw16.cleanyourworld.model_new.dao.factories.CollectionDao;
import com.ufos.cyw16.cleanyourworld.utlity.Choises;
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

    /**
     * Instantiates a new Calendar month view fragment.
     */
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


    /**
     * this method prepare fragment for the configuration @param inflater the inflater
     *
     * @param container the container
     * @return the view
     */
    private View initializeMonthCalendar(LayoutInflater inflater, ViewGroup container) {
        View v;
        // Inflate the layout for Calendarfragment's default view (week view)
        v = inflater.inflate(R.layout.calendar_subfragment_month_view, container, false);
        initializeCalendarMonthView(v);
        return v;
    }

    /**
     * this method initialize CalendarView's month view @param v the v
     */
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

    /**
     * initialize Recycler View
     * <p/>
     * Called in either in week view or in month view  @param v the v
     */
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

    /**
     * Generate day trash card.
     *
     * @param list the list
     * @param date the date
     */
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

    /**
     * this method determinates the current date @return the int [ ]
     */
    private int[] computeToday() {
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int[] dateInformations = {day, month, year};

        return dateInformations;
    }

    /**
     * Create card structure day trash info.
     *
     * @param calendar the calendar
     * @return the day trash info
     */
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
            collectionList = collectionDao.getCollectionByDayOfWeek(Choises.getIdComune(), calendar.get(Calendar.DAY_OF_WEEK));
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

    /**
     * Select day string.
     *
     * @param calendar the calendar
     * @return the string
     */
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


    /**
     * this method creates the string dd/mm/yyyy @param calendar the calendar
     *
     * @return the string
     */
    private String computeDateString(GregorianCalendar calendar) {

        String date;

        date = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + "/"
                + Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" +
                Integer.toString(calendar.get(Calendar.YEAR));

        return date;
    }

    /**
     * GETTER AND SETTER @return the calendar view
     */
    public CalendarView getCalendarView() {
        return calendarView;
    }

    /**
     * Sets calendar view.
     *
     * @param calendarView the calendar view
     */
    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }

    /**
     * Gets tv date.
     *
     * @return the tv date
     */
    public TextView getTvDate() {
        return tvDate;
    }

    /**
     * Sets tv date.
     *
     * @param tvDate the tv date
     */
    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    /**
     * Gets tv trash.
     *
     * @return the tv trash
     */
    public TextView getTvTrash() {
        return tvTrash;
    }

    /**
     * Sets tv trash.
     *
     * @param tvTrash the tv trash
     */
    public void setTvTrash(TextView tvTrash) {
        this.tvTrash = tvTrash;
    }

    /**
     * Gets pref.
     *
     * @return the pref
     */
    public SharedPreferences getPref() {
        return pref;
    }

    /**
     * Sets pref.
     *
     * @param pref the pref
     */
    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    /**
     * Gets editor.
     *
     * @return the editor
     */
    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    /**
     * Sets editor.
     *
     * @param editor the editor
     */
    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    /**
     * Gets view mode.
     *
     * @return the view mode
     */
    public CalendarViewType getViewMode() {
        return viewMode;
    }

    /**
     * Sets view mode.
     *
     * @param viewMode the view mode
     */
    public void setViewMode(CalendarViewType viewMode) {
        this.viewMode = viewMode;
    }

    /**
     * Gets rv week list.
     *
     * @return the rv week list
     */
    public RecyclerView getRvWeekList() {
        return rvWeekList;
    }

    /**
     * Sets rv week list.
     *
     * @param rvWeekList the rv week list
     */
    public void setRvWeekList(RecyclerView rvWeekList) {
        this.rvWeekList = rvWeekList;
    }

    /**
     * Gets calendar month adapter.
     *
     * @return the calendar month adapter
     */
    public CalendarMonthAdapter getCalendarMonthAdapter() {
        return calendarMonthAdapter;
    }

    /**
     * Sets calendar month adapter.
     *
     * @param calendarMonthAdapter the calendar month adapter
     */
    public void setCalendarMonthAdapter(CalendarMonthAdapter calendarMonthAdapter) {
        this.calendarMonthAdapter = calendarMonthAdapter;
    }

    /**
     * Gets day trash info list.
     *
     * @return the day trash info list
     */
    public List<DayTrashInfo> getDayTrashInfoList() {
        return dayTrashInfoList;
    }

    /**
     * Sets day trash info list.
     *
     * @param dayTrashInfoList the day trash info list
     */
    public void setDayTrashInfoList(List<DayTrashInfo> dayTrashInfoList) {
        this.dayTrashInfoList = dayTrashInfoList;
    }

    /**
     * Get day choosen int [ ].
     *
     * @return the int [ ]
     */
    public int[] getDayChoosen() {
        return dayChoosen;
    }

    /**
     * Sets day choosen.
     *
     * @param dayChoosen the day choosen
     */
    public void setDayChoosen(int[] dayChoosen) {
        this.dayChoosen = dayChoosen;
    }
}
