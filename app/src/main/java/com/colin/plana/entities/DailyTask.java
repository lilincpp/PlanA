package com.colin.plana.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.colin.plana.constants.TaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public final class DailyTask implements Parcelable {

    private String name;
    private int dailyNumber;
    private int type;
    private ArrayList<TaskEntity> taskEntities;


    public DailyTask(String name, int dailyNumber, int type) {
        this.name = name;
        this.dailyNumber = dailyNumber;
        this.type = type;
    }

    public DailyTask(String name, int type, ArrayList<TaskEntity> taskEntities) {
        this.name = name;
        this.type = type;
        this.dailyNumber = -1;
        this.taskEntities = taskEntities;
    }

    public DailyTask(Parcel in) {
        this.name = in.readString();
        this.dailyNumber = in.readInt();
        this.type = in.readInt();
        this.taskEntities = in.readArrayList(TaskEntity.class.getClassLoader());
    }

    /**
     * 显示的列表是否为空
     * @return
     */
    public boolean isEmpty() {
        return taskEntities == null || taskEntities.size() == 0;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(int dailyNumber) {
        this.dailyNumber = dailyNumber;
    }


    public ArrayList<TaskEntity> getTaskEntities() {
        return taskEntities;
    }

    public void setTaskEntities(ArrayList<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public static final Parcelable.Creator<DailyTask> CREATOR = new Creator<DailyTask>() {
        @Override
        public DailyTask createFromParcel(Parcel source) {
            return new DailyTask(source);
        }

        @Override
        public DailyTask[] newArray(int size) {
            return new DailyTask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(dailyNumber);
        dest.writeInt(type);
        dest.writeList(taskEntities);
    }
}
