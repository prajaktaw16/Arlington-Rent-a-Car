package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.CarsDAO;
import com.example.arlingtonrentacar.database.ReservationsDAO;
import com.example.arlingtonrentacar.renter.RenterReservationsSummaryActivity;
import com.example.arlingtonrentacar.renter.RenterViewReservation;

import java.util.ArrayList;
import java.util.Calendar;

public class ManagerSearchCarsController {
    private Context mContext;
    private Cursor cursor;
    private String start_Date_Time;
    ArrayList<SearchCarSummaryItem> carDetails = new ArrayList<>();
    SearchCarSummaryItem carDetailsObj;
    private CarModel car;

    public ManagerSearchCarsController(Context context){
        mContext = context;
    }

    public void searchCars(String carName, Calendar startDate, String startTime){
        ManagerSearchCar managerSearchCar = new ManagerSearchCar(startDate, startTime);
        if(managerSearchCar.validData()){
            Calendar startDateTime = AAUtil.getCalendarDateWithTime(startDate, startTime);
            addDataToSession(AAUtil.formatDate(startDateTime, AAUtil.DATABASE_DATE_TIME_FORMAT));
            ReservationsDAO reservationsDAO = ReservationsDAO.getInstance(mContext);
            start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
            cursor = reservationsDAO.viewReservationsForCar(carName,start_Date_Time);
            Log.d("ManagerSearchcarSummary", "cursor.getCount():"+cursor.getCount());
            if(cursor.getCount() >0){
                Log.d("ManagerSearchcarSummary", "cursor.getCount():"+cursor.getCount());
                Toast toast = Toast.makeText(mContext, "No Cars found for this date and time", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                CarsDAO carsDao = CarsDAO.getInstance(mContext);
                CarName carname = AAUtil.carNameStrToEnum(carName);
                car = carsDao.getCarByCarName(carname);

                carDetailsObj = new SearchCarSummaryItem();
                carDetailsObj.setCarName(car.getName().toString());
                carDetailsObj.setCarCapacity(car.getCapacity());
                carDetailsObj.setCarStatus(car.getStatus());
                carDetailsObj.setRateWeekDay(car.getRateWeekDay());
                carDetailsObj.setRateWeekEnd(car.getRateWeekEnd());
                carDetailsObj.setRateWeek(car.getRateWeek());
                carDetailsObj.setRateGPS(car.getRateGPS());
                carDetailsObj.setRateXM(car.getRateXM());
                carDetailsObj.setRateOnStar(car.getRateOnStar());
                Double totalPrice = car.getRateWeekDay()+car.getRateWeekEnd()+car.getRateWeek()+ car.getRateGPS()+ car.getRateXM()+ car.getRateOnStar();
                carDetailsObj.setTotalPrice(totalPrice);
                //carDetailsObj.setCarNumber(1212);
                carDetails.add(carDetailsObj);
                Intent intent = new Intent(mContext, ManagerSearchCarSummaryScreen.class);
                intent.putExtra("carDetails", carDetails);
                mContext.startActivity(intent);
            }

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
