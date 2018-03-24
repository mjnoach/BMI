package com.example.andrzej.lab2_bmi;

import org.junit.Test;

import static org.junit.Assert.*;


public class BmiKgCmTest {
    @Test
    public void calculateBmi() throws Exception {
        BmiKgCm bmi = new BmiKgCm(70.0, 170.0);
        Double bmiVal = bmi.getBmi();

        assertEquals(24.22, bmiVal, 2);
    }

    @Test
    public void getCategory() {
        Bmi bmi = new BmiKgCm(50.0, 170.0);
        int bmiCat = bmi.getCategory();
        assertEquals(-1, bmiCat);
    }

    @Test (expected = IllegalArgumentException.class)
    public void bmiInvalidInput() {
        new BmiKgCm(0.0, 170.0);
    }

}