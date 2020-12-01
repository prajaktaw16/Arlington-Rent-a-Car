package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.ReservationsDAO;

import java.util.ArrayList;

public class ManagerViewAvailableCarSummaryScreen extends AppCompatActivity implements ManagerViewAvailableCarAdapter.ViewAvailableCarListListener  {
    ArrayList<SearchCarSummaryItem> carDetails = new ArrayList<>();
    SearchCarSummaryItem carDetailsObj;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String start_Date_Time;
    private Cursor cursor;
    private CarModel car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_avialable_car_summary_screen);
        start_Date_Time = getIntent().getStringExtra("start_Date_Time");
        Log.d("ManagerViewAvailableCarSummaryScreen", "start_Date_Time:"+start_Date_Time);
        recyclerView = findViewById(R.id.availcarRecycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //ReservationsDAO reservationsDAO = ReservationsDAO.getInstance(this);
        //cursor = reservationsDAO.viewReservations(start_Date_Time);
      /*  if(cursor.moveToFirst()){
            Toast toast = Toast.makeText(this, "No Cars found for this date and time", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT);
            for(int i=1; i<6; i++) {
                carDetailsObj = new SearchCarSummaryItem();
                carDetailsObj.setCarName(i+"Smart");
                carDetailsObj.setCarCapacity(i+2);
                carDetailsObj.setTotalPrice(2.99);
                carDetailsObj.setCarStatus(i+"Available");
                carDetails.add(carDetailsObj);
            }
        }*/
        carDetails =  (ArrayList<SearchCarSummaryItem>) getIntent().getSerializableExtra("carDetails");
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAdapter = new ManagerViewAvailableCarAdapter(carDetails,(ManagerViewAvailableCarAdapter.ViewAvailableCarListListener) this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.hasFixedSize();
        Toast toast = Toast.makeText(this, "Sucess", Toast.LENGTH_SHORT);

    }

    public void viewCarDetails(View view) {
        Intent intent = new Intent(this, ManagerViewCarDetailScreen.class);
        startActivity(intent);
    }

    @Override
    public void onSearchCarListClick(int position) {
        Intent intent = new Intent(this, ManagerViewCarDetailScreen.class);
        intent.putExtra("car", carDetails.get(position));
        intent.putExtra("pageName", "viewCar");
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
    public void redirectToHome(View view){
        Intent intent = new Intent(this, ManagerHomeScreen.class);
        startActivity(intent);
    }
}