package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.arlingtonrentacar.database.SystemUserDAO;

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
        initGreetingForAdmin(username);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
    //delete this func after adding sassyalis
    public void viewuser(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
    public void viewProfile(View view) {
        Intent intent = new Intent(this, ViewProfile.class);
        startActivity(intent);
    }
    private void initGreetingForAdmin(String username){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(this);
        String fullName = systemUserDAO.getFullNameByUsername(username);
        TextView userGreeting = findViewById(R.id.welcometext);

        String greeting = AAUtil.getGreetingByHour();
        greeting += ", " + fullName;

        userGreeting.setText(greeting);
    }
}