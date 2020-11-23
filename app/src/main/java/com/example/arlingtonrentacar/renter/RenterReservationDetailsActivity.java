/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.CarName;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RenterHomeScreen;
import com.example.arlingtonrentacar.RequestCarActivity;

import java.util.Calendar;

public class RenterReservationDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener{
    private static final String LOG_TAG = RenterReservationDetailsActivity.class.getSimpleName();
    private Context mContext;
    private ReservationSummaryItem mSelectedSummaryItem;
    private AAReservationModel mReservation;
    private ControllerRenterReservationDetails mReservationDetailsController;
    private String mStartTime, mEndTime;
    private boolean gpsSelected, xmSelected, onStarSelected;
    private EditText mETNumOfRiders;
    private Switch mSwitchGPS, mSwitchXM, mSwitchOnStar;
    private TextView mTVReservationID, mTVCarCapacity,
            mTVTotalPrice, mTVAAAMemStatus,  mTVCarNumber ;
    private Spinner mSpinnerCarName, mSpinnerStartTime, mSpinnerEndTime;
    private int mCarCapacity;
    private CarName selectedCarName;
    private Calendar mSelectedStartDate, mSelectedEndDate;
    private String mSelectedStartTime, mSelectedEndTime;
    private SharedPreferences mSessionPref;
    private Button mBtnStartDate, mBtnEndDate;

