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

    public boolean validRequestCarFormData(){
        final int WEEK_LENGTH = 7;
        final int MAX_NUM_OF_RIDERS = 9;
        boolean result = true;
        Calendar today = Calendar.getInstance();
        if(this.numOfRiders.equals(AAUtil.EMPTYSTR)|| !AAUtil.isNumeric(this.numOfRiders)){
            result = false;
        }else if(Integer.parseInt(this.numOfRiders) > MAX_NUM_OF_RIDERS){
            result = false;
        }else if(this.startDate.before(today) || endDate.before(today)){
            result = false;
        }else if(AAUtil.equalDateAndTime(this.startDate, this.endDate)){
            result = false;
        }else if(this.startDate.after(this.endDate) || this.endDate.before(this.startDate)){
            result = false;
        }else if(AAUtil.getdaysOfWeekBetweenDates(this.startDate, this.endDate).size() > WEEK_LENGTH){
            result = false;
        }
        return result;
    }

    public String getNumOfRiders() {
        return numOfRiders;
    }

}
