package com.example.arlingtonrentacar;

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
            return Role.RENTER;
        }
    }

    public static String roleEnumToStr(Role role){
        if(role == Role.RENTER){
            return "Renter";
        }else if(role == Role.MANAGER){
            return  "Manager";
        }else{
            return "Admin";
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


}
