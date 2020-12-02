/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RequestedCarListAdapter;

import java.util.LinkedList;

public class RenterReservationSummaryListAdapter
        extends RecyclerView.Adapter<RenterReservationSummaryListAdapter.RenterReservationSummaryHolder> {
    private final String LOG_TAG = RenterReservationSummaryListAdapter.class.getSimpleName();
    private final LinkedList<ReservationSummaryItem> mReservationSummaryList;
    private LayoutInflater mInflater;
    private Context mContext;

    public RenterReservationSummaryListAdapter(Context context, LinkedList<ReservationSummaryItem> summaryList){
        mInflater = LayoutInflater.from(context);
        this.mReservationSummaryList = summaryList;
        mContext = context;
    }

    @NonNull
    @Override
    public RenterReservationSummaryListAdapter.RenterReservationSummaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.renter_reservations_summary_item, parent, false);
        return new RenterReservationSummaryListAdapter.RenterReservationSummaryHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RenterReservationSummaryListAdapter.RenterReservationSummaryHolder holder, int position) {
        ReservationSummaryItem mCurrent = mReservationSummaryList.get(position);
        holder.mSummaryItemView.setText(mCurrent.toString());
    }

    @Override
    public int getItemCount() {
        return mReservationSummaryList.size();
    }

    class RenterReservationSummaryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mSummaryItemView;
        final RenterReservationSummaryListAdapter mAdapter;

        public RenterReservationSummaryHolder(View itemView, RenterReservationSummaryListAdapter adapter){
            super(itemView);
            mSummaryItemView = itemView.findViewById(R.id.rowRenterReservationSummaryItem);
            this.mAdapter = adapter;
            mSummaryItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final String METHOD_NAME = "onClick()";
            int mPosition = getLayoutPosition();
            ReservationSummaryItem selectedReservationSummaryItem = mReservationSummaryList.get(mPosition);
            Log.d(LOG_TAG, METHOD_NAME + ": selected reservation summary item: \n" + selectedReservationSummaryItem.toString());
            Intent intent = new Intent(mContext, RenterReservationDetailsActivity.class);
            intent.putExtra(mContext.getString(R.string.parcel_selected_reservation_summary_item), selectedReservationSummaryItem);
            mContext.startActivity(intent);
        }
    }
}
