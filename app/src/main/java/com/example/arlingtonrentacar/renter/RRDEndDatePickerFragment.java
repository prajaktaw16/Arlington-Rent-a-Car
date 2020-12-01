/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RRDEndDatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RRDEndDatePickerFragment  extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RRDEndDatePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RRDEndDatePickerFragment newInstance(String param1, String param2) {
        RRDEndDatePickerFragment fragment = new RRDEndDatePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SharedPreferences sessionPref = AAUtil.getLogInSession(getActivity());
        String startDate = sessionPref.getString(getString(R.string.session_reservation_details_end_date_yyyy_mm_dd), "");
        String[] dateparts = startDate.split("-");
        int year, month, day;
        if(dateparts.length == 3){
            year = Integer.parseInt(dateparts[0]);
            month = Integer.parseInt(dateparts[1]) - 1;
            day = Integer.parseInt(dateparts[2]);
        }else{
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        RenterReservationDetailsActivity activity = (RenterReservationDetailsActivity) getActivity();
        activity.processRDEndDatePickerResult(year, month, day);
    }
}