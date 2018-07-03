package com.agnitio.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CustomCalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_calendar);
         CalendarCustomView mView = findViewById(R.id.custom_calendar);
    }
}
