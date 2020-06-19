package com.example.task_list.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class LocalDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite DB";
    private static int VERSION = 1; // update this variable when you want to run onUpgrade again on user cellphone


    private static String NAME_DB = "DB_TASKS";
    public static String TABLE_NAME = "tasks";
    public static String KEY_TEXT = "name";
    public static String KEY_ID = "id";

    public LocalDbHelper(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    // Initialize (Called once). Normally used to create tabled
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TEXT +
                    " TEXT NOT NULL" + ")";
            db.execSQL(CREATE_TABLE);

            Log.i(TAG, "success");
        } catch (Exception e) {
            Log.e(TAG, "Error to create table");
            Log.e(TAG, e.getMessage());
        }
    }

    // When the app is already installed and we're just updating version
    // Can be used to crate new columns on an already created table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
