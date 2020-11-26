package com.example.arlingtonrentacar.manager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.StartDatePickerFragment_ViewReservationCalendar;
import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.database.ReservationsDAO;
import com.example.arlingtonrentacar.database.SystemUserDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    private String start_Date_Time;
    private ReservationsDAO reservationsDAO;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservations_summary_screen);
        recyclerView = findViewById(R.id.calendar_list);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new ViewReservationCalendar_list_Adapter( reservationsData, this);
        recyclerView.setAdapter(mAdapter);
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//      todo: update query after changes to the DB table structure

//        String reservations_query =  "select * from reservations order by start_date_time desc, car_name asc;";
//        Cursor cursor =  db.rawQuery(reservations_query,null);
        startDate = Calendar.getInstance();
        startTime = "";
        startDateTextView = findViewById(R.id.startDate_Textview);
        spinnerStartTime = findViewById(R.id.startTime_spinner);
        arrayAdapterStartTime = getArrayAdapterByDayOfWeek(startDate.get(Calendar.DAY_OF_WEEK));
        setUpSpinner(spinnerStartTime, arrayAdapterStartTime);
        setUpDate(startDateTextView, startDate);

        reservationsDAO = ReservationsDAO.getInstance(this);
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
        start_Date_Time = AAUtil.formatDate(startDate, AAUtil.DATE_FORMAT_YYYY_MM_DD) +" "+ startTime.split(" ")[0];
        cursor = reservationsDAO.viewReservations(start_Date_Time);
        if(cursor.moveToFirst()){
            do{
                reservationsObj = new Reservations();
                reservationsObj.setReservationID(cursor.getString(cursor.getColumnIndex("reservation_id")));
                reservationsObj.setLastname(cursor.getString(cursor.getColumnIndex("last_name")));
                reservationsObj.setFirstname(cursor.getString(cursor.getColumnIndex("first_name")));
                reservationsObj.setCarName(cursor.getString(cursor.getColumnIndex("car_name")));
                reservationsObj.setCarCapacity(Integer.parseInt(cursor.getString(cursor.getColumnIndex("car_capacity"))));
                String start_date_time = cursor.getString(cursor.getColumnIndex("start_date_time"));
                String end_date_time = cursor.getString(cursor.getColumnIndex("end_date_time"));
                String start_date = start_date_time.split(" ")[0];
                String start_time = start_date_time.split(" ")[1];
                String end_date = end_date_time.split(" ")[0];
                String end_time = end_date_time.split(" ")[1];
                reservationsObj.setStartDate(start_date);
                reservationsObj.setStartTime(start_time);
                reservationsObj.setEndDate(end_date);
                reservationsObj.setEndTime(end_time);
                reservationsObj.setNumberOfRiders(Integer.parseInt(cursor.getString(cursor.getColumnIndex("num_of_riders"))));
                reservationsObj.setTotalPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex("total_price"))));
                reservationsObj.setGps(Integer.parseInt(cursor.getString(cursor.getColumnIndex("gps"))));
                reservationsObj.setSiriusxm(Integer.parseInt(cursor.getString(cursor.getColumnIndex("siriusxm"))));
                reservationsObj.setOnstar(Integer.parseInt(cursor.getString(cursor.getColumnIndex("onstar"))));
                reservationsObj.setAaaMemberStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex("aaa_member_status"))));

                reservationsData.add(reservationsObj);
            }while(cursor.moveToNext());
        }
        else{
            Toast toast = Toast.makeText(this, "No Reservations found for this date and time", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onReservationListClick(int position) {
        Intent intent = new Intent(this, View_Reservation_Details.class);
        intent.putExtra("ReservationsDataObj", reservationsData.get(position));
        intent.putExtra("Car_Number", position+1);
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
