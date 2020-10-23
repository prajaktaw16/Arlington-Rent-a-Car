package com.example.arlingtonrentacar.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;

public class ViewReservationCalendar_list_Adapter extends RecyclerView.Adapter<ViewReservationCalendar_list_Adapter.MyViewHolder> {

    private String[] mDataset;

    public ViewReservationCalendar_list_Adapter(String[] myDataset){
        mDataset = myDataset;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView carName_textView;
        public TextView startDate_textView;
        public TextView startTime_textView;
        public TextView endDate_textView;
        public TextView endTime_textView;
        public MyViewHolder(@NonNull View v) {
            super(v);
            carName_textView = v.findViewById(R.id.carname_textview);
            startDate_textView = v.findViewById(R.id.startdate_textview);
            startTime_textView = v.findViewById(R.id.starttime_textview);
            endDate_textView = v.findViewById(R.id.enddate_textview);
            endTime_textView = v.findViewById(R.id.endtime_textview);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_items_list, parent, false);
        return new MyViewHolder(v);
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

}
