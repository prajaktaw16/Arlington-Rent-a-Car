package com.example.arlingtonrentacar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
        final String ROLE_COL = "role";
        Log.d(LOG_TAG, METHOD_NAME + ": username: "+username);
        SQLiteDatabase databaseHandle = this.getWritableDatabase();
        String sql = "SELECT role FROM " + SYSTEM_USERS_TABLE + " WHERE username=?;";
        String[] selectionArgs = {username};
        Cursor cursor = databaseHandle.rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        String role = cursor.getString(cursor.getColumnIndex("role"));
        Log.d(LOG_TAG, METHOD_NAME + ": returned role: "+ role);
        return role;
    }
}
