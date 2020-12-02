package com.example.arlingtonrentacar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arlingtonrentacar.systemControllers.Admin_UpdateSelectedUserController;
import com.example.arlingtonrentacar.users.SystemUser;

public class view_selected_user_profile extends AppCompatActivity {
    private static final String LOG_TAG = "";
    private SystemUser mUser;
    private Admin_UpdateSelectedUserController mAdmin_UpdateSelectedUserController;
    private String username;
    private String password;
    private int utaID;
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String role;
    private int userStatus;

    public TextView username_textview;
    public TextView password_textview;
    public TextView utaid_textview;
    public TextView lastname_textview;
    public TextView firstname_textview;
    public TextView phone_textview;
    public TextView email_textview;
    public TextView streetaddress_textview;
    public TextView city_textview;
    public TextView state_textview;
    public TextView zip_textview;
    public TextView role_textview, userStatus_TV;

    public Spinner mRoleSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_selected_user_profile);
        getGUI();
        mAdmin_UpdateSelectedUserController = new Admin_UpdateSelectedUserController(this);
        Intent intent = new Intent();
        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        utaID = getIntent().getExtras().getInt("uta_id");
        lastName = getIntent().getExtras().getString("lastname");
        firstName = getIntent().getExtras().getString("firstname");
        phone = getIntent().getExtras().getString("phone");
        email = getIntent().getExtras().getString("email");
        streetAddress = getIntent().getExtras().getString("streetaddress");
        city = getIntent().getExtras().getString("city");
        state = getIntent().getExtras().getString("state");
        zip = getIntent().getExtras().getString("zip");
        role = getIntent().getExtras().getString("role");
        userStatus = getIntent().getExtras().getInt("status");
        mRoleSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, Role.values()));
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(username);
        userStatus = mUser.getUserStatus();
        Log.d(LOG_TAG, "status" + ": " + mUser.getUserStatus());
        setRole_GUI();
        setGUI();
    }

    public void setGUI() {
        username_textview.setText(username);
        password_textview.setText(password);
        utaid_textview.setText(String.valueOf(utaID));
        lastname_textview.setText(lastName);
        firstname_textview.setText(firstName);
        phone_textview.setText(phone);
        email_textview.setText(email);
        streetaddress_textview.setText(streetAddress);
        city_textview.setText(city);
        state_textview.setText(state);
        zip_textview.setText(zip);
        //role_textview.setText(role);
        userStatus_TV.setText(String.valueOf(userStatus));
        //userStatus_TV.setText("Inactive");
    }

    public void getGUI() {
        username_textview = findViewById(R.id.editText_userName3);
        password_textview = findViewById(R.id.editText_password3);
        utaid_textview = findViewById(R.id.editText_utaID3);
        lastname_textview = findViewById(R.id.editText_lastName3);
        firstname_textview = findViewById(R.id.editText_firstName3);
        phone_textview = findViewById(R.id.editText_phone3);
        email_textview = findViewById(R.id.editText_email3);
        streetaddress_textview = findViewById(R.id.editText_streetAddress3);
        city_textview = findViewById(R.id.editText_city3);
        state_textview = findViewById(R.id.editText_state3);
        zip_textview = findViewById(R.id.editText_zipCode3);
        role_textview = findViewById(R.id.userrole3);
        mRoleSpinner = findViewById(R.id.role_spinner);
        userStatus_TV = findViewById(R.id.user_status);

    }

    public void setUpdateGUI() {

        Intent intent = new Intent(this, view_selected_user_profile.class);
        intent.putExtra("username", mUser.getUsername());
        Log.d(LOG_TAG, "setUdateGUI" + ": " + mUser.getUsername());
        intent.putExtra("password", mUser.getPassword());
        intent.putExtra("uta_id", String.valueOf(mUser.getUtaID()));
        intent.putExtra("lastname", mUser.getLastName());
        intent.putExtra("firstname", mUser.getFirstName());
        intent.putExtra("phone", mUser.getPhone());
        intent.putExtra("email", mUser.getEmail());
        intent.putExtra("streetaddress", mUser.getStreetAddress());
        intent.putExtra("city", mUser.getCity());
        intent.putExtra("state", mUser.getState());
        intent.putExtra("zip", mUser.getZip());
        intent.putExtra("role", mUser.getRole());
        intent.putExtra("status", String.valueOf(mUser.getUserStatus()));
        startActivity(intent);

    }

    private int getIndex(Spinner spinner, String role) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(role)) {
                return i;
            }
        }
        return 0;
    }

    public void setRole_GUI() {

        // mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(username);
        String role = mUser.getRole().trim();
        mRoleSpinner.setSelection(getIndex(mRoleSpinner, role));
    }

    public void updateUserRole() {
        // mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(username);
        mUser.setRole(mRoleSpinner.getSelectedItem().toString().trim());
        String msg;
        if (!mUser.validateData()) {
            msg = "Invalid Data";
        } else {
            if (mAdmin_UpdateSelectedUserController.updateUserRole(mUser)) {
                msg = "Update Successful";
                setUpdateGUI();
            } else {
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
                        //Intent intent = new Intent(getApplicationContext(), view_selected_user_profile.class);
                        //startActivity(intent);
                        finish();
                        Toast.makeText(view_selected_user_profile.this, "Role Changed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
    }

    //Revoke User
    public void revokeUser() {
        //mUsername = mTVUsername.getText().toString().trim();
        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(username);
        Log.d(LOG_TAG, "revokeUser" + ": " + mUser.toString());
        int revoke = 0;
        int user_status = mUser.getUserStatus();
        if (user_status == 1) {
            mUser.setUserStatus(revoke);
            userStatus_TV.setText(Integer.toString(mUser.getUserStatus()));
           // userStatus_TV.setText("Inactive");
        }
        String msg;
        if (!mUser.validateData()) {
            msg = "Invalid Data";
        } else {
            if (mAdmin_UpdateSelectedUserController.revokeUser(mUser)) {
                msg = "Update Successful";
                setUpdateGUI();
            } else {
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
                        //Intent intent = new Intent(getApplicationContext(), view_selected_user_profile.class);
                        //startActivity(intent);
                        finish();
                        Toast.makeText(view_selected_user_profile.this, "User revoked successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
    }

    public void updateUserProfile() {

        mUser = mAdmin_UpdateSelectedUserController.getSystemUserByUsername(username);
        mUser.setPassword(password_textview.getText().toString().trim());
        mUser.setLastName(lastname_textview.getText().toString().trim());
        mUser.setFirstName(firstname_textview.getText().toString().trim());
         mUser.setUtaID(Integer.parseInt(utaid_textview.getText().toString().trim()));
        mUser.setPhone(phone_textview.getText().toString());
        mUser.setEmail(email_textview.getText().toString());
        mUser.setStreetAddress(streetaddress_textview.getText().toString());
        mUser.setCity(city_textview.getText().toString().trim());
        mUser.setState(state_textview.getText().toString().trim());
        mUser.setZip(zip_textview.getText().toString().trim());
        // mUser.setRole(mRoleSpinner.getSelectedItem().toString().trim());
        // mUser.setAaaMemberStatus(AAUtil.aaaMemberStatusEnumToInt(AAUtil.aaaMemberStatusStrToEnum(mAAAmemberStatSpinner.getSelectedItem().toString().trim())));
        String msg;
        if (!mUser.validateData()) {
            msg = "Invalid Data";
        } else {
            if (mAdmin_UpdateSelectedUserController.updateSelectedUserProfile(mUser)) {
                msg = "Update Successful";
                setUpdateGUI();
            } else {
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
                        //Intent intent = new Intent(getApplicationContext(), view_selected_user_profile.class);
                        //startActivity(intent);
                        finish();
                        Toast.makeText(view_selected_user_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("No", null).show();
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


