/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.renter.RequestCarSummaryItem;

import java.util.LinkedList;

public class RequestedCarListAdapter extends RecyclerView.Adapter<RequestedCarListAdapter.RequestedCarViewHolder> {
    private final String LOG_TAG = RequestedCarListAdapter.class.getSimpleName();
    private final LinkedList<RequestCarSummaryItem> mCarList;
    private LayoutInflater mInflater;
    private Context context;

    public RequestedCarListAdapter(Context context, LinkedList<RequestCarSummaryItem> carList){
        mInflater = LayoutInflater.from(context);
        this.mCarList = carList;
    }

    @NonNull
    @Override
    public RequestedCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View rItemView = mInflater.inflate(R.layout.requestedcarlist_rowitem, parent, false);
        return new RequestedCarViewHolder(rItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedCarViewHolder holder, int position) {
        RequestCarSummaryItem currentCarItem = mCarList.get(position);
        holder.requestedCarItemView.setText(currentCarItem.toString());
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    class RequestedCarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final String LOG_TAG = RequestedCarViewHolder.class.getSimpleName();
        public final TextView requestedCarItemView;
        final RequestedCarListAdapter mAdapter;

        public RequestedCarViewHolder(View itemView, RequestedCarListAdapter adapter){
            super(itemView);
            requestedCarItemView = itemView.findViewById(R.id.requestedcar_rowitem);
            this.mAdapter = adapter;
            requestedCarItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int i = this.getAdapterPosition();
            RequestCarSummaryItem selectedCarSummaryItem = mCarList.get(i);
            Intent intent = new Intent(context, RequestedCarDetailsActivity.class);
            intent.putExtra(context.getString(R.string.parcel_selected_requested_car_summary_item), selectedCarSummaryItem);
            context.startActivity(intent);
        }
    }

}
