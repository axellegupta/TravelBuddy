package com.example.axelle.travelbuddy.booking;

import java.util.Date;

/**
 * Created by roncool on 11/22/15.
 */

public class reply implements Comparable<reply>{
    private int key;
    private double id;
    public String wholeMsg;
    public String replyName;
    //  private String headLastChar;
    //  private String desc;
    //   private String linkedDesc;
    private boolean completed;
    public long timestamp;
    private String tags;
    private int echo;
    private int order;
    private boolean newreply;
    private int reports;
    private postedBy postedBy;
    private String text;
    public reply[] replies;
    private int value=0;

    public double getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public postedBy getPostedBy() {
        return postedBy;
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
    public reply() {
    }

    public reply(boolean a) {
    }

    /**
     * Set question from a String message
     *
     * @param message string message
     */
    public reply(String message, String replyName) {
        this.wholeMsg = message;
        this.echo = 0;

        this.replyName=replyName;

        this.reports = 0;
        this.timestamp = new Date().getTime();
        this.id = 3;
    }

    public reply(String message, int key, String replyName) {
        this.wholeMsg = message;
        this.echo = 0;
        this.replyName=replyName;

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

    // public String getHeadLastChar() {        return headLastChar;    }

    //   public String getLinkedDesc() {        return linkedDesc;     }

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

    public boolean isNewreply() {
        return newreply;
    }

    public void updateNewQuestion() {
        newreply = this.timestamp > new Date().getTime() - 180000;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    /**
     * New one/high echo goes bottom
     *
     * @param other other chat
     * @return order
     */

    public int compareTo(reply other) {
        // Push new on top
        other.updateNewQuestion(); // update NEW button
        this.updateNewQuestion();

        if (this.newreply != other.newreply) {
            return this.newreply ? 1 : -1; // this is the winner
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
        if (!(o instanceof reply)) {
            return false;
        }
        reply other = (reply) o;
        return key==other.key;
    }

    public int hashCode() {
        return key;
    }

    public String getReplyName() {
        return this.replyName;
    }


}
