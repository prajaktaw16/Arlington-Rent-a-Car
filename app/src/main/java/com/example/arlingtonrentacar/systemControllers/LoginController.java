/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.systemControllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.AdminHomeScreen;
import com.example.arlingtonrentacar.ManagerHomeScreen;
import com.example.arlingtonrentacar.R;
import com.example.arlingtonrentacar.RenterHomeScreen;
import com.example.arlingtonrentacar.Role;
import com.example.arlingtonrentacar.database.SystemUserDAO;

public class LoginController {
    private static final String LOG_TAG = LoginController.class.getSimpleName();
    private Context loginGUIContext;
    private SharedPreferences sessionPrefs;

    public LoginController(Context context){
        this.loginGUIContext = context;
    }

    public void login(String username, String password){
        final String METHOD_NAME = "login()";
        String msg = "";
        String role = "";
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(loginGUIContext);
        if(systemUserDAO.isCorrectCredentials(username, password)){
            role = systemUserDAO.getSystemUserRole(username);
            setUpLoggedInUserSession(AAUtil.roleStrToEnum(role), username);
            launchSysUserHomeScreen(AAUtil.roleStrToEnum(role), username);
        }else{
            msg = "Wrong username/password.\nPlease try Again.";
            Log.d(LOG_TAG, METHOD_NAME + ": " + msg);
            Toast.makeText(loginGUIContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void launchSysUserHomeScreen(Role role, String username){
        final String METHOD_NAME = "launchSysUserHomeScreen()";
        Log.d(LOG_TAG, METHOD_NAME + ": role: " + AAUtil.roleEnumToStr(role));
        Log.d(LOG_TAG, METHOD_NAME + ": username: " + username);

        String msg = "";
        Intent intent;
        if(role == Role.MANAGER){
            intent = new Intent(this.loginGUIContext, ManagerHomeScreen.class);
            this.loginGUIContext.startActivity(intent);
        }else if(role == Role.ADMIN){
            intent = new Intent(this.loginGUIContext, AdminHomeScreen.class);
            this.loginGUIContext.startActivity(intent);
        }else if(role == Role.RENTER){
            intent = new Intent(this.loginGUIContext, RenterHomeScreen.class);
            this.loginGUIContext.startActivity(intent);
        }else{
            Log.e(LOG_TAG, METHOD_NAME + ": invalid role. Msg to developer: This should never happen. Check Registration code. The passed role input is: " + AAUtil.roleEnumToStr(role));
            msg = username + " role: " + AAUtil.roleEnumToStr(role) + "\nValid role: Renter, Admin or Manager.\nPlease create a new account.";
            Toast.makeText(loginGUIContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void setUpLoggedInUserSession(Role role, String username){
        sessionPrefs = loginGUIContext.getSharedPreferences(loginGUIContext.getString(R.string.sessions_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sessions = sessionPrefs.edit();
        // clear is only called during login and logout
        sessions.clear();
        sessions.putString(loginGUIContext.getString(R.string.session_loggedin_username), username);
        sessions.putString(loginGUIContext.getString(R.string.session_loggedin_user_role), AAUtil.roleEnumToStr(role));
        sessions.commit();
    }
}
