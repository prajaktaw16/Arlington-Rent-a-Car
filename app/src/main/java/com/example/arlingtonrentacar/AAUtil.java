/**
 * Author: Sudipta Sharif (S.S)
 * School: University of Texas at Arlington
 * Course: CSE 5324 Fall 2020
 */
package com.example.arlingtonrentacar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.arlingtonrentacar.database.SystemUserDAO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

public class AAUtil {
    public static final String DATABASE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String USER_FRIENDLY_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm a";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String USD_CURRENCY_FORMAT = "#,###.##";
    public static final double TAX = 1.0825; // 8.25%
    public static final double DISCOUNT = 0.9; // 10%
    public static final String EMPTYSTR = "";
    public static final String EIGHT_AM = "08:00 AM";
    public static final String EIGHT_THIRTY_AM = "08:30 AM";
    public static final String NINE_AM = "09:00 AM";
    public static final String NINE_THIRTY_AM = "09:30 AM";
    public static final String TEN_AM  = "10:00 AM";
    public static final String TEN_THIRTY_AM = "10:30 AM";
    public static final String ELEVEN_AM = "11:00 AM";
    public static final String ELEVEN_THIRTY_AM = "11:30 AM";
    public static final String TWELVE_PM = "12:00 PM";
    public static final String TWELVE_THIRTY_PM = "12:30 PM";
    public static final String ONE_PM = "01:00 PM";
    public static final String ONE_THIRTY_PM = "01:30 PM";
    public static final String TWO_PM = "02:00 PM";
    public static final String TWO_THIRTY_PM = "02:30 PM";
    public static final String THREE_PM = "03:00 PM";
    public static final String THREE_THIRTY_PM = "03:30 PM";
    public static final String FOUR_PM = "04:00 PM";
    public static final String FOUR_THIRTY_PM = "04:30 PM";
    public static final String FIVE_PM = "05:00 PM";
    public static final String FIVE_THIRTY_PM = "05:30 PM";
    public static final String SIX_PM  = "06:00 PM";
    public static final String SIX_THIRTY_PM = "06:30 PM";
    public static final String SEVEN_PM = "07:00 PM";
    public static final String SEVEN_THIRTY_PM = "07:30 PM";
    public static final String EIGHT_PM = "08:00 PM";
    public static final int CAPACITY_CAR_SMART = 1;
    public static final int CAPACITY_CAR_ECONOMY = 3;
    public static final int CAPACITY_CAR_COMPACT = 4;
    public static final int CAPACITY_CAR_INTERMEDIATE = 4;
    public static final int CAPACITY_CAR_STANDARD = 5;
    public static final int CAPACITY_CAR_FULL_SIZE = 6;
    public static final int CAPACITY_CAR_SUV = 8;
    public static final int CAPACITY_CAR_MINIVAN = 9;
    public static final int CAPACITY_CAR_ULTRASPORTS = 2;
    public static final int CAPACITY_CAR_NONE = 0; // this will give invalid data whenever called/used as form input
    public static final int PLANET_EARTH_WEEK_SIZE = 7;

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

