//package com.example.axelle.travelbuddy;
//
//import android.app.Activity;
//import android.text.Html;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//
//import com.firebase.client.Query;
//
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import hk.ust.cse.hunkim.questionroom.db.DBUtil;
//import hk.ust.cse.hunkim.questionroom.question.Question;
//
///**
// * @author greg
// * @since 6/21/13
// * <p/>
// * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
// * data for each individual chat message
// */
//public class QuestionListAdapter extends FirebaseListAdapter<Question> {
//
//    // The mUsername for this client. We use tlasthis to indicate which messages originated from this user
//    private String roomName;
//    PrimaryActivity activity;
//
//
//    public QuestionListAdapter(Query ref, Activity activity, int layout, String roomName) {
//        super(ref, Question.class, layout, activity);
//
//        // Must be PrimaryActivity
//        assert (activity instanceof PrimaryActivity);
//
//        this.activity = (PrimaryActivity) activity;
//    }
//
//    /**
//     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
//     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
//     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
//     *
//     * @param view     A view instance corresponding to the layout we passed to the constructor.
//     * @param question An instance representing the current state of a chat message
//     */
//    @Override
//    protected void populateView(View view, Question question) {
//        DBUtil dbUtil = activity.getDbutil();
//
///*
//
//*/
//// TIMESTAMP
//        String dateString;
//
//        long secondsPassed = (System.currentTimeMillis()- question.getTimestamp())/1000;
//        if(secondsPassed >= 60*60*24*30){
//            dateString = (secondsPassed/(60*60*24*30))+" months ago";
//        }
//        else if(secondsPassed >= 60*60*24){
//            dateString = (secondsPassed/(60*60*24))+" days ago";
//        }
//        else if(secondsPassed >= 60*60){
//            dateString= (secondsPassed/(60*60))+" hours ago";
//        }
//        else if(secondsPassed >= 60){
//            dateString = (secondsPassed/60)+" minutes ago";
//        }
//        else{
//            dateString = "just now";
//        }
//
//        TextView a = (TextView) view.findViewById(R.id.timeStamp);
//        a.setText(dateString);
//
//
//        Button replyButton = (Button) view.findViewById(R.id.reply);
//        replyButton.setTag(question.getKey());
//
//        replyButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PrimaryActivity m = (PrimaryActivity) view.getContext();
//                        m.attemptJoin((String) view.getTag());
//                    }
//                }
//        );
//
//
//
//// Map a Chat object to an entry in our listview
//        int echo = question.getEcho();
//        Button echoButton = (Button) view.findViewById(R.id.echo);
//
//        echoButton.setTag(question.getKey()); // Set tag for ebutton
//
//        echoButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PrimaryActivity m = (PrimaryActivity) view.getContext();
//                        m.updateEcho((String) view.getTag());
//                    }
//                }
//        );
//
//        Button neg_echoButton = (Button) view.findViewById(R.id.neg_echo);
//
//        neg_echoButton.setTag(question.getKey()); // Set tag for nbutton
//
//        neg_echoButton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PrimaryActivity m = (PrimaryActivity) view.getContext();
//                        m.updateNeg_echo((String) view.getTag());
//
//
//                    }
//                }
//        );
//// ECHO DIFF (Works perfectly)
//        int diff =echo;
//        TextView total = (TextView) view.findViewById(R.id.total);
//        total.setText("" + diff);
//
//
//        String msgString = "";
//
//        question.updateNewQuestion();
//
////  SYNCHRONISATION (temporarily done)
//        if (question.isNewQuestion()) {
//            msgString = "<B><font color=RED>NEW </font></B>" + msgString;
//        }
//
//
//        msgString += noHTML(question.getWholeMsg());
//
//        switch (question.getSpinner()){
//            case "midterm": msgString = "<font color=#bb770f>"+msgString+"</font>";break;
//            case "final": msgString = "<font color=#FF7777>"+msgString+"</font>";break;
//            case "lab": msgString = "<font color=#8B008B>"+msgString+"</font>";break;
//            case "project": msgString = "<font color=#ff770f>"+msgString+"</font>";break;
//            case "tutorial": msgString = "<font color=#009900>"+msgString+"</font>";break;
//            case "general": msgString = "<font color=#4863A0>"+msgString+"</font>";break;
//            default: break;
//
//       }
//
//
//        ((TextView) view.findViewById(R.id.head_desc)).setText(Html.fromHtml(msgString));
//        view.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//
//                                      //  PrimaryActivity m = (PrimaryActivity) view.getContext();
//                                      //  m.updateEcho((String) view.getTag());
//                                    }
//                                }
//
//        );
//
//// check if we already clicked
////        boolean clicked = dbUtil.contains(question.getKey());
//
//            echoButton.setClickable(true);
//            neg_echoButton.setClickable(true);
//            echoButton.setEnabled(true);
//            neg_echoButton.setEnabled(true);
//
//        view.setTag(question.getKey());  // store key in the view
//
//        Button rep = ((Button) view.findViewById(R.id.rep));
//        rep.setTag(question.getKey());
//        rep.setVisibility(View.VISIBLE);
//        rep.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                     public void onClick(View view){
//                        PrimaryActivity m = (PrimaryActivity) view.getContext();
//                        m.updateReport((String) view.getTag());
//                    }
//
//        });
//
//// REPORT
//        if (question.getReports()>10){
//            TextView qmsg =((TextView) view.findViewById(R.id.head_desc));
//            qmsg.setVisibility(View.GONE);
//        }
//        else {
//            TextView qmsg = ((TextView) view.findViewById(R.id.head_desc));
//            qmsg.setVisibility(View.VISIBLE);
//        }
//
//        RelativeLayout messg = (RelativeLayout) view.findViewById(R.id.qbox);
//        String spinItem = activity.getSpinItem();
//        if (!spinItem.contains("general") && (!spinItem.contains("All"))) {
//            if(spinSearch(spinItem, question)==true)
//            {
//                messg.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                messg.setVisibility(View.GONE);
//            }
//
//        }
//        else{
//            messg.setVisibility(View.VISIBLE);
//        }
//
//}
//
//    @Override
//    protected void sortModels(List<Question> mModels) {
//        String sortItem = activity.getSortItem();
//
//        if (sortItem.contains("Popularity"))
//        {
//            Collections.sort(mModels);
//        }
//        else if (sortItem.contains("Newest"))
//        {
//            Collections.sort(mModels,  new Comparator<Question>() {
//
//                public int compare(Question o1, Question o2) {
//                    return Long.toString(o2.getTimestamp()).compareTo(Long.toString(o1.getTimestamp()));
//                }
//            });
//        }
//        Collections.sort(mModels);
//    }
//
//    @Override
//    protected void setKey(String key, Question model) {
//        model.setKey(key);
//    }
//
//    public static String noHTML(String message){
//        String temp= "";
//        for (int i=0;i<message.length();i++){
//            if(message.substring(i,i+1).equals("<")){
//                temp = temp+"<![CDATA[<]]>";
//            }
//            else if (message.substring(i,i+1).equals("&")){
//                temp = temp+"<![CDATA[&]]>";
//            }
//            else{
//                temp = temp + message.substring(i,i+1);
//            }
//        }
//        return temp;
//    }
//
//    public boolean spinSearch(String spinItem, Question question) {
//
//        if (spinItem.equals(question.getSpinner()))
//        {
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//}
