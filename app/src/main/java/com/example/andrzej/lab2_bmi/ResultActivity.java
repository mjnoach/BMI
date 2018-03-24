package com.example.andrzej.lab2_bmi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {


    private static final String RESULT_INTENT_EXTRA_BMI_VAL = MainActivity.RESULT_INTENT_EXTRA_BMI_VAL;
    private static final String RESULT_INTENT_EXTRA_BMI_CAT = MainActivity.RESULT_INTENT_EXTRA_BMI_CAT;

    private static final String DECIMAL_FORMAT_PATTERN = "#.##";


    TextView result;
    ImageButton home;


    private void initializeInterfaceHandles() {
        result = findViewById(R.id.result);
        home = findViewById(R.id.home);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initializeInterfaceHandles();

        Intent intent = getIntent();
        setBmiResult(intent);

        home.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }

        });
    }

    private void setBmiResult(Intent intent) {
        Double bmiValue = intent.getDoubleExtra(RESULT_INTENT_EXTRA_BMI_VAL,0);
        result.setText(new DecimalFormat(DECIMAL_FORMAT_PATTERN).format(bmiValue));

        Integer bmiCategory = intent.getIntExtra(RESULT_INTENT_EXTRA_BMI_CAT, 0);
        switch(bmiCategory) {
            case Bmi.UNDERWEIGHT:
                result.getRootView().setBackgroundColor(getColor(R.color.underweight));
                break;
            case Bmi.OVERWEIGHT:
                result.getRootView().setBackgroundColor(getColor(R.color.overweight));
                break;
            case Bmi.OBESE:
                result.getRootView().setBackgroundColor(getColor(R.color.obesity));
                break;
            default:
                result.getRootView().setBackgroundColor(getColor(R.color.normal));
                break;
        }
    }
}
