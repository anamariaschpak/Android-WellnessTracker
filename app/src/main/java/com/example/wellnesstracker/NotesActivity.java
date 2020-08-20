package com.example.wellnesstracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    public static final int SAVE = 1;
    public static final int EDIT = 2;
    public static final int DELETE = 3;

    Database database;
    String date;
    EditText et_noteContent;
    int userIdFromCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Intent passDate = getIntent();
        date = passDate.getStringExtra("passedDate");
        TextView tv = findViewById(R.id.tv_dateNotes);
        tv.setText(date);

        Context currentContext = this;

        database = Database.getInstance(currentContext);

        et_noteContent = findViewById(R.id.et_noteContent);

        userIdFromCalendar = getIntent().getIntExtra("passedId", -1);

        Note noteForCurrentUser = database.getNoteDao().getNoteByUserIdAndDate(userIdFromCalendar, date);

        if(noteForCurrentUser != null) {
            et_noteContent.setText(noteForCurrentUser.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, SAVE, 1, "SAVE");
        menu.add(0, EDIT, 2, "EDIT");
        menu.add(0, DELETE, 3, "DELETE");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case SAVE:
                database.getNoteDao().insertNote(new Note(date, et_noteContent.getText().toString(), userIdFromCalendar));
                et_noteContent.setFocusable(false);
                et_noteContent.setCursorVisible(false);
                break;

            case EDIT:
                et_noteContent.setFocusable(true);
                et_noteContent.requestFocusFromTouch();
                et_noteContent.setCursorVisible(true);
                database.getNoteDao().editNote(et_noteContent.getText().toString(), userIdFromCalendar);
                break;

            case DELETE:
                database.getNoteDao().deleteNoteByUserId(userIdFromCalendar);
                et_noteContent.setText("");

                return true;
        }

        return false;
    }
}
