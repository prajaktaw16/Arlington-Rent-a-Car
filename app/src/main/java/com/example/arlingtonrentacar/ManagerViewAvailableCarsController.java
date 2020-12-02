package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.CarsDAO;
import com.example.arlingtonrentacar.database.ReservationsDAO;

import java.util.ArrayList;
import java.util.Calendar;

public class ManagerViewAvailableCarsController {
    private Context mContext;
    private Cursor cursor,carCursor;
    private String start_Date_Time;
    ArrayList<SearchCarSummaryItem> carDetails = new ArrayList<>();
    SearchCarSummaryItem carDetailsObj;

    public ManagerViewAvailableCarsController(Context context){
        mContext = context;
    }

    public void searchCars(Calendar startDate, String startTime){
        ManagerSearchCar managerSearchCar = new ManagerSearchCar(startDate, startTime);
        if(managerSearchCar.validData()){
            Calendar startDateTime = AAUtil.getCalendarDateWithTime(startDate, startTime);
            addDataToSession(AAUtil.formatDate(startDateTime, AAUtil.DATABASE_DATE_TIME_FORMAT));
            ReservationsDAO reservationsDAO = ReservationsDAO.getInstance(mContext);
            start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
            carCursor = reservationsDAO.viewReservations(start_Date_Time);
            ArrayList<String> carNames = new  ArrayList<String>();
            CarsDAO carsDAO = CarsDAO.getInstance(mContext);
            Log.d("ManagerViewAvailableCarsController", "carCursor.getCount()):"+carCursor.getCount());
            if(carCursor.moveToFirst()){
                do{
                    carNames.add(carCursor.getString(carCursor.getColumnIndex("car_name")));
                }while(carCursor.moveToNext());
                cursor = carsDAO.searchAvailableCars(carNames.get(0));
                Log.d("ManagerViewAvailableCarsController", "carNames:"+carNames);
                //Toast toast = Toast.makeText(mContext, "No Cars found for this date and time", Toast.LENGTH_SHORT);
                //toast.show();
            }
            else{
                cursor = carsDAO.searchAllCars();
            }
            if(cursor.moveToFirst()){
                Log.d("ManagerViewAvailableCarsController", "carCursor.getCount():"+cursor.getCount());
                do{
                    Log.d("ManagerViewAvailableCarsController", "cursor.position:"+cursor.getPosition());
                    carDetailsObj = new SearchCarSummaryItem();
                    carDetailsObj.setCarName(cursor.getString(cursor.getColumnIndex("car_name")));
                    carDetailsObj.setCarCapacity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("capacity"))));
                    carDetailsObj.setCarStatus(cursor.getString(cursor.getColumnIndex("car_status")));
                    carDetailsObj.setRateWeekDay(Double.parseDouble(cursor.getString(cursor.getColumnIndex("weekday_rate"))));
                    carDetailsObj.setRateWeekEnd(Double.parseDouble(cursor.getString(cursor.getColumnIndex("weekend_rate"))));
                    carDetailsObj.setRateWeek(Double.parseDouble(cursor.getString(cursor.getColumnIndex("weekly_rate"))));
                    carDetailsObj.setRateGPS(Double.parseDouble(cursor.getString(cursor.getColumnIndex("gps_rate_perday"))));
                    carDetailsObj.setRateXM(Double.parseDouble(cursor.getString(cursor.getColumnIndex("siriusxm_rate_perday"))));
                    carDetailsObj.setRateOnStar(Double.parseDouble(cursor.getString(cursor.getColumnIndex("onstar_rate_perday"))));
                    String totalPrice = cursor.getString(cursor.getColumnIndex("weekly_rate"))+cursor.getString(cursor.getColumnIndex("gps_rate_perday"))+cursor.getString(cursor.getColumnIndex("siriusxm_rate_perday"))+ cursor.getString(cursor.getColumnIndex("onstar_rate_perday")) ;
                    Double costPerDay = Double.parseDouble(totalPrice);
                    carDetailsObj.setTotalPrice(costPerDay);
                    carDetailsObj.setCarNumber(cursor.getPosition()+1);
                    carDetails.add(carDetailsObj);
                }while(cursor.moveToNext());
                Log.d("ManagerViewAvailableCarsController", "carDetails:"+carDetails);
            }
                Intent intent = new Intent(mContext, ManagerViewAvailableCarSummaryScreen.class);
                intent.putExtra("carDetails", carDetails);
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
