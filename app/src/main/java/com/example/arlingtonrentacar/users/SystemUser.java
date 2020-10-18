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
}
