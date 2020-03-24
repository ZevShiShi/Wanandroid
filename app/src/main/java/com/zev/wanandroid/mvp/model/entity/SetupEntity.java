package com.zev.wanandroid.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SetupEntity implements Parcelable {

    private List<SecondSetup> children;
    private int id;
    private int courseId;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;


    public List<SecondSetup> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.children);
        dest.writeInt(this.id);
        dest.writeInt(this.courseId);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
    }

    public SetupEntity() {
    }

    protected SetupEntity(Parcel in) {
        this.children = in.createTypedArrayList(SecondSetup.CREATOR);
        this.id = in.readInt();
        this.courseId = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readInt();
    }

    public static final Parcelable.Creator<SetupEntity> CREATOR = new Parcelable.Creator<SetupEntity>() {
        @Override
        public SetupEntity createFromParcel(Parcel source) {
            return new SetupEntity(source);
        }

        @Override
        public SetupEntity[] newArray(int size) {
            return new SetupEntity[size];
        }
    };
}
