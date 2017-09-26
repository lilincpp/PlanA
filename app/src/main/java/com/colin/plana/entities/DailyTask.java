package com.colin.plana.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by colin on 2017/9/25.
 */

public final class DailyTask implements Parcelable {

    private String name;
    private int dailyNumber;
    private ArrayList<TaskEntity> taskEntities;

    public DailyTask(String name, int dailyNumber) {
        this.name = name;
        this.dailyNumber = dailyNumber;
    }

    public DailyTask(Parcel in) {
        this.name = in.readString();
        this.dailyNumber = in.readInt();
        this.taskEntities = in.readArrayList(TaskEntity.class.getClassLoader());
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

    public boolean isEmpty() {
        return taskEntities == null || taskEntities.size() == 0;
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
        dest.writeList(taskEntities);
    }
}
