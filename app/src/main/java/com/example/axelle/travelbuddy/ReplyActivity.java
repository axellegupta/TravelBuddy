package com.example.axelle.travelbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.axelle.travelbuddy.booking.Reply;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;

import com.example.axelle.travelbuddy.db.DBHelper;
import com.example.axelle.travelbuddy.db.DBUtil;

import java.util.ArrayList;
import java.util.List;
import com.firebase.client.GenericTypeIndicator;


public class ReplyActivity extends MainActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://travelbuddy-hkust.firebaseio.com/";

    private String roomName;
    private String keyName;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ReplyListAdapter mChatListAdapter;

    private DBUtil dbutil;

    public DBUtil getDbutil() {
        return dbutil;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialized once with an Android context.
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();
        assert (intent != null);


        roomName = intent.getStringExtra("ROOM_NAME");
        if (roomName == null || roomName.length() == 0) {
            roomName = "all";
        }

        keyName = intent.getStringExtra("KEY");

        setTitle("Replies");
        TextView textViewToChange = (TextView) findViewById(R.id.welcome);
        textViewToChange.setText("Replies");

        mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions").child(keyName).child("replies");

        EditText inputText = (EditText) findViewById(R.id.inputSearch);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    EditText inputText = (EditText) findViewById(R.id.inputSearch);
                    String input = inputText.getText().toString();
                    addComment(input);
                    inputText.setText("");
                }
                return true;
            }
        });

        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inputText = (EditText) findViewById(R.id.inputSearch);
                String input = inputText.getText().toString();
                addComment(input);
                inputText.setText("");
            }
        });

        // get the DB Helper
        DBHelper mDbHelper = new DBHelper(this);
        dbutil = new DBUtil(mDbHelper);

    }

    @Override
    public void onStart() {
        super.onStart();

        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes

        final ListView listView = getListView();

        // Tell our list adapter that we only want 200 messages at a time

        mChatListAdapter = new ReplyListAdapter(
                mFirebaseRef, this, R.layout.reply, keyName);

        listView.setAdapter(mChatListAdapter);

        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status

        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(ReplyActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReplyActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();

        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();

    }

/**
    private void sendReply() {

        if (!input.equals("")) {
            // Create our 'model', a Chat object
            mFirebaseRef.once("value", function(snapshot)) {
                GenericTypeIndicator<List<Reply>> t = new GenericTypeIndicator<List<Reply>>() {
                };
                List<comment> messages = dataSnapshot.getValue(t);
                ArrayList<Reply> tempComments = new ArrayList<Reply>();

                Reply question = new Reply(input);
            }
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.setPriority(0);
            mFirebaseRef.setValue(1);
            mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions").child(keyName).child("replies").child("0");
            mFirebaseRef.setValue(question);
            inputText.setText("");
        }
    }
**/
    public void addComment(final String input) {

        if (!input.equals("")) {

            final Firebase commentRef = mFirebaseRef;

            commentRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                /*
                                Map groupNames = snapshot.getValue();
                                List groupNamesList = new ArrayList(groupNames.values());
                                */
                            GenericTypeIndicator<List<Reply>> msgList = new GenericTypeIndicator<List<Reply>>() {
                            };
                            List<Reply> messages = dataSnapshot.getValue(msgList);

                            ArrayList<Reply> tempReplies = new ArrayList<Reply>();

                            if (messages != null) {
                                for (Reply getComment : messages) {
                                    tempReplies.add(getComment);
                                }
                            }

                            SharedPreferences settings = getSharedPreferences("GlobalSettings", 0);
                            String pname = settings.getString("personName", "NOT FOUND");

                            Reply newComment = new Reply();
                            newComment.setReplyName(pname);


                            newComment.wholeMsg = input;
                            newComment.timestamp= new Date().getTime();
                            tempReplies.add(newComment);

                            commentRef.setValue(tempReplies);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    }
            );

        }
    }
}