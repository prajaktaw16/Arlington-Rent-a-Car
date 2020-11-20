/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;

import com.example.arlingtonrentacar.database.AAReservationsDAO;

import java.util.LinkedList;

public class ControllerRenterReservationsSummary {
    private static final String LOG_TAG = ControllerRenterReservationsSummary.class.getSimpleName();
    private Context mContext;

    public ControllerRenterReservationsSummary(Context context){
        this.mContext = context;
    }

    public LinkedList<ReservationSummaryItem> generateReservationSummaryItemList(String startDateTime){
        LinkedList<ReservationSummaryItem> summaryItemList;
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(mContext);
        summaryItemList = reservationsDAO.getRenterReservationSummaryItems(startDateTime);
        return  summaryItemList;
    }
}
