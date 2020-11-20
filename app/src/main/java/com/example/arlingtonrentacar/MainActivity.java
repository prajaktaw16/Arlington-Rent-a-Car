/**
 * Author (Refactored): Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.systemControllers.LoginController;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_screen);
        setSupportActionBar(toolbar);
    }

    public void registerBtnOnClickEventHandler(View view){
        Log.d(LOG_TAG, "Register Button Clicked. Taking user to Registration Screen.");
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void loginBtnOnClickEventHandler(View view){
        LoginController loginController = new LoginController(this);
        EditText et_username, et_password;
        et_username = findViewById(R.id.edittext_username);
        et_password = findViewById(R.id.edittext_password);
        String username, password;
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        loginController.login(username, password);
    }
}
