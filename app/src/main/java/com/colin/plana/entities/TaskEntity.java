package com.colin.plana.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by colin on 2017/9/25.
 */

public final class TaskEntity implements Parcelable {

    public TaskEntity(int id, String title, String content, int dailyNumber, int type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dailyNumber = dailyNumber;
        this.type = type;
    }

    public TaskEntity(String title, String content, int dailyNumber, int type) {
        this.title = title;
        this.content = content;
        this.dailyNumber = dailyNumber;
        this.type = type;
    }

    public TaskEntity(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.dailyNumber = in.readInt();
        this.type = in.readInt();
    }

    private int id;
    private String title;
    private String content;
    private int dailyNumber;
    private int type;

    //是否被选中
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDailyNumber() {
        return dailyNumber;
    }

    public void setDailyNumber(int dailyNumber) {
        this.dailyNumber = dailyNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static final Parcelable.Creator<TaskEntity> CREATOR = new Creator<TaskEntity>() {
        @Override
        public TaskEntity createFromParcel(Parcel source) {
            return new TaskEntity(source);
        }

        @Override
        public TaskEntity[] newArray(int size) {
            return new TaskEntity[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(dailyNumber);
        parcel.writeInt(type);
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dailyNumber=" + dailyNumber +
                '}';
    }
}
