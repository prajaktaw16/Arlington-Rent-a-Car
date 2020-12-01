package com.example.arlingtonrentacar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.database.Reservations;
import com.example.arlingtonrentacar.manager.ViewReservationCalendar_list_Adapter;
import com.example.arlingtonrentacar.users.SystemUser;


public class search_user_list_adapter extends RecyclerView.Adapter<search_user_list_adapter.MyViewHolder>{

    private ArrayList<SystemUser> usersData;
    private usersListlistener musersListListener;

    public search_user_list_adapter(ArrayList<SystemUser> usersData, search_user_list_adapter.usersListlistener usersListListener){
        this.usersData = usersData;
        musersListListener = usersListListener;
    }

    public interface usersListlistener{
        void onusersListClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_list_item, parent, false);
        return new MyViewHolder(v, musersListListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userName_textview.setText(usersData.get(position).getUsername());
        holder.last_nameOutput_textview.setText(usersData.get(position).getLastName());
        holder.first_name_textview.setText(usersData.get(position).getFirstName());
        holder.phone_textview.setText(usersData.get(position).getPhone());
        holder.email_textview.setText(usersData.get(position).getEmail());
        holder.role_textview.setText(usersData.get(position).getRole());
        holder.uta_ID_textview.setText(String.valueOf(usersData.get(position).getUtaID()));
    }

    @Override
    public int getItemCount() {
        return usersData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView userName_textview;
        public TextView last_nameOutput_textview;
        public TextView first_name_textview;
        public TextView phone_textview;
        public TextView email_textview;
        public TextView role_textview;
        public TextView uta_ID_textview;
        usersListlistener ulistener;

        public MyViewHolder(@NonNull View v, search_user_list_adapter.usersListlistener musersListListener){
            super(v);
            this.ulistener = musersListListener;
            userName_textview = v.findViewById(R.id.username_textview);
            last_nameOutput_textview = v.findViewById(R.id.lastname2_textview);
            first_name_textview = v.findViewById(R.id.firstname2_textview);
            phone_textview = v.findViewById(R.id.phone2_textview) ;
            email_textview = v.findViewById(R.id.email2_textview);
            role_textview = v.findViewById(R.id.role2_textview);
            uta_ID_textview = v.findViewById(R.id.utaid2_textview);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ulistener.onusersListClick(getAdapterPosition());
        }
    }
}



