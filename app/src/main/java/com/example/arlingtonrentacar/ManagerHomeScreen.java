package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.arlingtonrentacar.manager.View_Reservation_Calendar;
import com.example.arlingtonrentacar.manager.View_Reservation_Details;

public class ManagerHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_screen);

    }
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void view_reservation_calendar(View view){
        Intent intent = new Intent(this, View_Reservation_Calendar.class);
        startActivity(intent);
    }
}