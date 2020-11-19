package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.AAReservationsDAO;
import com.example.arlingtonrentacar.database.CarsDAO;
import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.renter.RequestCarSummaryItem;
import com.example.arlingtonrentacar.users.SystemUser;

import java.util.Calendar;

public class RequestedCarDetailsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private static final String LOG_TAG = RequestedCarDetailsActivity.class.getSimpleName();
    private RequestCarSummaryItem carSummaryItem;
    private TextView tvCarNumber, tvCarName, tvCarCapacity, tvNumberOfRiders, tvStartDateTime, tvEndDateTime, tvTotalCost;
    private Switch gpsSwitch, onStarSwitch, xmSwitch;
    private SystemUser user;
    private CarModel car;
    private AAReservationModel reservation;
    private Context mContext;
    private Invoice invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_car_details);
        mContext = this;
        carSummaryItem = (RequestCarSummaryItem) getIntent().getParcelableExtra(getString(R.string.parcelable_selected_requested_car_summary_item));
        Log.d(LOG_TAG, METHOD_NAME + ": Parcelled RequestCarSummaryItem: \n" + carSummaryItem.allFieldsToString());

        SharedPreferences session = AAUtil.getLogInSession(mContext);
        String username = session.getString(getString(R.string.session_loggedin_username), "");
        setUpDetails();
        this.user = getSystemUser(username);
        this.car = getCar(AAUtil.carNameStrToEnum(carSummaryItem.getCarName()));

        Calendar startDateTime = AAUtil.databaseDateTimeToCalendar(carSummaryItem.getStartDateTime());
        Calendar endDateTime = AAUtil.databaseDateTimeToCalendar(carSummaryItem.getEndDateTime());
        this.invoice = new Invoice(car, startDateTime, endDateTime);
        this.invoice.setUserIsAAAMember(AAUtil.intToBool(user.getAaaMemberStatus()));
        this.invoice.setGpsSelected(false);
        this.invoice.setOnStarSelected(false);
        this.invoice.setXmSelected(false);

        gpsSwitch = findViewById(R.id.gpsSwitch);
        onStarSwitch = findViewById(R.id.onstarSwitch);
        xmSwitch = findViewById(R.id.siriusxmSwitch);

        gpsSwitch.setOnCheckedChangeListener(this);
        onStarSwitch.setOnCheckedChangeListener(this);
        xmSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton == gpsSwitch){
            if(isChecked){
                this.invoice.setGpsSelected(true);
            }else {
                this.invoice.setGpsSelected(false);
            }
        }else if(compoundButton == onStarSwitch){
            if(isChecked){
                this.invoice.setOnStarSelected(true);
            }else {
                this.invoice.setOnStarSelected(false);
            }
        }else if(compoundButton == xmSwitch){
            if(isChecked){
                this.invoice.setXmSelected(true);
            }else {
                this.invoice.setXmSelected(false);
            }
        }
        this.tvTotalCost.setText(AAUtil.getAmountInCurrency(this.invoice.calculateTotalCost(), AAUtil.USD_CURRENCY_FORMAT));
    }

    private void setUpDetails(){
        tvCarNumber = findViewById(R.id.tvCarNumber);
        tvCarName = findViewById(R.id.tv_carName);
        tvCarCapacity = findViewById(R.id.tv_NumOfRiders);
        tvStartDateTime = findViewById(R.id.tv_StartDateTime);
        tvEndDateTime = findViewById(R.id.tv_EndDateTime);
        tvTotalCost = findViewById(R.id.tv_TotalPrice);
        tvNumberOfRiders = findViewById(R.id.tv_NumOfRiders);
        tvCarNumber.setText(Integer.toString(this.carSummaryItem.getCarNumber()));
        tvCarName.setText(this.carSummaryItem.getCarName());
        tvCarCapacity.setText(Integer.toString(this.carSummaryItem.getCapacity()));
        tvStartDateTime.setText(AAUtil.convertDBDateToTargetFormat(this.carSummaryItem.getStartDateTime(), AAUtil.USER_DATE_TIME_FORMAT));
        tvEndDateTime.setText(AAUtil.convertDBDateToTargetFormat(this.carSummaryItem.getEndDateTime(), AAUtil.USER_DATE_TIME_FORMAT));
        tvTotalCost.setText(AAUtil.getAmountInCurrency(this.carSummaryItem.getTotalCost(), AAUtil.USD_CURRENCY_FORMAT));
        tvNumberOfRiders.setText(Integer.toString(this.carSummaryItem.getNumOfRiders()));
    }

    private SystemUser getSystemUser(String username){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        SystemUser user = systemUserDAO.getSystemUserByUsername(username);
        return user;
    }

    private CarModel getCar(CarName carName){
        CarsDAO carsDAO = CarsDAO.getInstance(mContext);
        CarModel carModel = carsDAO.getCarByCarName(carName);
        return carModel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_requested_car_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.rcd_menu_logout){
            Toast.makeText(this, "Logout Menu Clicked", Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void onClickEventHandlerRequestCarBtn(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to request this car and make reservation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addReservationToDB();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.create().show();
    }

    private void addReservationToDB(){
        String msg;
        String reservationID = AAUtil.generateGUID();
        String username = this.user.getUsername();
        String lastName = this.user.getLastName();
        String firstName = this.user.getFirstName();
        CarName carName = this.car.getName();
        int carCapacity = this.car.getCapacity();
        Calendar startDateTime = AAUtil.databaseDateTimeToCalendar(this.carSummaryItem.getStartDateTime());
        Calendar endDateTime = AAUtil.databaseDateTimeToCalendar(this.carSummaryItem.getEndDateTime());
        int numOfRiders = this.carSummaryItem.getNumOfRiders();
        double totalPrice = this.invoice.calculateTotalCost();
        int gps = AAUtil.boolToInt(this.invoice.isGpsSelected());
        int xm = AAUtil.boolToInt(this.invoice.isXmSelected());
        int onStar = AAUtil.boolToInt(this.invoice.isOnStarSelected());
        int aaaMemStat = this.user.getAaaMemberStatus();

        AAReservationModel reservation = new AAReservationModel(
                reservationID,
                username,
                lastName,
                firstName,
                carName,
                carCapacity,
                startDateTime,
                endDateTime,
                numOfRiders,
                totalPrice,
                gps,
                xm,
                onStar,
                aaaMemStat
        );

        AAReservationsDAO aaReservationsDAO = AAReservationsDAO.getInstance(mContext);
        if(aaReservationsDAO.addReservation(reservation)){
            msg = "Reservation Added Successfully";
        }else{
            msg = "Unable to add reservation.\nPlease try again.";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }
}