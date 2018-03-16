package com.example.andrzej.lab2_bmi;

/**
 * Created by andrzej on 08/03/2018.
 */

public class BmiLbIn extends Bmi {

    BmiLbIn(Double w, Double h) {
        super(w, h);
    }

    @Override
    void calculateBmi() {
        bmi = weight / Math.pow(height, 2) * 703;
    }
}
