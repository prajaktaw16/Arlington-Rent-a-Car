package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.arlingtonrentacar.database.DatabaseHelper;

public class RenterHomeScreen extends AppCompatActivity {
    private final String LOG_TAG = RenterHomeScreen.class.getSimpleName();
    private DatabaseHelper dbHelper;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_home_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra(MainActivity.USERNAME);
        Log.d(LOG_TAG, "Username passed from login screen: " + username);

        dbHelper = new DatabaseHelper(this);

        initGreetingForRenter(username);
    }
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchRequestCarActivity(View view) {
        Intent intent = new Intent(this, RequestCarActivity.class);
        startActivity(intent);
    }

    private void initGreetingForRenter(String username){
        TextView userGreeting = findViewById(R.id.tvUserGreeting);

        String greeting = AAUtil.getGreetingByHour();
        greeting += ", " + dbHelper.getFullNameByUsername(username);

        userGreeting.setText(greeting);
    }


}