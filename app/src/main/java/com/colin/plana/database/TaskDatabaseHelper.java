package com.colin.plana.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.colin.plana.constants.TaskType;
import com.colin.plana.entities.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public final class TaskDatabaseHelper {

    private TaskDatabaseHelper() {
    }

    public static ArrayList<TaskEntity> queryTaskList(Context context,int queryType) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getReadableDatabase();
        final String sql = "SELECT * FROM " + DatabaseHelper.TABLE_TASK + " WHERE type =?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(queryType)});
        ArrayList<TaskEntity> taskEntities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                final int id = cursor.getInt(cursor.getColumnIndex("id"));
                final String title = cursor.getString(cursor.getColumnIndex("title"));
                final String content = cursor.getString(cursor.getColumnIndex("content"));
                final int belong = cursor.getInt(cursor.getColumnIndex("daily_number"));
                final int type = cursor.getInt(cursor.getColumnIndex("type"));
                taskEntities.add(new TaskEntity(id, title, content, belong, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return taskEntities;
    }

    public static void saveTask(Context context, TaskEntity entity) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        final String sql = "INSERT INTO " + DatabaseHelper.TABLE_TASK + " (title,content,daily_number,type) VALUES (?,?,?,?)";
        database.execSQL(sql, new Object[]{entity.getTitle(), entity.getContent(), entity.getDailyNumber(), entity.getType()});
        database.close();

    }

    public static void changeTaskType(Context context, TaskEntity task, int targeType) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        final String sql = "UPDATE " + DatabaseHelper.TABLE_TASK + " SET type=? WHERE id=?";
        database.execSQL(sql, new Object[]{targeType, task.getId()});
        database.close();
    }

    public static void deleteTask(Context context, TaskEntity entity) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        final String sql = "DELETE FROM " + DatabaseHelper.TABLE_TASK + " WHERE id =?";
        database.execSQL(sql, new Object[]{entity.getId()});
        database.close();
    }

    public static void deleteAllTask(Context context) {
        SQLiteDatabase database = DatabaseHelper.getInstance(context).getWritableDatabase();
        final String sql = "DELETE FROM " + DatabaseHelper.TABLE_TASK;
        database.execSQL(sql, new Object[]{});
        database.close();
    }
}
