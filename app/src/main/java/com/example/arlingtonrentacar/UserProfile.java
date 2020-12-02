package com.example.arlingtonrentacar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.renter.ControllerRenterViewProfile;
import com.example.arlingtonrentacar.renter.RenterViewProfileActivity;
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
            mETPhone, mETEmail, mETAddress, mETCity, mETState, mETZip;
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
        //call user status only
        setNewRole_GUI();
        setNewUserStatus_GUI();
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
        mTVUserStatus = findViewById(R.id.user_status);
        mAAAmemberStatSpinner = findViewById(R.id.user_aaa_status);
        mAAAmemberStatSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, AAAMemberStatus.values()));
        mRoleSpinner = findViewById(R.id.user_role_spinner);
        mRoleSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Role.values()));
        mTVUserStatus.findViewById(R.id.user_status);

    }

    public void setGUIValues()
    {
        mUsername = mTVUsername.getText().toString().trim();
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(mUsername);
        mETPassword.setText(mUser.getPassword());
        mETFristName.setText(mUser.getFirstName());
        mETLastName.setText(mUser.getLastName());
        mETUTAID.setText(mUser.getUtaID());
        mETPhone.setText(mUser.getPhone());
        mETEmail.setText(mUser.getEmail());
        mETAddress.setText(mUser.getStreetAddress());
        mETCity.setText(mUser.getCity());
        mETZip.setText(mUser.getZip());
        mETState.setText(mUser.getState());
        mETZip.setText(mUser.getZip());
    }
    public void setNewUserStatus_GUI()
    {
        mUsername = mTVUsername.getText().toString().trim();
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(mUsername);
        String user_status_changed = Integer.toString(mUser.getUserStatus());
        mTVUserStatus.setText(user_status_changed);
    }
    public void setNewRole_GUI()
    {
        mUsername = mTVUsername.getText().toString().trim();
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(mUsername);
        String role = mUser.getRole().trim();
        mRoleSpinner.setSelection(getIndex(mRoleSpinner, role));
    }
    private int getIndex(Spinner spinner, String role){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(role)){
                return i;
            }
        }

        return 0;
    }
    public void updateUserProfile()
    {

        //mUser.setUsername(mTVUsername.getText().toString().trim());
        mUser.setPassword(mETPassword.getText().toString().trim());
        mUser.setLastName(mETLastName.getText().toString().trim());
        mUser.setFirstName(mETFristName.getText().toString().trim());
        mUser.setUtaID(Integer.parseInt(mETUTAID.getText().toString().trim()));
        mUser.setPhone(mETPhone.getText().toString());
        mUser.setEmail(mETEmail.getText().toString());
        mUser.setStreetAddress(mETAddress.getText().toString());
        mUser.setCity(mETCity.getText().toString().trim());
        mUser.setState(mETState.getText().toString().trim());
        mUser.setZip(mETZip.getText().toString().trim());
       // mUser.setRole(mRoleSpinner.getSelectedItem().toString().trim());
        mUser.setAaaMemberStatus(AAUtil.aaaMemberStatusEnumToInt(AAUtil.aaaMemberStatusStrToEnum(mAAAmemberStatSpinner.getSelectedItem().toString().trim())));
        String msg;
        if(!mUser.validateData()){
            msg = "Invalid Data";
        }else{
            if(mAdmin_UpdateSelectedUserController.updateSelectedUserProfile(mUser)){
                msg = "Update Successful";
            }else{
                msg = "Update failed. Please try again.";
            }
        }
    }

    public void btnUpdateUserProfileOnClickHandler(View view) {
        //Confirmation box to update
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Update User Profile").setMessage("Are you sure you want to update user profile?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUserProfile();
                        finish();
                        Toast.makeText(UserProfile.this, "Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
    }
    //Change Role
    public void updateUserRole()
    {
        mUser.setUsername(mTVUsername.getText().toString().trim());
        mUser.setRole(mRoleSpinner.getSelectedItem().toString().trim());
        String msg;
        if(!mUser.validateData()){
            msg = "Invalid Data";
        }else{
            if(mAdmin_UpdateSelectedUserController.updateUserRole(mUser)){
                msg = "Update Successful";
            }else{
                msg = "Update failed. Please try again.";
            }
        }
    }
    public void btnChangeRole(View view) {
        //Confirmation box to update role
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Change User Role").setMessage("Are you sure you want to change user role?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateUserRole();
                        setNewRole_GUI();
                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UserProfile.this, "Role Changed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
    }
    //revoke user
    public void revokeUser()
    {
        mUsername = mTVUsername.getText().toString().trim();
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(mUsername);
        Log.d(LOG_TAG, "revokeUser" + ": " + mUser.toString());
        int revoke = 0;
        int user_status = mUser.getUserStatus();
        if (user_status == 1)
        {
            mUser.setUserStatus(revoke);
            mTVUserStatus.setText(Integer.toString(mUser.getUserStatus()));
        }
        String msg;
        if(!mUser.validateData()){
            msg = "Invalid Data";
        }else{
            if(mAdmin_UpdateSelectedUserController.revokeUser(mUser)){
                msg = "Update Successful";
            }else{
                msg = "Update failed. Please try again.";
            }
        }
    }
    public void btnRevokeUser(View view) {
        //Confirmation box to update
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Revoke User").setMessage("Are you sure you want to revoke user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        revokeUser();
                        Intent intent = new Intent(getApplicationContext(),UserProfile.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(UserProfile.this, "User revoked successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_renter_view_profile, menu);
        return true;
    }
}