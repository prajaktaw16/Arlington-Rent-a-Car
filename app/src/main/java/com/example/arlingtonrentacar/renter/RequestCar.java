/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import com.example.arlingtonrentacar.AAUtil;
import java.util.Calendar;


public class RequestCar{
    private static String LOG_TAG = RequestCar.class.getSimpleName();
    private String numOfRiders;
    private Calendar startDate;
    private Calendar endDate;

    public RequestCar(String numOfRiders, Calendar startDate, Calendar endDate) {
        this.numOfRiders = numOfRiders;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String validRequestCarFormData(){
        String result = "";
        Calendar today = Calendar.getInstance();
        if(this.numOfRiders.equals(AAUtil.EMPTYSTR)|| !AAUtil.isNumeric(this.numOfRiders)){
            result = "Invalid number of riders. It can't be empty or non-numeric.";
        }else if(Integer.parseInt(this.numOfRiders) > AAUtil.CAPACITY_CAR_MINIVAN || Integer.parseInt(this.numOfRiders) < AAUtil.CAPACITY_CAR_SMART){
            result = "Invalid number of riders. It must be between " + AAUtil.CAPACITY_CAR_SMART + " and " + AAUtil.CAPACITY_CAR_MINIVAN;
        }else if(AAUtil.getdaysOfWeekBetweenDates(this.startDate, this.endDate).size() > AAUtil.PLANET_EARTH_WEEK_SIZE){
            result = "Invalid data: can't rent for more than a week.";
        }else if(this.startDate.before(today) || endDate.before(today)){
            result = "Invalid data: Start/End date time can't be before today's date time.";
        }else if(AAUtil.equalDateAndTime(this.startDate, this.endDate)){
            result = "Invalid data: Start/End date time can't be equal.";
        }else if(this.startDate.after(this.endDate) || this.endDate.before(this.startDate)){
            result = "Invalid data: Start/End date not in chronological order.";
        }
        return result;
    }

    public String getNumOfRiders() {
        return numOfRiders;
    }

}
