package com.example.arlingtonrentacar;

import java.util.Calendar;

public class AAReservationModel {
    private static final String LOG_TAG = AAReservationModel.class.getSimpleName();
    private String reservationID;
    private String username;
    private String lastName;
    private String firstName;
    private CarName carName;
    private int carCapacity;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private int numOfRiders;
    private double totalPrice;
    private int gps;
    private int xm;
    private int onStar;
    private int aaaMemStatus;

    public AAReservationModel() {
    }

    public AAReservationModel(String reservationID, String username, String lastName, String firstName, CarName carName, int carCapacity, Calendar startDateTime, Calendar endDateTime, int numOfRiders, double totalPrice, int gps, int xm, int onStar, int aaaMemStatus) {
        this.reservationID = reservationID;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.carName = carName;
        this.carCapacity = carCapacity;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numOfRiders = numOfRiders;
        this.totalPrice = totalPrice;
        this.gps = gps;
        this.xm = xm;
        this.onStar = onStar;
        this.aaaMemStatus = aaaMemStatus;
    }

    public boolean validReservationDateTime(Calendar otherStartDateTime, Calendar otherEndDateTime){
        boolean result;
        if(otherStartDateTime.before(this.startDateTime) && otherEndDateTime.before(this.startDateTime)){
            result = true;
        }else if(otherStartDateTime.after(this.endDateTime) && otherEndDateTime.after(this.endDateTime)){
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public CarName getCarName() {
        return carName;
    }

    public void setCarName(CarName carName) {
        this.carName = carName;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    public Calendar getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Calendar startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Calendar getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Calendar endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getNumOfRiders() {
        return numOfRiders;
    }

    public void setNumOfRiders(int numOfRiders) {
        this.numOfRiders = numOfRiders;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getGps() {
        return gps;
    }

    public void setGps(int gps) {
        this.gps = gps;
    }

    public int getXm() {
        return xm;
    }

    public void setXm(int xm) {
        this.xm = xm;
    }

    public int getOnStar() {
        return onStar;
    }

    public void setOnStar(int onStar) {
        this.onStar = onStar;
    }

    public int getAaaMemStatus() {
        return aaaMemStatus;
    }

    public void setAaaMemStatus(int aaaMemStatus) {
        this.aaaMemStatus = aaaMemStatus;
    }
}
