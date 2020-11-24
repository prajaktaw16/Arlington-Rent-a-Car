/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.systemControllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.MainActivity;
import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.users.SystemUser;

public class RegisterController {
    private static final String LOG_TAG = RegisterController.class.getSimpleName();
    private Context registerGUIContext;

    public RegisterController(Context context){
        this.registerGUIContext = context;
    }

    public void register(String username, String password, String lastName, String firstName,
                           String role, String utaID, String phone, String email, String address,
                           String city, String state, String zip, int aaaMemStat, int userStat){
        final String METHOD_NAME = "register()";
        final String EMPTY = "";
        String msg = "";
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(registerGUIContext);
        SystemUser user;
        boolean r = systemUserDAO.isRegistered(username);

        if(!r){
            msg = SystemUser.validateData(username, password, lastName, firstName, utaID, role,
                    phone, email, address, city, state, zip);
        }else{
            msg = "Username: " + username+" taken.\nPlease use another.";
        }

        if(msg.equals(EMPTY)){
            user = new SystemUser(username, password, lastName, firstName, role, Integer.parseInt(utaID), phone,
                    email, address, city, state, zip, aaaMemStat, userStat);
            msg = systemUserDAO.store(user);
        }

        if(msg.equals(EMPTY)){
            msg = "Registration successful.";
            this.registerGUIContext.startActivity(new Intent(this.registerGUIContext, MainActivity.class));
        }
        Log.d(LOG_TAG, METHOD_NAME + ": msg = " + msg);
        Toast.makeText(this.registerGUIContext, msg, Toast.LENGTH_LONG).show();
    }

}
