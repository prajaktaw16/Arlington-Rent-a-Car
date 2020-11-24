/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;

import java.util.Calendar;
import java.util.LinkedList;

public class RenterReservationsSummaryActivity extends AppCompatActivity {
    private static final String LOG_TAG = RenterReservationsSummaryActivity.class.getSimpleName();
    private SharedPreferences mSessionPrefs;
    private LinkedList<ReservationSummaryItem> mReservationSummaryList;
    private ControllerRenterReservationsSummary mController;
    private RecyclerView mRecyclerView;
    private RenterReservationSummaryListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_reservations_summary);

        mController = new ControllerRenterReservationsSummary(this);
        mSessionPrefs = AAUtil.getLogInSession(this);
        String startDateTime = mSessionPrefs.getString(getString(R.string.sessionStartDateTimeViewReservationFormData), "");
        mReservationSummaryList = mController.generateReservationSummaryItemList(startDateTime);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView = findViewById(R.id.recyclerViewRenterReservationsSummary);
        mAdapter = new RenterReservationSummaryListAdapter(this, mReservationSummaryList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.hasFixedSize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_renter_reservations_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.renterViewReservationsSummaryMenuLogout){
            AAUtil.logout(this);
        }
        return(super.onOptionsItemSelected(item));
    }
}