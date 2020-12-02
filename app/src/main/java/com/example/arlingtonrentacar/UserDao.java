package com.example.arlingtonrentacar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.arlingtonrentacar.users.SystemUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class UserDao extends SQLiteOpenHelper{
    private static final String dbname = "ArlingtonAuto.db";
    public UserDao( Context context) {
        super(context,dbname , null, 1);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table system_users(firstname text, " +
                "lastname text, username text primary key,password text,UTAID text,role text,email text,phone text,street_address text," +
                "city text,state text,zipcode text,arlington_auto_member text)";
        db.execSQL(qry);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS system_users");
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from system_users ", null );
        return res;
    }
    public String addRecord(String firstname, String lastname, String username,String password,String UTAID,String role,
                            String email,String phone,String street_address, String city, String state,String zipcode, String arlington_auto_member)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstname",firstname);
        cv.put("lastname",lastname);
        cv.put("username",username);
        cv.put("password",password);
        cv.put("UTAID",UTAID);
        cv.put("role",role);
        cv.put("email",email);
        cv.put("phone",phone);
        cv.put("street_address",street_address);
        cv.put("city",city);
        cv.put("state",state);
        cv.put("zipcode",zipcode);
        cv.put("arlington_auto_member",arlington_auto_member);

        long res =  db.insert("system_users", null, cv );;
        if(res== -1)
            return "failed";
        else
            return "Account Created Successfully";

    }

    public String updateUserRecord(String firstname, String lastname, String username,String password,String UTAID,String role,
                            String email,String phone,String street_address, String city, String state,String zipcode, String arlington_auto_member)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("firstname",firstname);
        cv.put("lastname",lastname);
        cv.put("username",username);
        cv.put("password",password);
        cv.put("UTAID",UTAID);
        cv.put("role",role);
        cv.put("email",email);
        cv.put("phone",phone);
        cv.put("street_address",street_address);
        cv.put("city",city);
        cv.put("state",state);
        cv.put("zipcode",zipcode);
        cv.put("arlington_auto_member",arlington_auto_member);

        long res =  db.update("system_users", cv,username + "= ?", new String[]{} );
        if(res== -1)
            return "failed";
        else
            return "User Profile Updated Successfully";

    }
}