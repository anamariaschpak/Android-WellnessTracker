package com.example.wellnesstracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button btnNotes = (Button) findViewById(R.id.btn_notes);
        final CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year);

            }
        });


        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNotes = new Intent(CalendarActivity.this, NotesActivity.class);
                openNotes.putExtra("passedDate", selectedDate);
                openNotes.putExtra("passedId", getIntent().getIntExtra("passedUserById", -1));
                startActivity(openNotes);
            }
        });


    }
}
