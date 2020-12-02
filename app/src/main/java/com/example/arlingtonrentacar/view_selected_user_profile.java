package com.example.arlingtonrentacar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class view_selected_user_profile extends AppCompatActivity {

    private String username;
    private String password;
    private int utaID;
    private String lastName;
    private String firstName;
    private String phone;
    private String email;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String role;

    public TextView username_textview;
    public TextView password_textview;
    public TextView utaid_textview;
    public TextView lastname_textview;
    public TextView firstname_textview;
    public TextView phone_textview;
    public TextView email_textview;
    public TextView streetaddress_textview;
    public TextView city_textview;
    public TextView state_textview;
    public TextView zip_textview;
    public TextView role_textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_selected_user_profile);
        getGUI();

        username = getIntent().getExtras().getString("username");
        password = getIntent().getExtras().getString("password");
        utaID = getIntent().getExtras().getInt("uta_id");
        lastName = getIntent().getExtras().getString("lastname");
        firstName = getIntent().getExtras().getString("firstname");
        phone = getIntent().getExtras().getString("phone");
        email = getIntent().getExtras().getString("email");
        streetAddress = getIntent().getExtras().getString("streetaddress");
        city = getIntent().getExtras().getString("city");
        state = getIntent().getExtras().getString("state");
        zip = getIntent().getExtras().getString("zip");
        role = getIntent().getExtras().getString("role");

        setGUI();
    }

    public void getGUI(){
        username_textview = findViewById(R.id.editText_userName3);
        password_textview = findViewById(R.id.editText_password3);
        utaid_textview = findViewById(R.id.editText_utaID3);
        lastname_textview = findViewById(R.id.editText_lastName3);
        firstname_textview = findViewById(R.id.editText_firstName3);
        phone_textview = findViewById(R.id.editText_phone3);
        email_textview = findViewById(R.id.editText_email3);
        streetaddress_textview = findViewById(R.id.editText_streetAddress3);
        city_textview = findViewById(R.id.editText_city3);
        state_textview = findViewById(R.id.editText_state3);
        zip_textview = findViewById(R.id.editText_zipCode3);
        role_textview = findViewById(R.id.userrole3);
    }

    public void setGUI(){
        username_textview.setText(username);
        password_textview.setText(password);
        utaid_textview.setText(String.valueOf(utaID));
        lastname_textview.setText(lastName);
        firstname_textview.setText(firstName);
        phone_textview.setText(phone);
        email_textview.setText(email);
        streetaddress_textview.setText(streetAddress);
        city_textview.setText(city);
        state_textview.setText(state);
        zip_textview.setText(zip);
        role_textview.setText(role);
    }
}
