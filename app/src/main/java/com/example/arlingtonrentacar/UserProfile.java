package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.systemControllers.Admin_UpdateSelectedUserController;
import com.example.arlingtonrentacar.users.SystemUser;

public class UserProfile extends AppCompatActivity {
    private final String LOG_TAG = UserProfile.class.getSimpleName();
    private SharedPreferences sessionPrefs;
    private SystemUser mUser;
    private DatabaseHelper dbHelper;
    private Admin_UpdateSelectedUserController mAdmin_UpdateSelectedUserController;
    private Context mContext;
    private String mUsername;
    private EditText mETPassword, mETLastName, mETFristName, mETUTAID,
            mETPhone, mETEmail, mETAddress, mETCity, mETState, mETZip,mRole;
    private TextView mTVUserStatus, mTVUsername;
    private Spinner mAAAmemberStatSpinner, mRoleSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String METHOD_NAME = "onCreate()";
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);
        mContext = this;
        mAdmin_UpdateSelectedUserController = new Admin_UpdateSelectedUserController(this);
        sessionPrefs = AAUtil.getLogInSession(this);
        mUsername = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        Log.d(LOG_TAG, METHOD_NAME + ": usermame: " + mUsername);//prints current logged in user

        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(mUsername);
         Log.d(LOG_TAG, METHOD_NAME + ": " + mUser.toString());
        initGUIViews();
        setGUIValues();

    }

    private void initGUIViews(){
        // rvp = renter view profile
        mTVUsername = findViewById(R.id.user_name);
        mETPassword = findViewById(R.id.user_pswd);
        mETLastName = findViewById(R.id.user_last_name);
        mETFristName = findViewById(R.id.user_first_name);
        mETUTAID = findViewById(R.id.user_utaid);
        mETPhone = findViewById(R.id.user_phone);
        mETEmail = findViewById(R.id.user_email);
        mETAddress = findViewById(R.id.user_address);
        mETCity = findViewById(R.id.user_city);
        mETState = findViewById(R.id.user_state);
        mETZip = findViewById(R.id.user_zipcode);
        mRole= findViewById(R.id.user_role);

    }

    public void setGUIValues()
    {

        mTVUsername.setText(mUser.getUsername());
        mETPassword.setText(mUser.getPassword());
        mETFristName.setText(mUser.getFirstName());
        mETLastName.setText(mUser.getLastName());
        mETUTAID.setText(String.valueOf(mUser.getUtaID()));
        mETPhone.setText(mUser.getPhone());
        mETEmail.setText(mUser.getEmail());
        mETAddress.setText(mUser.getStreetAddress());
        mETCity.setText(mUser.getCity());
        mETZip.setText(mUser.getZip());
        mETState.setText(mUser.getState());
        mETZip.setText(mUser.getZip());
        mRole.setText(mUser.getRole());
    }
    public void adminHome(View view) {
        Intent intent = new Intent(this, AdminHomeScreen.class);
        startActivity(intent);
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
        }
        return(super.onOptionsItemSelected(item));
    }
}