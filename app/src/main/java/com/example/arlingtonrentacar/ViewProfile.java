package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.users.SystemUser;


public class ViewProfile extends AppCompatActivity {
    private String role;
    private SystemUser managerObj;
    private SystemUserDAO systemUserDAO;
    private SharedPreferences sessionPrefs;
    private String username;
    private DatabaseHelper dbHelper;
    private Context ViewProfile_Context;

    private EditText mETPassword, mETLastName, mETFirstName, mETUTAID,
            mETPhone, mETEmail, mETAddress, mETCity, mETState, mETZip, mETUsername;
    private TextView mETRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewProfile_Context = this;
        setContentView(R.layout.activity_view_profile);
        dbHelper = DatabaseHelper.getInstance(ViewProfile_Context);
        sessionPrefs = getSharedPreferences(getString(R.string.sessions_preference_file_key), Context.MODE_PRIVATE);
        username = sessionPrefs.getString(getString(R.string.session_loggedin_username), "");
        role = sessionPrefs.getString(getString(R.string.session_loggedin_user_role),"");
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(this);
//        String userRole = role.getText().toString();
        String userRole = role;
        if(userRole.equalsIgnoreCase("Admin")){
//            hideUpdateButton();
        }
        if(userRole.equalsIgnoreCase("Renter")){
//            setVisbilityForRenterAttr();
        }
        else{
            managerObj = systemUserDAO.getSystemUserByUsername(username);
            initGUIViews();
            setGUIViews();
        }

    }

    private void initGUIViews(){
        // rvp = renter view profile
        mETLastName = findViewById(R.id.editText_lastName3);
        mETFirstName = findViewById(R.id.editText_firstName3);
        mETUsername  = findViewById(R.id.editText_userName3);
        mETPassword = findViewById(R.id.editText_password3);
        mETUTAID = findViewById(R.id.editText_utaID3);
        mETRole = findViewById(R.id.userrole3);
        mETPhone = findViewById(R.id.editText_phone3);
        mETEmail = findViewById(R.id.editText_email3);
        mETAddress = findViewById(R.id.editText_streetAddress3);
        mETCity = findViewById(R.id.editText_city3);
        mETState = findViewById(R.id.editText_state3);
        mETZip = findViewById(R.id.editText_zipCode3);
    }

    private void setGUIViews(){
        mETLastName.setText(managerObj.getLastName());
        mETFirstName.setText(managerObj.getFirstName());
        mETUsername.setText(managerObj.getUsername());
        mETPassword.setText(managerObj.getPassword());
        mETUTAID.setText(Integer.toString(managerObj.getUtaID()));
        mETRole.setText(managerObj.getRole());
        mETPhone.setText(managerObj.getPhone());
        mETEmail.setText(managerObj.getEmail());
        mETAddress.setText(managerObj.getStreetAddress());
        mETCity.setText(managerObj.getCity());
        mETState.setText(managerObj.getState());
        mETZip.setText(managerObj.getZip());
    }

//    public void hideUpdateButton() {
//        Button updateBtn = (Button) findViewById(R.id.update_btn);
//        updateBtn.setVisibility(View.INVISIBLE);
//    }
//    public void setVisbilityForRenterAttr() {
//        TextView aaMemLabel = (TextView) findViewById(R.id.aamemlabel);
//        TextView aaMemval = (TextView) findViewById(R.id.aamemval);
//        TextView userStatusLabel = (TextView) findViewById(R.id.userstatuslabel);
//        TextView userStatVal = (TextView) findViewById(R.id.userstatusval);
//        aaMemLabel.setVisibility(View.VISIBLE);
//        aaMemval.setVisibility(View.VISIBLE);
//        userStatusLabel.setVisibility(View.VISIBLE);
//        userStatVal.setVisibility(View.VISIBLE);
//    }

    public void update(View view){
        systemUserDAO = SystemUserDAO.getInstance(ViewProfile_Context);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to update the profile?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean flag = updateManagerProfile(managerObj);
//                        if deleted successfully redirect and show toast
                        if(flag){
                            Intent intent = new Intent(ViewProfile_Context, ManagerHomeScreen.class);
                            Toast toast = Toast.makeText(ViewProfile_Context, "Profile updated successfully", Toast.LENGTH_SHORT);
                            toast.show();
                            startActivity(intent);
                        }
//                        if failed show only toast
                        else{
                            Toast toast = Toast.makeText(ViewProfile_Context, "Could not update profile!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }

    public boolean updateManagerProfile(SystemUser manager){
        boolean result = false;
        SQLiteDatabase dbHandle = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", mETUsername.getText().toString());
        values.put("password", mETPassword.getText().toString());
        values.put("last_name", mETLastName.getText().toString());
        values.put("first_name", mETFirstName.getText().toString());
        values.put("role", mETRole.getText().toString());
        values.put("uta_id", mETUTAID.getText().toString());
        values.put("phone", mETPhone.getText().toString());
        values.put("email", mETPhone.getText().toString());
        values.put("street_address", mETAddress.getText().toString());
        values.put("city", mETCity.getText().toString());
        values.put("state", mETState.getText().toString());
        values.put("zip_code", mETZip.getText().toString());

        String selection = "username = \""+manager.getUsername()+"\"";
//        String[] selectionArgs = {manager.getUsername()};
        int count = dbHandle.update("system_users", values, selection, null);
//        sql = "update system_users set lastname = \"" + manager+ "
        if(count == 1){ // num of rows affected should be 1, as username is pk
            result = true;
            dbHandle.close();
        }
        return result;
    }

    public void redirectToHome(View view){
        Intent intent = new Intent(this, ManagerHomeScreen.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}