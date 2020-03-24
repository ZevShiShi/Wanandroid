package com.zev.wanandroid.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SecondSetup implements Parcelable {

    public int id;
    public int courseId;
    public String name;
    public int order;
    public int parentChapterId;
    public boolean userControlSetTop;
    public int visible;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.courseId);
        dest.writeString(this.name);
        dest.writeInt(this.order);
        dest.writeInt(this.parentChapterId);
        dest.writeByte(this.userControlSetTop ? (byte) 1 : (byte) 0);
        dest.writeInt(this.visible);
    }

    public SecondSetup() {
    }

    protected SecondSetup(Parcel in) {
        this.id = in.readInt();
        this.courseId = in.readInt();
        this.name = in.readString();
        this.order = in.readInt();
        this.parentChapterId = in.readInt();
        this.userControlSetTop = in.readByte() != 0;
        this.visible = in.readInt();
    }

    public static final Parcelable.Creator<SecondSetup> CREATOR = new Parcelable.Creator<SecondSetup>() {
        @Override
        public SecondSetup createFromParcel(Parcel source) {
            return new SecondSetup(source);
        }

        @Override
        public SecondSetup[] newArray(int size) {
            return new SecondSetup[size];
        }
    };
}
