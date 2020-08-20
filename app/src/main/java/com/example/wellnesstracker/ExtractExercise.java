package com.example.wellnesstracker;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExtractExercise extends AsyncTask<Void, Void, Void> {

    private String wholeFileIntoString = "";
    private ArrayList<Exercise> exercises = new ArrayList<Exercise>();


    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for (int i = 1; i <= 10; i++) {
                URL apiURL = new URL("https://wger.de/api/v2/exercise/?%3Fformat=json&language=2&page=" + i + "&status=2");
                HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "a93c50d765abf9c14afc23e72a6c665a4215e71a");

                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                String line;

                while ((line = br.readLine()) != null) {
                    wholeFileIntoString += line;
                }

                Parsing();
                wholeFileIntoString = "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Parsing() {
        try {
            JSONObject wholeFile = new JSONObject(wholeFileIntoString);
            JSONArray resultsArray = wholeFile.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                Exercise newExercise = new Exercise();
                newExercise.setName(resultsArray.getJSONObject(i).getString("name"));

                newExercise.setDescription(resultsArray.getJSONObject(i).getString("description"));

                int categID = resultsArray.getJSONObject(i).getInt("category");
                switch (categID) {
                    case 10:
                        newExercise.setCategory("Abs");
                        break;
                    case 8:
                        newExercise.setCategory("Arms");
                        break;
                    case 12:
                        newExercise.setCategory("Back");
                        break;
                    case 14:
                        newExercise.setCategory("Calves");
                        break;
                    case 11:
                        newExercise.setCategory("Chest");
                        break;
                    case 9:
                        newExercise.setCategory("Legs");
                        break;
                    case 13:
                        newExercise.setCategory("Shoulders");
                        break;
                }

                int equipID = resultsArray.getJSONObject(i).getJSONArray("equipment").optInt(0,7);
                switch (equipID) {
                    case 1:
                        newExercise.setEquipment("Barbell");
                        break;
                    case 8:
                        newExercise.setEquipment("Bench");
                        break;
                    case 3:
                        newExercise.setEquipment("Dumbbell");
                        break;
                    case 4:
                        newExercise.setEquipment("Mat");
                        break;
                    case 9:
                        newExercise.setEquipment("Incline bench");
                        break;
                    case 10:
                        newExercise.setEquipment("Kettlebell");
                        break;
                    case 7:
                        newExercise.setEquipment("none(bodyweight exercise)");
                        break;
                    case 6:
                        newExercise.setEquipment("Pull-up bar");
                        break;
                    case 5:
                        newExercise.setEquipment("Swiss ball");
                        break;
                    case 2:
                        newExercise.setEquipment("SZ-bar");
                        break;
                }

                exercises.add(i, newExercise);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}


