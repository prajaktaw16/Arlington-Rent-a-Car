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
import com.example.arlingtonrentacar.renter.ReservationSummaryItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private SearchCarListListener mListListener;
    private ArrayList<SearchCarSummaryItem> mSearchCarSummaryList;
    //private ArrayList<CarDetails> carDetails;


    public RecylerViewAdapter( ArrayList<SearchCarSummaryItem> carDetails, SearchCarListListener searchCarListListener) {
        this.mSearchCarSummaryList = carDetails;
        this.mListListener = searchCarListListener;
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

        holder.carName_textView.setText(mSearchCarSummaryList.get(position).getCarName());
        //holder.carnumber_textView.setText( Integer.toString(mSearchCarSummaryList.get(position).getCarNumber()));
        holder.carStatus_textView.setText(mSearchCarSummaryList.get(position).getCarStatus());
        holder.carCapacity_textView.setText(Integer.toString(mSearchCarSummaryList.get(position).getCarCapacity()));
        holder.totalPrice_textView.setText( Double.toString(mSearchCarSummaryList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {

        return mSearchCarSummaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView carName_textView;
       // public TextView carnumber_textView;
        public TextView carCapacity_textView;
        public TextView carStatus_textView;
        public TextView totalPrice_textView;
        SearchCarListListener searchCarListListener;
        public ViewHolder(@NonNull View itemView, SearchCarListListener mSearchCarListListener) {

            super(itemView);
            carName_textView = itemView.findViewById(R.id.car_name);
           // carnumber_textView = itemView.findViewById(R.id.car_number);
            carStatus_textView = itemView.findViewById(R.id.car_status);
            carCapacity_textView = itemView.findViewById(R.id.car_capacity);
            totalPrice_textView = itemView.findViewById(R.id.cost_per_day);
            this.searchCarListListener = mListListener;
            itemView.setOnClickListener((View.OnClickListener) this);

        }

        @Override
        public void onClick(View view) {
            mListListener.onSearchCarListClick(getAdapterPosition());
        }
    }
}
