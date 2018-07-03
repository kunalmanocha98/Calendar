package com.agnitio.calendar;

import java.util.Date;

public class EventskeObjects {
    private int id;
    private String message;
    private Date date;
    public EventskeObjects(String message, Date date) {
        this.message = message;
        this.date = date;
    }
    public EventskeObjects(int id, String message, Date date) {
        this.date = date;
        this.message = message;
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }
}
