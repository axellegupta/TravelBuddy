package com.example.axelle.travelbuddy.booking;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Booking implements Comparable<Booking> {

    private String displayName;
    private String display;
    /**
     * Must be synced with firebase JSON structure
     * Each must have getters
     */
    public reply[] replies;
    private boolean isReply;
    private boolean newreply;

    public boolean isNewreply(){
        return newreply;
    }

    private String key;
    private String id;
    private String username;
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
    public String getUsername() { return username; }
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
    public Booking(String message, int year, int month, int day, String time, String from, String to, String display, String username, String displayName) {
        this.wholeMsg = message;
        this.echo = 0;
        this.reports = 0;
        this.calendarYear = year;
        this.calendarMonth = month;
        this.calendarDay = day;
        this.time = time;
        this.from = from;
        this.to = to;
        this.display=display;
        this.username = username;
        this.displayName = displayName;


        String[] arr1 = time.split(":");
        Calendar c = GregorianCalendar.getInstance();
        c.set(year, month, day, Integer.parseInt(arr1[0]), Integer.parseInt(arr1[1]));
        this.timestamp = c.getTime().getTime();
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

    public String getDisplay(){return this.display;}

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


        String[] arr1 = other.time.split(":");
        String[] arr2 = this.time.split(":");
//        Date thisDate = new Date(this.getCalendarYear(), this.getCalendarMonth(), this.getCalendarDay(), Integer.getInteger(arr2[0]), Integer.getInteger(arr2[1]));
//        if (this.newQuestion != other.newQuestion) {
//            return this.newQuestion ? 1 : -1; // this is the winner
//        }


        if (this.calendarYear == other.calendarYear) {
            if (this.calendarMonth == other.calendarMonth) {
                if (this.calendarDay == other.calendarDay) {
                    if (Integer.parseInt(arr2[0]) == Integer.parseInt(arr1[0])) { // hour
                        if (Integer.parseInt(arr2[1]) == Integer.parseInt(arr1[1])) { // minute
                            return 0;
                        } else {
                            return Integer.parseInt(arr1[1]) > Integer.parseInt(arr2[1]) ? -1 : 1;
                        }
                    } else {
                        return Integer.parseInt(arr1[0]) > Integer.parseInt(arr2[0]) ? -1 : 1;
                    }
                }
                else
                    return other.calendarDay > this.calendarDay ? -1 : 1;
            }
            else
                return other.calendarMonth > this.calendarMonth ? -1 : 1;
        }
        else
            return other.calendarYear > this.calendarYear ? -1 : 1;


//        if (this.echo == other.echo) {
//            if (other.calendarMonth == this.calendarMonth) {
//                return 0;
//            }
//            return other.calendarMonth > this.calendarMonth ? -1 : 1;
//        }
//   return this.echo - other.echo;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) o;
        return key.equals(other.key);
    }


    public String getDisplayName() {
        return this.displayName;
    }
}