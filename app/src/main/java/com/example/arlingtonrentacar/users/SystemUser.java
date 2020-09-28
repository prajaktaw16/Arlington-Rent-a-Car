package com.example.arlingtonrentacar.users;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.arlingtonrentacar.database.DatabaseHelper;

public class SystemUser {
    String userName;
    String password;

    public SystemUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public SystemUser() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Cursor checkPassword(DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from system_users where username = '" + userName + "' and password = '" + password + "';", null );
        return res;
    }
    public String addRecord(DatabaseHelper databaseHelper,String firstname, String lastname, String username,String password,String UTAID,String role,
                            String email,String phone,String street_address, String city, String state,String zipcode, String arlington_auto_member)
    {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
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
}
