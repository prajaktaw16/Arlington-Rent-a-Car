package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.systemControllers.RegisterController;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();
    private String state;
    private Role role;
    private AAAMemberStatus aaaMemberStatus;
    private Spinner spinnerStates, spinnerRoles, spinnerAAMemStatus;
    private ArrayAdapter<CharSequence> adapterStates, adapterRoles, adapterAAMemStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        aaaMemberStatus = AAAMemberStatus.NO;

        spinnerStates = findViewById(R.id.spinner_states);
        adapterStates = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        setUpSpinner(spinnerStates, adapterStates);

        spinnerRoles = findViewById(R.id.spinner_role);
        adapterRoles = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        setUpSpinner(spinnerRoles, adapterRoles);

        spinnerAAMemStatus = findViewById(R.id.spinner_aaa_status);
        adapterAAMemStatus = ArrayAdapter.createFromResource(this, R.array.aaa_status, android.R.layout.simple_spinner_item);
        setUpSpinner(spinnerAAMemStatus, adapterAAMemStatus);

    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(RegisterActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner_states) {
            state = adapterView.getItemAtPosition(i).toString();
        }else if(adapterView.getId() == R.id.spinner_role){
            role = AAUtil.roleStrToEnum(adapterView.getItemAtPosition(i).toString());
            setAaaMemberStatusVisibility(role);
        }else if(adapterView.getId() == R.id.spinner_aaa_status){
            aaaMemberStatus = AAUtil.aaaMemberStatusStrToEnum(adapterView.getItemAtPosition(i).toString());
        }
        Log.d(LOG_TAG, "State: " + state);
        Log.d(LOG_TAG, "Role: " + role);
        Log.d(LOG_TAG, "AAA Member Status: " + AAUtil.aaaMemberStatusEnumToInt(aaaMemberStatus));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setAaaMemberStatusVisibility(Role role){
        TextView tvAAAMemStatus = findViewById(R.id.aaaMemStatLabel);
        Spinner spinnerAAAMemStatus = findViewById(R.id.spinner_aaa_status);
        if(role == Role.RENTER){
            tvAAAMemStatus.setVisibility(View.VISIBLE);
            spinnerAAAMemStatus.setVisibility(View.VISIBLE);
        }else{
            tvAAAMemStatus.setVisibility(View.INVISIBLE);
            spinnerAAAMemStatus.setVisibility(View.INVISIBLE);
            aaaMemberStatus = AAAMemberStatus.NO;
        }
    }

    public void registerUser(View view) {
        final String METHOD_NAME = "registerUser()";
        EditText etUsername, etPassword, etFirstName, etLastName, etUTAID, etEmail, etPhone, etAddress,
                etCity, etZip;
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etLastName = findViewById(R.id.et_lastName);
        etFirstName = findViewById(R.id.et_firstName);
        etUTAID = findViewById(R.id.et_utaid);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_street_address);
        etCity = findViewById(R.id.et_city);
        etZip = findViewById(R.id.et_zipcode);

        String selectedState = this.state;
        String selectedRole = AAUtil.roleEnumToStr(this.role);
        int selectedAAStat = AAUtil.aaaMemberStatusEnumToInt(this.aaaMemberStatus);
        int userStatus = 0; // 0 - revoked, because Manager, and Admin does not use this
        if(this.role == Role.RENTER){
            userStatus = 1; // 1 - active, because this is checked during renting car
        }
        Log.d(LOG_TAG, METHOD_NAME + ": state = " + state);
        Log.d(LOG_TAG, METHOD_NAME + ": role = " + selectedRole);
        Log.d(LOG_TAG, METHOD_NAME + ": aaa mem status = " + selectedAAStat);
        Log.d(LOG_TAG, METHOD_NAME + ": user status = " + userStatus);

        RegisterController registerController = new RegisterController(this);
        registerController.register(
                etUsername.getText().toString().trim(),
                etPassword.getText().toString().trim(),
                etLastName.getText().toString().trim(),
                etFirstName.getText().toString().trim(),
                selectedRole,
                etUTAID.getText().toString().trim(),
                etPhone.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                etAddress.getText().toString().trim(),
                etCity.getText().toString().trim(),
                selectedState,
                etZip.getText().toString().trim(),
                selectedAAStat,
                userStatus
        );
    }
}