    private ArrayAdapter<CharSequence> mAdapterStartTime, mAdapterEndTime, mAdapterCarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_details);
        mContext = this;
        mSessionPref = AAUtil.getLogInSession(mContext);
        mReservationDetailsController = new ControllerRenterReservationDetails(this);
        mSelectedSummaryItem = getIntent().getParcelableExtra(getString(R.string.parcel_selected_reservation_summary_item));
        Log.d(LOG_TAG, METHOD_NAME + ": selected reservation item id: " + mSelectedSummaryItem.getReservationID());
        mReservation = mReservationDetailsController.getReservationByID(mSelectedSummaryItem.getReservationID());
        mSelectedStartDate = mReservation.getStartDateTime();
        mSelectedEndDate = mReservation.getEndDateTime();
        addDataToSession(AAUtil.formatDate(mSelectedStartDate, AAUtil.DATE_FORMAT_YYYY_MM_DD), AAUtil.formatDate(mSelectedEndDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));

        createGUIViews();
        setUpReservationDetailsGUI();
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(RenterReservationDetailsActivity.this);
    }

    private void createGUIViews(){
        mTVReservationID = findViewById(R.id.rdReservationIDTextView);
        mTVAAAMemStatus = findViewById(R.id.rdAAMemberStatusTextView);
        mTVCarNumber = findViewById(R.id.rdCarNumberTextView);
        mTVCarCapacity = findViewById(R.id.rdCarCapacityTextView);
        mETNumOfRiders = findViewById(R.id.rdNumOfRidersEditText);
        mTVTotalPrice = findViewById(R.id.rdTotalPriceTextView);
        mSwitchGPS = findViewById(R.id.switchRDGPS);
        mSwitchXM = findViewById(R.id.switchRDXM);
        mSwitchOnStar = findViewById(R.id.switchRDOnStar);


        mSpinnerCarName = findViewById(R.id.rdSpinnerCarName);
        mAdapterCarName = ArrayAdapter.createFromResource(mContext, R.array.aaCarNames, android.R.layout.simple_spinner_item);
        setUpSpinner(mSpinnerCarName, mAdapterCarName);
        mSpinnerCarName.setSelection(mAdapterCarName.getPosition(AAUtil.carNameEnumToStr(mReservation.getCarName())));

        mStartTime = AAUtil.getUserFriendlyTimeFromCalendarDate(mSelectedStartDate);
        mSpinnerStartTime = findViewById(R.id.rdSpinnerStartTime);
        mAdapterStartTime = getArrayAdapterByDayOfWeek(mSelectedStartDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerStartTime, mAdapterStartTime);
        mSpinnerStartTime.setSelection(mAdapterStartTime.getPosition(mStartTime));

        mEndTime = AAUtil.getUserFriendlyTimeFromCalendarDate(mSelectedEndDate);
        mSpinnerEndTime = findViewById(R.id.rdSpinnerEndTime);
        mAdapterEndTime = getArrayAdapterByDayOfWeek(mSelectedEndDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerEndTime, mAdapterEndTime);
        mSpinnerEndTime.setSelection(mAdapterEndTime.getPosition(mEndTime));

        // rdBtnStartDate
        mBtnStartDate = findViewById(R.id.rdBtnStartDate);
        mBtnEndDate = findViewById(R.id.rdBtnEndDate);
    }

    private void setUpReservationDetailsGUI(){
        mTVReservationID.setText(mReservation.getReservationID().trim());
        mTVAAAMemStatus.setText(AAUtil.aaaMemberStatusIntToStr(mReservation.getAaaMemStatus()));
        mTVCarNumber.setText(Integer.toString(mSelectedSummaryItem.getCarNumber()));

        mCarCapacity = mReservation.getCarCapacity();
        mTVCarCapacity.setText(Integer.toString(mCarCapacity));
        mETNumOfRiders.setText(Integer.toString(mReservation.getNumOfRiders()));

        mTVTotalPrice.setText(AAUtil.getAmountInCurrency(mReservation.getTotalPrice(), AAUtil.USD_CURRENCY_FORMAT));

        // switch
        gpsSelected = AAUtil.intToBool(mReservation.getGps());
        xmSelected = AAUtil.intToBool(mReservation.getXm());
        onStarSelected = AAUtil.intToBool(mReservation.getOnStar());
        mSwitchGPS.setChecked(gpsSelected);
        mSwitchXM.setChecked(xmSelected);
        mSwitchOnStar.setChecked(onStarSelected);
        mSwitchGPS.setOnCheckedChangeListener(this);
        mSwitchXM.setOnCheckedChangeListener(this);
        mSwitchOnStar.setOnCheckedChangeListener(this);

        // button
        mBtnStartDate.setText(AAUtil.formatDate(mSelectedStartDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        mBtnEndDate.setText(AAUtil.formatDate(mSelectedEndDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
    }

    public void cancelReservationBtnOnClickHandler(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel this reservation?If Yes, you will be redirected back to your Home Screen.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancelReservationAndNavigateToTargetScreen();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    public void cancelReservationAndNavigateToTargetScreen(){
        if(mReservationDetailsController.cancelReservation(mReservation.getReservationID())){
            Intent intent = new Intent(mContext, RenterHomeScreen.class);
            mContext.startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final String METHOD_NAME = "onItemSelected()";
        if(adapterView.getId() == R.id.rdSpinnerCarName){
            selectedCarName = AAUtil.carNameStrToEnum(adapterView.getItemAtPosition(i).toString().trim());
            mCarCapacity = AAUtil.getCarCapcityByName(selectedCarName);
            mTVCarCapacity.setText(Integer.toString(mCarCapacity));
        }else if(adapterView.getId() == R.id.rdSpinnerStartTime){
            mSelectedStartTime = adapterView.getItemAtPosition(i).toString().trim();
        }else if(adapterView.getId() == R.id.rdSpinnerEndTime){
            mSelectedEndTime = adapterView.getItemAtPosition(i).toString().trim();
        }
        Log.d(LOG_TAG, METHOD_NAME + ": selected car: " + AAUtil.carNameEnumToStr(selectedCarName));
        Log.d(LOG_TAG, METHOD_NAME + ": selected start time: " + mSelectedStartTime);
        Log.d(LOG_TAG, METHOD_NAME + ": selected end time: " + mSelectedEndTime);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(compoundButton == mSwitchGPS){
            if(isChecked){
                gpsSelected = true;
            }else{
                gpsSelected = false;
            }
        }else if(compoundButton == mSwitchXM){
            if(isChecked){
                xmSelected = true;
            }else{
                xmSelected = false;
            }
        }else {
            if(isChecked){
                onStarSelected = true;
            }else{
                onStarSelected = false;
            }
        }
    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(RenterReservationDetailsActivity.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(RenterReservationDetailsActivity.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(RenterReservationDetailsActivity.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
    }

    private void resetStartTimeSpinner(){
        mAdapterStartTime = getArrayAdapterByDayOfWeek(mSelectedStartDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerStartTime, mAdapterStartTime);
    }

    private void resetEndTimeSpinner(){
        mAdapterEndTime = getArrayAdapterByDayOfWeek(mSelectedEndDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerEndTime, mAdapterEndTime);
    }

    private void addDataToSession(String startDate, String endDate){
        SharedPreferences.Editor session = mSessionPref.edit();
        session.putString(getString(R.string.session_reservation_details_start_date_yyyy_mm_dd), startDate);
        session.putString(getString(R.string.session_reservation_details_end_date_yyyy_mm_dd), endDate);
        session.commit();
    }

    public void btnUpdateReservationOnClickHandler(View view) {

    }

    public void showRDStartDatePicker(View view) {
        RRDStartDatePickerFragment newFragment = new RRDStartDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "reservationDetailsStartDatePicker");
    }

    public void showRDEndDatePicker(View view) {
        RRDEndDatePickerFragment newFragment = new RRDEndDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "reservationDetailsEndDatePicker");
    }

    public void processRDStartDatePickerResult(int year, int month, int day){
        mSelectedStartDate.clear();
        mSelectedStartDate.set(year, month, day);
        setDateButtonLabel(mBtnStartDate, AAUtil.formatDate(mSelectedStartDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        resetStartTimeSpinner();
    }

    public void processRDEndDatePickerResult(int year, int month, int day){
        mSelectedEndDate.clear();
        mSelectedEndDate.set(year, month, day);
        setDateButtonLabel(mBtnEndDate, AAUtil.formatDate(mSelectedEndDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        resetEndTimeSpinner();
    }

    private void setDateButtonLabel(Button btn, String dateLabel){
        btn.setText(dateLabel);
    }
}