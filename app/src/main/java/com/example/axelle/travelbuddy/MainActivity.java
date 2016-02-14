//package com.example.axelle.travelbuddy;
//
//import android.app.ListActivity;
//import android.content.Intent;
//import android.database.DataSetObserver;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;
//
//import hk.ust.cse.hunkim.questionroom.db.DBHelper;
//import hk.ust.cse.hunkim.questionroom.db.DBUtil;
//import hk.ust.cse.hunkim.questionroom.question.Question;
//
//public class MainActivity extends ListActivity {
//
//    // TODO: change this to your own Firebase URL
//    private static final String FIREBASE_URL = "https://travelbuddy-hkust.firebaseio.com/";
//
//    private String roomName;
//    private Firebase mFirebaseRef;
//    private ValueEventListener mConnectedListener;
//    private QuestionListAdapter mChatListAdapter;
//    private String spinItem;
//    private String sortItem;
// //   private List<Question> mModels;
//
//    private DBUtil dbutil;
//
//    public DBUtil getDbutil() {
//        return dbutil;
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        //initialized once with an Android context.
//        Firebase.setAndroidContext(this);
//
//        setContentView(R.layout.activity_main);
//
//        Intent intent = getIntent();
//        assert (intent != null);
//
//// SPINNER
//        final Spinner forSort = (Spinner) findViewById(R.id.sorts);
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.sortArray, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        forSort.setAdapter(adapter1);
//
//        forSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> aview, View mview, int arg2, long arg3) {
//
//                TextView mytext = (TextView) mview;
//                sortItem = mytext.getText().toString();
//                if (mytext.getText().equals("~Sort Questions~"))
//                {
//                    mFirebaseRef.orderByChild("echo");
//                }
//                else if (mytext.getText().equals("Newest-Oldest")) {
//                    Toast.makeText(MainActivity.this, "Sorting from " + mytext.getText(), Toast.LENGTH_SHORT).show();
//                   mFirebaseRef.orderByChild("date");
//
//                  //  mChatListAdapter.sortModels(mModels);
//                }
//                else {
//                    Toast.makeText(MainActivity.this, "Sorting by " + mytext.getText(), Toast.LENGTH_SHORT).show();
//                  //  mFirebaseRef.orderByChild("echo");
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });
//
//        Spinner forCathash = (Spinner) findViewById(R.id.cathash);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.catArray, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        forCathash.setAdapter(adapter);
//        // CharSequence temp;
//        forCathash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> aview, View mview, int arg2, long arg3) {
//
//                TextView mytext = (TextView) mview;
//                if ( !mytext.getText().equals("~All Categories~")){
//                Toast.makeText(MainActivity.this, "Category chosen is " + mytext.getText(), Toast.LENGTH_SHORT).show();}
//                spinItem=mytext.getText().toString();
//                // ListView lv = (ListView) findViewById(R.id.list);
//             //   mChatListAdapter.sortModels(lv.getItemAtPosition());
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//
//            }
//        });
//
//// ROOM
//        roomName = intent.getStringExtra(JoinActivity.ROOM_NAME);
//        if (roomName == null || roomName.length() == 0) {
//            roomName = "all";
//        }
//
//        setTitle("Room name: " + roomName);
//        TextView textViewToChange = (TextView) findViewById(R.id.welcome);
//        textViewToChange.setText(roomName);
//
//
//        // Setup our Firebase mFirebaseRef
//        mFirebaseRef = new Firebase(FIREBASE_URL).child(roomName).child("questions");
//
//
//        // Setup our input methods. Enter key on the keyboard or pushing the send button
//        final EditText inputText = (EditText) findViewById(R.id.inputSearch);
//        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//
//               if(actionId==EditorInfo.IME_ACTION_SEARCH|| keyEvent == null || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                   TextView msg = (TextView) findViewById(R.id.head_desc);
//                   //mChatListAdapter.performSearch(msg, inputText);
//               }
//               if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    sendMessage();
//                }
//                return true;
//            }
//        });
//
//  /*      inputText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                // When user changed the Text
//                performSearch();
//                //mFirebaseRef.orderByChild(qmsg).equalTo(msg);
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                //
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                //
//
//            }
//        });*/
//
//
//        findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendMessage();
//            }
//        });
//
//        // get the DB Helper
//        DBHelper mDbHelper = new DBHelper(this);
//        dbutil = new DBUtil(mDbHelper);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
//        final ListView listView = getListView();
//        // Tell our list adapter that we only want 200 messages at a time
//        mChatListAdapter = new QuestionListAdapter(
//                mFirebaseRef.limitToFirst(200),
//                this, R.layout.question, roomName);
//                listView.setAdapter(mChatListAdapter);
//
//        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                listView.setSelection(mChatListAdapter.getCount() - 1);
//            }
//        });
//
//        // Finally, a little indication of connection status
//        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                boolean connected = (Boolean) dataSnapshot.getValue();
//                if (connected) {
//                    Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                // No-op
//            }
//        });
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
//        mChatListAdapter.cleanup();
//    }
//
//    private void sendMessage() {
//        EditText inputText = (EditText) findViewById(R.id.inputSearch);
//        String input = inputText.getText().toString();
//        if (!input.equals("") && !spinItem.equals("~All Categories~")) {
//            // Create our 'model', a Chat object
//
//            Question question = new Question(input);
//            question.setSpinner(spinItem);
//             // Create a new, auto-generated child of that chat location, and save our chat data there
//            mFirebaseRef.push().setValue(question);
//            inputText.setText("");
//        }
//        else if (spinItem.equals("~All Categories~")){
//            Toast.makeText(MainActivity.this, "Choose a Category", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//
//
//
//    public void attemptJoin(String key) {
//        // Reset errors.
//
//
//        // Store values at the time of the login attempt.
//
//
//            // Start main activity
//            Intent intent = new Intent(this, ReplyActivity.class);
//            intent.putExtra("ROOM_NAME", roomName);
//            intent.putExtra("KEY",key);
//
//            startActivity(intent);
//    }
//
//    public void updateEcho(String key) {
//
//        if(dbutil.contains(key+"l")){
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
//           final Firebase orderRef = mFirebaseRef.child(key).child("order");
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
//            dbutil.delete(key+"l");
//            Log.e("Dupkey", "Like + like");
//            return;
//        }
//        else if (dbutil.contains(key+"d")){
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
//            dbutil.put(key+"l");
//            dbutil.delete(key+"d");
//            Log.e("Dupkey","Like + dislike");
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
//
//    public void updateNeg_echo(String key){
//          if(dbutil.contains(key+"d")){
//              final Firebase neg_echoRef = mFirebaseRef.child(key).child("echo");
//              neg_echoRef.addListenerForSingleValueEvent(
//                      new ValueEventListener() {
//                          @Override
//                          public void onDataChange(DataSnapshot dataSnapshot) {
//                              Long neg_echoValue = (Long) dataSnapshot.getValue();
//                              Log.e("Neg_echo:", "" + neg_echoValue);
//
//                              neg_echoRef.setValue(neg_echoValue + 1);
//                          }
//
//                          @Override
//                          public void onCancelled(FirebaseError firebaseError) {
//
//                          }
//                      }
//              );
//
//              final Firebase orderRef = mFirebaseRef.child(key).child("order");
//              orderRef.addListenerForSingleValueEvent(
//                      new ValueEventListener() {
//                          @Override
//                          public void onDataChange(DataSnapshot dataSnapshot) {
//                              Long orderValue = (Long) dataSnapshot.getValue();
//                              Log.e("Order update:", "" + orderValue);
//
//                              orderRef.setValue(orderValue + 1);
//                          }
//
//                          @Override
//                          public void onCancelled(FirebaseError firebaseError) {
//
//                          }
//                      }
//              );
//              dbutil.delete(key+"d");
//              Log.e("Dupkey", "Dislike + Dislike");
//              return;
//          }
//        else if (dbutil.contains(key+"l")){
//              final Firebase neg_echoRef = mFirebaseRef.child(key).child("echo");
//              neg_echoRef.addListenerForSingleValueEvent(
//                      new ValueEventListener() {
//                          @Override
//                          public void onDataChange(DataSnapshot dataSnapshot) {
//                              Long neg_echoValue = (Long) dataSnapshot.getValue();
//                              Log.e("Neg_echo:", "" + neg_echoValue);
//
//                              neg_echoRef.setValue(neg_echoValue - 2);
//                          }
//
//                          @Override
//                          public void onCancelled(FirebaseError firebaseError) {
//
//                          }
//                      }
//              );
//
//              final Firebase orderRef = mFirebaseRef.child(key).child("order");
//              orderRef.addListenerForSingleValueEvent(
//                      new ValueEventListener() {
//                          @Override
//                          public void onDataChange(DataSnapshot dataSnapshot) {
//                              Long orderValue = (Long) dataSnapshot.getValue();
//                              Log.e("Order update:", "" + orderValue);
//
//                              orderRef.setValue(orderValue - 2);
//                          }
//
//                          @Override
//                          public void onCancelled(FirebaseError firebaseError) {
//
//                          }
//                      }
//              );
//              dbutil.put(key+"d");
//              dbutil.delete(key+"l");
//              Log.e("Dupkey","Dislike + like");
//              return;
//          }
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
//        dbutil.put(key+"d");
//    }
//
//    public void updateReport(String key) {
//        if (dbutil.contains(key+"r")){
//            Log.e("Dupkey", "Key is already in the Db!");
//            return;
//        }
//        final Firebase reportRef = mFirebaseRef.child(key).child("reports");
//        reportRef.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Long reportValue = (Long) dataSnapshot.getValue();
//                        Log.e("Report update:", "" + reportValue);
//
//                        reportRef.setValue(reportValue + 1);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                }
//        );
//        dbutil.put(key+"r");
//    }
//
//    public String getSpinItem(){return spinItem;}
//    public String getSortItem(){return sortItem;}
//   public void Close(View view) {
//        finish();
//    }
//}
