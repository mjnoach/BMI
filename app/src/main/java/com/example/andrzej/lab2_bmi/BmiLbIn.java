package com.example.andrzej.lab2_bmi;


class BmiLbIn extends Bmi {

    BmiLbIn(Double w, Double h) {
        super(w, h);
    }

    @Override
    void calculateBmi() {
        bmi = weight / Math.pow(height, 2) * 703;
    }
}
