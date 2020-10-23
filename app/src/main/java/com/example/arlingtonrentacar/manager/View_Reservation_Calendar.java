package com.example.arlingtonrentacar.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;

public class View_Reservation_Calendar extends AppCompatActivity {

    String[] myDataset = {"Test1", "Test2","Test3", "Test4", "Test5"};
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

        mAdapter = new ViewReservationCalendar_list_Adapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }

    public void view_reservation_details(View view){
        Intent intent = new Intent(this, View_Reservation_Details.class);
        startActivity(intent);
    }

}
