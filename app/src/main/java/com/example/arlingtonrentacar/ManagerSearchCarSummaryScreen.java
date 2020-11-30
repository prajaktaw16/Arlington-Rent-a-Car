package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.database.CarDetails;
import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.manager.ViewReservationCalendar_list_Adapter;
import com.example.arlingtonrentacar.manager.View_Reservation_Details;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ManagerSearchCarSummaryScreen extends AppCompatActivity implements RecylerViewAdapter.SearchCarListListener{

    ArrayList<CarDetails> carDetails = new ArrayList<>();
    CarDetails carDetailsObj;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_search_car_summary_screen);
        recyclerView = findViewById(R.id.recyclerView_seachCar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecylerViewAdapter(carDetails, (RecylerViewAdapter.SearchCarListListener) this);
        recyclerView.setAdapter(mAdapter);

        for(int i=1; i<6; i++) {
            carDetailsObj = new CarDetails();
            carDetailsObj.setCarName(i+"Smart");
            carDetailsObj.setCarCapacity(i+"2");
            carDetailsObj.setCarNumber(i+"1202");
            carDetailsObj.setCarCostPerDay(i+"32.99 USD");
            carDetailsObj.setCarStatus(i+"Available");
            carDetails.add(carDetailsObj);
        }

    }
    @Override
    public void onSearchCarListClick(int position) {
        Intent intent = new Intent(this, ManagerViewCarDetailScreen.class);
        startActivity(intent);
    }

}
