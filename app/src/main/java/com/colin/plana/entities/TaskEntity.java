package com.colin.plana.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by colin on 2017/9/25.
 */

public final class TaskEntity implements Parcelable {

    public TaskEntity(String title, String content, int belong) {
        this.title = title;
        this.content = content;
        this.belong = belong;
    }

    public TaskEntity(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.belong = in.readInt();
    }

    private String title;
    private String content;
    private int belong;

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

    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
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
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(belong);
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", belong=" + belong +
                '}';
    }
}
