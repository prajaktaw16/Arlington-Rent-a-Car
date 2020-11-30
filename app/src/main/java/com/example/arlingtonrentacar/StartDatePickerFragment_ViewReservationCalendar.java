package com.example.arlingtonrentacar;

        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.DialogFragment;
        import androidx.fragment.app.Fragment;
        import android.widget.DatePicker;

        import com.example.arlingtonrentacar.manager.View_Reservation_Calendar;

        import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartDatePickerFragment_ViewReservationCalendar extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create new instance of DatePicker and return in
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        View_Reservation_Calendar activity = (View_Reservation_Calendar) getActivity();
        activity.processStartDatePickerResult(year, month, day);
    }
}