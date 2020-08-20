package com.example.wellnesstracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;

public class NutritionActivity extends AppCompatActivity {

    Button btnGo ;
    EditText searchedFood ;
    TextView tvEnergy, tvProteins, tvFat, tvCarbs, tvFibers, tvEnergyLabel, tvProteinsLabel, tvFatLabel, tvCarbsLabel, tvFibersLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        btnGo= (Button) findViewById(R.id.btn_go);
        searchedFood= (EditText) findViewById(R.id.et_searchedFood);
        tvEnergy = findViewById(R.id.tv_energy);
        tvProteins = findViewById(R.id.tv_proteins);
        tvFat = findViewById(R.id.tv_fat);
        tvCarbs = findViewById(R.id.tv_carbs);
        tvFibers = findViewById(R.id.tv_fibers);
        tvEnergyLabel = findViewById(R.id.tv_energyLabel);
        tvProteinsLabel = findViewById(R.id.tv_proteinsLabel);
        tvFatLabel = findViewById(R.id.tv_fatLabel);
        tvCarbsLabel = findViewById(R.id.tv_carbsLabel);
        tvFibersLabel = findViewById(R.id.tv_fibersLabel);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExtractFood extractFood = new ExtractFood() {
                    @Override
                    protected void onPostExecute(String s) {
                        //Log.v("test extract food", data);
                        if(getParsedArrayNull() == true){
                            Toast.makeText(getApplicationContext(), "The searched food doesn't exist in the database!", Toast.LENGTH_LONG).show();
                            tvEnergyLabel.setVisibility(View.INVISIBLE);
                            tvProteinsLabel.setVisibility(View.INVISIBLE);
                            tvFatLabel.setVisibility(View.INVISIBLE);
                            tvCarbsLabel.setVisibility(View.INVISIBLE);
                            tvFibersLabel.setVisibility(View.INVISIBLE);
                        }
                        else {
                            tvEnergy.setText(getEnergy().toString());
                            tvProteins.setText(getProteins().toString());
                            tvFat.setText(getFat().toString());
                            tvCarbs.setText(getCarbs().toString());
                            tvFibers.setText(getFibers().toString());
                        }

                    }
                };

                try {
                    // URL for all foods: https://api.edamam.com/api/food-database/parser?ingr=all&app_id=069bcd35&app_key=76cb9538f4df3c3a0bccc269ae4b4a6b
                    extractFood.execute(new URL("https://api.edamam.com/api/food-database/parser?ingr=" + searchedFood.getText() + "&app_id=069bcd35&app_key=76cb9538f4df3c3a0bccc269ae4b4a6b"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
