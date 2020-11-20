package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.arlingtonrentacar.database.SystemUserDAO;

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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


}