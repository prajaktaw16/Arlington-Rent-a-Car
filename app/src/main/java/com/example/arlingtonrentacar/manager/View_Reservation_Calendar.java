package com.example.arlingtonrentacar.manager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.database.Reservations;

import java.util.ArrayList;

public class View_Reservation_Calendar extends AppCompatActivity implements ViewReservationCalendar_list_Adapter.ReservationListListener {

    ArrayList<Reservations> reservationsData = new ArrayList<>();
    Reservations reservationsObj;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservations_summary_screen);
        recyclerView = findViewById(R.id.calendar_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ViewReservationCalendar_list_Adapter( reservationsData, this);
        recyclerView.setAdapter(mAdapter);

        for(int i=1; i<6; i++) {
            reservationsObj = new Reservations();
            reservationsObj.setEndDate(i+"1/2/2020");
            reservationsObj.setEndTime(i+"1PM");
            reservationsObj.setStartDate(i+"0/2/2020");
            reservationsObj.setStartTime(i+"2PM");
            reservationsData.add(reservationsObj);
        }
    }

    @Override
    public void onReservationListClick(int position) {
        Intent intent = new Intent(this, View_Reservation_Details.class);
        startActivity(intent);
    }
}
