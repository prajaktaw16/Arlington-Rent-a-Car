/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.users.SystemUser;

public class RenterViewProfileActivity extends AppCompatActivity {
    private final String LOG_TAG = RenterViewProfileActivity.class.getSimpleName();
    private SharedPreferences sessionPrefs;
    private String mUsername;
    private SystemUser mSystemUser;
    private ControllerRenterViewProfile mViewProfileController;
    private Context mContext;

    private EditText mETUsername, mETPassword, mETLastName, mETFristName, mETUTAID,
            mETPhone, mETEmail, mETAddress, mETCity, mETState, mETZip, mETAAAmemberStat;
    private TextView mTVUserStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_view_profile);
        mContext = this;
        mViewProfileController = new ControllerRenterViewProfile(this);
        sessionPrefs = AAUtil.getLogInSession(this);
        mUsername = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        Log.d(LOG_TAG, METHOD_NAME + ": usermame: " + mUsername);
        mSystemUser = mViewProfileController.getSystemUserByUsername(mUsername);
        Log.d(LOG_TAG, METHOD_NAME + ": " + mSystemUser.toString());

        initGUIViews();
        setGUIViews();
    }

    private void initGUIViews(){
        // rvp = renter view profile
        mETUsername = findViewById(R.id.rvpUsernameEditText);
        mETPassword = findViewById(R.id.rvpPasswordEditText);
        mETLastName = findViewById(R.id.rvpLastNameEditText);
        mETFristName = findViewById(R.id.rvpFirstNameEditText);
        mETUTAID = findViewById(R.id.rvpUTAIDEditText);
        mETPhone = findViewById(R.id.rvpPhoneEditText);
        mETEmail = findViewById(R.id.rvpEmailEditText);
        mETAddress = findViewById(R.id.rvpAddressEditText);
        mETCity = findViewById(R.id.rvpCityEditText);
        mETState = findViewById(R.id.rvpStateEditText);
        mETZip = findViewById(R.id.rvpZipEditText);
        mTVUserStatus = findViewById(R.id.rvcUserStatusTextView);
        mETAAAmemberStat = findViewById(R.id.rvpAAMemberEditText);
    }

    private void setGUIViews(){
        mETUsername.setText(mSystemUser.getUsername());
        mETPassword.setText(mSystemUser.getPassword());
        mETLastName.setText(mSystemUser.getLastName());
        mETFristName.setText(mSystemUser.getFirstName());
        mETUTAID.setText(Integer.toString(mSystemUser.getUtaID()));
        mETPhone.setText(mSystemUser.getPhone());
        mETEmail.setText(mSystemUser.getEmail());
        mETAddress.setText(mSystemUser.getStreetAddress());
        mETCity.setText(mSystemUser.getCity());
        mETState.setText(mSystemUser.getState());
        mETZip.setText(mSystemUser.getZip());
        mETAAAmemberStat.setText(AAUtil.aaaMemberStatusIntToStr(mSystemUser.getAaaMemberStatus()));
        mTVUserStatus.setText(AAUtil.intUserStatusToStr(mSystemUser.getUserStatus()));
    }
}