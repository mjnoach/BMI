package com.example.andrzej.lab2_bmi;

import org.junit.Test;

import static org.junit.Assert.*;


public class BmiLbInTest {
    @Test
    public void calculateBmi() throws Exception {
        Bmi bmi = new BmiLbIn(120.0, 60.0);
        Double bmiVal = bmi.getBmi();
        assertEquals(23.43, bmiVal, 2);
    }

}