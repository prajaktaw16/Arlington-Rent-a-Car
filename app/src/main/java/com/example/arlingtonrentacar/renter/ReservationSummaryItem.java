/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.arlingtonrentacar.AAUtil;

public class ReservationSummaryItem implements Parcelable {
    private int carNumber;
    private int carCapacity;
    private String carName;
    private String startDateTime;
    private String endDateTime;
    private double totalPrice;
    private String reservationID;

    public ReservationSummaryItem(){

    }

    public ReservationSummaryItem(String reservationID, int carNumber, int carCapacity, String carName, String startDateTime, String endDateTime, double totalPrice) {
        this.carNumber = carNumber;
        this.carName = carName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.totalPrice = totalPrice;
        this.carCapacity = carCapacity;
        this.reservationID = reservationID;
    }

    @Override
    public String toString() {
        return "Car Number: " + carNumber +
                "\nCar Name: " + carName +
                "\nStart Date Time: " + startDateTime +
                "\nEnd Date Time: " + endDateTime +
                "\nTotal Price: " + AAUtil.getAmountInCurrency(totalPrice, AAUtil.USD_CURRENCY_FORMAT) +
                "\nReservation ID: " + reservationID;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double gettotalPrice() {
        return totalPrice;
    }

    public void settotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.carNumber);
        out.writeInt(this.carCapacity);
        out.writeString(this.carName);
        out.writeString(this.startDateTime);
        out.writeString(this.endDateTime);
        out.writeDouble(this.totalPrice);
        out.writeString(this.reservationID);
    }

    public static final Parcelable.Creator<ReservationSummaryItem> CREATOR
            = new Parcelable.Creator<ReservationSummaryItem>(){
      public ReservationSummaryItem createFromParcel(Parcel in){
          return new ReservationSummaryItem(in);
      }

      public ReservationSummaryItem[] newArray(int size){
          return new ReservationSummaryItem[size];
      }
    };

    private ReservationSummaryItem(Parcel in){
        this.carNumber = in.readInt();
        this.carCapacity = in.readInt();
        this.carName = in.readString();
        this.startDateTime = in.readString();
        this.endDateTime = in.readString();
        this.totalPrice = in.readDouble();
        this.reservationID = in.readString();
    }
}
