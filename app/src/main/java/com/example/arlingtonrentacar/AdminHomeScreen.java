package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AdminHomeScreen extends AppCompatActivity {
    private final String LOG_TAG = AdminHomeScreen.class.getSimpleName();
    private String username;
    private SharedPreferences sessionPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_screen);

        sessionPrefs = AAUtil.getLogInSession(this);
        this.username = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        Log.d(LOG_TAG, "Username passed from login screen: " + username);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //delete this func after adding sassyalis
    public void viewuser(View view) {
        Intent intent = new Intent(this, search_user.class);
        startActivity(intent);
    }
    public void viewProfile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
}