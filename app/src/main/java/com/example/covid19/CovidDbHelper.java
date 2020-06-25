package com.example.covid19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;



public class CovidDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "Covid19.db";
    public static final String TAG = "DBHelper Class";


    public CovidDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d(TAG,"created a new database");
        db.execSQL(Contract.LocationTable.SQL_CREATE_ENTRIES);
        db.execSQL(Contract.IndiaLocationTable.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"deleting the old database");
        db.execSQL(Contract.LocationTable.SQL_DELETE_ENTRIES);
        db.execSQL(Contract.IndiaLocationTable.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
