package com.example.arlingtonrentacar.manager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.database.Reservations;

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
    private Reservations reservations;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_reservation_details_screen);
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

        reservations = getIntent().getParcelableExtra("ReservationsDataObj");
//        System.out.println(reservations);

        reservation_id_textview.setText(reservations.getReservationID());
        carnumber_textview.setText(reservations.getCar_number().toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
