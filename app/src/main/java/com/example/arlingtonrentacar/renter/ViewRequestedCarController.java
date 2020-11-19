package com.example.arlingtonrentacar.renter;

import android.content.Context;
import android.util.Log;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarModel;
import com.example.arlingtonrentacar.CarName;
import com.example.arlingtonrentacar.Invoice;
import com.example.arlingtonrentacar.database.AAReservationsDAO;
import com.example.arlingtonrentacar.database.CarsDAO;

import java.util.Calendar;
import java.util.LinkedList;

public class ViewRequestedCarController {
    private static final String LOG_TAG = ViewRequestedCarController.class.getSimpleName();
    private Context mvContext;

    public ViewRequestedCarController(Context context){
        this.mvContext = context;
    }

    public LinkedList<RequestCarSummaryItem> generateAvailableCarList(String numOfRider, String startDateTime, String endDateTime){
        LinkedList<RequestCarSummaryItem> availableCarList;
        LinkedList<CarModel> carModelList;
        CarsDAO carsDAO = CarsDAO.getInstance(this.mvContext);
        carModelList = carsDAO.getCarsByNumOfRiders(Integer.parseInt(numOfRider));
        availableCarList = getAvailableCars(carModelList, Integer.parseInt(numOfRider), startDateTime, endDateTime);
        return availableCarList;
    }

    public LinkedList<RequestCarSummaryItem> getAvailableCars(LinkedList<CarModel> carList, int numOfRiders, String startDateTime, String endDateTime){
        AAReservationsDAO reservationsDAO = AAReservationsDAO.getInstance(this.mvContext);
        LinkedList<RequestCarSummaryItem> carSummaryItemList = new LinkedList<RequestCarSummaryItem>();
        RequestCarSummaryItem carSummaryItem;
        Invoice invoice;
        LinkedList<AAReservationModel> reservationList;
        CarModel car;
        AAReservationModel reservation;
        Calendar calStartDateTime, calEndDateTime;
        calStartDateTime = AAUtil.databaseDateTimeToCalendar(startDateTime);
        calEndDateTime = AAUtil.databaseDateTimeToCalendar(endDateTime);
        int carNumber = 0;
        for(int i = 0; i < carList.size(); i++){
            car = carList.get(i);
            reservationList = reservationsDAO.getReservationsByCarName(car.getName());
            if(reservationList.size() == 0){
                carNumber++;
                invoice = new Invoice(car, calStartDateTime, calEndDateTime);
                carSummaryItem = createCarSummaryItem(startDateTime, endDateTime, car.getName(), carNumber, car.getCapacity(), numOfRiders, invoice.calculateBasicTotalCost());
                carSummaryItemList.add(carSummaryItem);
            }else{
                for(int j = 0; j < reservationList.size(); j++){
                    reservation = reservationList.get(j);
                    if(reservation.validReservationDateTime(calStartDateTime, calEndDateTime)){
                        carNumber++;
                        invoice = new Invoice(car, calStartDateTime, calEndDateTime);
                        carSummaryItem = createCarSummaryItem(startDateTime, endDateTime, car.getName(), carNumber, car.getCapacity(), numOfRiders, invoice.calculateBasicTotalCost());
                        carSummaryItemList.add(carSummaryItem);
                    }
                }
            }
        }
        return carSummaryItemList;
    }

    private RequestCarSummaryItem createCarSummaryItem(String startDateTime, String endDateTime, CarName carName, int carNumber, int capacity, int numOfRiders, double totalCost){
        RequestCarSummaryItem carSummaryItem = new RequestCarSummaryItem(startDateTime, endDateTime, AAUtil.carNameEnumToStr(carName), carNumber, capacity, numOfRiders, totalCost);
        return carSummaryItem;
    }
}
