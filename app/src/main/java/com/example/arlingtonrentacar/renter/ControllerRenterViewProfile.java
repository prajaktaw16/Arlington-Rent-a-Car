/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar.renter;

import android.content.Context;

import com.example.arlingtonrentacar.database.SystemUserDAO;
import com.example.arlingtonrentacar.users.SystemUser;

public class ControllerRenterViewProfile {
    private static Context mContext;
    //private static Context mContext;
    private final String LOG_TAG = ControllerRenterViewProfile.class.getSimpleName();
    //private Context mContext;

    public ControllerRenterViewProfile(Context context){
        mContext = context;
    }

    public static SystemUser getSystemUserByUsername(String username){
        SystemUser user;
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        user = systemUserDAO.getSystemUserByUsername(username);
        return user;
    }

    public boolean updateRenterProfile(SystemUser renter){
        SystemUserDAO systemUserDAO = SystemUserDAO.getInstance(mContext);
        return systemUserDAO.updateRenterProfile(renter);
    }
}
