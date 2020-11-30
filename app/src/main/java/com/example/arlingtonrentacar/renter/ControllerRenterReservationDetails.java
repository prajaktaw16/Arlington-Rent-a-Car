/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarModel;
import com.example.arlingtonrentacar.CarName;
import com.example.arlingtonrentacar.database.AAReservationsDAO;
import com.example.arlingtonrentacar.database.CarsDAO;

import java.util.Calendar;
import java.util.LinkedList;

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

    public CarModel getSelectedCar(CarName carName){
        CarsDAO carsDAO = CarsDAO.getInstance(mContext);
        CarModel car = carsDAO.getCarByCarName(carName);
        return car;
    }

    public String upDateReservation(AAReservationModel reservation){
        String msg = "";
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(mContext);

        if(!reservationsDAO.updateReservation(reservation)){
            msg = "Failed to update reservation. Please try again later.";
        }else {
            msg = "Update Successful!";
        }
        return msg;
    }

    public String validateData(AAReservationModel reservation){
        String msg = "";
        Calendar today = Calendar.getInstance();
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(mContext);
        LinkedList<Calendar> allReservationDatesofCar = reservationsDAO.getAllReservationDatesOfCar(reservation.getCarName());

        if(reservation.getNumOfRiders() < AAUtil.CAPACITY_CAR_SMART || reservation.getNumOfRiders() > AAUtil.CAPACITY_CAR_MINIVAN){
            msg = "Reservation not updated. Invalid number of riders. It must be between " + AAUtil.CAPACITY_CAR_SMART + " and " + AAUtil.CAPACITY_CAR_MINIVAN;
        }else if(reservation.getNumOfRiders() > reservation.getCarCapacity()){
            msg = "Reservation not updated. Number of riders more than car capacity";
        }else if(reservation.getStartDateTime().before(today) || reservation.getEndDateTime().before(today)){
            msg = "Reservation not updated. Start/End date time can't be before today's date time.";
        }else if(reservation.getStartDateTime().after(reservation.getEndDateTime()) || reservation.getEndDateTime().before(reservation.getStartDateTime())){
            msg = "Reservation not updated. Start/End date not in order.";
        }else if(AAUtil.equalDateAndTime(reservation.getStartDateTime(), reservation.getEndDateTime())){
            msg = "Reservation not updated. Start/End date time can't be equal.";
        }else if(AAUtil.getdaysOfWeekBetweenDates(reservation.getStartDateTime(), reservation.getEndDateTime()).size() > AAUtil.PLANET_EARTH_WEEK_SIZE){
            msg = "Reservation not updated. Requested dates more than a week.";
        }else if(!AAUtil.schedulable(reservation.getStartDateTime(), reservation.getEndDateTime(), allReservationDatesofCar)){
            msg = "Reservation not updated. Time slot booked. Please choose different start/end date time or a different car.";
        }
        return msg;
    }
}
