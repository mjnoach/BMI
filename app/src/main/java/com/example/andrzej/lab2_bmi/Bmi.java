package com.example.andrzej.lab2_bmi;


abstract class Bmi {

    static final int UNDERWEIGHT = -1;
    private static final int NORMAL = 0;
    static final int OBESE = 2;
    static final int OVERWEIGHT = 1;

    private static final Double UNDERWEIGHT_UPPER_BOUND = 18.5;
    private static final Double NORMAL_UPPER_BOUND = 25.0;
    private static final Double OVERWEIGHT_UPPER_BOUND = 30.0;

    static final String ERR_WEIGHT = "0";
    static final String ERR_HEIGHT = "1";
    static final String ERR_W_H = "2";


    Double height, weight, bmi;
    private Integer category;

    Bmi(Double w, Double h) {
        weight = w;
        height = h;
        validateBmi();
        calculateBmi();
        setCategory();
    }

    private void validateBmi() throws IllegalArgumentException {
        if (weight == 0 && height == 0)
            throw new IllegalArgumentException(ERR_W_H);
        if (weight == 0)
            throw new IllegalArgumentException(ERR_WEIGHT);
        if (height == 0)
            throw new IllegalArgumentException(ERR_HEIGHT);
    }

    abstract void calculateBmi();

    Double getBmi() {
        return bmi;
    }

    private void setCategory() {
        if (bmi < UNDERWEIGHT_UPPER_BOUND)
            category = UNDERWEIGHT;
        else if (bmi >= UNDERWEIGHT_UPPER_BOUND & bmi < NORMAL_UPPER_BOUND)
            category = NORMAL;
        else if (bmi >= NORMAL_UPPER_BOUND & bmi < OVERWEIGHT_UPPER_BOUND)
            category = OVERWEIGHT;
        else
            category = OBESE;
    }

    Integer getCategory() { return category; }
}