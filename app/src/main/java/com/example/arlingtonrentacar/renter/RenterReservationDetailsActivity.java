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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAReservationModel;
import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RenterHomeScreen;

public class RenterReservationDetailsActivity extends AppCompatActivity {
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
            mTVTotalPrice, mTVAAAMemStatus, mTVStartDate, mTVEndDate, mTVCarNumber ;
    private Spinner mSpinnerStartTime, mSpinnerEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservation_details);
        mContext = this;
        mReservationDetailsController = new ControllerRenterReservationDetails(this);
        mSelectedSummaryItem = getIntent().getParcelableExtra(getString(R.string.parcel_selected_reservation_summary_item));
        Log.d(LOG_TAG, METHOD_NAME + ": selected reservation item id: " + mSelectedSummaryItem.getReservationID());
        mReservation = mReservationDetailsController.getReservationByID(mSelectedSummaryItem.getReservationID());
        createGUIViews();
        setUpReservationDetailsGUI();
    }

    private void createGUIViews(){
        mTVReservationID = findViewById(R.id.rdReservationIDTextView);
    }

    private void setUpReservationDetailsGUI(){
        mTVReservationID.setText(mReservation.getReservationID().trim());

//        mTVCarCapacity.setText(Integer.toString(mSelectedSummaryItem.getCarNumber()));
//        mETNumOfRiders.setText(Integer.toString(mReservation.getNumOfRiders()));
//        mTVCarCapacity.setText(Integer.toString(mReservation.getCarCapacity()));
//        mTVTotalPrice.setText(AAUtil.getAmountInCurrency(mReservation.getTotalPrice(), AAUtil.USD_CURRENCY_FORMAT));
//        mTVAAAMemStatus.setText(AAUtil.aaaMemberStatusIntToStr(mReservation.getAaaMemStatus()));
//        mTVStartDate.setText(AAUtil.formatDate(mReservation.getStartDateTime(), AAUtil.DATE_FORMAT_YYYY_MM_DD));
//        mTVEndDate.setText(AAUtil.formatDate(mReservation.getEndDateTime(), AAUtil.DATE_FORMAT_YYYY_MM_DD));
//        mStartTime = AAUtil.getUserFriendlyTimeFromCalendarDate(mReservation.getStartDateTime());
//        mEndTime = AAUtil.getUserFriendlyTimeFromCalendarDate(mReservation.getEndDateTime());
//        gpsSelected = AAUtil.intToBool(mReservation.getGps());
//        xmSelected = AAUtil.intToBool(mReservation.getXm());
//        onStarSelected = AAUtil.intToBool(mReservation.getOnStar());
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

}