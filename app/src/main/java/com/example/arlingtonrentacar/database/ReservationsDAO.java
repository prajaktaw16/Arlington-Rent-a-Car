package com.example.arlingtonrentacar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

public class ReservationsDAO {
    private static final String LOG_TAG = ReservationsDAO.class.getSimpleName();
    private  String TABLE_NAME = "reservations";
    private String COLUMN_RESERVATION_ID= "reservation_id";
    private String COLUMN_USERNAME = "username";
    private String COLUMN_LAST_NAME = "last_name";
    private String COLUMN_FIRST_NAME = "first_name";
    private String COLUMN_CAR_NAME = "car_name";
    private String COLUMN_CAR_CAPACITY = "car_capacity";
    private String COLUMN_START_DATE_TIME = "start_date_time";
    private String COLUMN_END_DATE_TIME = "end_date_time";
    private String COLUMN_NUM_OF_RIDERS = "num_of_riders";
    private String COLUMN_TOTAL_PRICE = "total_price";
    private String COLUMN_GPS = "gps";
    private String COLUMN_SIRIUS_XM = "siriusxm";
    private String COLUMN_ONSTAR = "onstar";
    private String COLUMN_AAA_MEMBER_STATUS = "aaa_member_status";

    private static ReservationsDAO reservationsDAO;
    private DatabaseHelper dbHelper;

    private ReservationsDAO(@Nullable Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static ReservationsDAO getInstance(@Nullable Context context){
        if(reservationsDAO == null){
            reservationsDAO = new ReservationsDAO(context);
        }
        return reservationsDAO;
    }

    public Cursor viewReservations(String start_dateTime){
        final String METHOD_NAME = "viewReservations()";
        Log.d(LOG_TAG, METHOD_NAME);
        SQLiteDatabase databaseHandle = dbHelper.getReadableDatabase();
        String reservations_query =  "select * from reservations where start_date_time=\""+start_dateTime+"\" order by start_date_time desc, car_name asc;";
        Cursor cursor =  databaseHandle.rawQuery(reservations_query,null);
        return cursor;
    }

    public boolean deleteReservation(String reservationID){
        SQLiteDatabase databaseHandle = dbHelper.getWritableDatabase();
        String reservations_query = "delete from reservations where reservation_id='"+reservationID+"'";
        int query_result =  databaseHandle.delete(TABLE_NAME, "reservation_id ='" + reservationID + "'",null);
        if(query_result>0){
            return true;
        }
        return false;
    }
}
