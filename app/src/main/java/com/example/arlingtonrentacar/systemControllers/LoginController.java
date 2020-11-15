package com.example.arlingtonrentacar.systemControllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.AdminHomeScreen;
import com.example.arlingtonrentacar.ManagerHomeScreen;
import com.example.arlingtonrentacar.RenterHomeScreen;
import com.example.arlingtonrentacar.Role;
import com.example.arlingtonrentacar.database.SystemUserDAO;

public class LoginController {
    private static final String LOG_TAG = LoginController.class.getSimpleName();
    public static final String USERNAME = "com.example.arlingtonrentacar.systemControllers.LoginController.USERNAME";
    private Context loginGUIContext;

    public LoginController(Context context){
        this.loginGUIContext = context;
    }

    public void login(String username, String password){
        final String METHOD_NAME = "login()";
        String msg = "";
        String role = "";
        SystemUserDAO systemUserDAO = new SystemUserDAO(loginGUIContext);
        if(systemUserDAO.isCorrectCredentials(username, password)){
            role = systemUserDAO.getSystemUserRole(username);
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
            intent.putExtra(this.USERNAME, username);
            this.loginGUIContext.startActivity(intent);
        }else if(role == Role.ADMIN){
            intent = new Intent(this.loginGUIContext, AdminHomeScreen.class);
            intent.putExtra(this.USERNAME, username);
            this.loginGUIContext.startActivity(intent);
        }else if(role == Role.RENTER){
            intent = new Intent(this.loginGUIContext, RenterHomeScreen.class);
            intent.putExtra(this.USERNAME, username);
            this.loginGUIContext.startActivity(intent);
        }else{
            Log.e(LOG_TAG, METHOD_NAME + ": invalid role. Msg to developer: This should never happen. Check Registration code. The passed role input is: " + AAUtil.roleEnumToStr(role));
            msg = username + " role: " + AAUtil.roleEnumToStr(role) + "\nValid role: Renter, Admin or Manager.\nPlease create a new account.";
            Toast.makeText(loginGUIContext, msg, Toast.LENGTH_LONG).show();
        }
    }
}
