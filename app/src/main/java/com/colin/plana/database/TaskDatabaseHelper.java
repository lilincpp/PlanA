package com.colin.plana.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.colin.plana.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public final class TaskDatabaseHelper {

    private TaskDatabaseHelper() {
    }

    public static List<TaskEntity> queryAllTask(Context context) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        final String sql = "SELECT * FROM " + DatabaseHelper.TABLE_TASK;
        Cursor cursor = database.rawQuery(sql, new String[]{});
        List<TaskEntity> taskEntities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                final String title = cursor.getString(cursor.getColumnIndex("title"));
                final String content = cursor.getString(cursor.getColumnIndex("content"));
                final int belong = cursor.getInt(cursor.getColumnIndex("belong"));
                taskEntities.add(new TaskEntity(title, content, belong));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return taskEntities;
    }

    public static void saveTask(Context context, TaskEntity entity) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        final String sql = "INSERT INTO " + DatabaseHelper.TABLE_TASK + " (title,content,belong) VALUES (?,?,?)";
        database.execSQL(sql, new Object[]{entity.getTitle(), entity.getContent(), entity.getBelong()});
        database.close();
    }
}
