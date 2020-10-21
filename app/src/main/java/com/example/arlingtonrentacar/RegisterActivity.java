package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
        }

        if(adapterView.getId() == R.id.spinner_role){
            role = AAUtil.roleStrToEnum(adapterView.getItemAtPosition(i).toString());
            setAaaMemberStatusVisibility(role);
        }

        if(adapterView.getId() == R.id.spinner_aaa_status){
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
        Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        Log.d(LOG_TAG, "Registration Successful. Redirecting to Login screen.");
    }
}