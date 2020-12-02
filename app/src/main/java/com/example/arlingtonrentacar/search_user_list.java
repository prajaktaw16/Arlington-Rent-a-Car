package com.example.arlingtonrentacar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.users.SystemUser;

import java.util.ArrayList;

public class search_user_list extends AppCompatActivity implements search_user_list_adapter.usersListlistener, AdapterView.OnItemSelectedListener {

    public String lastname;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    ArrayList<SystemUser> usersDataList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private SystemUser systemUserObj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_list);
        recyclerView = findViewById(R.id.users_list);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new search_user_list_adapter(usersDataList, this);
        recyclerView.setAdapter(mAdapter);
        lastname = getIntent().getExtras().getString("lastname");
        dbHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase databaseHandle = dbHelper.getReadableDatabase();

//        select * from system_users where last_name = "Wayne";
        String search_users_query =  "select * from system_users where last_name = \""+lastname+"\"; ";
        Cursor cursor =  databaseHandle.rawQuery(search_users_query,null);
        if(cursor.moveToFirst()){
            do{
                systemUserObj = new SystemUser();
                systemUserObj.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                systemUserObj.setLastName(cursor.getString(cursor.getColumnIndex("last_name")));
                systemUserObj.setFirstName(cursor.getString(cursor.getColumnIndex("first_name")));
                systemUserObj.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                systemUserObj.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                systemUserObj.setRole(cursor.getString(cursor.getColumnIndex("role")));
                systemUserObj.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                systemUserObj.setStreetAddress(cursor.getString(cursor.getColumnIndex("street_address")));
                systemUserObj.setCity(cursor.getString(cursor.getColumnIndex("city")));
                systemUserObj.setState(cursor.getString(cursor.getColumnIndex("state")));
                systemUserObj.setZip(cursor.getString(cursor.getColumnIndex("zip_code")));
                systemUserObj.setUtaID(Integer.parseInt(cursor.getString(cursor.getColumnIndex("uta_id"))));

                usersDataList.add(systemUserObj);
            }while(cursor.moveToNext());
        }
        else{
            Toast toast = Toast.makeText(this, "No Users found for this date and time", Toast.LENGTH_SHORT);
            toast.show();
        }
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onusersListClick(int position) {
        Intent intent = new Intent(this, view_selected_user_profile.class);
        intent.putExtra("username", usersDataList.get(position).getUsername());
        intent.putExtra("password", usersDataList.get(position).getPassword());
        intent.putExtra("uta_id", usersDataList.get(position).getUtaID());
        intent.putExtra("lastname", usersDataList.get(position).getLastName());
        intent.putExtra("firstname", usersDataList.get(position).getFirstName());
        intent.putExtra("phone", usersDataList.get(position).getPhone());
        intent.putExtra("email", usersDataList.get(position).getEmail());
        intent.putExtra("streetaddress", usersDataList.get(position).getStreetAddress());
        intent.putExtra("city", usersDataList.get(position).getCity());
        intent.putExtra("state", usersDataList.get(position).getState());
        intent.putExtra("zip", usersDataList.get(position).getZip());
        intent.putExtra("role", usersDataList.get(position).getRole());

        startActivity(intent);
    }
}
