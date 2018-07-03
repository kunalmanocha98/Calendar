package com.agnitio.calendar;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.agnitio.calendar.Database.DatabaseQuery;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarCustomView extends LinearLayout {
    private static final String TAG = CalendarCustomView.class.getSimpleName();
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private Context context;
    private GridAdapter mAdapter;
    private GridAdapter2 recycleradapter;
    private RecyclerView rv;
    private GridLayoutManager gridLayoutManager;
    List<Date> dayValueInCells = new ArrayList<Date>();
    List<EventskeObjects> mEvents = new ArrayList<>();
    final int[] height = {0};
    Boolean flag = true;

    //    private DatabaseQuery mQuery;
    public CalendarCustomView(Context context) {
        super(context);
    }

    public CalendarCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
        Log.d(TAG, "I need to call this method");
    }

    public CalendarCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView) view.findViewById(R.id.calendar_grid);
        rv = view.findViewById(R.id.recycleradapter);
        gridLayoutManager = new GridLayoutManager(context, 7);
        gridLayoutManager.setOrientation(VERTICAL);
        rv.setLayoutManager(gridLayoutManager);

    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setGridCellClickEvents() {
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Clicked " + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpCalendarAdapter() {
        dayValueInCells.clear();
        mEvents.clear();
//        mQuery = new DatabaseQuery(context);
//        List<EventskeObjects> mEvents = mQuery.getAllFutureEvents();
        String dateInString = "27-06-2018";
        DateFormat format = new SimpleDateFormat("d-MM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mEvents.add(new EventskeObjects("hello", date));
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Log.d(TAG, "Number of date " + dayValueInCells.size());
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        if(flag) {
            final ViewTreeObserver observe = calendarGridView.getViewTreeObserver();
            observe.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    calendarGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    height[0] = calendarGridView.getHeight();
                    setvalues();
                    flag=false;
                }
            });
        }else{
            setvalues();
        }


    }

    private void setvalues() {

        mAdapter = new GridAdapter(context, dayValueInCells, cal, mEvents, height[0]);
        calendarGridView.setAdapter(mAdapter);

    }

}