    public static String aaaMemberStatusIntToStr(int status){
        if(status == 1){
            return "Yes";
        }else{
            return "No";
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
        boolean result;
        if(numStr.equals(EMPTYSTR)){
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

    public static String carNameEnumToStr(CarName carName){
        String carNameStr;
        if(carName == CarName.SMART){
            carNameStr = "Smart";
        }else if(carName == CarName.ECONOMY){
            carNameStr = "Economy";
        }else if(carName == CarName.COMPACT){
            carNameStr = "Compact";
        }else if(carName == CarName.INTERMEDIATE){
            carNameStr = "Intermediate";
        }else if(carName == CarName.STANDARD){
            carNameStr= "Standard";
        }else if(carName == CarName.FULLSIZE){
            carNameStr = "Full Size";
        }else if(carName == CarName.SUV){
            carNameStr = "SUV";
        }else if(carName == CarName.MINIVAN){
            carNameStr = "MiniVan";
        }else if(carName == CarName.ULTRASPORTS){
            carNameStr = "Ultra Sports";
        }else{
            carNameStr = "None";
        }
        return carNameStr;
    }

    public static CarName carNameStrToEnum(String strCarName){
        CarName enumCarName;
        strCarName = strCarName.toLowerCase();
        if(strCarName.equals("smart")){
            enumCarName = CarName.SMART;
        }else if(strCarName.equals("economy")){
            enumCarName = CarName.ECONOMY;
        }else if(strCarName.equals("compact")){
            enumCarName = CarName.COMPACT;
        }else if(strCarName.equals("intermediate")){
            enumCarName = CarName.INTERMEDIATE;
        }else if(strCarName.equals("standard")){
            enumCarName = CarName.STANDARD;
        }else if(strCarName.equals("full size")){
            enumCarName = CarName.FULLSIZE;
        }else if(strCarName.equals("suv")){
            enumCarName = CarName.SUV;
        }else if(strCarName.equals("minivan")){
            enumCarName = CarName.MINIVAN;
        }else if(strCarName.equals("ultra sports")){
            enumCarName = CarName.ULTRASPORTS;
        }else{
            enumCarName = CarName.NONE;
        }
        return enumCarName;
    }

    public static String formatDate(Calendar calendar, String dateFormatPattern){
        String formattedDate = "";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatPattern);
        Date date = calendar.getTime();
        formattedDate = formatter.format(date);
        return formattedDate;
    }

    public static int getHourOfDayFromTimeStr(String timeWithAMPM){
        int result = 0;
        if(timeWithAMPM.equals(EIGHT_AM) || timeWithAMPM.equals(EIGHT_THIRTY_AM)){
            result = 8;
        }else if(timeWithAMPM.equals(NINE_AM) || timeWithAMPM.equals(NINE_THIRTY_AM)){
            result = 9;
        }else if(timeWithAMPM.equals(TEN_AM) || timeWithAMPM.equals(TEN_THIRTY_AM)){
            result = 10;
        }else if(timeWithAMPM.equals(ELEVEN_AM) || timeWithAMPM.equals(ELEVEN_THIRTY_AM)){
            result = 11;
        }else if(timeWithAMPM.equals(TWELVE_PM) || timeWithAMPM.equals(TWELVE_THIRTY_PM)){
            result = 12;
        }else if(timeWithAMPM.equals(ONE_PM) || timeWithAMPM.equals(ONE_THIRTY_PM)){
            result = 13;
        }else if(timeWithAMPM.equals(TWO_PM) || timeWithAMPM.equals(TWO_THIRTY_PM)){
            result = 14;
        }else if(timeWithAMPM.equals(THREE_PM) || timeWithAMPM.equals(THREE_THIRTY_PM)){
            result = 15;
        }else if(timeWithAMPM.equals(FOUR_PM) || timeWithAMPM.equals(FOUR_THIRTY_PM)){
            result = 16;
        }else if(timeWithAMPM.equals(FIVE_PM) || timeWithAMPM.equals(FIVE_THIRTY_PM)){
            result = 17;
        }else if(timeWithAMPM.equals(SIX_PM) || timeWithAMPM.equals(SIX_THIRTY_PM)){
            result = 18;
        }else if(timeWithAMPM.equals(SEVEN_PM) || timeWithAMPM.equals(SEVEN_THIRTY_PM)){
            result = 19;
        }else if(timeWithAMPM.equals(EIGHT_PM)){
            result = 20;
        }
        return result;
    }

    public static int getMinFromTimeStr(String time){
        int result = 0;
        if(time.contains(":00")){
            return 0;
        }else if(time.contains(":30")){
            return 30;
        }
        return result;
    }

    public static Calendar getCalendarDateWithTime(Calendar calendar, String time){
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.clear();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int hourOfDay = getHourOfDayFromTimeStr(time);
        int min = getMinFromTimeStr(time);

        resultCalendar.set(year, month, date, hourOfDay, min);
        return resultCalendar;
    }

    public static boolean equalDateAndTime(Calendar date1, Calendar date2){
        boolean result = false;
        if((date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                (date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)) &&
                (date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH)) &&
                (date1.get(Calendar.HOUR_OF_DAY) == date2.get(Calendar.HOUR_OF_DAY)) &&
                (date1.get(Calendar.MINUTE) == date2.get(Calendar.MINUTE)))){
            result = true;
        }
        return  result;
    }

