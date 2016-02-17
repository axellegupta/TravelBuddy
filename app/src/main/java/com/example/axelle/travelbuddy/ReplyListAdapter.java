package com.example.axelle.travelbuddy;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.axelle.travelbuddy.booking.Reply;
import com.firebase.client.Query;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.example.axelle.travelbuddy.db.DBUtil;

import org.w3c.dom.Text;

/**
 * Created by roncool on 11/22/15.
 */
public class ReplyListAdapter extends FirebaseListAdapter<Reply> {
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String roomName;
    MainActivity activity;


    public ReplyListAdapter(Query ref, Activity activity, int layout, String roomName) {
        super(ref, Reply.class, layout, activity);

        // Must be MainActivity
        assert (activity instanceof MainActivity);

        this.activity = (MainActivity) activity;
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view     A view instance corresponding to the layout we passed to the constructor.
     * @param question An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Reply question) {
        DBUtil dbUtil = activity.getDbutil();


// TIMESTAMP
        String dateString;

        long secondsPassed = (System.currentTimeMillis()- question.getTimestamp())/1000;
        if(secondsPassed >= 60*60*24*30){
            dateString = (secondsPassed/(60*60*24*30))+" months ago";
        }
        else if(secondsPassed >= 60*60*24){
            dateString = (secondsPassed/(60*60*24))+" days ago";
        }
        else if(secondsPassed >= 60*60){
            dateString= (secondsPassed/(60*60))+" hours ago";
        }
        else if(secondsPassed >= 60){
            dateString = (secondsPassed/60)+" minutes ago";
        }
        else{
            dateString = "just now";
        }

        TextView a = (TextView) view.findViewById(R.id.timeStamp);
        a.setText(dateString);

        TextView p = (TextView) view.findViewById(R.id.pername);
        p.setText(question.getReplyName());
/**

        Button replyButton = (Button) view.findViewById(R.id.Reply);
        replyButton.setTag(question.getKey());

        replyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity m = (MainActivity) view.getContext();
                        m.attemptJoin((String) view.getTag());
                    }
                }
        );


**/
// Map a Chat object to an entry in our listview
        /**
        int echo = question.getEcho();
        Button echoButton = (Button) view.findViewById(R.id.echo);

        echoButton.setTag(question.getKey()); // Set tag for ebutton

        echoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity m = (MainActivity) view.getContext();
                        m.updateEcho((String) view.getTag());
                    }
                }
        );

        Button neg_echoButton = (Button) view.findViewById(R.id.neg_echo);

        neg_echoButton.setTag(question.getKey()); // Set tag for nbutton

        neg_echoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity m = (MainActivity) view.getContext();
                        m.updateNeg_echo((String) view.getTag());


                    }
                }
        );
// ECHO DIFF (Works perfectly)
        int diff =echo;
        TextView total = (TextView) view.findViewById(R.id.total);
        total.setText("" + diff);

         **/
        String msgString = "";

        question.updateNewQuestion();

//  SYNCHRONISATION (temporarily done)

        msgString += noHTML(question.getWholeMsg());


        if (question.isNewreply()) {
            msgString = "<B><font color=RED>NEW </font></B>" + msgString;
        }



        ((TextView) view.findViewById(R.id.head_desc)).setText(Html.fromHtml(msgString));
        view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //  MainActivity m = (MainActivity) view.getContext();
                                        //  m.updateEcho((String) view.getTag());
                                    }
                                }

        );

// check if we already clicked
//        boolean clicked = dbUtil.contains(question.getKey());
/**
        echoButton.setClickable(true);
        neg_echoButton.setClickable(true);
        echoButton.setEnabled(true);
        neg_echoButton.setEnabled(true);
**/
        view.setTag(question.getKey());  // store key in the view
/**
        Button rep = ((Button) view.findViewById(R.id.rep));
        rep.setTag(question.getKey());
        rep.setVisibility(View.VISIBLE);
        rep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MainActivity m = (MainActivity) view.getContext();
                m.updateReport((String) view.getTag());
            }

        });

// REPORT
        if (question.getReports()>10){
            TextView qmsg =((TextView) view.findViewById(R.id.head_desc));
            qmsg.setVisibility(View.GONE);
        }
        else {
            TextView qmsg = ((TextView) view.findViewById(R.id.head_desc));
            qmsg.setVisibility(View.VISIBLE);
        }
 **/
    }

    @Override
    protected void sortModels(List<Reply> mModels) {
        Collections.sort(mModels);
    }

    @Override
    protected void setKey(String key, Reply model) {
        Random r = new Random();
        int Low = 10;
        int High = 100;
        int Result = r.nextInt(High-Low) + Low;
        model.setKey(Result);
    }

    public static String noHTML(String message){
        String temp= "";
        for (int i=0;i<message.length();i++){
            if(message.substring(i,i+1).equals("<")){
                temp = temp+"<![CDATA[<]]>";
            }
            else if (message.substring(i,i+1).equals("&")){
                temp = temp+"<![CDATA[&]]>";
            }
            else{
                temp = temp + message.substring(i,i+1);
            }
        }
        return temp;
    }
    public void performSearch(TextView msg, EditText inputText){

        String input = inputText.getText().toString();
        String qmsg = msg.getText().toString();
        if (!input.toLowerCase().contains(qmsg.toLowerCase())){
            msg.setVisibility(View.GONE);
        }
    }


}
