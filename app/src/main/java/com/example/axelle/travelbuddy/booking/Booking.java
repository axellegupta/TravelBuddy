package com.example.axelle.travelbuddy.booking;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Booking implements Comparable<Booking> {

    /**
     * Must be synced with firebase JSON structure
     * Each must have getters
     */
    private String key;
    private String id;
    private String wholeMsg;
    private boolean completed;
    private long timestamp;
    private String tags;
    private int echo;
    private int order;
    private boolean newQuestion;
    private int reports;

    private String text;
    private int value=0;

    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;
    private String time;
    private String from;
    private String to;

    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public int getReports() {
        return reports;
    }

   public String getDateString() {
        return dateString;
    }

    private String dateString;

    public String getTrustedDesc() {
        return trustedDesc;
    }

    private String trustedDesc;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Booking() {
    }

    /**
     * Set question from a String message
     *
     * @param message string message
     */
    public Booking(String message, int year, int month, int day, String time) {
        this.wholeMsg = message;
        this.echo = 0;
        this.reports = 0;
        this.timestamp = new Date().getTime();
        this.calendarYear = year;
        this.calendarMonth = month;
        this.calendarDay = day;
        this.time = time;

    }

    public Booking(String message, int key) {
        this.wholeMsg = message;
        this.echo = 0;
        this.reports = 0;
        this.timestamp = new Date().getTime();

    }
    /* -------------------- Getters ------------------- */

    //   public String getDesc() {        return desc;    }

    public int getEcho() {
        return echo;
    }

    public String getWholeMsg() {
        return wholeMsg;
    }


    public boolean isCompleted() {
        return completed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTags() {
        return tags;
    }

    public int getOrder() {
        return order;
    }

    public boolean isNewQuestion() {
        return newQuestion;
    }

    public void updateNewQuestion() {
        newQuestion = this.timestamp > new Date().getTime() - 180000;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCalendarYear() { return this.calendarYear;}

    public int getCalendarMonth() { return this.calendarMonth; }

    public int getCalendarDay() { return this.calendarDay; }

    public String getTime() { return this.time; }

    public String getFrom() { return this.from; }

    public String getTo() { return this.to; }

    /**
     * New one/high echo goes bottom
     *
     * @param other other chat
     * @return order
     */
    @Override
    public int compareTo(Booking other) {
        // Push new on top
        other.updateNewQuestion(); // update NEW button
        this.updateNewQuestion();

        if (this.newQuestion != other.newQuestion) {
            return this.newQuestion ? 1 : -1; // this is the winner
        }


        if (this.echo == other.echo) {
            if (other.timestamp == this.timestamp) {
                return 0;
            }
            return other.timestamp > this.timestamp ? -1 : 1;
        }
        return this.echo - other.echo;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) o;
        return key.equals(other.key);
    }


}