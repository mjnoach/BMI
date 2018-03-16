package com.example.andrzej.lab2_bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        retrieveSavedData();


        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weightET = findViewById(R.id.weight_et);
                EditText heightET = findViewById(R.id.height_et);

                // Bitwise AND operator used to check both conditions (which set errors if arguments are invalid)
                if (validateInput(weightET) & validateInput(heightET)) {
                    String weightInput = weightET.getText().toString();
                    String heightInput = heightET.getText().toString();

                    Switch unitsSwitch = findViewById(R.id.switch_units);
                    Boolean units = unitsSwitch.isChecked();

                    try {
                        Bmi bmi;
                        if (!units)
                            bmi = new BmiKgCm(Double.valueOf(weightInput), Double.valueOf(heightInput));
                        else
                            bmi = new BmiLbIn(Double.valueOf(weightInput), Double.valueOf(heightInput));
                        Double bmiValue = bmi.getBmi();

                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra("bmiValue", bmiValue);
                        intent.putExtra("bmiCategory", bmi.getCategory());

                        intent.putExtra("weightInput", weightInput);
                        intent.putExtra("heightInput", heightInput);
                        intent.putExtra("units", units);
                        startActivity(intent);
                    }
                    catch (IllegalArgumentException e) {
                        if (e.getMessage().equals("0"))
                            weightET.setError("Value must be greater than zero");
                        if (e.getMessage().equals("1"))
                            heightET.setError("Value must be greater than zero");
                        if (e.getMessage().equals("2")) {
                            weightET.setError("Value must be greater than zero");
                            heightET.setError("Value must be greater than zero");
                        }
                    }
                }
            }

            private boolean validateInput(EditText input) {
                String inputString = input.getText().toString();
                if (inputString.equals("")) {
                    input.setError("Missing value");
                    return false;
                }
                return true;
            }
        });


        findViewById(R.id.switch_units).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weightET = findViewById(R.id.weight_et);
                EditText heightET = findViewById(R.id.height_et);

                eraseInput(weightET);
                eraseInput(heightET);

                toggleUnitsColors();
            }

            private void eraseInput(EditText input) {
                input.setText("");
            }
        });
    }

//    private void restoreInstance() {
//        Intent intent = getIntent();
//        Boolean units = intent.getBooleanExtra("units", false);
//        String weightInput = intent.getStringExtra("weightInput");
//        String heightInput = intent.getStringExtra("heightInput");
//
//        EditText weightET = findViewById(R.id.weight_et);
//        EditText heightET = findViewById(R.id.height_et);
//        Switch unitsSwitch = findViewById(R.id.switch_units);
//
//        weightET.setText(weightInput);
//        heightET.setText(heightInput);
//        unitsSwitch.setChecked(units);
//        if (units)
//            setUnitsImperial();
//        else
//            setUnitsMetric();
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//
//        Switch unitsSwitch = findViewById(R.id.switch_units);
//        // Metric: true
//        // Imperial: false
//        Boolean units = !unitsSwitch.isChecked();
//        savedInstanceState.putBoolean("units", units);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        Boolean units = savedInstanceState.getBoolean("units");
//        Switch unitsSwitch = findViewById(R.id.switch_units);
//        if (units & unitsSwitch.isChecked())
//            unitsSwitch.setChecked(false);
//    }

    private void toggleUnitsColors() {
        Switch units = findViewById(R.id.switch_units);
        TextView metricLabel = findViewById(R.id.metric);
        TextView imperialLLabel = findViewById(R.id.imperial);

        if (!units.isChecked()) {
            metricLabel.setTextColor(getColor(R.color.colorAccent));
            imperialLLabel.setTextColor(getColor(R.color.Grey));

        }
        else {
            imperialLLabel.setTextColor(getColor(R.color.colorAccent));
            metricLabel.setTextColor(getColor(R.color.Grey));
        }
    }

    private void setUnitsImperial() {
        TextView metricLabel = findViewById(R.id.metric);
        TextView imperialLLabel = findViewById(R.id.imperial);
        imperialLLabel.setTextColor(getColor(R.color.colorAccent));
        metricLabel.setTextColor(getColor(R.color.Grey));
    }

    private void setUnitsMetric() {
        setUnitsImperial();
        toggleUnitsColors();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch  (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                startActivity(intent);
                break;
            case R.id.save:
                if (saveData()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Saved successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Save FAILED", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
        return true;
    }


    private boolean saveData() {
        SharedPreferences sharedPref = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);

        EditText weightET = findViewById(R.id.weight_et);
        EditText heightET = findViewById(R.id.height_et);
        Switch unitsSwitch = findViewById(R.id.switch_units);

        String weightInput = weightET.getText().toString();
        String heightInput = heightET.getText().toString();
        Boolean units = unitsSwitch.isChecked();

        boolean success;
        success = sharedPref.edit().putString("weight", weightInput).commit();
        success &= sharedPref.edit().putString("height", heightInput).commit();
        success &= sharedPref.edit().putBoolean("units", units).commit();

        return success;
    }


    private void retrieveSavedData() {
        SharedPreferences sharedPref = getSharedPreferences("com.example.app", Context.MODE_PRIVATE);

        EditText weightET = findViewById(R.id.weight_et);
        EditText heightET = findViewById(R.id.height_et);
        Switch unitsSwitch = findViewById(R.id.switch_units);

        weightET.setText(sharedPref.getString("weight", null));
        heightET.setText(sharedPref.getString("height", null));
        unitsSwitch.setChecked(sharedPref.getBoolean("units", false));

        if (unitsSwitch.isChecked())
            setUnitsImperial();
        else
            setUnitsMetric();
    }
}
