package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class RequestedCarListAdapter extends RecyclerView.Adapter<RequestedCarListAdapter.RequestedCarViewHolder> {
    private final LinkedList<String> mCarList;
    private LayoutInflater mInflater;
    private Context context;

    public RequestedCarListAdapter(Context context, LinkedList<String> carList){
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
        String rCurrent = mCarList.get(position);
        holder.requestedCarItemView.setText(rCurrent);
    }

    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    class RequestedCarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            // TODO: for Interation 3 will need to change this, for now just call the target activity
            Intent intent = new Intent(context, RequestedCarDetailsActivity.class);
            context.startActivity(intent);
        }
    }

}
