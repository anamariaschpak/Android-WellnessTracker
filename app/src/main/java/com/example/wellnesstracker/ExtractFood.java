package com.example.wellnesstracker;

import android.net.Network;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

public class ExtractFood extends AsyncTask<URL, Void, String> {

    private String data="";
    private String foodID;
    private Double energy ;
    private Double proteins;
    private Double fat;
    private Double carbs;
    private Double fibers;
    private Boolean isParsedArrayNull =false;


    @Override
    protected String doInBackground(URL... urls) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");
//            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                data += line;
            }
            Log.v("test_json", data);
            parseJSON(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void parseJSON(String data){
        try {
            JSONObject wholeObject = new JSONObject(data);
            JSONArray parsedArray = wholeObject.getJSONArray("parsed");
            if(parsedArray.length() == 0){
                isParsedArrayNull = true;
            }
            JSONObject parantezePatrate = parsedArray.getJSONObject(0);
            JSONObject foodObject = parantezePatrate.getJSONObject("food");
            JSONObject nutrientsObject = foodObject.getJSONObject("nutrients");
            energy = nutrientsObject.getDouble("ENERC_KCAL");
            proteins = nutrientsObject.getDouble("PROCNT");
            fat = nutrientsObject.getDouble("FAT");
            carbs = nutrientsObject.getDouble("CHOCDF");
            fibers = nutrientsObject.getDouble("FIBTG");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Double getEnergy() {
        return energy;
    }

    public Double getProteins() {
        return proteins;
    }

    public Double getFat() {
        return fat;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getFibers() {
        return fibers;
    }

    public Boolean getParsedArrayNull() {
        return isParsedArrayNull;
    }
}
