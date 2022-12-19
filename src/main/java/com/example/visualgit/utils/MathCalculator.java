package com.example.visualgit.utils;

import java.text.ParseException;

public interface MathCalculator<T> {
    int operator(T obj) throws ParseException;
}
