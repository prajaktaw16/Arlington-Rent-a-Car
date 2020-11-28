/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.renter.RenterViewProfileActivity;
import com.example.arlingtonrentacar.renter.RenterViewReservationsActivity;

public class RenterHomeScreen extends AppCompatActivity {
    private final String LOG_TAG = RenterHomeScreen.class.getSimpleName();
    private String username;
    private SharedPreferences sessionPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_home_screen);

        sessionPrefs = getSharedPreferences(getString(R.string.sessions_preference_file_key), Context.MODE_PRIVATE);
        this.username = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        Log.d(LOG_TAG, "Username passed from login screen: " + username);

        initGreetingForRenter(username);
    }

    public void logout(View view) {
        AAUtil.logout(this);
    }

    public void launchRequestCarActivity(View view) {
        Intent intent = new Intent(this, RequestCarActivity.class);
        startActivity(intent);
    }

    private void initGreetingForRenter(String username){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(this);
        String fullName = systemUserDAO.getFullNameByUsername(username);
        TextView userGreeting = findViewById(R.id.tvUserGreeting);

        String greeting = AAUtil.getGreetingByHour();
        greeting += ", " + fullName;

        userGreeting.setText(greeting);
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void viewProfile(View view) {
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }
    public void launchViewReservationsActivity(View view) {
        Intent intent = new Intent(this, RenterViewReservationsActivity.class);
        startActivity(intent);
    }

    public void launchRenterViewProfileActivity(View view) {
        Intent intent = new Intent(this, RenterViewProfileActivity.class);
        startActivity(intent);
    }
}