    public static Calendar databaseDateTimeToCalendar(String dateTime){
        // Note: dateTime must be stored in db as: 'yyyy-MM-dd HH:mm'
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        String[] arrDatTime = dateTime.trim().split(" ");
        String date = arrDatTime[0].trim();
        String time = arrDatTime[1].trim();

        String[] arrDateParts = date.split("-");
        String[] arrTimeParts = time.split(":");

        int year = Integer.parseInt(arrDateParts[0].trim());
        int month = Integer.parseInt(arrDateParts[1].trim()) - 1; // month starts from 0
        int dayOfMonth = Integer.parseInt(arrDateParts[2].trim());

        int hourOfDay = Integer.parseInt(arrTimeParts[0].trim());
        int min = Integer.parseInt(arrTimeParts[1].trim());

        calendar.set(year, month, dayOfMonth, hourOfDay, min);
        return calendar;
    }

    public static String generateGUID(){
        // GUID: Globally Unique ID
        return UUID.randomUUID().toString().toUpperCase();
    }

    public static String convertDBDateToTargetFormat(String dateInDatabaseFormat, String targetDateFormat){
        // Note: dateInDatabaseFormat must be in the format: 'yyyy-MM-dd HH:mm'
        String result;
        Calendar calendar = databaseDateTimeToCalendar(dateInDatabaseFormat);
        result = formatDate(calendar, targetDateFormat);
        return result;
    }


    public static String getUserFriendlyTimeFromCalendarDate(Calendar calendar){
        // In retrospect should have stored all datetime in database as "yyyy-MM-dd'T'HH:mm"
        String userFriendlyDateTime = formatDate(calendar, AAUtil.USER_FRIENDLY_DATE_TIME_FORMAT);
        String[] arrDateTime = userFriendlyDateTime.split(" ");
        if(arrDateTime.length == 3){
            return  arrDateTime[1] + " " + arrDateTime[2];
        }else{
            return "";
        }
    }

    public static SharedPreferences getLogInSession(Context context){
        SharedPreferences session = context.getSharedPreferences(context.getString(R.string.sessions_preference_file_key), Context.MODE_PRIVATE);
        return session;
    }

    public static ArrayList<Integer> getdaysOfWeekBetweenDates(Calendar date, Calendar other) {
        final int DAY_ADDEND = 1;
        ArrayList<Integer> days = new ArrayList<Integer>();

        Calendar date1 = Calendar.getInstance();
        date1.clear();
        date1.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

        Calendar date2 = Calendar.getInstance();
        date2.clear();
        date2.set(other.get(Calendar.YEAR), other.get(Calendar.MONTH), other.get(Calendar.DAY_OF_MONTH));

        while(!date1.equals(date2)) {
            days.add(date1.get(Calendar.DAY_OF_WEEK));
            date1.add(Calendar.DAY_OF_MONTH, DAY_ADDEND);
        }
        // need to add the last day, as we are doing partial days
        days.add(date1.get(Calendar.DAY_OF_WEEK));
        return days;
    }

    public static int getNumOfWeekdays(ArrayList<Integer> days) {
        int result = 0;
        for(int i = 0; i < days.size(); i++) {
            if(!(days.get(i) == Calendar.SUNDAY || days.get(i) == Calendar.SATURDAY)) {
                result++;
            }
        }
        return result;
    }

    public static int getNumOfWeekends(ArrayList<Integer> days) {
        int result = 0;
        for(int i = 0; i < days.size(); i++) {
            if(days.get(i) == Calendar.SUNDAY || days.get(i) == Calendar.SATURDAY) {
                result++;
            }
        }
        return result;
    }

    public static String getAmountInCurrency(double amount, String currencyFormat){
        DecimalFormat df = new DecimalFormat(currencyFormat);
        String currency = df.format(amount);
        currency = padZeroAsNeeded(currency);
        currency = "$" + currency;
        return currency;
    }

    private static String padZeroAsNeeded(String amount){
        String[] parts = amount.split("\\.");

        if(parts.length == 1){
            amount = amount + ".00";
        }else if(parts[1].trim().length() == 1){
            amount = amount + "0";
        }
        return amount;
    }

