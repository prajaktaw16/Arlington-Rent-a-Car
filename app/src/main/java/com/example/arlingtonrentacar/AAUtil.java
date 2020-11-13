package com.example.arlingtonrentacar;

import com.example.arlingtonrentacar.database.DatabaseHelper;

import java.util.Calendar;

public class AAUtil {

    public static Role roleStrToEnum(String role){
        role = role.toLowerCase();
        if(role.equals("renter")){
            return Role.RENTER;
        }else if(role.equals("manager")){
            return Role.MANAGER;
        }else if(role.equals("admin")){
            return Role.ADMIN;
        }else{
            return Role.NONE;
        }
    }

    public static String roleEnumToStr(Role role){
        if(role == Role.RENTER){
            return "renter";
        }else if(role == Role.MANAGER){
            return  "manager";
        }else if(role == Role.ADMIN){
            return "admin";
        }else {
            return "none";
        }
    }

    public static AAAMemberStatus aaaMemberStatusStrToEnum(String status){
        status = status.toLowerCase();
        if(status.equals("yes"))
            return AAAMemberStatus.YES;
        else
            return AAAMemberStatus.NO;
    }

    public static int aaaMemberStatusEnumToInt(AAAMemberStatus status){
        if (status == AAAMemberStatus.YES)
            return 1;
        else
            return 0;
    }

    public static AAAMemberStatus aaaMemberStatusIntToEnum(int status){
        if(status == 1){
            return AAAMemberStatus.YES;
        }else{
            return AAAMemberStatus.NO;
        }
    }

    public static String getGreetingByHour(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        if(hour >= 0 && hour < 12){
            return "Good Morning";
        }else if(hour >= 12 && hour < 16){
            return "Good Afternoon";
        }else if(hour >= 16 && hour < 21){
            return "Good Evening";
        }else{
            return "Hello";
        }
    }

    public static String quoteStr(String strToQuote){
        return "\'" + strToQuote + "\'";
    }

    public static boolean isNumeric(String numStr){
        final String EMPTY = "";
        boolean result;
        if(numStr.equals(EMPTY)){
            result = false;
        }else if(numStr == null){
            result = false;
        }else{
            try{
                double d = Double.parseDouble(numStr);
                result = true;
            }catch (Exception e){
                result = false;
            }
        }
        return result;
    }

}
