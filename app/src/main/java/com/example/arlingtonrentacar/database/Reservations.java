package com.example.arlingtonrentacar.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservations implements Parcelable {
    String carName;
    String startDate;
    String startTime;
    String endDate;
    String endTime;
    String reservationID;
    Integer car_number;
    String lastname;
    String firstname;
    Integer carCapacity;
    Integer numberOfRiders;
    Float totalPrice;
    Integer gps;
    Integer siriusxm;
    Integer onstar;
    Integer aaaMemberStatus;


    public Reservations() {
    }


    protected Reservations(Parcel in) {
        carName = in.readString();
        startDate = in.readString();
        startTime = in.readString();
        endDate = in.readString();
        endTime = in.readString();
        reservationID = in.readString();
        if (in.readByte() == 0) {
            car_number = null;
        } else {
            car_number = in.readInt();
        }
        lastname = in.readString();
        firstname = in.readString();
        if (in.readByte() == 0) {
            carCapacity = null;
        } else {
            carCapacity = in.readInt();
        }
        if (in.readByte() == 0) {
            numberOfRiders = null;
        } else {
            numberOfRiders = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPrice = null;
        } else {
            totalPrice = in.readFloat();
        }
        if (in.readByte() == 0) {
            gps = null;
        } else {
            gps = in.readInt();
        }
        if (in.readByte() == 0) {
            siriusxm = null;
        } else {
            siriusxm = in.readInt();
        }
        if (in.readByte() == 0) {
            onstar = null;
        } else {
            onstar = in.readInt();
        }
        if (in.readByte() == 0) {
            aaaMemberStatus = null;
        } else {
            aaaMemberStatus = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carName);
        dest.writeString(startDate);
        dest.writeString(startTime);
        dest.writeString(endDate);
        dest.writeString(endTime);
        dest.writeString(reservationID);
        if (car_number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(car_number);
        }
        dest.writeString(lastname);
        dest.writeString(firstname);
        if (carCapacity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(carCapacity);
        }
        if (numberOfRiders == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfRiders);
        }
        if (totalPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(totalPrice);
        }
        if (gps == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gps);
        }
        if (siriusxm == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(siriusxm);
        }
        if (onstar == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(onstar);
        }
        if (aaaMemberStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(aaaMemberStatus);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Reservations> CREATOR = new Creator<Reservations>() {
        @Override
        public Reservations createFromParcel(Parcel in) {
            return new Reservations(in);
        }

        @Override
        public Reservations[] newArray(int size) {
            return new Reservations[size];
        }
    };

    public Integer getCar_number() {
        return car_number;
    }

    public void setCar_number(Integer car_number) {
        this.car_number = car_number;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }



    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(Integer carCapacity) {
        this.carCapacity = carCapacity;
    }

    public Integer getNumberOfRiders() {
        return numberOfRiders;
    }

    public void setNumberOfRiders(Integer numberOfRiders) {
        this.numberOfRiders = numberOfRiders;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getGps() {
        return gps;
    }

    public void setGps(Integer gps) {
        this.gps = gps;
    }

    public Integer getSiriusxm() {
        return siriusxm;
    }

    public void setSiriusxm(Integer siriusxm) {
        this.siriusxm = siriusxm;
    }

    public Integer getOnstar() {
        return onstar;
    }

    public void setOnstar(Integer onstar) {
        this.onstar = onstar;
    }

    public Integer getAaaMemberStatus() {
        return aaaMemberStatus;
    }

    public void setAaaMemberStatus(Integer aaaMemberStatus) {
        this.aaaMemberStatus = aaaMemberStatus;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
