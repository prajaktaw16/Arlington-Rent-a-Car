package com.example.arlingtonrentacar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.users.SystemUser;

public class MainActivity extends AppCompatActivity {

    EditText editText_username, editText_password;
    String username, password, database_name = "ArlingtonAuto.db";
    Intent intent;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    SystemUser systemUser;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
    }

    public void checkValidUser(View view){
        String db_password, role = " ";

        editText_username = findViewById(R.id.edittext_username);
        editText_password = findViewById(R.id.edittext_password);
        username = editText_username.getText().toString().trim();
        password = editText_password.getText().toString().trim();

//        Set username and password details using System User class
        systemUser = new SystemUser();
        systemUser.setUserName(username);
        systemUser.setPassword(password);

        if(username.length()!=0 && password.length()!=0){
            cursor = systemUser.checkPassword(databaseHelper);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    role = cursor.getString(cursor.getColumnIndex("role")).trim().toLowerCase();
                }

                db_password = cursor.getString(cursor.getColumnIndex("password")).trim().toLowerCase();;
//          check for username and password using db query
                if(!password.equalsIgnoreCase(db_password)){
                    editText_username.setText("");
                    editText_password.setText("");
                }

//          if they match start an activity according to the role
                else{
                    if(!role.isEmpty()){
                        if(role.equals("renter")){
                            startActivity(new Intent(this, ManagerHomeScreen.class));
                        }
                        else if(role.equals("manager")){
                            startActivity(new Intent(this, ManagerHomeScreen.class));
                        }
                        else {
                            startActivity(new Intent(this, testactivity.class));
                        }
                    }
                }
            }

        }
    }
}
