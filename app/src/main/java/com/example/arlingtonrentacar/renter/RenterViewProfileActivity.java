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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.users.SystemUser;

public class RenterViewProfileActivity extends AppCompatActivity {
    private final String LOG_TAG = RenterViewProfileActivity.class.getSimpleName();
    private SharedPreferences sessionPrefs;
    private String mUsername;
    private SystemUser mRenter;
    private ControllerRenterViewProfile mViewRenterProfileController;
    private Context mContext;

    private EditText mETPassword, mETLastName, mETFristName, mETUTAID,
            mETPhone, mETEmail, mETAddress, mETCity, mETState, mETZip, mETAAAmemberStat;
    private TextView mTVUserStatus, mTVUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_view_profile);
        mContext = this;
        mViewRenterProfileController = new ControllerRenterViewProfile(this);
        sessionPrefs = AAUtil.getLogInSession(this);
        mUsername = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        Log.d(LOG_TAG, METHOD_NAME + ": usermame: " + mUsername);
        mRenter = mViewRenterProfileController.getSystemUserByUsername(mUsername);
        Log.d(LOG_TAG, METHOD_NAME + ": " + mRenter.toString());
        initGUIViews();
        setGUIViews();
    }

    private void initGUIViews(){
        // rvp = renter view profile
        mTVUsername = findViewById(R.id.rvcUsernameTextView);
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
        mTVUsername.setText(mRenter.getUsername());
        mETPassword.setText(mRenter.getPassword());
        mETLastName.setText(mRenter.getLastName());
        mETFristName.setText(mRenter.getFirstName());
        mETUTAID.setText(Integer.toString(mRenter.getUtaID()));
        mETPhone.setText(mRenter.getPhone());
        mETEmail.setText(mRenter.getEmail());
        mETAddress.setText(mRenter.getStreetAddress());
        mETCity.setText(mRenter.getCity());
        mETState.setText(mRenter.getState());
        mETZip.setText(mRenter.getZip());
        mETAAAmemberStat.setText(AAUtil.aaaMemberStatusIntToStr(mRenter.getAaaMemberStatus()));
        mTVUserStatus.setText(AAUtil.intUserStatusToStr(mRenter.getUserStatus()));
    }

    public void btnUpdateProfileOnClickHandler(View view) {
        mRenter.setPassword(mETPassword.getText().toString().trim());
        mRenter.setLastName(mETLastName.getText().toString().trim());
        mRenter.setFirstName(mETFristName.getText().toString().trim());
        mRenter.setUtaID(Integer.parseInt(mETUTAID.getText().toString().trim()));
        mRenter.setPhone(mETPhone.getText().toString());
        mRenter.setEmail(mETEmail.getText().toString());
        mRenter.setStreetAddress(mETAddress.getText().toString());
        mRenter.setCity(mETCity.getText().toString().trim());
        mRenter.setState(mETState.getText().toString().trim());
        mRenter.setZip(mETZip.getText().toString().trim());
        mRenter.setAaaMemberStatus(AAUtil.aaaMemberStatusEnumToInt(AAUtil.aaaMemberStatusStrToEnum(mETAAAmemberStat.getText().toString().trim())));
        String msg;
        if(!mRenter.validateData()){
            msg = "Invalid Data";
        }else{
            if(mViewRenterProfileController.updateRenterProfile(mRenter)){
                msg = "Update Successful";
            }else{
                msg = "Update failed. Please try again.";
            }
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}