package com.example.arlingtonrentacar.systemControllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.arlingtonrentacar.MainActivity;
import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.users.SystemUser;

public class Admin_UpdateSelectedUserController {

    private Context adminGUIContext;
    private static final String LOG_TAG = Admin_UpdateSelectedUserController.class.getSimpleName();
    private Context mContext;
    public Admin_UpdateSelectedUserController(Context context){
        mContext = context;
    }

    public boolean updateSelectedUserProfile(SystemUser user){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        return systemUserDAO.updateSelectedUserProfile(user);
    }

    public boolean updateUserRole(SystemUser user){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        return systemUserDAO.updateUserRole(user);
    }
    public boolean revokeUser(SystemUser user){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        return systemUserDAO.revokeUser(user);
    }


    public SystemUser getSystemUserByUsername(String username){
        SystemUser user;
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        user = systemUserDAO.getSystemUserByUsername(username);
        return user;
    }
}
