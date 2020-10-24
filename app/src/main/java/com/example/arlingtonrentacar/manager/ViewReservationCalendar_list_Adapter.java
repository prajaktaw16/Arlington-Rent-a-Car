package com.example.arlingtonrentacar.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;

public class ViewReservationCalendar_list_Adapter extends RecyclerView.Adapter<ViewReservationCalendar_list_Adapter.MyViewHolder> {

    private String[] mDataset;
    private ReservationListListener mreservationListListener;
    private Context context;

    public ViewReservationCalendar_list_Adapter(Context context, String[] myDataset, ReservationListListener reservationListListener){
        this.context = context;
        mDataset = myDataset;
        mreservationListListener = reservationListListener;
    }

    public interface ReservationListListener {
        void onReservationListClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.calendar_items_list, parent, false);
        return new MyViewHolder(v, mreservationListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.carName_textView.setText(mDataset[position]);
        holder.startDate_textView.setText(mDataset[position]);
        holder.startTime_textView.setText(mDataset[position]);
        holder.endDate_textView.setText(mDataset[position]);
        holder.endTime_textView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView carName_textView;
        public TextView startDate_textView;
        public TextView startTime_textView;
        public TextView endDate_textView;
        public TextView endTime_textView;
        ReservationListListener reservationListListener;

        public MyViewHolder(@NonNull View v, ReservationListListener mreservationListListener) {
            super(v);
            carName_textView = v.findViewById(R.id.carname_textview);
            startDate_textView = v.findViewById(R.id.startdate_textview);
            startTime_textView = v.findViewById(R.id.starttime_textview);
            endDate_textView = v.findViewById(R.id.enddate_textview);
            endTime_textView = v.findViewById(R.id.endtime_textview);
            this.reservationListListener = mreservationListListener;
//          attach onclicklistener to the viewholder
            v.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            reservationListListener.onReservationListClick(getAdapterPosition());
        }

    }

}
