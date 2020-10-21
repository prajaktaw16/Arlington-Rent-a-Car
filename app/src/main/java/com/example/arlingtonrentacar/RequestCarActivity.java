package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RequestCarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static String LOG_TAG = RequestCarActivity.class.getSimpleName();
    public static final String EXTRA_NUM_OF_RIDERS = "com.example.arlingtonrentacar.EXTRA_NUM_OF_RIDERS";
    public static final String EXTRA_START_DATE = "com.example.arlingtonrentacar.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.arlingtonrentacar.EXTRA_END_DATE";
    public static final String EXTRA_START_TIME = "com.example.arlingtonrentacar.EXTRA_START_TIME";
    public static final String EXTRA_END_TIME = "com.example.arlingtonrentacar.EXTRA_END_TIME";

    private Calendar startDate, endDate;
    private String startTime, endTime;
    private TextView startDateTextView, endDateTextView;
    private Spinner spinnerStartTime, spinnerEndTime;
    private ArrayAdapter<CharSequence> arrayAdapterStartTime, arrayAdapterEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_car);

        startDate = Calendar.getInstance();
        startTime = "";
        endDate = Calendar.getInstance();
        endTime = "";

        startDateTextView = findViewById(R.id.tvStartDate);
        endDateTextView = findViewById(R.id.tvEndDate);

        spinnerStartTime = findViewById(R.id.spinner_start_time);
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);

        spinnerEndTime = findViewById(R.id.spinner_end_time);
        setViewVisibility(spinnerEndTime, false);

        setUpDate(startDateTextView, startDate);
        clearTextView(endDateTextView);
    }

    public void requestCar(View view) {
        String numOfRiders = ((EditText)findViewById(R.id.etNumberOfRiders)).getText().toString();
        String startDateStr = formatDateAsMMDDYYYY(startDate);
        String endDateStr = formatDateAsMMDDYYYY(endDate);

        Log.d(LOG_TAG, "Num of Riders = " + numOfRiders);
        Log.d(LOG_TAG, "Start Date = " + startDateStr);
        Log.d(LOG_TAG, "Start Time = " + startTime);
        Log.d(LOG_TAG, "End Date = " + endDateStr);
        Log.d(LOG_TAG, "End Time = " + endTime);

        // TODO: Launch the ViewRequestedCar Activity
        // TODO: Pass the above values as extra from this activity to ViewRequestedCar activity
    }

    private void setUpDate(TextView targetDateTextView, Calendar calendar){
        String dateStr = formatDateAsMMDDYYYY(calendar);
        targetDateTextView.setText(dateStr);
        Log.d(LOG_TAG, "Day of the Week: " + calendar.get(Calendar.DAY_OF_WEEK));
    }

    private void clearTextView(TextView textView){
        textView.setText("");
    }

    public void showStartDatePicker(View view) {
        DialogFragment startDateFragment = new StartDatePickerFragmentRC();
        startDateFragment.show(getSupportFragmentManager(), getString(R.string.rcStartDatePicker));
    }

    public void processStartDatePickerResult(int year, int month, int day){
        startDate.set(year, month, day);
        setUpDate(startDateTextView, startDate);
        resetStartDateSpinner();
    }
    private void resetStartDateSpinner(){
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);
        Log.d(LOG_TAG, "resetting start date spinner");
    }

    public void showEndDatePicker(View view) {
        DialogFragment endDateFragment = new EndDatePickerFragmentRC();
        endDateFragment.show(getSupportFragmentManager(), getString(R.string.rcEndDatePicker));
    }
    public void processEndDatePickerResult(int year, int month, int day){
        endDate.set(year, month, day);
        setUpDate(endDateTextView, endDate);
        resetEndDateSpinner();
    }
    private void resetEndDateSpinner(){
        arrayAdapterEndTime = getArrayAdapterByDayOfWeek(endDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerEndTime, arrayAdapterEndTime);
        setViewVisibility(spinnerEndTime, true);
        Log.d(LOG_TAG, "resetting end date spinner");
    }

    private void setViewVisibility(View view, boolean visible){
        if(visible) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner_start_time){
            startTime = adapterView.getItemAtPosition(i).toString();
        }else if(adapterView.getId() == R.id.spinner_end_time){
            endTime = adapterView.getItemAtPosition(i).toString();
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

    private String formatDateAsMMDDYYYY(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month + 1);
        String dayStr = Integer.toString(day);

        String dateStr = (monthStr + "/" + dayStr + "/" + yearStr);
        return dateStr;
    }
}