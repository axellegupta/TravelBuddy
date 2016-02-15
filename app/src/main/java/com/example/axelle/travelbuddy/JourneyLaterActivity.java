package com.example.axelle.travelbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by prashanthcr on 8/2/2016.
 */
public class JourneyLaterActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "GlobalSettings";

    private int calendarYear = 2016;
    private int calendarMonth = 1;
    private int calendarDay = 4;
    private String time = "5:30";
    private String from = "from";
    private String to = "to";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_later);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        from = settings.getString("alpha", "PLACE NOT FOUND");
        to = settings.getString("beta", "PLACE NOT FOUND");

        TextView txt = (TextView) findViewById(R.id.journey_details);
        txt.setText("You will be travelling from \n" + from + "\n to \n" + to);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendarDay = dayOfMonth;
                calendarMonth = month;
                calendarYear = year;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addEvent(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        EditText inputTime = (EditText) findViewById(R.id.inputTime);
        time = inputTime.getText().toString();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        from = settings.getString("alpha", "PLACE NOT FOUND");
        to = settings.getString("beta", "PLACE NOT FOUND");

        intent.putExtra("calendarYear", calendarYear);
        intent.putExtra("calendarMonth", calendarMonth);
        intent.putExtra("calendarDay", calendarDay);
        intent.putExtra("time", time);
        intent.putExtra("from", from);
        intent.putExtra("to", to);

        startActivity(intent);

    }
}
