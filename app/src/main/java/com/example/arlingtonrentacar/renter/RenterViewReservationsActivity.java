/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RequestCarActivity;
import com.example.arlingtonrentacar.StartDatePickerFragmentRC;

import java.util.Calendar;

public class RenterViewReservationsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String LOG_TAG = RenterViewReservationsActivity.class.getSimpleName();
    private Calendar mStartDate;
    private String mStartTime;
    private Spinner mSpinnerStartTime;
    private ArrayAdapter<CharSequence> mArrayAdapterStartTime;
    private Button mBtnStartDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_view_reservations);

        mStartDate = Calendar.getInstance();

        mSpinnerStartTime = findViewById(R.id.spinnerRenterViewReservationStartTime);
        mArrayAdapterStartTime = getArrayAdapterByDayOfWeek(mStartDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerStartTime, mArrayAdapterStartTime);

        mBtnStartDatePicker = findViewById(R.id.btnRenterViewReservationsDatePicker);
        mBtnStartDatePicker.setText(AAUtil.formatDate(mStartDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
    }

    public void renterViewReservShowStarteDatePicker(View view) {
        DialogFragment newFragment = new RenterViewReservationsDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.renterViewReservationsDP));
    }

    public void processDatePickerResult(int year, int month, int day){
        mStartDate.clear();
        mStartDate.set(year, month, day);
        resetStartTimeSpinner();
        setDateButtonLabel(mBtnStartDatePicker, AAUtil.formatDate(mStartDate, AAUtil.DATE_FORMAT_YYYY_MM_DD));
    }

    private ArrayAdapter<CharSequence> getArrayAdapterByDayOfWeek(int dayOfWeek){
        ArrayAdapter<CharSequence> adapter;
        if(dayOfWeek == Calendar.SATURDAY){
            adapter = ArrayAdapter.createFromResource(RenterViewReservationsActivity.this, R.array.saturday_hours, android.R.layout.simple_spinner_item);
        }else if(dayOfWeek == Calendar.SUNDAY){
            adapter = ArrayAdapter.createFromResource(RenterViewReservationsActivity.this, R.array.sunday_hours, android.R.layout.simple_spinner_item);
        }else{
            adapter = ArrayAdapter.createFromResource(RenterViewReservationsActivity.this, R.array.weekday_hours, android.R.layout.simple_spinner_item);
        }
        return adapter;
    }

    private void resetStartTimeSpinner(){
        mArrayAdapterStartTime = getArrayAdapterByDayOfWeek(mStartDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(mSpinnerStartTime, mArrayAdapterStartTime);
    }

    private void setUpSpinner(Spinner spinner, ArrayAdapter<CharSequence> arrayAdapter){
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(RenterViewReservationsActivity.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mStartTime = adapterView.getItemAtPosition(i).toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void renterViewReservationsOnclickHandler(View view) {
        RenterViewReservationsController controller = new RenterViewReservationsController(this);
        controller.viewMyReservations(mStartDate, mStartTime);
    }

    private void setDateButtonLabel(Button btn, String dateLabel){
        btn.setText(dateLabel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_renter_reservations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.renterViewReservationsMenuLogout){
            AAUtil.logout(this);
        }
        return(super.onOptionsItemSelected(item));
    }
}