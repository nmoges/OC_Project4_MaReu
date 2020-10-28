package com.openclassrooms.mareu.utils;

import java.util.Comparator;

/**
 * This class is used to sort the Meeting list according to their date and time
 * in the ascending order
 */
public class TimeComparator implements Comparator<String> {

    private int comparatorResult;

    public TimeComparator() {

        this.comparatorResult = 0;
    }

    @Override
    public int compare(String startHour, String endHour) {

        int hourStartHour = Integer.parseInt(startHour.substring(0,2));
        int hourEndHour = Integer.parseInt(endHour.substring(0,2));

        if (hourStartHour > hourEndHour) {
            comparatorResult = -1; // ERROR : Start hour can't be superior to End hour
        }
        else if (hourStartHour < hourEndHour) {
            comparatorResult = 1; // OK
        }
        else { // If Start hour == End hour, must compare the minutes
            int minutesStartHour = Integer.parseInt(startHour.substring(3));
            int minutesEndHour = Integer.parseInt(endHour.substring(3));
            if (minutesStartHour < minutesEndHour) {
                comparatorResult = 1; // OK
            }
            else {
                comparatorResult = -1; // ERROR
            }
        }
        return comparatorResult;
    }
}
