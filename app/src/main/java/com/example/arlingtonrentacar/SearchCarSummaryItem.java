package com.example.arlingtonrentacar;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchCarSummaryItem implements Parcelable {

    private String carName;
    private int carNumber;
    private int carCapacity;
    private String carStatus;
    private double totalPrice;
    private double rateWeekDay;
    private double rateWeekEnd;
    private double rateWeek;
    private double rateGPS;
    private double rateXM;
    private double rateOnStar;
    public SearchCarSummaryItem(){

    }
    protected SearchCarSummaryItem(Parcel in) {
        carName = in.readString();
        carNumber = in.readInt();
        carCapacity = in.readInt();
        carStatus = in.readString();
        totalPrice = in.readDouble();
        rateWeekDay = in.readDouble();
        rateWeekEnd = in.readDouble();
        rateWeek = in.readDouble();
        rateGPS = in.readDouble();
        rateXM = in.readDouble();
        rateOnStar = in.readDouble();
    }

    public static final Creator<SearchCarSummaryItem> CREATOR = new Creator<SearchCarSummaryItem>() {
        @Override
        public SearchCarSummaryItem createFromParcel(Parcel in) {
            return new SearchCarSummaryItem(in);
        }

        @Override
        public SearchCarSummaryItem[] newArray(int size) {
            return new SearchCarSummaryItem[size];
        }
    };



    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getRateWeekDay() {
        return rateWeekDay;
    }

    public void setRateWeekDay(double rateWeekDay) {
        this.rateWeekDay = rateWeekDay;
    }

    public double getRateWeekEnd() {
        return rateWeekEnd;
    }

    public void setRateWeekEnd(double rateWeekEnd) {
        this.rateWeekEnd = rateWeekEnd;
    }

    public double getRateWeek() {
        return rateWeek;
    }

    public void setRateWeek(double rateWeek) {
        this.rateWeek = rateWeek;
    }

    public double getRateGPS() {
        return rateGPS;
    }

    public void setRateGPS(double rateGPS) {
        this.rateGPS = rateGPS;
    }

    public double getRateXM() {
        return rateXM;
    }

    public void setRateXM(double rateXM) {
        this.rateXM = rateXM;
    }

    public double getRateOnStar() {
        return rateOnStar;
    }

    public void setRateOnStar(double rateOnStar) {
        this.rateOnStar = rateOnStar;
    }
    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public SearchCarSummaryItem(String carName, int carNumber,int carCapacity, String carStatus, double totalPrice, double rateWeekDay, double rateWeekEnd, double rateWeek, double rateGPS, double rateXM, double rateOnStar) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.carCapacity = carCapacity;
        this.carStatus = carStatus;
        this.totalPrice = totalPrice;
        this.rateWeekDay = rateWeekDay;
        this.rateWeekEnd = rateWeekEnd;
        this.rateWeek = rateWeek;
        this.rateGPS = rateGPS;
        this.rateXM = rateXM;
        this.rateOnStar = rateOnStar;
    }

    @Override
    public String toString() {
        return "Car Name: " + carName +
                "\nCar Capacity: " + carCapacity +
                "\nCar Status: " + carStatus +
                "\nTotal Price: " + AAUtil.getAmountInCurrency(totalPrice, AAUtil.USD_CURRENCY_FORMAT) ;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carName);
        dest.writeInt(carNumber);
        dest.writeInt(this.carCapacity);
        dest.writeString(this.carStatus);
        dest.writeDouble(this.totalPrice);
        dest.writeDouble(this.rateWeekDay);
        dest.writeDouble(this.rateWeekEnd);
        dest.writeDouble(this.rateWeek);
        dest.writeDouble(this.rateGPS);
        dest.writeDouble(this.rateXM);
        dest.writeDouble(this.rateOnStar);
    }

}
