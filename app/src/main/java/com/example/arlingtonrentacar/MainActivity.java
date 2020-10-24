package com.example.arlingtonrentacar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.users.SystemUser;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String USERNAME = "com.example.arlingtonrentacar.USERNAME";
    private EditText editText_username, editText_password;
    private String username, password;
    private DatabaseHelper databaseHelper;
    private SystemUser systemUser;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_screen);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        Log.d(LOG_TAG, "databaseHelper created");
    }

    public void checkValidUser(View view){
        String role;
        Intent intent;

        editText_username = findViewById(R.id.edittext_username);
        editText_password = findViewById(R.id.edittext_password);
        username = editText_username.getText().toString().trim();
        password = editText_password.getText().toString().trim();

        systemUser = new SystemUser();
        systemUser.setUserName(username);
        systemUser.setPassword(password);

        if(username.length()!=0 && password.length()!=0){
            cursor = systemUser.checkPassword(databaseHelper);
            if(cursor.moveToFirst()) {
                role = cursor.getString(cursor.getColumnIndex("role")).trim().toLowerCase();
                if (role.equals("renter")) {
                    intent = new Intent(this, RenterHomeScreen.class);
                    intent.putExtra(USERNAME, username);
                    startActivity(intent);
                } else if (role.equals("manager")) {
                    intent = new Intent(this, ManagerHomeScreen.class);
                    intent.putExtra(USERNAME, username);
                    startActivity(intent);
                } else {
                    intent = new Intent(this, AdminHomeScreen.class);
                    intent.putExtra(USERNAME, username);
                    startActivity(intent);
                }
            }else{
                Toast.makeText(MainActivity.this, "Incorrect username and password.\nPlease try again.", Toast.LENGTH_LONG).show();
            }
        }else{
            Log.d(LOG_TAG, "No username or password entered.");
        }
    }

    public void registerBtnOnClickEventHandler(View view){
        Log.d(LOG_TAG, "Register Button Clicked. Taking user to Registration Screen.");
        startActivity(new Intent(this, RegisterActivity.class));
    }


}
