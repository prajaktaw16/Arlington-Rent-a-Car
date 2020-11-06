package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;

public class ViewRequestedCarActivity extends AppCompatActivity {
    private static String LOG_TAG = ViewRequestedCarActivity.class.getSimpleName();
    private String startDateStr, endDateStr, startTimeStr, endTimeStr;
    private int numOfRiders;
    private RecyclerView vrcRecyclerView;
    private RequestedCarListAdapter vrcAdapter;


    // TODO: change the following in Iteration 3
    private final LinkedList<String> carList = new LinkedList<>();


    // TODO: Remove this, as it's only for Ahmed
    private final LinkedList<String> resvList = new LinkedList<>();

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


        // TODO: Remove the following, it's only for Ahmed
        initCarList(); // Uncomment later
        // TODO: take this out later!
        //initReservationList();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        vrcRecyclerView = findViewById(R.id.vrc_recyclerview);
        // TODO: change this later
        vrcAdapter = new RequestedCarListAdapter(this, carList); // uncomment later
        //vrcAdapter = new RequestedCarListAdapter(this, resvList);
        vrcRecyclerView.setAdapter(vrcAdapter);
        vrcRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        vrcRecyclerView.addItemDecoration(itemDecoration);
        vrcRecyclerView.hasFixedSize();
    }

    // TODO: Change this in Iteration 3
    private void initCarList() {
        carList.addLast(makeViewRequestedCarItemStr(1, "Smart", 1, "$32.99"));
        carList.addLast(makeViewRequestedCarItemStr(2, "Economy", 3, "$39.99"));
        carList.addLast(makeViewRequestedCarItemStr(3, "Compact", 4, "$44.99"));
        carList.addLast(makeViewRequestedCarItemStr(4, "Intermediate", 4, "$45.99"));
        carList.addLast(makeViewRequestedCarItemStr(5, "Standard", 5, "$48.99"));
        carList.addLast(makeViewRequestedCarItemStr(6, "Full Size", 6, "$52.99"));
        carList.addLast(makeViewRequestedCarItemStr(8, "MiniVan", 9, "$59.99"));
        carList.addLast(makeViewRequestedCarItemStr(7, "SUV", 8, "$59.99"));
        carList.addLast(makeViewRequestedCarItemStr(9, "Ultra Sports", 2, "$199.99"));
    }

    // TODO: Remove this in Iteration 3 (as needed), it's throwaway code
    private String makeViewRequestedCarItemStr(int carNum, String carName, int capacity, String rate) {
        String row_item = "Car Number: " + carNum + "\n";
        row_item += "Car Name: " + carName + "\n";
        row_item += "Car Capacity: " + capacity + "\n";
        row_item += "Total Price: " + rate;
        return row_item;
    }

    // TODO: take this out later
    private String makeAResvDatePortion(String startDate, String startTime, String endDate, String endTime) {
        return "Start Date: " + startDate + "\nStart Time: " + startTime + "\nEnd Date: " + endDate + "\nEnd Time: " + endTime;
    }

    // TODO: take this out later
    private void initReservationList() {
        String rsv = makeViewRequestedCarItemStr(1, "Smart", 1, "$95.00");
        rsv += "\n" + makeAResvDatePortion("10/28/2020", "8:30 AM", "10/30/2020", "8:00 PM");
        resvList.add(rsv);

        rsv = makeViewRequestedCarItemStr(2, "Economy", 3, "$100.05");
        rsv += "\n" + makeAResvDatePortion("11/04/2020", "4:30 PM", "11/08/2020", "1:00 PM");
        resvList.add(rsv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_requested_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            Toast.makeText(this, "Logout Menu Clicked", Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}

