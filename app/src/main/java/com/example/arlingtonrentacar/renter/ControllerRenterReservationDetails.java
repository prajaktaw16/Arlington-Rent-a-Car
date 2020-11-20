/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.database.AAReservationsDAO;

public class ControllerRenterReservationDetails {
    private static final String LOG_TAG = ControllerRenterReservationDetails.class.getSimpleName();
    private Context mContext;

    public ControllerRenterReservationDetails(Context context){
        mContext = context;

    }

    public AAReservationModel getReservationByID(String reservationID){
        AAReservationModel reservationModel;
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(mContext);
        reservationModel = reservationsDAO.getReservationByID(reservationID);
        return reservationModel;
    }

    public boolean cancelReservation(String reservationID){
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(mContext);
        if(reservationsDAO.deleteReservation(reservationID)){
            return true;
        }else{
            return false;
        }
    }
}
