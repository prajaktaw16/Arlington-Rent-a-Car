package com.example.arlingtonrentacar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RequestedCarListAdapter extends RecyclerView.Adapter<RequestedCarListAdapter.RequestedCarViewHolder> {
    private final LinkedList<String> rCarList;
    private LayoutInflater rInflater;

    public RequestedCarListAdapter(Context context, LinkedList<String> carList){
        rInflater = LayoutInflater.from(context);
        this.rCarList = carList;
    }

    @NonNull
    @Override
    public RequestedCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rItemView = rInflater.inflate(R.layout.requestedcarlist_rowitem, parent, false);
        return new RequestedCarViewHolder(rItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedCarViewHolder holder, int position) {
        String rCurrent = rCarList.get(position);
        holder.requestedCarItemView.setText(rCurrent);
    }

    @Override
    public int getItemCount() {
        return rCarList.size();
    }

    class RequestedCarViewHolder extends RecyclerView.ViewHolder{
        public final TextView requestedCarItemView;
        final RequestedCarListAdapter rAdapter;

        public RequestedCarViewHolder(View itemView, RequestedCarListAdapter adapter){
            super(itemView);
            requestedCarItemView = itemView.findViewById(R.id.requestedcar_rowitem);
            this.rAdapter = adapter;
        }
    }

}
