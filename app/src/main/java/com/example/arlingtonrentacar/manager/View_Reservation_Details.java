/**
 * Author: Shubham Patil
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */

package com.example.arlingtonrentacar.manager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RenterHomeScreen;
import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.database.ReservationsDAO;

public class View_Reservation_Details extends AppCompatActivity{

    public TextView reservation_id_textview;
    public TextView carnumber_textview;
    public TextView carname_textview;
    public TextView carcapacity_textview;
    public TextView startdate_textview;
    public TextView starttime_textview;
    public TextView enddate_textview;
    public TextView endtime_textview;
    public TextView lastname_textview;
    public TextView firstname_textview;
    public TextView numberofriders_textview;
    public TextView gps_textview;
    public TextView onstar_textview;
    public TextView siriusxm_textview;
    public TextView totalprice_textview;
    public TextView arlingtonautoclubmember_textview;
    public int car_number;
    private Reservations reservations;
    private ReservationsDAO reservationsDAO;
    private Context viewReservationDetails_Context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reservation_details_screen);
        viewReservationDetails_Context = this ;

        getGUI();
        reservations = getIntent().getParcelableExtra("ReservationsDataObj");
        car_number = getIntent().getExtras().getInt("Car_Number");
        setGUI();

    }

    public void getGUI(){
        reservation_id_textview = findViewById(R.id.reservationID_textview);
        carnumber_textview = findViewById(R.id.carnumber_textView);;
        carname_textview = findViewById(R.id.carname_textview);
        carcapacity_textview = findViewById(R.id.carcapacity_textview);;
        startdate_textview = findViewById(R.id.startdate_textview);
        starttime_textview = findViewById(R.id.starttime_textview);
        enddate_textview = findViewById(R.id.enddate_textview);
        endtime_textview = findViewById(R.id.endtime_textview);
        lastname_textview = findViewById(R.id.lastname_textview);
        firstname_textview = findViewById(R.id.firstname_textview);
        numberofriders_textview = findViewById(R.id.numberofriders_textview);
        gps_textview = findViewById(R.id.gps_textview);
        onstar_textview = findViewById(R.id.onstar_textview);;
        siriusxm_textview = findViewById(R.id.siriusxm_textview);
        totalprice_textview = findViewById(R.id.totalprice_textview);
        arlingtonautoclubmember_textview = findViewById(R.id.arlingtonautoclubmember_textview);
    }

    public void setGUI(){
        reservation_id_textview.setText(reservations.getReservationID());
        carnumber_textview.setText(String.valueOf(car_number));
        carname_textview.setText(reservations.getCarName());
        carcapacity_textview.setText(reservations.getCarCapacity().toString());
        startdate_textview.setText(reservations.getStartDate());
        starttime_textview.setText(reservations.getStartTime());
        enddate_textview.setText(reservations.getEndDate());
        endtime_textview.setText(reservations.getEndTime());
        lastname_textview.setText(reservations.getLastname());
        firstname_textview.setText(reservations.getFirstname());
        numberofriders_textview.setText(reservations.getNumberOfRiders().toString());
        gps_textview.setText(reservations.getGps().toString());
        onstar_textview.setText(reservations.getOnstar().toString());
        siriusxm_textview.setText(reservations.getSiriusxm().toString());
        totalprice_textview.setText(reservations.getTotalPrice().toString());
        arlingtonautoclubmember_textview.setText(reservations.getAaaMemberStatus().toString());
    }

    public void onDeleteClick(View view){
        reservationsDAO = ReservationsDAO.getInstance(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this reservation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean flag = reservationsDAO.deleteReservation(reservations.getReservationID());
//                        if deleted successfully redirect and show toast
                        if(flag){
                            Intent intent = new Intent(viewReservationDetails_Context, View_Reservation_Calendar.class);
                            Toast toast = Toast.makeText(viewReservationDetails_Context, "Reservation deleted successfully", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(intent);
                        }
//                        if failed show only toast
                        else{
                            Toast toast = Toast.makeText(viewReservationDetails_Context, "Could not delete reservation!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
