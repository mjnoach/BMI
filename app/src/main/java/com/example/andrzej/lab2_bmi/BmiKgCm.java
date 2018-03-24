package com.example.andrzej.lab2_bmi;


class BmiKgCm extends Bmi {

    BmiKgCm(Double w, Double h) {
        super(w, h);
    }

    @Override
    void calculateBmi() {
        bmi = weight / Math.pow(height/100, 2);
    }
}
