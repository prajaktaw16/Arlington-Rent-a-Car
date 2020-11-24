package com.example.arlingtonrentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ViewProfile extends AppCompatActivity {
    private TextView role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        role = (TextView) findViewById(R.id.userrole);
        String userRole = role.getText().toString();
        if(userRole.equalsIgnoreCase("Admin")){
            hideUpdateButton();
        }
        if(userRole.equalsIgnoreCase("Renter")){
            setVisbilityForRenterAttr();
        }

    }

    public void hideUpdateButton() {
        Button updateBtn = (Button) findViewById(R.id.update_btn);
        updateBtn.setVisibility(View.INVISIBLE);
    }
    public void setVisbilityForRenterAttr() {
        TextView aaMemLabel = (TextView) findViewById(R.id.aamemlabel);
        TextView aaMemval = (TextView) findViewById(R.id.aamemval);
        TextView userStatusLabel = (TextView) findViewById(R.id.userstatuslabel);
        TextView userStatVal = (TextView) findViewById(R.id.userstatusval);
        aaMemLabel.setVisibility(View.VISIBLE);
        aaMemval.setVisibility(View.VISIBLE);
        userStatusLabel.setVisibility(View.VISIBLE);
        userStatVal.setVisibility(View.VISIBLE);
    }
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}