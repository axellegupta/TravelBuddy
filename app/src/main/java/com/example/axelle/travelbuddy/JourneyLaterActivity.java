package com.example.axelle.travelbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by prashanthcr on 8/2/2016.
 */
public class JourneyLaterActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "GlobalSettings";

    private int calendarYear = 2016;
    private int calendarMonth = 1;
    private int calendarDay = 4;
    private String time = "5:30";
    private LatLng from = new LatLng(120, 112.1);
    private LatLng to = new LatLng(112.3, -120);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_later);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        String alpha = settings.getString("alpha", "PLACE NOT FOUND");
        String beta = settings.getString("beta", "PLACE NOT FOUND");

        TextView txt = (TextView) findViewById(R.id.journey_details);
        txt.setText("You will be travelling from " + alpha + " to " + beta + ".\nConfirm?");

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendarDay = dayOfMonth;
                calendarMonth = month;
                calendarYear = year;
            }
        });

        final EditText inputTime = (EditText) findViewById(R.id.inputTime);
        inputTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(getApplicationContext(), inputTime.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addEvent(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeView.class);
        intent.putExtra("position", 1);

        startActivity(intent);
    }
}
