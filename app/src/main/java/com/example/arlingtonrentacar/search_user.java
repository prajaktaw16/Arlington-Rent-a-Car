package com.example.arlingtonrentacar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class search_user extends AppCompatActivity {

    public TextView last_name_textview;
    public String last_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        last_name_textview = findViewById(R.id.last_name);
    }

    public void onSearchUser_ButtonClick(View view){
        last_name = last_name_textview.getText().toString();
        Intent intent = new Intent(this, search_user_list.class);
        intent.putExtra("lastname", last_name);
        startActivity(intent);
    }
}
