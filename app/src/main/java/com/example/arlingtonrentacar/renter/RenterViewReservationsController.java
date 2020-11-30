/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;

import java.util.Calendar;

public class RenterViewReservationsController {
    private static final String LOG_TAG = RenterViewReservationsController.class.getSimpleName();
    private Context mContext;

    public RenterViewReservationsController(Context context){
        mContext = context;
    }

    public void viewMyReservations(Calendar startDate, String startTime){
        RenterViewReservation renterViewReservation = new RenterViewReservation(startDate, startTime);
        if(renterViewReservation.validData()){
            Calendar startDateTime = AAUtil.getCalendarDateWithTime(startDate, startTime);
            addDataToSession(AAUtil.formatDate(startDateTime, AAUtil.DATABASE_DATE_TIME_FORMAT));
            Intent intent = new Intent(mContext, RenterReservationsSummaryActivity.class);
            mContext.startActivity(intent);
        }else{
            Toast.makeText(mContext, "Invalid Data", Toast.LENGTH_LONG).show();
        }
    }

    private void addDataToSession(String startDateTime){
        SharedPreferences sessionPrefs = AAUtil.getLogInSession(mContext);
        SharedPreferences.Editor session = sessionPrefs.edit();
        session.putString(mContext.getString(R.string.sessionStartDateTimeViewReservationFormData), startDateTime);
        session.commit();
    }

}
