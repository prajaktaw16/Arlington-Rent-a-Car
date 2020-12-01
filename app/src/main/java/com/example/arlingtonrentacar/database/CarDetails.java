package com.example.arlingtonrentacar.database;

public class CarDetails {
    String carName;
    String carNumber;
    String carStatus;
    String carCapacity;
    String carCostPerDay;

    public CarDetails() {

    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(String carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getCarCostPerDay() {
        return carCostPerDay;
    }

    public void setCarCostPerDay(String carCostPerDay) {
        this.carCostPerDay = carCostPerDay;
    }

    public CarDetails(String carName, String carNumber, String carStatus, String carCapacity, String carCostPerDay) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.carStatus = carStatus;
        this.carCapacity = carCapacity;
        this.carCostPerDay = carCostPerDay;
    }
}
