package com.example.arlingtonrentacar.manager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arlingtonrentacar.R;

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

        reservation_id_textview.setText("1");
        carnumber_textview.setText("21");
        carname_textview.setText("Smart");
        carcapacity_textview.setText("1");
        startdate_textview.setText("10/10/20");
        starttime_textview.setText("1:00PM");
        enddate_textview.setText("10/10/20");
        endtime_textview.setText("5:00PM");
        lastname_textview.setText("Jane");
        firstname_textview.setText("Marry");
        numberofriders_textview.setText("1");
        gps_textview.setText("Yes");
        onstar_textview.setText("No");
        siriusxm_textview.setText("yes");
        totalprice_textview.setText("200");
        arlingtonautoclubmember_textview.setText("Yes");

    }
}
