package com.example.axelle.travelbuddy;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.axelle.travelbuddy.booking.Booking;
import com.example.axelle.travelbuddy.db.DBHelper;
import com.example.axelle.travelbuddy.db.DBUtil;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends ListActivity {

    // TODO: change this to your own Firebase URL
    private static final String FIREBASE_URL = "https://travelbuddy-hkust.firebaseio.com/";

    private String roomName;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private BookingListAdapter mChatListAdapter;
    private String spinItem;
    private String sortItem;
    //   private List<Booking> mModels;

    private DBUtil dbutil;

    public DBUtil getDbutil() {
        return dbutil;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialized once with an Android context.
        Firebase.setAndroidContext(this);

        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        assert (intent != null);


// ROOM
        roomName = "All Bookings";

        setTitle("Room name: " + roomName);
        TextView textViewToChange = (TextView) findViewById(R.id.welcome);
        textViewToChange.setText(roomName);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions");


        // Setup our input methods. Enter key on the keyboard or pushing the send button


  /*      inputText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                performSearch();
                //mFirebaseRef.orderByChild(qmsg).equalTo(msg);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                //

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //

            }
        });*/

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
        mChatListAdapter = new BookingListAdapter(
                mFirebaseRef.limitToFirst(200),
                this, R.layout.question, roomName);
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
                    Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    sendMessage();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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

    private void sendMessage() {
        // Create our 'model', a Chat object

        Intent intent = getIntent();

        int year = intent.getIntExtra("calendarYear", 0);
        int month = intent.getIntExtra("calendarMonth", 0);
        int day = intent.getIntExtra("calendarDay", 0);
        String time = intent.getStringExtra("time");

        Booking question = new Booking("user", year, month, day, time);
        // Create a new, auto-generated child of that chat location, and save our chat data there
        mFirebaseRef.push().setValue(question);
    }

//    public void updateEcho(String key) {
//
//        if (dbutil.contains(key + "l")) {
//            final Firebase echoRef = mFirebaseRef.child(key).child("echo");
//            echoRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long echoValue = (Long) dataSnapshot.getValue();
//                            Log.e("Echo update:", "" + echoValue);
//
//                            echoRef.setValue(echoValue - 1);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//
//            final Firebase orderRef = mFirebaseRef.child(key).child("order");
//            orderRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long orderValue = (Long) dataSnapshot.getValue();
//                            Log.e("Order update:", "" + orderValue);
//
//                            orderRef.setValue(orderValue - 1);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//            dbutil.delete(key + "l");
//            Log.e("Dupkey", "Like + like");
//            return;
//        } else if (dbutil.contains(key + "d")) {
//            final Firebase echoRef = mFirebaseRef.child(key).child("echo");
//            echoRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long echoValue = (Long) dataSnapshot.getValue();
//                            Log.e("Echo update:", "" + echoValue);
//
//                            echoRef.setValue(echoValue + 2);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//
//            final Firebase orderRef = mFirebaseRef.child(key).child("order");
//            orderRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long orderValue = (Long) dataSnapshot.getValue();
//                            Log.e("Order update:", "" + orderValue);
//
//                            orderRef.setValue(orderValue + 2);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//            dbutil.put(key + "l");
//            dbutil.delete(key + "d");
//            Log.e("Dupkey", "Like + dislike");
//            return;
//        }
//
//        //like button
//        final Firebase echoRef = mFirebaseRef.child(key).child("echo");
//        echoRef.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Long echoValue = (Long) dataSnapshot.getValue();
//                        Log.e("Echo update:", "" + echoValue);
//
//                        echoRef.setValue(echoValue + 1);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                }
//        );
//
//        final Firebase orderRef = mFirebaseRef.child(key).child("order");
//        orderRef.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Long orderValue = (Long) dataSnapshot.getValue();
//                        Log.e("Order update:", "" + orderValue);
//
//                        orderRef.setValue(orderValue + 1);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                }
//        );
//        //Update SQLite DB
//        Log.e("Dupkey", "Like + Neither");
//        dbutil.put(key + "l");
//    }

//    public void updateNeg_echo(String key) {
//        if (dbutil.contains(key + "d")) {
//            final Firebase neg_echoRef = mFirebaseRef.child(key).child("echo");
//            neg_echoRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long neg_echoValue = (Long) dataSnapshot.getValue();
//                            Log.e("Neg_echo:", "" + neg_echoValue);
//
//                            neg_echoRef.setValue(neg_echoValue + 1);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//
//            final Firebase orderRef = mFirebaseRef.child(key).child("order");
//            orderRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long orderValue = (Long) dataSnapshot.getValue();
//                            Log.e("Order update:", "" + orderValue);
//
//                            orderRef.setValue(orderValue + 1);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//            dbutil.delete(key + "d");
//            Log.e("Dupkey", "Dislike + Dislike");
//            return;
//        } else if (dbutil.contains(key + "l")) {
//            final Firebase neg_echoRef = mFirebaseRef.child(key).child("echo");
//            neg_echoRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long neg_echoValue = (Long) dataSnapshot.getValue();
//                            Log.e("Neg_echo:", "" + neg_echoValue);
//
//                            neg_echoRef.setValue(neg_echoValue - 2);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//
//            final Firebase orderRef = mFirebaseRef.child(key).child("order");
//            orderRef.addListenerForSingleValueEvent(
//                    new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            Long orderValue = (Long) dataSnapshot.getValue();
//                            Log.e("Order update:", "" + orderValue);
//
//                            orderRef.setValue(orderValue - 2);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    }
//            );
//            dbutil.put(key + "d");
//            dbutil.delete(key + "l");
//            Log.e("Dupkey", "Dislike + like");
//            return;
//        }
//
//        //dislike button
//        final Firebase neg_echoRef = mFirebaseRef.child(key).child("echo");
//        neg_echoRef.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Long neg_echoValue = (Long) dataSnapshot.getValue();
//                        Log.e("Neg_echo:", "" + neg_echoValue);
//
//                        neg_echoRef.setValue(neg_echoValue - 1);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                }
//        );
//
//        final Firebase orderRef = mFirebaseRef.child(key).child("order");
//        orderRef.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Long orderValue = (Long) dataSnapshot.getValue();
//                        Log.e("Order update:", "" + orderValue);
//
//                        orderRef.setValue(orderValue - 1);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                }
//        );
//        //Update SQLite DB
//        Log.e("Dupkey", "Dislike + Neither");
//        dbutil.put(key + "d");
//    }

    public void updateReport(String key) {
        if (dbutil.contains(key + "r")) {
            Log.e("Dupkey", "Key is already in the Db!");
            return;
        }
        final Firebase reportRef = mFirebaseRef.child(key).child("reports");
        reportRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long reportValue = (Long) dataSnapshot.getValue();
                        Log.e("Report update:", "" + reportValue);

                        reportRef.setValue(reportValue + 1);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                }
        );
        dbutil.put(key + "r");
    }

    public void Close(View view) {
        finish();
    }
}
