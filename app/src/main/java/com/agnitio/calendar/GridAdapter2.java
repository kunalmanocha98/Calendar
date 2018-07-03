package com.agnitio.calendar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GridAdapter2 extends RecyclerView.Adapter<GridAdapter2.ViewHolder> {
    Context c;
    List<Date> monthlydates;
    Calendar calendar;
    List<EventskeObjects> events;


    public GridAdapter2(Context context, List<Date> dayValueInCells, Calendar cal, List<EventskeObjects> mEvents) {
        this.c=context;
        this.monthlydates =dayValueInCells;
        this.calendar=cal;
        this.events=mEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cell_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date mDate = monthlydates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        holder.cellNumber.setText(String.valueOf(dayValue));
        holder.cv.setVisibility(View.GONE);
        holder.cv.setBackgroundColor(Color.parseColor("#ffffff"));
        Calendar eventCalendar = Calendar.getInstance();
        for(int i = 0; i < events.size(); i++){
            eventCalendar.setTime(events.get(i).getDate());
            if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                    && displayYear == eventCalendar.get(Calendar.YEAR)){
                holder.cv.setBackgroundColor(Color.parseColor("#ed5050"));
                holder.eventIndicator.setText("hello");
//                cv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return monthlydates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cellNumber,eventIndicator;
        LinearLayout cv;
        public ViewHolder(View itemView) {
            super(itemView);
            cellNumber = itemView.findViewById(R.id.calendar_date_id);
            eventIndicator=itemView.findViewById(R.id.event_id);
            cv=itemView.findViewById(R.id.event_wrapper);
        }
    }
}
