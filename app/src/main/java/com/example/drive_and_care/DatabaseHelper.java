package com.example.drive_and_care;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "mtag";
    private Context context;
    private static final String DB_NAME = "Test.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "user";
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "name";
    private static final String COL_CITY = "city";
    public static final String COL_PHONE = "phone";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT, " +
                        COL_CITY + " TEXT, " +
                        COL_PHONE + " TEXT);";


        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    void addUser(String userName, String userCity, String userPhone){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, userName);
        cv.put(COL_CITY, userCity);
        cv.put(COL_PHONE, userPhone);
        long res = sqLiteDatabase.insert(TABLE_NAME, null, cv);


        if (res  == -1){
            Log.d(TAG, "addUser: Failed to insert user");
        }
        else {
            Log.d(TAG, "addUser: User added success");
        }
    }
}
