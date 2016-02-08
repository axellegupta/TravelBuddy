package com.example.axelle.travelbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/**
 * Created by prashanthcr on 8/2/2016.
 */
public class JourneyLaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_later);

        TextView txt = (TextView) findViewById(R.id.journey_details);
        AutoCompleteTextView alpha = (AutoCompleteTextView) findViewById(R.id.start_location);
        AutoCompleteTextView beta = (AutoCompleteTextView) findViewById(R.id.end_location);

//        txt.setText("You will be travelling from " + alpha.getText() + " to " + beta.getText() + ".\nConfirm?");
    }
}
