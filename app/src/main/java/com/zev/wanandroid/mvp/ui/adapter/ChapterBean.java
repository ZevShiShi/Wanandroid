package com.zev.wanandroid.mvp.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class ChapterBean implements Parcelable {
    private boolean showNew;
    private boolean showTag;
    private boolean showTop;
    private boolean showDesc;

    private String author;
    private String time;
    private String type;
    private String title;
    private String desc;
    private String tag;
    private String link;
    private String imgLink;
    private int id;
    private int originId;
    private boolean collect;


    public ChapterBean() {
    }

    public ChapterBean(String author, String time, String type, String title, String desc) {
        this.author = author;
        this.time = time;
        this.type = type;
        this.title = title;
        this.desc = desc;
    }

    public ChapterBean(String author, String time, String type, String title, String desc, String imgLink) {
        this.author = author;
        this.time = time;
        this.type = type;
        this.title = title;
        this.desc = desc;
        this.imgLink = imgLink;
    }


    public boolean isShowNew() {
        return showNew;
    }

    public void setShowNew(boolean showNew) {
        this.showNew = showNew;
    }

    public boolean isShowTag() {
        return showTag;
    }

    public void setShowTag(boolean showTag) {
        this.showTag = showTag;
    }

    public boolean isShowTop() {
        return showTop;
    }

    public void setShowTop(boolean showTop) {
        this.showTop = showTop;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isShowDesc() {
        return showDesc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setShowDesc(boolean showDesc) {
        this.showDesc = showDesc;
    }


    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    @Override
    public String toString() {
        return "ChapterBean{" +
                "showNew=" + showNew +
                ", showTag=" + showTag +
                ", showTop=" + showTop +
                ", showDesc=" + showDesc +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", tag='" + tag + '\'' +
                ", link='" + link + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", id='" + id + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.showNew ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showTag ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showTop ? (byte) 1 : (byte) 0);
        dest.writeByte(this.showDesc ? (byte) 1 : (byte) 0);
        dest.writeString(this.author);
        dest.writeString(this.time);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.tag);
        dest.writeString(this.link);
        dest.writeString(this.imgLink);
        dest.writeInt(this.id);
        dest.writeInt(this.originId);
        dest.writeByte(this.collect ? (byte) 1 : (byte) 0);
    }

    protected ChapterBean(Parcel in) {
        this.showNew = in.readByte() != 0;
        this.showTag = in.readByte() != 0;
        this.showTop = in.readByte() != 0;
        this.showDesc = in.readByte() != 0;
        this.author = in.readString();
        this.time = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.desc = in.readString();
        this.tag = in.readString();
        this.link = in.readString();
        this.imgLink = in.readString();
        this.id = in.readInt();
        this.originId = in.readInt();
        this.collect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ChapterBean> CREATOR = new Parcelable.Creator<ChapterBean>() {
        @Override
        public ChapterBean createFromParcel(Parcel source) {
            return new ChapterBean(source);
        }

        @Override
        public ChapterBean[] newArray(int size) {
            return new ChapterBean[size];
        }
    };
}
