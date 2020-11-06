package com.example.arlingtonrentacar.manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RequestCarActivity;
import com.example.arlingtonrentacar.StartDatePickerFragmentRC;
import com.example.arlingtonrentacar.StartDatePickerFragment_ViewReservationCalendar;
import com.example.arlingtonrentacar.database.Reservations;

import java.util.ArrayList;
import java.util.Calendar;

public class View_Reservation_Calendar extends AppCompatActivity implements ViewReservationCalendar_list_Adapter.ReservationListListener, AdapterView.OnItemSelectedListener {

    ArrayList<Reservations> reservationsData = new ArrayList<>();
    Reservations reservationsObj;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView startDateTextView;
    private Calendar startDate;
    private Spinner spinnerStartTime;
    private ArrayAdapter<CharSequence> arrayAdapterStartTime;
    private String startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservations_summary_screen);
        recyclerView = findViewById(R.id.calendar_list);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new ViewReservationCalendar_list_Adapter( reservationsData, this);
        recyclerView.setAdapter(mAdapter);

        for(int i=1; i<4; i++) {
            reservationsObj = new Reservations();
            reservationsObj.setEndDate(i+"1/2/2020");
            reservationsObj.setEndTime("20PM");
            reservationsObj.setStartDate(i+"0/2/2020");
            reservationsObj.setStartTime("1"+i+"PM");
            if(i==1){
                reservationsObj.setCarName("Smart");
            }else{
                reservationsObj.setCarName("Economy");
            }

            reservationsData.add(reservationsObj);
        }


        startDate = Calendar.getInstance();
        startTime = "";
        startDateTextView = findViewById(R.id.startDate_Textview);
        spinnerStartTime = findViewById(R.id.startTime_spinner);
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);

        setUpDate(startDateTextView, startDate);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.vrc_menu_logout){
            Toast.makeText(this, "Logout Menu Clicked", Toast.LENGTH_LONG).show();
            return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

    public void onSubmitClick(View view){
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onReservationListClick(int position) {
        Intent intent = new Intent(this, View_Reservation_Details.class);
        startActivity(intent);
    }

    public void showStartDatePicker(View view) {
        DialogFragment startDateFragment = new StartDatePickerFragment_ViewReservationCalendar();
        startDateFragment.show(getSupportFragmentManager(), getString(R.string.rcStartDatePicker));
    }

    private void setUpDate(TextView targetDateTextView, Calendar calendar){
        String dateStr = formatDateAsMMDDYYYY(calendar);
        targetDateTextView.setText(dateStr);
    }

    public void processStartDatePickerResult(int year, int month, int day){
        startDate.set(year, month, day);
        setUpDate(startDateTextView, startDate);
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(View_Reservation_Calendar.this);
    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(View_Reservation_Calendar.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(View_Reservation_Calendar.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(View_Reservation_Calendar.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.startTime_spinner){
            startTime = adapterView.getItemAtPosition(i).toString();
        }
//        }else if(adapterView.getId() == R.id.spinner_end_time){
//            endTime = adapterView.getItemAtPosition(i).toString();
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
