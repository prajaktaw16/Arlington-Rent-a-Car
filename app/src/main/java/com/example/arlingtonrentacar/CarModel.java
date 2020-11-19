package com.example.arlingtonrentacar;

public class CarModel {
    private static final String LOG_TAG = CarModel.class.getSimpleName();
    private CarName name;
    private int capacity;
    private String status;
    private double rateWeekDay;
    private double rateWeekEnd;
    private double rateWeek;
    private double rateGPS;
    private double rateXM;
    private double rateOnStar;

    public CarModel() {
    }

    public CarModel(CarName name, int capacity, String status, double rateWeekDay, double rateWeekEnd,
                    double rateWeek, double rateGPS, double rateXM, double rateOnStar) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.rateWeekDay = rateWeekDay;
        this.rateWeekEnd = rateWeekEnd;
        this.rateWeek = rateWeek;
        this.rateGPS = rateGPS;
        this.rateXM = rateXM;
        this.rateOnStar = rateOnStar;
    }

    public CarName getName() {
        return name;
    }

    public void setName(CarName name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
