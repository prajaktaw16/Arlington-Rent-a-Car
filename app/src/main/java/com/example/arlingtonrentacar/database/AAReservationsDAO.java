/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarName;
import com.example.arlingtonrentacar.renter.ReservationSummaryItem;

import java.util.Calendar;
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

    public LinkedList<Calendar> getAllReservationDatesOfCar(CarName carName){
        final String METHOD_NAME = "getReservationsByCarName()";
        String strCarName = AAUtil.carNameEnumToStr(carName);
        LinkedList<Calendar> reservationDateList = new LinkedList<Calendar>();
        SQLiteDatabase dbHandle = this.dbHelper.getWritableDatabase();
        String sql = "SELECT " + COLUMN_START_DATE_TIME + ", " + COLUMN_END_DATE_TIME + " FROM " + TABLE_RESERVATIONS + " WHERE " + COLUMN_CAR_NAME + " = ?;";
        String[] selectionArgs = {strCarName};
        Log.d(LOG_TAG, METHOD_NAME + ": SQL = " + sql);

        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        Log.d(LOG_TAG, METHOD_NAME+": Total reservation dates found of car " + strCarName +" = " + cursor.getCount());
        Calendar reservationStartDateTime, reservationEndDateTime;

        while(cursor.moveToNext()){
            reservationStartDateTime = AAUtil.databaseDateTimeToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE_TIME)));
            reservationDateList.add(reservationStartDateTime);

            reservationEndDateTime = AAUtil.databaseDateTimeToCalendar(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE_TIME)));
            reservationDateList.add(reservationEndDateTime);
        }
        return reservationDateList;
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
        return result;
    }

    public LinkedList<ReservationSummaryItem> getRenterReservationSummaryItems(String startDateTime, String username){
        final String METHOD_NAME = "getRenterReservationSummaryItems()";
        LinkedList<ReservationSummaryItem> summaryItemList = new LinkedList<ReservationSummaryItem>();
        ReservationSummaryItem summaryItem;
        SQLiteDatabase dbHandle = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE " + COLUMN_START_DATE_TIME + " >= ? AND "+ COLUMN_USERNAME +" =? ORDER BY " + COLUMN_START_DATE_TIME +" DESC, " + COLUMN_END_DATE_TIME + " DESC, " + COLUMN_TOTAL_PRICE + " ASC;";
        String[] selectionArgs = {startDateTime, username};
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        int carNumber = 0;
        int carCapacity;
        String carName, dbStartDateTime, dbEndDateTime, reservationID;
        double totalPrice;
        while (cursor.moveToNext()){
            carNumber++;
            carCapacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAR_CAPACITY));
            carName = cursor.getString(cursor.getColumnIndex(COLUMN_CAR_NAME));
            dbStartDateTime = AAUtil.convertDBDateToTargetFormat(cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE_TIME)).trim(), AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT);
            dbEndDateTime = AAUtil.convertDBDateToTargetFormat(cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE_TIME)).trim(), AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT);
            totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_PRICE));
            reservationID = cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_ID));
            summaryItem = new ReservationSummaryItem(reservationID, carNumber, carCapacity, carName, dbStartDateTime, dbEndDateTime, totalPrice);
            summaryItemList.add(summaryItem);
            Log.d(LOG_TAG, METHOD_NAME + ": Reservation Summary Items:\n" + summaryItem.toString());
        }
        return summaryItemList;
    }

    public AAReservationModel getReservationByID(String reservationID){
        AAReservationModel reservation = new AAReservationModel();
        SQLiteDatabase dbHandle = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_RESERVATIONS + " WHERE " + COLUMN_RESERVATION_ID + " = ?;";
        String[] selectionArgs = {reservationID.trim()};
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();

        String dbReservationID = cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_ID));
        String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
        String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
        String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
        String carName = cursor.getString(cursor.getColumnIndex(COLUMN_CAR_NAME));
        int carCapacity = cursor.getInt(cursor.getColumnIndex(COLUMN_CAR_CAPACITY));
        String startDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_START_DATE_TIME));
        String endDateTime = cursor.getString(cursor.getColumnIndex(COLUMN_END_DATE_TIME));
        int numOfRiders = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_OF_RIDERS));
        double totalPrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_PRICE));
        int gps = cursor.getInt(cursor.getColumnIndex(COLUMN_GPS));
        int xm = cursor.getInt(cursor.getColumnIndex(COLUMN_XM));
        int onStar = cursor.getInt(cursor.getColumnIndex(COLUMN_ONSTAR));
        int aaaMemStat = cursor.getInt(cursor.getColumnIndex(COLUMN_AAA_MEM_STAT));

        reservation.setReservationID(dbReservationID);
        reservation.setUsername(username);
        reservation.setLastName(lastName);
        reservation.setFirstName(firstName);
        reservation.setCarName(AAUtil.carNameStrToEnum(carName));
        reservation.setCarCapacity(carCapacity);
        reservation.setStartDateTime(AAUtil.databaseDateTimeToCalendar(startDateTime));
        reservation.setEndDateTime(AAUtil.databaseDateTimeToCalendar(endDateTime));
        reservation.setNumOfRiders(numOfRiders);
        reservation.setTotalPrice(totalPrice);
        reservation.setGps(gps);
        reservation.setXm(xm);
        reservation.setOnStar(onStar);
        reservation.setAaaMemStatus(aaaMemStat);

        return reservation;
    }

    public boolean deleteReservation(String reservationID){
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        String selection = COLUMN_RESERVATION_ID + " = ?";
        String[] selectionArgs = {reservationID};
        int deletedRows = dbHandle.delete(TABLE_RESERVATIONS, selection, selectionArgs);
        if(deletedRows == 0){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateReservation(AAReservationModel reservation){
        boolean result = false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAR_NAME, AAUtil.carNameEnumToStr(reservation.getCarName()));
        values.put(COLUMN_CAR_CAPACITY, reservation.getCarCapacity());
        values.put(COLUMN_START_DATE_TIME, AAUtil.formatDate(reservation.getStartDateTime(), AAUtil.DATABASE_DATE_TIME_FORMAT));
        values.put(COLUMN_END_DATE_TIME, AAUtil.formatDate(reservation.getEndDateTime(), AAUtil.DATABASE_DATE_TIME_FORMAT));
        values.put(COLUMN_NUM_OF_RIDERS, reservation.getNumOfRiders());
        values.put(COLUMN_TOTAL_PRICE, reservation.getTotalPrice());
        values.put(COLUMN_GPS, reservation.getGps());
        values.put(COLUMN_XM, reservation.getXm());
        values.put(COLUMN_ONSTAR, reservation.getOnStar());
        String selection = COLUMN_RESERVATION_ID + " = ?";
        String[] selectionArgs = {reservation.getReservationID()};
        int count = db.update(
          TABLE_RESERVATIONS,
          values,
          selection,
          selectionArgs
        );
        if(count == 1){
            result = true;
        }
        return result;
    }

}
