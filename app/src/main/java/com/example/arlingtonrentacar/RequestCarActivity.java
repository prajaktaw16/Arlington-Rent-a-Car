/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.renter.RequestCarController;

import java.util.Calendar;

public class RequestCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String LOG_TAG = RequestCarActivity.class.getSimpleName();
    private Calendar startDate, endDate;
    private String startTime, endTime;
    private String numOfRiders;
    private Spinner spinnerStartTime, spinnerEndTime;
    private ArrayAdapter<CharSequence> arrayAdapterStartTime, arrayAdapterEndTime;
    private Button mBtnStartDate, mBtnEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_car);

        this.startDate = Calendar.getInstance();
        this.startTime = "";
        this.endDate = Calendar.getInstance();
        this.endDate.add(Calendar.DAY_OF_MONTH, 1);
        this.endTime = "";


        this.spinnerStartTime = findViewById(R.id.spinner_start_time);
        this.arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(this.spinnerStartTime, this.arrayAdapterStartTime);

        this.spinnerEndTime = findViewById(R.id.spinner_end_time);
        this.arrayAdapterEndTime = getArrayAdapterByDayOfWeek(endDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(this.spinnerEndTime, this.arrayAdapterEndTime);

        mBtnStartDate = findViewById(R.id.btnStartDatePicker);
        mBtnEndDate = findViewById(R.id.btnEndDatePicker);
        setDateButtonLabel(mBtnStartDate, AAUtil.formatDate(this.startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        setDateButtonLabel(mBtnEndDate, AAUtil.formatDate(this.endDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
    }

    public void requestCarBtnClickEventHandler(View view){
        this.numOfRiders = ((EditText)findViewById(R.id.etNumberOfRiders)).getText().toString();
        RequestCarController requestCarController = new RequestCarController(this);
        requestCarController.geAvailableCarList(this.numOfRiders, this.startDate, this.startTime, this.endDate, this.endTime);
    }

    public void showStartDatePicker(View view) {
        DialogFragment startDateFragment = new StartDatePickerFragmentRC();
        startDateFragment.show(getSupportFragmentManager(), getString(R.string.rcStartDatePicker));
    }

    public void processStartDatePickerResult(int year, int month, int day){
        this.startDate.clear();
        this.startDate.set(year, month, day);
        setDateButtonLabel(mBtnStartDate, AAUtil.formatDate(this.startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        resetStartDateSpinner();
    }
    private void resetStartDateSpinner(){
        this.arrayAdapterStartTime = getArrayAdapterByDayOfWeek(this.startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(this.spinnerStartTime, this.arrayAdapterStartTime);
        Log.d(LOG_TAG, "resetting start date spinner");
    }

    public void showEndDatePicker(View view) {
        DialogFragment endDateFragment = new EndDatePickerFragmentRC();
        endDateFragment.show(getSupportFragmentManager(), getString(R.string.rcEndDatePicker));
    }
    public void processEndDatePickerResult(int year, int month, int day){
        this.endDate.clear();
        this.endDate.set(year, month, day);
        setDateButtonLabel(mBtnEndDate, AAUtil.formatDate(this.endDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
        resetEndDateSpinner();
    }
    private void resetEndDateSpinner(){
        this.arrayAdapterEndTime = getArrayAdapterByDayOfWeek(this.endDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(this.spinnerEndTime, this.arrayAdapterEndTime);
        Log.d(LOG_TAG, "resetting end date spinner");
    }

    private void setViewVisibility(View view, boolean visible){
        if(visible) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner_start_time){
            this.startTime = adapterView.getItemAtPosition(i).toString().trim();
        }else if(adapterView.getId() == R.id.spinner_end_time){
            this.endTime = adapterView.getItemAtPosition(i).toString().trim();
        }
        Log.d(LOG_TAG, "Start Time: " + startTime);
        Log.d(LOG_TAG, "End Time: " + endTime);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(RequestCarActivity.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(RequestCarActivity.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(RequestCarActivity.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(RequestCarActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.rc_menu_logout){
            AAUtil.logout(this);
        }
        return(super.onOptionsItemSelected(item));
    }

    private void setDateButtonLabel(Button btn, String dateLabel){
        btn.setText(dateLabel);
    }
}