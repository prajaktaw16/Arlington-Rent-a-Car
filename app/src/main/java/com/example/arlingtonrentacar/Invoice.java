package com.example.arlingtonrentacar;

import java.util.ArrayList;
import java.util.Calendar;

public class Invoice {
    private static final String LOG_TAG = Invoice.class.getSimpleName();
    private final double TAX = AAUtil.TAX;
    private final double DISCOUNT = AAUtil.DISCOUNT;
    private CarModel car;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private boolean userIsAAAMember;
    private boolean gpsSelected;
    private boolean onStarSelected;
    private boolean xmSelected;

    public Invoice(CarModel car, Calendar startDateTime, Calendar endDateTime){
        this.car = car;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public CarModel getCar() {
        return car;
    }

    public void setCar(CarModel car) {
        this.car = car;
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

    public boolean isUserIsAAAMember() {
        return userIsAAAMember;
    }

    public void setUserIsAAAMember(boolean userIsAAAMember) {
        this.userIsAAAMember = userIsAAAMember;
    }

    public boolean isGpsSelected() {
        return gpsSelected;
    }

    public void setGpsSelected(boolean gpsSelected) {
        this.gpsSelected = gpsSelected;
    }

    public boolean isOnStarSelected() {
        return onStarSelected;
    }

    public void setOnStarSelected(boolean onStarSelected) {
        this.onStarSelected = onStarSelected;
    }

    public boolean isXmSelected() {
        return xmSelected;
    }

    public void setXmSelected(boolean xmSelected) {
        this.xmSelected = xmSelected;
    }

    public double calculateBasicTotalCost(){
        final int WEEK_LENGTH = 7;
        double totalCost = 0.0;
        int numOfWeekDays;
        int numOfWeekEnds;
        ArrayList<Integer> reservationDays = AAUtil.getdaysOfWeekBetweenDates(this.startDateTime, this.endDateTime);
        if(reservationDays.size() == WEEK_LENGTH){
            totalCost = this.car.getRateWeek() * AAUtil.TAX;
        }else{
            numOfWeekDays = AAUtil.getNumOfWeekdays(reservationDays);
            numOfWeekEnds = AAUtil.getNumOfWeekends(reservationDays);
            totalCost = ((numOfWeekDays * this.car.getRateWeekDay()) + (numOfWeekEnds * this.car.getRateWeekEnd())) * AAUtil.TAX;
        }
        return totalCost;
    }

    public double calculateTotalCost(){
        final int WEEK_LENGTH = 7;
        double totalCost = 0.0;
        int numOfWeekDays;
        int numOfWeekEnds;
        ArrayList<Integer> reservationDays = AAUtil.getdaysOfWeekBetweenDates(this.startDateTime, this.endDateTime);
        if(reservationDays.size() == WEEK_LENGTH){
            totalCost = this.car.getRateWeek();
            totalCost += calculateExtraCost(WEEK_LENGTH);
            totalCost = applyDiscount(totalCost);
            totalCost = aaplyTax(totalCost);
        }else{
            numOfWeekDays = AAUtil.getNumOfWeekdays(reservationDays);
            numOfWeekEnds = AAUtil.getNumOfWeekends(reservationDays);
            totalCost = ((numOfWeekDays * this.car.getRateWeekDay()) + (numOfWeekEnds * this.car.getRateWeekEnd()));
            totalCost += calculateExtraCost(reservationDays.size());
            totalCost = applyDiscount(totalCost);
            totalCost = aaplyTax(totalCost);
        }
        return totalCost;
    }

    private double calculateExtraCost(int numOfDays){
        double extraCost = 0.0;
        if(this.gpsSelected){
            extraCost += this.car.getRateGPS() * numOfDays;
        }
        if(this.xmSelected){
            extraCost += this.car.getRateXM() * numOfDays;
        }
        if(this.onStarSelected){
            extraCost += this.car.getRateOnStar() * numOfDays;
        }
        return extraCost;
    }

    private double applyDiscount(double amount){
        if(this.userIsAAAMember){
            amount = amount * DISCOUNT;
        }
        return amount;
    }

    private double aaplyTax(double amount){
        amount *= TAX;
        return amount;
    }


}
