package com.krixi.sqlitecrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KS114 on 8/4/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "Employee_Database.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE employees " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "empName TEXT, " +
                "empEmail TEXT, empRelocate TEXT ) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql = "DROP TABLE IF EXISTS employees";
        db.execSQL(sql);
        onCreate(db);
    }
}
