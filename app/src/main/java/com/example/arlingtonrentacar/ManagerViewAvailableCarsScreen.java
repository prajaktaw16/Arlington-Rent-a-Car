package com.example.arlingtonrentacar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ManagerViewAvailableCarsScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView dateView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    //private TextView startDateTextView;
    private java.util.Calendar startDate;
    private Spinner spinnerStartTime;
    private ArrayAdapter<CharSequence> arrayAdapterStartTime;
    private String startTime;
    private String carName;
    private Button mBtnStartDate;
    private String start_Date_Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_available_cars_screen);
        startDate = java.util.Calendar.getInstance();
        startTime = "";
        dateView=(TextView)findViewById(R.id.dateTv);
        spinnerStartTime = findViewById(R.id.spinner3);
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(java.util.Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);
        setUpDate(dateView, startDate);
    }

    public void viewAvailableCar(View view) {
        //this.carName = ((EditText)findViewById(R.id.car_name)).getText().toString();
        ManagerViewAvailableCarsController viewAvailCars = new ManagerViewAvailableCarsController(this);
        viewAvailCars.searchCars(startDate,startTime);
       /* start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
        Log.d("ManagerViewAvailableCarsScreen", "startDate:"+start_Date_Time);
        Intent intent = new Intent(this, ManagerViewAvailableCarSummaryScreen.class);
        intent.putExtra("start_Date_Time", start_Date_Time);
        startActivity(intent);*/
    }
    public void showStartDatePicker(View view) {
        DialogFragment startDateFragment = new ManagerAvailCarDatePicker();
        startDateFragment.show(getSupportFragmentManager(), getString(R.string.rcStartDatePicker));
    }

    private void setUpDate(TextView targetDateTextView, java.util.Calendar calendar){
        String dateStr = formatDateAsMMDDYYYY(calendar);
        targetDateTextView.setText(dateStr);
    }

    public void processStartDatePickerResult(int year, int month, int day){
        startDate.set(year, month, day);
        setUpDate(dateView, startDate);
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(ManagerViewAvailableCarsScreen.this);
    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == java.util.Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(ManagerViewAvailableCarsScreen.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == java.util.Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(ManagerViewAvailableCarsScreen.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(ManagerViewAvailableCarsScreen.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
    }

    private String formatDateAsMMDDYYYY(java.util.Calendar calendar){
        int year = calendar.get(java.util.Calendar.YEAR);
        int month = calendar.get(java.util.Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String yearStr = Integer.toString(year);
        String monthStr = Integer.toString(month + 1);
        String dayStr = Integer.toString(day);

        String dateStr = (monthStr + "/" + dayStr + "/" + yearStr);
        return dateStr;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner3){
            startTime = adapterView.getItemAtPosition(i).toString();
            // Log.d("ManagerSearchcarSummary", "startTime:"+startTime);
        }
//        }else if(adapterView.getId() == R.id.spinner_end_time){
//            endTime = adapterView.getItemAtPosition(i).toString();
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_requested_car_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.rcd_menu_logout){
            AAUtil.logout(this);
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}