    public static boolean intToBool(int val){
        boolean result = false;
        if(val == 1){
            result = true;
        }
        return result;
    }

    public static int boolToInt(boolean bool){
        int result = 0;
        if(bool){
            result = 1;
        }
        return result;
    }

    public static boolean schedulable(Calendar startDateTime, Calendar endDateTime, LinkedList<Calendar> reservationDateList) {
        boolean result = false;
        final int INDEX_FIRST_DATE = 0;
        final int INDEX_LAST_DATE = reservationDateList.size()-1;
        if(reservationDateList.size() == 0){
            result = true;
        }else if(startDateTime.before(reservationDateList.get(INDEX_FIRST_DATE)) && endDateTime.before(reservationDateList.get(INDEX_FIRST_DATE))) {
            result = true;
        }else if(startDateTime.after(reservationDateList.get(INDEX_LAST_DATE)) && endDateTime.after(reservationDateList.get(INDEX_LAST_DATE))) {
            result = true;
        }else {
            result = schedulableInBetweenDates(startDateTime, endDateTime, reservationDateList);
        }
        return result;
    }

    public static boolean schedulableInBetweenDates(Calendar startDateTime, Calendar endDateTime, LinkedList<Calendar> reservationDateList) {
        final int INDEX_FIST_RESERVATION_END_DATE = 1;
        final int INDEX_ADDEND_FOR_NEXT_RESERVATION_END_DATE = 2;
        boolean schedulable = false;
        Calendar currentReservationEndDateTime, nextReservationStartDateTime;
        int index = INDEX_FIST_RESERVATION_END_DATE;
        while(!schedulable && (index < reservationDateList.size()-2)) {
            currentReservationEndDateTime = reservationDateList.get(index);
            nextReservationStartDateTime = reservationDateList.get(index+1);

            if(startDateTime.after(currentReservationEndDateTime) && endDateTime.before(nextReservationStartDateTime)) {
                schedulable = true;
            }
            index += INDEX_ADDEND_FOR_NEXT_RESERVATION_END_DATE;
        }
        return schedulable;
    }

    public static String getLoggedInUserName(Context context){
        SharedPreferences sessionPref = getLogInSession(context);
        String username = sessionPref.getString(context.getString(R.string.session_loggedin_username), "");
        return username;
    }

    public static int getCarCapcityByName(CarName carName){
        int capacity;
        if(carName == CarName.SMART){
            capacity = CAPACITY_CAR_SMART;
        }else if(carName == CarName.ECONOMY){
            capacity = CAPACITY_CAR_ECONOMY;
        }else if(carName == CarName.COMPACT){
            capacity = CAPACITY_CAR_COMPACT;
        }else if(carName == CarName.INTERMEDIATE){
            capacity = CAPACITY_CAR_INTERMEDIATE;
        }else if(carName == CarName.STANDARD){
            capacity = CAPACITY_CAR_STANDARD;
        }else if(carName == CarName.FULLSIZE){
            capacity = CAPACITY_CAR_FULL_SIZE;
        }else if(carName == CarName.SUV){
            capacity = CAPACITY_CAR_SUV;
        }else if(carName == CarName.MINIVAN){
            capacity = CAPACITY_CAR_MINIVAN;
        }else if(carName == CarName.ULTRASPORTS){
            capacity = CAPACITY_CAR_ULTRASPORTS;
        }else{
            capacity = CAPACITY_CAR_NONE;
        }
        return capacity;
    }

    public static void logout(Context context){
        SharedPreferences sessionPrefs = getLogInSession(context);
        SharedPreferences.Editor session = sessionPrefs.edit();
        session.clear();
        session.commit();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static UserStatus getSystemUserStatus(Context context){
        return AAUtil.userStatusIntToEnum(SystemUserDAO.getInstance(context).getSystemUserStatus(AAUtil.getLoggedInUserName(context)));
    }

    public static UserStatus userStatusIntToEnum(int userStatus){
        if(userStatus == 1){
            return UserStatus.ACTIVE;
        }else{
            return UserStatus.REVOKED;
        }
    }

    public static String userStatusEnumToStr(UserStatus userStatus){
        if(userStatus == UserStatus.ACTIVE){
            return "Active";
        }else{
            return "Revoked";
        }
    }

}
