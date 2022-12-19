package com.example.visualgit.utils;

import com.example.visualgit.entity.Release;

import java.text.ParseException;
import java.util.List;

public class MathUtils {
    private MathUtils(){}

    public static <T> double getAvg(List<T> objects,MathCalculator<T> mathCalculator) throws ParseException {
        long sum=0;
        for (T object : objects) {
            sum += mathCalculator.operator(object);
        }
        return (double) sum/(double)objects.size();
    }

    public static <T> double getStandardDeviation(List<T> objects,MathCalculator<T> mathCalculator) throws ParseException {
        double avg = getAvg(objects,mathCalculator);
        double sd = 0;
        for(T obj:objects){
            sd+=Math.pow(mathCalculator.operator(obj)-avg,2);
        }
        return Math.sqrt(sd);
    }

    public static <T> double getRange(List<T> objects,MathCalculator<T> mathCalculator) throws ParseException {
        if(objects.isEmpty()) return 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        for(T obj:objects){
            max = Math.max(max,mathCalculator.operator(obj));
            min = Math.min(min,mathCalculator.operator(obj));
        }
        return max-min;
    }


    public static void sort(List<Release> list){
        list.sort(((o1, o2) -> {
            String time1=o1.getRelease_time();
            String time2=o2.getRelease_time();
            time1 = dealDate(time1);
            time2 = dealDate(time2);

            if(Integer.parseInt(time1.substring(0,4)) < Integer.parseInt(time2.substring(0,4))) return -1;
            else if(Integer.parseInt(time1.substring(0,4)) > Integer.parseInt(time2.substring(0,4))) return 1;
            else {
                if(Integer.parseInt(time1.substring(4,6)) < Integer.parseInt(time2.substring(4,6))) return -1;
                else if(Integer.parseInt(time1.substring(4,6)) > Integer.parseInt(time2.substring(4,6))) return 1;
                else {
                    if(Integer.parseInt(time1.substring(6,8)) < Integer.parseInt(time2.substring(6,8))) return -1;
                    else if(Integer.parseInt(time1.substring(6,8)) > Integer.parseInt(time2.substring(6,8))) return 1;
                    else {
                        if(Integer.parseInt(time1.substring(8,10)) < Integer.parseInt(time2.substring(8,10))) return -1;
                        else if(Integer.parseInt(time1.substring(8,10)) > Integer.parseInt(time2.substring(8,10))) return 1;
                        else {
                            if(Integer.parseInt(time1.substring(10,12)) < Integer.parseInt(time2.substring(10,12))) return -1;
                            else if(Integer.parseInt(time1.substring(10,12)) > Integer.parseInt(time2.substring(10,12))) return 1;
                            else {
                                return Integer.compare(Integer.parseInt(time1.substring(12, 14)), Integer.parseInt(time2.substring(12, 14)));
                            }
                        }
                    }
                }
            }


        }));
    }

    public static String dealDate(String date){
        date = date.replaceAll(":","");
        date = date.replaceAll("-","");
        date = date.replaceAll("T","");
        date = date.replaceAll("Z","");
        return date;
    }


}
