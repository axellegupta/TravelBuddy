package com.example.axelle.travelbuddy.question;

import java.util.Date;

/**
 * Created by hunkim on 7/16/15.
 */
public class Question implements Comparable<Question> {

    /**
     * Must be synced with firebase JSON structure
     * Each must have getters
     */
    private String key;
    private String id;
    private String wholeMsg;
    //  private String headLastChar;
    //  private String desc;
    //   private String linkedDesc;
    private boolean completed;
    private long timestamp;
    private String tags;
    private int echo;
    private int order;
    private boolean newQuestion;
    private int reports;

    private postedBy postedBy;
    private String category;
    private String text;
    public reply[] replies;
    private int value=0;
    private boolean isReply;
    private boolean newreply;

    public boolean isNewreply(){
        return newreply;
    }
    public String getId() {
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

    public String getSpinner() {
        return category;
    }

    public void setSpinner(String a) {
        this.category = a;
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
    private Question() {
    }

    /**
     * Set question from a String message
     *
     * @param message string message
     */
    public Question(String message) {
        this.wholeMsg = message;
        this.echo = 0;
        this.category ="general";
        this.reports = 0;
        this.timestamp = new Date().getTime();
    }

    public Question(String message, int key) {
        this.wholeMsg = message;
        this.echo = 0;
        this.category = "general";
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

    /**
     * New one/high echo goes bottom
     *
     * @param other other chat
     * @return order
     */
    @Override
    public int compareTo(Question other) {
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
        if (!(o instanceof Question)) {
            return false;
        }
        Question other = (Question) o;
        return key.equals(other.key);
    }


}