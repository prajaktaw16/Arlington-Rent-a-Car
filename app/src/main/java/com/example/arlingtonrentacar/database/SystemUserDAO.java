package com.example.arlingtonrentacar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.arlingtonrentacar.users.SystemUser;

import java.security.KeyStore;

public class SystemUserDAO extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static final String ARLINGTON_AUTO_DB = "ArlingtonAuto.db";
    private static final String SYSTEM_USERS_TABLE = "system_users";
    private static final int VERSION = 3;

    public SystemUserDAO(@Nullable Context context){
        super(context, ARLINGTON_AUTO_DB, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean isCorrectCredentials(String username, String password){
        final String METHOD_NAME = "isCorrectCredentials()";
        Log.d(LOG_TAG, METHOD_NAME +": username, password: " + username + ", " + password);
        SQLiteDatabase databaseHandle = this.getWritableDatabase();
        String sql = "SELECT username FROM " + SYSTEM_USERS_TABLE + " WHERE username=? AND password=?;";
        String[] selectionArgs = {username, password};
        Cursor cursor = databaseHandle.rawQuery(sql, selectionArgs);
        if(cursor.getCount() == 1)
            return true;
        else
            return false;
    }

    public String getSystemUserRole(String username){
        final String METHOD_NAME = "getSystemUserRole()";
        String role = "";
        Log.d(LOG_TAG, METHOD_NAME + ": username: "+username);
        SQLiteDatabase databaseHandle = this.getWritableDatabase();
        String sql = "SELECT role FROM " + SYSTEM_USERS_TABLE + " WHERE username=?;";
        String[] selectionArgs = {username};
        Cursor cursor = databaseHandle.rawQuery(sql, selectionArgs);
        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndex("role"));
            Log.d(LOG_TAG, METHOD_NAME + ": returned role: "+ role);
        }else{
            Log.d(LOG_TAG, METHOD_NAME + "moveToFirst() returned false. returned role is empty string. user won't be able to login.");
        }
        return role;
    }

    public boolean isRegistered(String username){
        final String METHOD_NAME = "isRegistered()";
        Log.d(LOG_TAG, METHOD_NAME + ": username: "+username);
        boolean result = false;
        SQLiteDatabase dbHandle = this.getWritableDatabase();
        String sql = "SELECT username FROM " + SYSTEM_USERS_TABLE + " WHERE username=?;";
        String[] selectionArgs = {username};
        Cursor cursor = dbHandle.rawQuery(sql, selectionArgs);
        if(cursor.getCount() == 0){
            result = false;
        }else{
            result = true;
        }
        return result;
    }

    public String store(SystemUser user){
        final String METHOD_NAME = "store()";
        String msg = "";
        SQLiteDatabase dbHandle = this.getWritableDatabase();

        String sql = "INSERT INTO " + SYSTEM_USERS_TABLE + " (username, password, last_name, first_name, " +
                "role, uta_id, phone, email, street_address, city, state, zip_code, aaa_member_status, " +
                "user_status) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        String[] bindArgs = {
                user.getUsername(),
                user.getPassword(),
                user.getLastName(),
                user.getFirstName(),
                user.getRole(),
                Integer.toString(user.getUtaID()),
                user.getPhone(),
                user.getEmail(),
                user.getStreetAddress(),
                user.getCity(),
                user.getState(),
                user.getZip(),
                Integer.toString(user.getAaaMemberStatus()),
                Integer.toString(user.getUserStatus())
        };
        Log.d(LOG_TAG, METHOD_NAME + " Finished creating sql str and binding args.");
        try{
            dbHandle.execSQL(sql, bindArgs);
            Log.d(LOG_TAG, METHOD_NAME + " SQL: " + sql);
        }catch (Exception e){
            msg = "Registration failed.\nPlease try again later.";
            Log.e(LOG_TAG, METHOD_NAME + "This should never happen.\nFailed to register user.\n Error: " + e.getMessage());
        }
        return msg;
    }
}
