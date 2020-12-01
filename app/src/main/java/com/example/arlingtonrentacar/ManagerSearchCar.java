package com.example.arlingtonrentacar;

import java.util.Calendar;

public class ManagerSearchCar {
    private Calendar mStartDate;
    private String mStartTime;

    public ManagerSearchCar(Calendar startDate, String startTime){
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
