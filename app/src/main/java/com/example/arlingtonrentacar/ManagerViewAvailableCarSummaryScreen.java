package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManagerViewAvailableCarSummaryScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_avialable_car_summary_screen);
    }

    public void viewCarDetails(View view) {
        Intent intent = new Intent(this, ManagerViewCarDetailScreen.class);
        startActivity(intent);
    }
}