package com.example.wellnesstracker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class NotesReportActivity extends AppCompatActivity {

    public static final int SAVE = 1;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_notes);

        ListView lv_notes = findViewById(R.id.lv_notes);
        database = Database.getInstance(this);

        List<Note> notesList = database.getNoteDao().getAllNotes();
        ArrayAdapter<Note> notesAdapter = new ArrayAdapter<Note>(this, R.layout.display_notes, notesList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                convertView = inflater.inflate(R.layout.display_notes, parent ,false);

                TextView tv_noteId = convertView.findViewById(R.id.tv_noteId);
                TextView tv_noteDate = convertView.findViewById(R.id.tv_noteDate);
                TextView tv_noteContent = convertView.findViewById(R.id.tv_noteContent);
                TextView tv_noteUserId = convertView.findViewById(R.id.tv_noteUserId);

                tv_noteId.setText(String.valueOf(getItem(position).getId()));
                tv_noteDate.setText(getItem(position).getDate());
                tv_noteContent.setText(getItem(position).getContent());
                tv_noteUserId.setText(String.valueOf(getItem(position).getUserId()));
                tv_noteId.setTextColor(Color.BLUE);
                tv_noteDate.setTextColor(Color.BLUE);
                tv_noteContent.setTextColor(Color.BLUE);
                tv_noteUserId.setTextColor(Color.BLUE);


                return convertView;
            }
        };


        lv_notes.setAdapter(notesAdapter);

    }

    private void writeNotesReport(String fileName, List<Note> noteList){
        try {
            FileOutputStream fos = openFileOutput(fileName, Activity.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fos);

            for(Note note: noteList){
                dos.writeInt(note.getId());
                dos.writeUTF(note.getDate());
                dos.writeUTF(note.getContent());
                dos.writeInt(note.getUserId());
            }

            dos.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, SAVE, 1, "Save .dat report");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case SAVE:
                writeNotesReport("notes_report.dat", database.getNoteDao().getAllNotes());
                return true;
        }

        return false;
    }
}
