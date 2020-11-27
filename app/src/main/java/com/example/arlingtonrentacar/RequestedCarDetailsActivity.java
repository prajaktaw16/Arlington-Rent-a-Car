/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.arlingtonrentacar.renter.RenterViewReservationsActivity;
import com.example.arlingtonrentacar.renter.RequestCarSummaryItem;
import com.example.arlingtonrentacar.users.SystemUser;

import org.w3c.dom.Text;

import java.util.Calendar;

public class RequestedCarDetailsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{
    private static final String LOG_TAG = RequestedCarDetailsActivity.class.getSimpleName();
    private RequestCarSummaryItem carSummaryItem;
    private TextView tvCarNumber, tvCarName, tvCarCapacity, tvNumberOfRiders, tvStartDateTime, tvEndDateTime, tvTotalCost;
    private Switch gpsSwitch, onStarSwitch, xmSwitch;
    private SystemUser user;
    private CarModel car;
    private Context mContext;
    private Invoice invoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_car_details);
        mContext = this;
        carSummaryItem = (RequestCarSummaryItem) getIntent().getParcelableExtra(getString(R.string.parcel_selected_requested_car_summary_item));
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
        }else{
            if(isChecked){
                this.invoice.setXmSelected(true);
            }else {
                this.invoice.setXmSelected(false);
            }
        }
        this.tvTotalCost.setText(AAUtil.getAmountInCurrency(this.invoice.calculateTotalCost(), AAUtil.USD_CURRENCY_FORMAT));
        updateTextViewLabel((TextView) findViewById(R.id.totalPriceLabel), "Total Price (Full):");
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
        tvStartDateTime.setText(AAUtil.convertDBDateToTargetFormat(this.carSummaryItem.getStartDateTime(), AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT));
        tvEndDateTime.setText(AAUtil.convertDBDateToTargetFormat(this.carSummaryItem.getEndDateTime(), AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT));
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
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void onClickEventHandlerRequestCarBtn(View view) {
        if(AAUtil.getSystemUserStatus(this) == AAAMemberStatus.NO){
            showUserStatusRevokedAlertDialog();
        }else {
            this.tvTotalCost.setText(AAUtil.getAmountInCurrency(this.invoice.calculateTotalCost(), AAUtil.USD_CURRENCY_FORMAT));
            updateTextViewLabel((TextView) findViewById(R.id.totalPriceLabel), "Total Price (Full):");
            showRequestCarConfirmationAlertDialog();
        }
    }

    private void showRequestCarConfirmationAlertDialog(){
        String msg = "Do you want to request this car and make reservation? Total Price: " + AAUtil.getAmountInCurrency(invoice.calculateTotalCost(), AAUtil.USD_CURRENCY_FORMAT);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg)
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

    private void showUserStatusRevokedAlertDialog(){
        String msg = "Oops! Sorry, you can't rent any car. Your rental status is 'Revoked'. Please contact: aarentalservice@customercare.com";
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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
            Dialog dialog = registrationSuccessAndNavigationDialog(reservationID);
            dialog.show();
        }else{
            msg = "Reservation failed.\nPlease try again.";
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private Dialog registrationSuccessAndNavigationDialog(String reservationID){
        String msg = "Reservation successful. Reservation ID:\n" + reservationID;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg)
                .setPositiveButton("View Reservations", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        navigateToTarget(RenterViewReservationsActivity.class);
                    }
                })
                .setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        navigateToTarget(RenterHomeScreen.class);
                    }
                });
        return builder.create();
    }

    private void navigateToTarget(Class<?> cls){
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }

    private void updateTextViewLabel(TextView textView, String newLabel){
        textView.setText(newLabel.trim());
    }
}