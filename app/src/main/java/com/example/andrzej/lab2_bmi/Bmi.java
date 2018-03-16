package com.example.andrzej.lab2_bmi;


abstract class Bmi {

    private static final Integer UNDERWEIGHT = -1;
    private static final Integer NORMAL = 0;
    private static final Integer OVERWEIGHT = 1;
    private static final Integer OBESE = 2;

    private static final Double UNDERWEIGHT_UPPER_BOUND = 18.5;
    private static final Double NORMAL_UPPER_BOUND = 25.0;
    private static final Double OVERWEIGHT_UPPER_BOUND = 30.0;


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
            throw new IllegalArgumentException("2");
        if (weight == 0)
            throw new IllegalArgumentException("0");
        if (height == 0)
            throw new IllegalArgumentException("1");
    }

    abstract void calculateBmi();

    Double getBmi() {
        return bmi;
    }

    private void setCategory() {
        if (bmi < UNDERWEIGHT_UPPER_BOUND)
            category = UNDERWEIGHT;
        else if (bmi >= UNDERWEIGHT_UPPER_BOUND & bmi < NORMAL_UPPER_BOUND) // CZY TU MUSI BYC OPERATOR BINARNY AND ZEBY WYLICZYLO OBIE WARTOSCI? CZY W PRZYPADKU PORWOWNYWANIA I TAK WYLICZA WSZYSTKIE?
            category = NORMAL;
        else if (bmi >= NORMAL_UPPER_BOUND & bmi < OVERWEIGHT_UPPER_BOUND)
            category = OVERWEIGHT;
        else
            category = OBESE;
    }

    Integer getCategory() { return category; }
}