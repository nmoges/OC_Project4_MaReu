package com.openclassrooms.mareu.utils;

public class DateAndTimeConverter {

    public static String timeConverter(int hour, int minutes){
        String convertedTime = "";

        // HH
        if(hour < 10){ convertedTime = convertedTime + "0" + hour + ":"; }
        else{ convertedTime = convertedTime + hour + ":";}
        // MM
        if(minutes < 10){ convertedTime = convertedTime + "0" + minutes; }
        else{ convertedTime = convertedTime + minutes; }

        return convertedTime;
    }

    public static String dateConverter(int year, int month, int day){
        String convertedDate = "";

        // JJ
        if(day < 10){ convertedDate = "0" + convertedDate + day;}
        else{convertedDate = convertedDate + day; }
        // MM
        if(month+1 < 10){ convertedDate = convertedDate +  "/0" + (month+1) + "/"; }
        else{ convertedDate = convertedDate + "/" + (month+1) + "/"; }
        // AA
        convertedDate = convertedDate + year;

        return convertedDate;
    }
}
