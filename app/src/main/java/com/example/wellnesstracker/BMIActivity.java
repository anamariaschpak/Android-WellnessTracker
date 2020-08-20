package com.example.wellnesstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    private EditText et_weight;
    private EditText et_height;
    private TextView result;

    public static final int SAVE = 1;

    public Float bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        et_weight = (EditText)findViewById(R.id.et_Weight);
        et_height = (EditText)findViewById(R.id.et_Height);
        result = (TextView)findViewById(R.id.tv_result);

        Intent mainIntent = getIntent();
        String name = mainIntent.getStringExtra("Name");

        SharedPreferences sharedPreferences = getSharedPreferences("bmiSharedPrefs", 0);
        float savedWeight = sharedPreferences.getFloat("weight", 0);
        float savedHeight = sharedPreferences.getFloat("height", 0);
        if(savedHeight != 0 && savedHeight != 0) {
            et_weight.setText(Float.toString(savedWeight));
            et_height.setText(Float.toString(savedHeight));
        }
    }

    public void computeBMI(View view) {

        float weightFloat = Float.parseFloat(et_weight.getText().toString());
        float heightFloat = Float.parseFloat(et_height.getText().toString());

        bmi = weightFloat / (heightFloat*heightFloat);
        printBMI(bmi);

        SharedPreferences sharedPreferences = getSharedPreferences("bmiSharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor bmiEditor =  sharedPreferences.edit();
        bmiEditor.putFloat("weight", weightFloat);
        bmiEditor.putFloat("height", heightFloat);
        bmiEditor.apply();


    }

    private void printBMI(float bmi){
        String bmiDiagnostic = "";

        if(Float.compare(bmi, 16f) <= 0){
            bmiDiagnostic = getString(R.string.extremely_underweight);
        } else if(Float.compare(bmi, 16f) > 0 && Float.compare(bmi, 18.5f) <= 0){
            bmiDiagnostic = getString(R.string.underweight);
        } else if(Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0){
            bmiDiagnostic = getString(R.string.normal);
        } else if(Float.compare(bmi, 25f) > 0 && Float.compare(bmi, 30f) <= 0){
            bmiDiagnostic = getString(R.string.overweight);
        } else if(Float.compare(bmi, 30f) > 0 && Float.compare(bmi, 35f) <= 0){
            bmiDiagnostic = getString(R.string.obese);
        } else if(Float.compare(bmi, 35f) > 0){
            bmiDiagnostic = getString(R.string.extremely_obese);
        }

        String bmiString = Float.toString(bmi);
        result.setText(getString(R.string.bmi_diagnostic, bmiString, bmiDiagnostic));
    }
}
