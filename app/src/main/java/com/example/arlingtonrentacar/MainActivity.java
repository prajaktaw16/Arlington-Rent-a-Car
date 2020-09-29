package com.example.arlingtonrentacar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.example.arlingtonrentacar.database.DatabaseHelper;
import com.example.arlingtonrentacar.users.SystemUser;

public class MainActivity extends AppCompatActivity {

    EditText editText_username, editText_password;
    String username, password;
    Intent intent;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    SystemUser systemUser;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_screen);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(this);
        addDummyData();
    }

    public void checkValidUser(View view){
        String db_password, role = " ";

        editText_username = findViewById(R.id.edittext_username);
        editText_password = findViewById(R.id.edittext_password);
        username = editText_username.getText().toString().trim();
        password = editText_password.getText().toString().trim();

//        Set username and password details using System User class
        systemUser = new SystemUser();
        systemUser.setUserName(username);
        systemUser.setPassword(password);

        if(username.length()!=0 && password.length()!=0){
            cursor = systemUser.checkPassword(databaseHelper);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){
                    role = cursor.getString(cursor.getColumnIndex("role")).trim().toLowerCase();
                }

                db_password = cursor.getString(cursor.getColumnIndex("password")).trim().toLowerCase();;
//          check for username and password using db query
                if(!password.equalsIgnoreCase(db_password)){
                    editText_username.setText("");
                    editText_password.setText("");
                }

//          if they match start an activity according to the role
                else{
                    if(role.equals("renter")){
                        startActivity(new Intent(this, RenterHomeScreen.class));
                    }
                    else if(role.equals("manager")){
                        startActivity(new Intent(this, ManagerHomeScreen.class));
                    }
                    else {
                        startActivity(new Intent(this, AdminHomeScreen.class));
                    }
                }
            }

        }
    }

    public void addDummyData(){
        systemUser = new SystemUser();
        systemUser.addRecord(databaseHelper,"Ashwini","Trale","ashwiniTrale8","ashwiniT8","","Manager",
                "ashwinitrale@gmail.com","9765890897","abc Street","Pune","MH","425316","No");
        systemUser.addRecord(databaseHelper,"Prajakta","Waikar","prajaktaWaikar16","prajaktaW16","1234","Renter",
                "prajaktawaikar@gmail.com","7658344231","xyz Street","Pune","MH","425317","Yes");
        systemUser.addRecord(databaseHelper,"Sayali","Deshmukh","sayaliDeshmukh1","sayaliD1","","Admin",
                "sayalideshmukh@gmail.com","7356298674","pqr Street","Pune","MH","425416","No");
        systemUser.addRecord(databaseHelper,"Shubham","Patil","shubhamPatil2","shubhamP2","","Admin",
                "shubhampatil@gmail.com","9256298674","lmn Street","Pune","MH","400416","No");
        systemUser.addRecord(databaseHelper,"Ahmed","Almutairi","ahmedAlmutairi3","ahmedA3","1238","Renter",
                "ahmedA@gmail.com","7350298674","saudi Street","Pune","MH","420016","Yes");
        systemUser.addRecord(databaseHelper,"Sudipta","Sharif","sudiptaShrif4","sudiptaS4","","Manager",
                "sudiptaS@gmail.com","950298674","bngl Street","Pune","MH","411016","No");

    }
}
