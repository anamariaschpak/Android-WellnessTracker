package com.example.wellnesstracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExercisesReportActivity extends AppCompatActivity {

    Database database;
    public static final int SAVE = 1;
    ArrayList<String> exercisesNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_exercises);

        database = Database.getInstance(getApplicationContext());

        List<Exercise> exercises = database.getExerciseDao().getAllExercises();

        for(Exercise ex : exercises){
            exercisesNames.add(ex.getName());
        }

        Collections.sort(exercisesNames, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        ArrayAdapter exercisesAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, exercisesNames);
        ListView lv_exerciseList = findViewById(R.id.lv_exercisesList);
        lv_exerciseList.setAdapter(exercisesAdapter);
    }

    private void writeExercisesReport(String fileName, ArrayList<String> exercisesNames){
        try {
            FileOutputStream fos = openFileOutput(fileName, Activity.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            for(String ex : exercisesNames){
                osw.write(ex);
                osw.write("\n");
            }
            osw.write("\n");

            osw.flush();

            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, SAVE, 1, "Save .txt report");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case SAVE:
                writeExercisesReport("exercises_report.txt", exercisesNames);
                return true;
        }

        return false;
    }
}
