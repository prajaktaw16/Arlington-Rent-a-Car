package com.example.arlingtonrentacar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.arlingtonrentacar.AAUtil;
import com.example.arlingtonrentacar.R;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    public static final String SYSTEM_USERS_TABLE = "system_users";
    public static final String RESERVATIONS_TABLE = "reservations";
    public static final String CARS_TABLE = "cars";
    public static final String AA_STORE_HRS_TABLE = "aa_store_hrs";
    public static final String ARLINGTON_AUTO_DB = "ArlingtonAuto.db";
    private static final int DB_VERSION  = 3;
    private static final int DEV_MODE = 1;

    private static DatabaseHelper instance;

    private DatabaseHelper(@Nullable Context context) {
        super(context, ARLINGTON_AUTO_DB, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(@Nullable Context context){
        final String METHOD_NAME = "getInstance()";
        Log.d(LOG_TAG, METHOD_NAME + ": entered.");
        if(instance == null){
            instance = new DatabaseHelper(context);
            Log.d(LOG_TAG, METHOD_NAME + ": new instance created.");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "onCreate() called.");
        createTblSystemUsers(sqLiteDatabase);
        createTblReservations(sqLiteDatabase);
        createTblCars(sqLiteDatabase);
        createTblAAStoreHrs(sqLiteDatabase);

        carsAddData(sqLiteDatabase);
        aaStoreHrsAddData(sqLiteDatabase);
        if(DEV_MODE == 1){
            sysUsersAddDummyData(sqLiteDatabase);
            Log.d(LOG_TAG, "Adding Dummy Data to system_users table");
            reservationsAddDummyData(sqLiteDatabase);
            Log.d(LOG_TAG, "Adding Dummy Data to reservations table");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "onUpgrade() called.");
        Log.d(LOG_TAG, "onUpgrade(): oldVersion = " + oldVersion);
        Log.d(LOG_TAG, "onUpgrade(): newVersion = " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SYSTEM_USERS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CARS_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AA_STORE_HRS_TABLE);
        onCreate(sqLiteDatabase);
    }

    private void createTblSystemUsers(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "createTblSystemUsers()";
        String sql = "CREATE TABLE " + SYSTEM_USERS_TABLE + " ( " +
                "username TEXT PRIMARY KEY, " +
                "password TEXT, " +
                "last_name TEXT, " +
                "first_name TEXT, " +
                "role TEXT, " +
                "uta_id INTEGER, " +
                "phone TEXT, " +
                "email TEXT, " +
                "street_address TEXT, " +
                "city TEXT, " +
                "state TEXT, " +
                "zip_code TEXT, " +
                "aaa_member_status INTEGER DEFAULT 0, "+
                "user_status INTEGER DEFAULT 0" +
                ");";
        Log.d(LOG_TAG, SYSTEM_USERS_TABLE + ": " + sql);
        sqLiteDatabase.execSQL(sql);
    }

    private void createTblReservations(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "createTblReservations()";
        String sql = "CREATE TABLE " + RESERVATIONS_TABLE + " ( " +
                "reservation_id TEXT PRIMARY KEY, " +
                "car_number INTEGER, " +
                "last_name TEXT, " +
                "first_name TEXT, " +
                "car_name TEXT, " +
                "car_capacity INTEGER, " +
                "start_date TEXT, " +
                "start_time TEXT, " +
                "end_date TEXT, " +
                "end_time TEXT, " +
                "num_of_riders INTEGER, " +
                "total_price REAL, " +
                "gps INTEGER, " +
                "siriusxm INTEGER, " +
                "onstar INTEGER, " +
                "aaa_member_status INTEGER "+
                ");";

        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG, RESERVATIONS_TABLE + ": " + sql);
    }

    private void createTblCars(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "createTblCars()";
        String sql = "CREATE TABLE " + CARS_TABLE + " ( " +
                "car_name TEXT PRIMARY KEY, " +
                "capacity INTEGER, " +
                "car_status TEXT DEFAULT \"\", " +
                "weekday_rate real, " +
                "weekend_rate real, " +
                "weekly_rate real, " +
                "gps_rate_perday real, " +
                "siriusxm_rate_perday real, " +
                "onstar_rate_perday real " +
                ");";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG, CARS_TABLE + ": " + sql);
    }

    private void createTblAAStoreHrs(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "createTblAAStoreHrs()";
        String sql = "CREATE TABLE " + AA_STORE_HRS_TABLE + " ( " +
                "day_of_week TEXT PRIMARY KEY, " +
                "open_time TEXT, " +
                "close_time TEXT " +
                ");";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG, AA_STORE_HRS_TABLE + ": " + sql);
    }

    private void sysUsersAddDummyData(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "sysUsersAddDummyData()";
        String sql = "INSERT INTO " + SYSTEM_USERS_TABLE + "(username, password, last_name, " +
                "first_name, role, uta_id, phone, email, street_address, city, state, zip_code," +
                "aaa_member_status, user_status)" +
                "VALUES" +
                "(\"johndoe\", \"asdf\", \"Doe\", \"John\", \"renter\", 1999999999, \"1234567890\", " +
                "\"a@a.com\", \"Main Street\", \"Fort Wayne\", \"IN\", \"12345\", 1, 1)," +
                "(\"marryjane\", \"asdf\", \"Jane\", \"Marry\", \"manager\", 2999999999, \"1234567891\", " +
                "\"b@b.com\", \"Wall Street\", \"New York\", \"NY\", \"54321\", 0, 0)," +
                "(\"brucewayne\", \"asdf\", \"Wayne\", \"Bruce\", \"admin\", 3999999999, \"1234567892\", " +
                "\"c@c.com\", \"Back Street\", \"Gotham City\", \"NJ\", \"56789\", 0, 0);";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG,  sql);
    }

    private void reservationsAddDummyData(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "sysUsersAddDummyData()";
        String sql = "INSERT INTO " + RESERVATIONS_TABLE + "(reservation_id, car_number, last_name, " +
                "first_name, car_name, car_capacity, start_date, start_time, end_date, end_time, num_of_riders, total_price," +
                "gps, siriusxm, onstar, aaa_member_status)" +
                "VALUES" +
                "(\"1\",21, \"Doe\", \"John\", \"Smart\", 1, \"01/12/2020\", " +
                "\"12:00\", \"03/12/2020\", \"1:00\","+
                "1, 40, 1, 1, 1, 1),"+
                "(\"2\",22, \"Jane\", \"Marry\", \"Economy\", 3, \"02/12/2020\", " +
                "\"1:00\", \"04/12/2020\", \"2:00\","+
                "1, 60, 1, 0, 0, 1),"+
                "(\"3\",23, \"Wayne\", \"Bruce\", \"Compact\", 4, \"03/12/2020\", " +
                "\"11:00\", \"05/12/2020\", \"3:00\","+
                "1, 80, 1, 1, 0, 1);";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG,  sql);
    }

    private void carsAddData(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "carsAddData()";
        String sql = "INSERT INTO " + CARS_TABLE + "(car_name, capacity, weekday_rate, weekend_rate, " +
                "weekly_rate, gps_rate_perday, onstar_rate_perday, siriusxm_rate_perday)" +
                "VALUES "+
                "(\"Smart\", 1, 32.99, 37.99, 230.93, 3.00, 5.00, 7.00)," +
                "(\"Economy\", 3, 39.99, 44.99, 279.93, 3.00, 5.00, 7.00)," +
                "(\"Compact\", 4, 44.99, 49.99, 314.93, 3.00, 5.00, 7.00)," +
                "(\"Intermediate\", 4, 45.99, 50.99, 321.93, 3.00, 5.00, 7.00)," +
                "(\"Standard\", 5, 48.99, 53.99, 342.93, 3.00, 5.00, 7.00)," +
                "(\"Full Size\", 6, 52.99, 57.99, 370.93, 3.00, 5.00, 7.00)," +
                "(\"SUV\", 8, 59.99, 64.99, 419.93, 3.00, 5.00, 7.00)," +
                "(\"MiniVan\", 9, 59.99, 64.99, 419.93, 3.00, 5.00, 7.00)," +
                "(\"Ultra Sports\", 2, 199.99, 204.99, 1399.93, 5.00, 7.00, 9.00);";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG,  sql);
    }

    private void aaStoreHrsAddData(SQLiteDatabase sqLiteDatabase){
        final String LOG_TAG = "aaStoreHrsAddData()";
        String sql = "INSERT INTO " + AA_STORE_HRS_TABLE + "(day_of_week, open_time, close_time) " +
                "VALUES " +
                "(\"Monday\", \"08:00 AM\", \"08:00 PM\"), " +
                "(\"Tuesday\", \"08:00 AM\", \"08:00 PM\"), " +
                "(\"Wednesday\", \"08:00 AM\", \"08:00 PM\"), " +
                "(\"Thursday\", \"08:00 AM\", \"08:00 PM\"), " +
                "(\"Friday\", \"08:00 AM\", \"08:00 PM\"), " +
                "(\"Saturday\", \"08:00 AM\", \"05:00 PM\"), " +
                "(\"Sunday\", \"12:00 PM\", \"05:00 PM\");";
        sqLiteDatabase.execSQL(sql);
        Log.d(LOG_TAG,  sql);
    }
}
