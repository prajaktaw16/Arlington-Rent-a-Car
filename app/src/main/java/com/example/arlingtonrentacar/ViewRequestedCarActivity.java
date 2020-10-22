package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;

public class ViewRequestedCarActivity extends AppCompatActivity {
    private static String LOG_TAG = ViewRequestedCarActivity.class.getSimpleName();
    private String startDateStr, endDateStr, startTimeStr, endTimeStr;
    private int numOfRiders;
    private RecyclerView vrcRecyclerView;
    private RequestedCarListAdapter vrcAdapter;


    // TODO: change the following in Iteration 3
    private final LinkedList<String> carList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requested_car);

        Intent intent = getIntent();
        startDateStr = intent.getStringExtra(RequestCarActivity.EXTRA_START_DATE);
        endDateStr = intent.getStringExtra(RequestCarActivity.EXTRA_END_DATE);
        startTimeStr = intent.getStringExtra(RequestCarActivity.EXTRA_START_TIME);
        endTimeStr = intent.getStringExtra(RequestCarActivity.EXTRA_END_TIME);
        numOfRiders = Integer.parseInt(intent.getStringExtra(RequestCarActivity.EXTRA_NUM_OF_RIDERS));

        Log.d(LOG_TAG, "Num of Riders = " + numOfRiders);
        Log.d(LOG_TAG, "Start Date = " + startDateStr);
        Log.d(LOG_TAG, "Start Time = " + startTimeStr);
        Log.d(LOG_TAG, "End Date = " + endDateStr);
        Log.d(LOG_TAG, "End Time = " + endTimeStr);


        initCarList();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        vrcRecyclerView = findViewById(R.id.vrc_recyclerview);
        vrcAdapter = new RequestedCarListAdapter(this, carList);
        vrcRecyclerView.setAdapter(vrcAdapter);
        vrcRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vrcRecyclerView.addItemDecoration(itemDecoration);
        vrcRecyclerView.hasFixedSize();
    }

    // TODO: Change this in Iteration 3
    private void initCarList(){
        carList.addLast(makeViewRequestedCarItemStr(1, "Smart", 1, "$32.99"));
        carList.addLast(makeViewRequestedCarItemStr(2, "Economy", 3, "$39.99"));
        carList.addLast(makeViewRequestedCarItemStr(3, "Compact", 4, "$44.99"));
        carList.addLast(makeViewRequestedCarItemStr(4, "Intermediate", 4, "$45.99"));
        carList.addLast(makeViewRequestedCarItemStr(5, "Standard", 5, "$48.99"));
        carList.addLast(makeViewRequestedCarItemStr(6, "Full Size", 6, "$52.99"));
        carList.addLast(makeViewRequestedCarItemStr(7, "SUV", 8, "$59.99"));
        carList.addLast(makeViewRequestedCarItemStr(8, "MiniVan", 9, "$59.99"));
        carList.addLast(makeViewRequestedCarItemStr(9, "Ultra Sports", 2, "$199.99"));
    }
    // TODO: Remove this in Iteration 3 (as needed), it's throwaway code
    private String makeViewRequestedCarItemStr(int carNum, String carName, int capacity, String rate){
        String row_item = "Car Number: " + carNum + "\n";
        row_item += "Car Name: " + carName + "\n";
        row_item += "Car Capacity: " + capacity + "\n";
        row_item += "Total Price: " + rate;
        return row_item;
    }
}