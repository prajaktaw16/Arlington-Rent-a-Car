package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.database.CarDetails;
import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.manager.ViewReservationCalendar_list_Adapter;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private final SearchCarListListener mListListener;
    private ArrayList<CarDetails> carDetails;
    private Context mcontext;


    public RecylerViewAdapter(ArrayList<CarDetails> carDetails, SearchCarListListener mListListener) {
        this.carDetails = carDetails;
        this.mListListener = mListListener;
        this.mcontext = mcontext;
    }
    public interface SearchCarListListener {
        void onSearchCarListClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_search_car_listtem,parent, false);
        ViewHolder holder= new ViewHolder(view,mListListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.carName_textView.setText(carDetails.get(position).getCarName());
        holder.startDate_textView.setText(carDetails.get(position).getCarNumber());
        holder.startTime_textView.setText(carDetails.get(position).getCarCapacity());
        holder.endDate_textView.setText(carDetails.get(position).getCarCostPerDay());
        holder.endTime_textView.setText(carDetails.get(position).getCarStatus());

        holder.carName_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcontext.startActivity(new Intent(mcontext, ManagerViewCarDetailScreen.class));
            }
        });

    }

    @Override
    public int getItemCount() {

        return carDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView carName_textView;
        public TextView startDate_textView;
        public TextView startTime_textView;
        public TextView endDate_textView;
        public TextView endTime_textView;
        SearchCarListListener rearchCarListListener;
        public ViewHolder(@NonNull View itemView, SearchCarListListener mSearchCarListListener) {

            super(itemView);
            carName_textView = itemView.findViewById(R.id.car_name);
            startDate_textView = itemView.findViewById(R.id.car_number);
            startTime_textView = itemView.findViewById(R.id.car_status);
            endDate_textView = itemView.findViewById(R.id.car_capacity);
            endTime_textView = itemView.findViewById(R.id.cost_per_day);
            this.rearchCarListListener=mListListener;
            itemView.setOnClickListener((View.OnClickListener) this);

        }

        @Override
        public void onClick(View view) {
            mListListener.onSearchCarListClick(getAdapterPosition());
        }
    }
}
