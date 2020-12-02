package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.arlingtonrentacar.database.Reservations;

public class ManagerViewCarDetailScreen extends AppCompatActivity {
    public TextView carname_textview;
    public TextView carcapacity_textview;
    public TextView carStatus_textview;
    public TextView rateWeekDay_textview;
    public TextView rateWeekEnd_textview;
    public TextView rateWeek_textview;
    public TextView gps_textview;
    public TextView onstar_textview;
    public TextView siriusxm_textview;
    private SearchCarSummaryItem car;
    private String prevPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_car_detail_screen);
        getGUI();
        car = getIntent().getParcelableExtra("car");
        prevPage = getIntent().getStringExtra("pageName");
        Log.d("viewCardDetails", "prevPage:"+prevPage);
        setGUI();

    }

    public void getGUI(){
        carname_textview = findViewById(R.id.view_car_name);
        carcapacity_textview = findViewById(R.id.view_car_capacity);
        carStatus_textview = findViewById(R.id.view_car_status);
        rateWeekDay_textview = findViewById(R.id.wkdayrate);
        rateWeekEnd_textview = findViewById(R.id.wkendrate);
        rateWeek_textview = findViewById(R.id.wklyrate);
        gps_textview = findViewById(R.id.gpsrte);
        onstar_textview = findViewById(R.id.onstarrate);;
        siriusxm_textview = findViewById(R.id.sxmrate);
    }

    public void setGUI(){
        carname_textview.setText(car.getCarName());
        carcapacity_textview.setText(Integer.toString(car.getCarCapacity()));
        carStatus_textview.setText(car.getCarStatus());
        rateWeekDay_textview.setText(Double.toString(car.getRateWeekDay()));
        rateWeekEnd_textview.setText(Double.toString(car.getRateWeekEnd()));
        rateWeek_textview.setText(Double.toString(car.getRateWeek()));
        gps_textview.setText(Double.toString(car.getRateGPS()));
        onstar_textview.setText(Double.toString(car.getRateOnStar()));
        siriusxm_textview.setText(Double.toString(car.getRateXM()));

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