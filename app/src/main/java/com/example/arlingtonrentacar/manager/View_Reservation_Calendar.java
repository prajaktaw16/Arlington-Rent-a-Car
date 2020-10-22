package com.example.arlingtonrentacar.manager;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;

public class View_Reservation_Calendar extends AppCompatActivity {

    String[] myDataset = {"Hello", "World","How", "Are", "You"};
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

        mAdapter = new calendar_list_Adapter(myDataset);
        recyclerView.setAdapter(mAdapter);

    }
}
