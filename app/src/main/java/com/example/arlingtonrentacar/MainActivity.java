package com.example.arlingtonrentacar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.arlingtonrentacar.systemControllers.LoginController;
import com.example.arlingtonrentacar.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_screen);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Log.d(LOG_TAG, "databaseHelper created");
    }

    public void registerBtnOnClickEventHandler(View view){
        Log.d(LOG_TAG, "Register Button Clicked. Taking user to Registration Screen.");
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void loginBtnOnClickEventHandler(View view){
        LoginController loginController = new LoginController(MainActivity.this);
        EditText et_username, et_password;
        et_username = findViewById(R.id.edittext_username);
        et_password = findViewById(R.id.edittext_password);
        String username, password;
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        loginController.login(username, password);
    }


}
