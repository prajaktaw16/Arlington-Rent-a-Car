/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.arlingtonrentacar.AAUtil;

public class RequestCarSummaryItem implements Parcelable {
    private String carName;
    private String startDateTime;
    private String endDateTime;
    private int carNumber;
    private int capacity;
    private double totalCost;
    private int numOfRiders;

    public RequestCarSummaryItem(String startDateTime, String endDateTime, String carName, int carNumber, int capacity, int numOfRiders, double totalCost) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.capacity = capacity;
        this.totalCost = totalCost;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.numOfRiders = numOfRiders;

    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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

    public int getNumOfRiders() {
        return numOfRiders;
    }

    public void setNumOfRiders(int numOfRiders) {
        this.numOfRiders = numOfRiders;
    }

    public String toString(){
        return "Car Number: " + this.carNumber +
                "\nCar Name: " + this.carName +
                "\nCar Capacity: " + this.capacity +
                "\nTotal Price: " + AAUtil.getAmountInCurrency(this.totalCost, AAUtil.USD_CURRENCY_FORMAT);
    }

    public String allFieldsToString(){
        String result = this.toString();
        result += "\nStart Date Time: " + AAUtil.convertDBDateToTargetFormat(this.startDateTime, AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT) + "\n";
        result += "End Date Time: " + AAUtil.convertDBDateToTargetFormat(this.endDateTime, AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT);
        result += "\nNum of Riders: " + this.numOfRiders;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(startDateTime);
        out.writeString(endDateTime);
        out.writeString(carName);
        out.writeInt(carNumber);
        out.writeInt(capacity);
        out.writeDouble(totalCost);
        out.writeInt(numOfRiders);
    }

    public static final Parcelable.Creator<RequestCarSummaryItem> CREATOR
            = new Parcelable.Creator<RequestCarSummaryItem>() {
        public RequestCarSummaryItem createFromParcel(Parcel in) {
            return new RequestCarSummaryItem(in);
        }

        public RequestCarSummaryItem[] newArray(int size) {
            return new RequestCarSummaryItem[size];
        }
    };

    private RequestCarSummaryItem(Parcel in) {
        this.startDateTime = in.readString();
        this.endDateTime = in.readString();
        this.carName = in.readString();
        this.carNumber = in.readInt();
        this.capacity = in.readInt();
        this.totalCost = in.readDouble();
        this.numOfRiders = in.readInt();
    }
}
