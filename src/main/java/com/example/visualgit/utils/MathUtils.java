package com.example.visualgit.utils;

import java.util.List;

public class MathUtils {
    private MathUtils(){}

    public static <T> double getAvg(List<T> objects,MathCalculator<T> mathCalculator){
        long sum=0;
        for (T object : objects) {
            sum += mathCalculator.operator(object);
        }
        return (double) sum/(double)objects.size();
    }

    public static <T> double getStandardDeviation(List<T> objects,MathCalculator<T> mathCalculator){
        double avg = getAvg(objects,mathCalculator);
        double sd = 0;
        for(T obj:objects){
            sd+=Math.pow(mathCalculator.operator(obj)-avg,2);
        }
        return Math.sqrt(sd);
    }

    public static <T> double getRange(List<T> objects,MathCalculator<T> mathCalculator){
        if(objects.isEmpty()) return 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for(T obj:objects){
            max = Math.max(max,mathCalculator.operator(obj));
            min = Math.min(min,mathCalculator.operator(obj));
        }
        return max-min;
    }

}
