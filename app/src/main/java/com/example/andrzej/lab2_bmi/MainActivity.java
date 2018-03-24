package com.example.andrzej.lab2_bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private static final String MISSING_VAL = "Missing value";
    private static final String WRONG_VAL = "Value must be greater than zero";
    private static final String ERR_WEIGHT = Bmi.ERR_WEIGHT;
    private static final String ERR_HEIGHT = Bmi.ERR_HEIGHT;
    private static final String ERR_W_H = Bmi.ERR_W_H;

    public static final String RESULT_INTENT_EXTRA_BMI_VAL = "bmiValue";
    public static final String RESULT_INTENT_EXTRA_BMI_CAT = "bmiCategory";

    private static final String TOAST_SAVE_SUCCESS = "Saved successfully!";
    private static final String TOAST_SAVE_FAIL = "Save FAILED";

    private static final String SHARED_PREF_NAME = "com.example.app";
    private static final String SHARED_PREF_WEIGHT = "weight";
    private static final String SHARED_PREF_HEIGHT = "height";
    private static final String SHARED_PREF_UNITS = "units";


    private EditText weightET;
    private EditText heightET;
    private Switch unitsSwitch;
    private Button calculate;
    private TextView metricLabel;
    private TextView imperialLLabel;


    private void initializeInterfaceHandles() {
        weightET = findViewById(R.id.weight_et);
        heightET = findViewById(R.id.height_et);
        unitsSwitch = findViewById(R.id.switch_units);
        calculate = findViewById(R.id.calculate);
        metricLabel = findViewById(R.id.metric);
        imperialLLabel = findViewById(R.id.imperial);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeInterfaceHandles();
        laodSavedData();
        initializeCalculateBtn();

        unitsSwitch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                eraseInput(weightET);
                eraseInput(heightET);

                toggleUnitsColors();
            }


            private void eraseInput(EditText input) {
                input.setText("");
            }
        });
    }


    private void initializeCalculateBtn() {
        calculate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // Bitwise AND operator used to check both conditions (which set errors if arguments are invalid)
                if (validateInput(weightET) & validateInput(heightET)) {
                    String weightInput = weightET.getText().toString();
                    String heightInput = heightET.getText().toString();

                    Boolean units = unitsSwitch.isChecked();

                    processInput(weightInput ,heightInput, units);
                }
            }


            private void processInput(String weight, String height, Boolean units) {
                try {
                    Bmi bmi = calcualateBmi(weight, height, units);
                    Double bmiValue = bmi.getBmi();
                    Integer bmiCategory = bmi.getCategory();

                    startResultActivity(MainActivity.this, bmiValue, bmiCategory);
                }
                catch (IllegalArgumentException e) {
                    handleErrors(e);
                }
            }


            private void handleErrors(IllegalArgumentException e) {
                if (e.getMessage().equals(ERR_WEIGHT) || e.getMessage().equals(ERR_W_H))
                    weightET.setError(WRONG_VAL);
                if (e.getMessage().equals(ERR_HEIGHT) || e.getMessage().equals(ERR_W_H))
                    heightET.setError(WRONG_VAL);
            }


            private void startResultActivity(Context context, Double bmiValue, int bmiCategory) {
                Intent starter = new Intent(context, ResultActivity.class);
                starter.putExtra(RESULT_INTENT_EXTRA_BMI_VAL, bmiValue);
                starter.putExtra(RESULT_INTENT_EXTRA_BMI_CAT, bmiCategory);
                context.startActivity(starter);
            }


            private Bmi calcualateBmi(String weight, String height, Boolean units) {
                Bmi bmi;
                if (!units)
                    bmi = new BmiKgCm(Double.valueOf(weight), Double.valueOf(height));
                else
                    bmi = new BmiLbIn(Double.valueOf(weight), Double.valueOf(height));
                return bmi;
            }


            private boolean validateInput(EditText input) {
                String inputString = input.getText().toString();
                if (inputString.equals("") || inputString.equals(".")) {
                    input.setError(MISSING_VAL);
                    return false;
                }
                return true;
            }
        });

    }


    private void toggleUnitsColors() {
        if (!unitsSwitch.isChecked()) {
            metricLabel.setTextColor(getColor(R.color.colorAccent));
            imperialLLabel.setTextColor(getColor(R.color.Grey));

        }
        else {
            imperialLLabel.setTextColor(getColor(R.color.colorAccent));
            metricLabel.setTextColor(getColor(R.color.Grey));
        }
    }


    private void setUnitsImperial() {
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
                    Toast toast = Toast.makeText(getApplicationContext(), TOAST_SAVE_SUCCESS, Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), TOAST_SAVE_FAIL, Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }
        return true;
    }


    private boolean saveData() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String weightInput = weightET.getText().toString();
        String heightInput = heightET.getText().toString();
        Boolean units = unitsSwitch.isChecked();

        boolean success;
        success = sharedPref.edit().putString(SHARED_PREF_WEIGHT, weightInput).commit();
        success &= sharedPref.edit().putString(SHARED_PREF_HEIGHT, heightInput).commit();
        success &= sharedPref.edit().putBoolean(SHARED_PREF_UNITS, units).commit();

        return success;
    }


    private void laodSavedData() {
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        weightET.setText(sharedPref.getString(SHARED_PREF_WEIGHT, null));
        heightET.setText(sharedPref.getString(SHARED_PREF_HEIGHT, null));
        unitsSwitch.setChecked(sharedPref.getBoolean(SHARED_PREF_UNITS, false));

        if (unitsSwitch.isChecked())
            setUnitsImperial();
        else
            setUnitsMetric();
    }
}
