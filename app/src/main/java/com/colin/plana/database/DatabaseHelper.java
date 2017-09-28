package com.colin.plana.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by colin on 2017/9/25.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "WeeklyTaskList.db";
    private static final int DATABASE_VERSION = 1;

    private static volatile DatabaseHelper INSTANCE = null;

    public static DatabaseHelper getInstance(Context context) {
        DatabaseHelper temp = INSTANCE;
        if (temp == null) {
            synchronized (DatabaseHelper.class) {
                temp = INSTANCE;
                if (temp == null) {
                    temp = new DatabaseHelper(context);
                    INSTANCE = temp;
                }
            }
        }
        return temp;
    }


    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_TASK = "table_task";

    private static final String CREATE_TABLE_TASK = "CREATE TABLE " + TABLE_TASK + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "title TEXT,"
            + "content TEXT,"
            + "daily_number INTEGER NOT NULL,"
            + "type INTEGER NOT NULL"
            + ")";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
