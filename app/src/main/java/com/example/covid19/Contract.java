package com.example.covid19;

import android.provider.BaseColumns;

public class Contract {
    private Contract(){}

    public static class LocationTable implements BaseColumns{
        public static final String TABLE_NAME = "LocationTable";
        public static final String COL_STATE = "state";
        public static final String COL_COUNTRY ="country";
        public static final String COL_LAT = "lat";
        public static final String COL_LONG = "long";
        public static final String COL_DATE = "date";
        public static final String COL_CONFIRMED = "confirmed";
        public static final String COL_DEATHS = "deaths";
        public static final String COL_RECOVERED = "recovered";

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_STATE+" VARCHAR(40),"+
                COL_COUNTRY+" VARCHAR(40),"+
                COL_LAT+" FLOAT(10,4),"+
                COL_LONG+" FLOAT(10,4),"+
                COL_DATE+" DATE,"+
                COL_CONFIRMED+" INTEGER,"+
                COL_DEATHS+" INTEGER,"+
                COL_RECOVERED+" INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class IndiaLocationTable implements BaseColumns{
        public static final String TABLE_NAME = "IndiaLocationTable";
        public static final String COL_DATE = "date";
        public static final String COL_STATE = "state";
        public static final String COL_RECOVERED = "recovered";
        public static final String COL_DEATHS = "deaths";
        public static final String COL_CONFIRMED = "confirmed";

        public static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+TABLE_NAME+" ("+
                _ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COL_DATE+" DATE,"+
                COL_STATE+" VARCHAR(40),"+
                COL_RECOVERED+" INTEGER,"+
                COL_DEATHS+" INTEGER,"+
                COL_CONFIRMED+" INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
