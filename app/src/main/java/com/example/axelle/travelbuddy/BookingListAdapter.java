package com.example.axelle.travelbuddy;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.axelle.travelbuddy.booking.Booking;
import com.example.axelle.travelbuddy.db.DBUtil;
import com.firebase.client.Query;

import java.util.Collections;
import java.util.List;

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class BookingListAdapter extends FirebaseListAdapter<Booking> {

    // The mUsername for this client. We use tlasthis to indicate which messages originated from this user
    private String roomName;
    MainActivity activity;


    public BookingListAdapter(Query ref, Activity activity, int layout, String roomName) {
        super(ref, Booking.class, layout, activity);

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
    protected void populateView(View view, Booking question) {
        DBUtil dbUtil = activity.getDbutil();

        Button replyButton = (Button) view.findViewById(R.id.reply);
        replyButton.setTag(question.getKey());

        replyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity m = (MainActivity) view.getContext();
                        m.onReply((String) view.getTag());
                    }
                }
        );
        TextView bookDate = (TextView) view.findViewById(R.id.bookDate);
        TextView bookTime = (TextView) view.findViewById(R.id.bookTime);
        TextView venue = (TextView) view.findViewById(R.id.bookVenue);
        TextView name = (TextView) view.findViewById(R.id.idname);
        TextView displayName = (TextView) view.findViewById(R.id.displayName);

        String date = question.getCalendarDay() + "/" + question.getCalendarMonth() + "/" + question.getCalendarYear();
        venue.setText(question.getDisplay());
        bookDate.setText(date);
        bookTime.setText(question.getTime());
        name.setText(question.getUsername());
        displayName.setText(question.getDisplayName());


// TIMESTAMP
        String dateString;

        long secondsPassed = (System.currentTimeMillis() - question.getTimestamp()) / 1000;
        if (secondsPassed >= 60 * 60 * 24 * 30) {
            dateString = (secondsPassed / (60 * 60 * 24 * 30)) + " months ago";
        } else if (secondsPassed >= 60 * 60 * 24) {
            dateString = (secondsPassed / (60 * 60 * 24)) + " days ago";
        } else if (secondsPassed >= 60 * 60) {
            dateString = (secondsPassed / (60 * 60)) + " hours ago";
        } else if (secondsPassed >= 60) {
            dateString = (secondsPassed / 60) + " minutes ago";
        } else {
            dateString = "just now";
        }

        String msgString = "";

        question.updateNewQuestion();

//  SYNCHRONISATION (temporarily done)
        if (question.isNewQuestion()) {
            msgString = "<B><font color=RED>NEW </font></B>" + msgString;
        }


        msgString += noHTML(question.getWholeMsg());

// check if we already clicked
//        boolean clicked = dbUtil.contains(question.getKey());

        view.setTag(question.getKey());  // store key in the view

//        Button rep = ((Button) view.findViewById(R.id.rep));
//        rep.setTag(question.getKey());
//        rep.setVisibility(View.VISIBLE);
//        rep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainActivity m = (MainActivity) view.getContext();
//                m.updateReport((String) view.getTag());
//            }
//
//        });

        RelativeLayout messg = (RelativeLayout) view.findViewById(R.id.qbox);

        if (activity.sort == true){
            if (sortSearch(activity.whoami, question)==false)
                messg.setVisibility(View.GONE);
            else
                messg.setVisibility(View.VISIBLE);

        }
        else
        {
            messg.setVisibility(View.VISIBLE);
        }



    }

    @Override
    protected void sortModels(List<Booking> mModels) {
        Collections.sort(mModels);
    }

    @Override
    protected void setKey(String key, Booking model) {
        model.setKey(key);
    }

    public static String noHTML(String message) {
        String temp = "";
        for (int i = 0; i < message.length(); i++) {
            if (message.substring(i, i + 1).equals("<")) {
                temp = temp + "<![CDATA[<]]>";
            } else if (message.substring(i, i + 1).equals("&")) {
                temp = temp + "<![CDATA[&]]>";
            } else {
                temp = temp + message.substring(i, i + 1);
            }
        }
        return temp;
    }
    public boolean sortSearch(String whoami, Booking question) {

        if (whoami.equals(question.getUsername()))
        {
            return true;
        }
        else{
            return false;
        }
    }

}
