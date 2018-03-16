package com.example.andrzej.lab2_bmi;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        Double bmiValue = intent.getDoubleExtra("bmiValue",-1);
        TextView bmi = findViewById(R.id.result);
        bmi.setText(new DecimalFormat("#.##").format(bmiValue));

        Integer bmiCategory = intent.getIntExtra("bmiCategory", 2);
        switch(bmiCategory) {
            case -1:
                bmi.getRootView().setBackgroundColor(getColor(R.color.underweight));
                break;
            case 1:
                bmi.getRootView().setBackgroundColor(getColor(R.color.overweight));
                break;
            case 2:
                bmi.getRootView().setBackgroundColor(getColor(R.color.obesity));
                break;
            default:
                bmi.getRootView().setBackgroundColor(getColor(R.color.normal));
                break;
        }

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            private Intent intent;

            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(ResultActivity.this, MainActivity.class);
//                Boolean units = intent.getBooleanExtra("units", false);
//                String weightInput = intent.getStringExtra("weightInput");
//                String heightInput = intent.getStringExtra("heightInput");
//                homeIntent.putExtra("weightInput", weightInput);
//                homeIntent.putExtra("heightInput", heightInput);
//                homeIntent.putExtra("units", units);
                startActivity(homeIntent);
            }

            public View.OnClickListener init(Intent intent) {
                this.intent = intent;
                return this;
            }
        }.init(intent));
    }
}
