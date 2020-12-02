package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.database.CarDetails;
import com.example.arlingtonrentacar.database.CarsDAO;
import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.database.ReservationsDAO;
import com.example.arlingtonrentacar.manager.ViewReservationCalendar_list_Adapter;
import com.example.arlingtonrentacar.manager.View_Reservation_Details;
import com.example.arlingtonrentacar.renter.RenterReservationSummaryListAdapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ManagerSearchCarSummaryScreen extends AppCompatActivity implements RecylerViewAdapter.SearchCarListListener{

    ArrayList<SearchCarSummaryItem> carDetails = new ArrayList<>();
    SearchCarSummaryItem carDetailsObj;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String carName,startTime,start_Date_Time;
    private Calendar startDate;
    private Cursor cursor;
    private CarModel car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carName = getIntent().getStringExtra("carName");
        start_Date_Time = getIntent().getStringExtra("start_Date_Time");
        Log.d("ManagerSearchcarSummary", "car Name:"+carName);
        Log.d("ManagerSearchcarSummary", "startDate:"+start_Date_Time);
        //mAdapter = new RecylerViewAdapter(carDetails, (RecylerViewAdapter.SearchCarListListener) this);
       // recyclerView.setAdapter(mAdapter);
        //start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
        //ReservationsDAO reservationsDAO = ReservationsDAO.getInstance(this);
       // cursor = reservationsDAO.viewReservationsForCar(carName,start_Date_Time);

        carDetails =  (ArrayList<SearchCarSummaryItem>) getIntent().getSerializableExtra("carDetails");
        setContentView(R.layout.activity_manager_search_car_summary_screen);
        recyclerView = findViewById(R.id.recyclerView_seachCar);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       // DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAdapter = new RecylerViewAdapter(carDetails, (RecylerViewAdapter.SearchCarListListener) this);
        recyclerView.setAdapter(mAdapter);
        //recyclerView.addItemDecoration(itemDecoration);
        //recyclerView.hasFixedSize();
        /*for(int i=1; i<6; i++) {
            carDetailsObj = new SearchCarSummaryItem();
            carDetailsObj.setCarName(i+"Smart");
            carDetailsObj.setCarCapacity(i+"2");
            carDetailsObj.setCarNumber(i+"1202");
            carDetailsObj.setCarCostPerDay(i+"32.99 USD");
            carDetailsObj.setCarStatus(i+"Available");
            carDetails.add(carDetailsObj);
        }*/
    }
    @Override
    public void onSearchCarListClick(int position) {
        Intent intent = new Intent(this, ManagerViewCarDetailScreen.class);
        intent.putExtra("car", carDetails.get(position));
        intent.putExtra("pageName", "searchCar");
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_requested_car_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.rcd_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

}
