package com.example.wellnesstracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    Button btnShowDetails;
    Spinner spinnerExercises;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    ArrayList<String> exercisesNames = new ArrayList<String>();
    String selectedOption = "";
    private static int noPages = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btnShowDetails = (Button) findViewById(R.id.btn_showDetails);
        spinnerExercises = (Spinner) findViewById(R.id.spinner_exercises);
        final ListView lvExerciseDetails = (ListView) findViewById(R.id.lv_exerciseDetails);

        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExerciseDetailsAdapter detailsAdapter = new ExerciseDetailsAdapter(WorkoutActivity.this, R.layout.exercise_details, selectedOption, exercises);

//                ExerciseDetailsAdapter detailsAdapter = new ExerciseDetailsAdapter(WorkoutActivity.this,R.layout.exercise_details,exercise);
                lvExerciseDetails.setAdapter(detailsAdapter);

                if(spinnerExercises.getSelectedItem() == null){
                    Toast.makeText(getApplicationContext(), "You didn't select any exercise!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        @SuppressLint("StaticFieldLeak") ExtractExercise extractExercise = new ExtractExercise() {
            @Override
            protected void onPostExecute(Void s) {

                Database database = Database.getInstance(getApplicationContext());

                exercises = getExercises();
                for(Exercise ex : exercises){
                    database.getExerciseDao().insertExercise(ex);
                }

                for(int i=0;i<exercises.size();i++){
                    exercisesNames.add(i, exercises.get(i).getName());
                }

                ArrayAdapter<String> exercisesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, exercisesNames);
                exercisesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerExercises.setAdapter(exercisesAdapter);

                spinnerExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedOption = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        };

        extractExercise.execute();

    }

}
