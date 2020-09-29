package com.example.arlingtonrentacar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SYSTEM_USERS_TABLE = "system_users";
    public static final String db_name = "ArlingtonAuto.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, 1);
    }

    //    called when first time database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table " + SYSTEM_USERS_TABLE + " (firstname text, " +
                "lastname text, username text primary key,password text,UTAID text,role text,email text,phone text,street_address text," +
                "city text,state text,zipcode text,arlington_auto_member text)";
        db.execSQL(qry);
    }

//    called when version number is changed
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
