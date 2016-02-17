package com.example.axelle.travelbuddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by prashanthcr on 8/2/2016.
 */
public class JourneyLaterActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "GlobalSettings";

    private int calendarYear = 2016;
    private int calendarMonth = 1;
    private int calendarDay = 4;
    private String time;
    private String from = "from";
    private String to = "to";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_later);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String display = settings.getString("display1","blah") + " -> "+settings.getString("display2","blah");

        from = settings.getString("alpha", "PLACE NOT FOUND");
        to = settings.getString("beta", "PLACE NOT FOUND");

        TextView txt = (TextView) findViewById(R.id.journey_details);
        txt.setText(display);

        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);

        Calendar now = GregorianCalendar.getInstance();

        calendar.setMinDate(now.getTime().getTime() - 5000);

        now.add(Calendar.MONTH, 1);

        calendar.setMaxDate(now.getTime().getTime());

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

        EditText min = (EditText) findViewById(R.id.minute);
        String minute = min.getText().toString();

        EditText inputTime = (EditText) findViewById(R.id.hour);
        String hour = inputTime.getText().toString();

        time = hour+":"+minute;



        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        from = settings.getString("alpha", "PLACE NOT FOUND");
        to = settings.getString("beta", "PLACE NOT FOUND");
        String display = settings.getString("display1","blah") + " -> "+settings.getString("display2","blah");

        hour = hour.equals("") ? "25" : hour;
        minute = minute.equals("") ? "61" : minute;
        if ((Integer.parseInt(hour)<=24&&Integer.parseInt(minute)<=60)&&(!(hour.equals(""))&&!(minute.equals("")))){
            if (!(from.equals("")) && !(to.equals(""))) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("calendarYear", calendarYear);
                intent.putExtra("calendarMonth", calendarMonth);
                intent.putExtra("calendarDay", calendarDay);
                intent.putExtra("time", time);
                intent.putExtra("from", from);
                intent.putExtra("to", to);
                intent.putExtra("display", display);

                startActivity(intent);
            }
            else Toast.makeText(getApplicationContext(), "Please select start and destination", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(),"Please enter a valid time in 24-hour format", Toast.LENGTH_SHORT).show();

    }

    public void viewBookings(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(intent);
    }
}
