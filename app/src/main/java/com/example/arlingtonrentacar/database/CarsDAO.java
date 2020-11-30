/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarModel;
import com.example.arlingtonrentacar.CarName;

import java.util.LinkedList;

public class CarsDAO {
    private static final String LOG_TAG = CarsDAO.class.getSimpleName();
    public static final String TABLE_CARS = "cars";
    public static final String COLUMN_CAR_NAME = "car_name";
    public static final String COLUMN_CAPACITY = "capacity";
    public static final String COLUMN_CAR_STATUS = "car_status";
    public static final String COLUMN_WEEKDAY_RATE = "weekday_rate";
    public static final String COLUMN_WEEKEND_RATE = "weekend_rate";
    public static final String COLUMN_WEEKLY_RATE = "weekly_rate";
    public static final String COLUMN_GPS_RATE_PER_DAY = "gps_rate_perday";
    public static final String COLUMN_XM_RATE_PER_DAY = "siriusxm_rate_perday";
    public static final String COLUMN_ONSTAR_RATE_PER_DAY = "onstar_rate_perday";

    private static CarsDAO carsDAO;
    private DatabaseHelper dbHelper;

    private CarsDAO(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public static CarsDAO getInstance(Context context){
        if(carsDAO == null){
            carsDAO = new CarsDAO(context);
        }
        return carsDAO;
    }

    public LinkedList<CarModel> getCarsByNumOfRiders(int numOfRiders){
        final String METHOD_NAME = "getCarsByNumOfRiders()";
        LinkedList<CarModel> carList = new LinkedList<CarModel>();
        SQLiteDatabase dbHandle = this.dbHelper.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_CARS + " WHERE " + COLUMN_CAPACITY + ">= ? AND " + COLUMN_CAR_STATUS + " = ?;";
        String[] selectionArgs = {Integer.toString(numOfRiders), "available"};
        Log.d(LOG_TAG, METHOD_NAME + ": SQL = " + sql);

        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        Log.d(LOG_TAG, METHOD_NAME + ": Total available cars where capacity >= numOfRiders = " + cursor.getCount());

        CarModel carModel;
        while(cursor.moveToNext()){
            carModel = new CarModel(
                    AAUtil.carNameStrToEnum(cursor.getString(cursor.getColumnIndex(COLUMN_CAR_NAME)).trim()),
                    Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CAPACITY)).trim()),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CAR_STATUS)).trim(),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKDAY_RATE)).trim()),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKEND_RATE)).trim()),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKLY_RATE)).trim()),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_GPS_RATE_PER_DAY)).trim()),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_XM_RATE_PER_DAY)).trim()),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_ONSTAR_RATE_PER_DAY)).trim())
            );
            carList.add(carModel);
        }
        return carList;
    }

    public CarModel getCarByCarName(CarName carName){
        CarModel carModel;
        SQLiteDatabase dbHandle = this.dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_CARS + " WHERE " + COLUMN_CAR_NAME + "= ?;";
        String[] selectionArgs = {AAUtil.carNameEnumToStr(carName)};
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        carModel = new CarModel(
                AAUtil.carNameStrToEnum(cursor.getString(cursor.getColumnIndex(COLUMN_CAR_NAME)).trim()),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CAPACITY)).trim()),
                cursor.getString(cursor.getColumnIndex(COLUMN_CAR_STATUS)).trim(),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKDAY_RATE)).trim()),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKEND_RATE)).trim()),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_WEEKLY_RATE)).trim()),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_GPS_RATE_PER_DAY)).trim()),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_XM_RATE_PER_DAY)).trim()),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_ONSTAR_RATE_PER_DAY)).trim())
        );
        return carModel;
    }
}
