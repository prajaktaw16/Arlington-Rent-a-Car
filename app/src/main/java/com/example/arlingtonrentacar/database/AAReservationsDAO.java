package com.example.arlingtonrentacar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarName;

import java.util.LinkedList;

public class AAReservationsDAO {
    private static final String LOG_TAG = AAReservationsDAO.class.getSimpleName();
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String COLUMN_RESERVATION_ID = "reservation_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_CAR_NAME = "car_name";
    public static final String COLUMN_CAR_CAPACITY = "car_capacity";
    public static final String COLUMN_START_DATE_TIME = "start_date_time";
    public static final String COLUMN_END_DATE_TIME = "end_date_time";
    public static final String COLUMN_NUM_OF_RIDERS = "num_of_riders";
    public static final String COLUMN_TOTAL_PRICE = "total_price";
    public static final String COLUMN_GPS = "gps";
    public static final String COLUMN_XM = "siriusxm";
    public static final String COLUMN_ONSTAR = "onstar";
    public static final String COLUMN_AAA_MEM_STAT = "aaa_member_status";

    private static AAReservationsDAO instace;
    private DatabaseHelper dbHelper;

    private AAReservationsDAO(Context context){
        this.dbHelper = DatabaseHelper.getInstance(context);
    }

    public static AAReservationsDAO getInstance(Context context){
        if(instace == null){
            instace = new AAReservationsDAO(context);
        }
        return instace;
    }

    public LinkedList<AAReservationModel> getReservationsByCarName(CarName carName){
        final String METHOD_NAME = "getReservationsByCarName()";
        String strCarName = AAUtil.carNameEnumToStr(carName);
        LinkedList<AAReservationModel> reservationList = new LinkedList<AAReservationModel>();
        SQLiteDatabase dbHandle = this.dbHelper.getWritableDatabase();
        String sql = "SELECT " + COLUMN_RESERVATION_ID + ", " + COLUMN_USERNAME + ", " +
                COLUMN_START_DATE_TIME + ", " + COLUMN_END_DATE_TIME + " FROM " + TABLE_RESERVATIONS + " WHERE " +
                COLUMN_CAR_NAME + " = ?;";
        String[] selectionArgs = {strCarName};
        Log.d(LOG_TAG, METHOD_NAME + ": SQL = " + sql);

        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        Log.d(LOG_TAG, METHOD_NAME+": Total reservation found for car " + strCarName +" = " + cursor.getCount());

        AAReservationModel reservationModel;
        while(cursor.moveToNext()){
            // A partial reservation model is used to just check if a car can be rented with the
            // provided start/end date time during requesting a car for reservation
            reservationModel = new AAReservationModel();
            reservationModel.setReservationID(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_ID)));
            reservationModel.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            reservationModel.setStartDateTime(AAUtil.databaseDateTimeToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE_TIME))));
            reservationModel.setEndDateTime(AAUtil.databaseDateTimeToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE_TIME))));
            reservationList.add(reservationModel);
        }
        cursor.close();
        return reservationList;
    }

    public boolean addReservation(AAReservationModel r){
        boolean result = true;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RESERVATION_ID, r.getReservationID());
        cv.put(COLUMN_USERNAME, r.getUsername()) ;
        cv.put(COLUMN_LAST_NAME, r.getLastName()) ;
        cv.put(COLUMN_FIRST_NAME, r.getFirstName()) ;
        cv.put(COLUMN_CAR_NAME, AAUtil.carNameEnumToStr(r.getCarName())) ;
        cv.put(COLUMN_CAR_CAPACITY, Integer.toString(r.getCarCapacity())) ;
        cv.put(COLUMN_START_DATE_TIME, AAUtil.formatDate(r.getStartDateTime(), AAUtil.DATABASE_DATE_TIME_FORMAT)) ;
        cv.put(COLUMN_END_DATE_TIME, AAUtil.formatDate(r.getEndDateTime(), AAUtil.DATABASE_DATE_TIME_FORMAT)) ;
        cv.put(COLUMN_NUM_OF_RIDERS, Integer.toString(r.getNumOfRiders())) ;
        cv.put(COLUMN_TOTAL_PRICE, Double.toString(r.getTotalPrice())) ;
        cv.put(COLUMN_GPS, r.getGps()) ;
        cv.put(COLUMN_XM, r.getXm()) ;
        cv.put(COLUMN_ONSTAR, r.getOnStar()) ;
        cv.put(COLUMN_AAA_MEM_STAT, r.getAaaMemStatus());

        long rowID = dbHandle.insert(TABLE_RESERVATIONS, null, cv);
        if(rowID == -1){
            result = false;
        }
        dbHandle.close();
        return result;
    }


}
