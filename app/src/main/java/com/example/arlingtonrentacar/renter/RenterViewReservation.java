/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import com.example.arlingtonrentacar.AAUtil;

import java.util.Calendar;

public class RenterViewReservation {
    private static final String LOG_TAG = RenterViewReservation.class.getSimpleName();

    private Calendar mStartDate;
    private String mStartTime;

    public RenterViewReservation(Calendar startDate, String startTime){
        mStartDate = startDate;
        mStartTime = startTime;
    }

    public boolean validData(){
        boolean result = true;
        if(mStartTime.equals(AAUtil.EMPTYSTR)){
            result = false;
        }else if(mStartDate == null){
            result = false;
        }
        return result;
    }
}
