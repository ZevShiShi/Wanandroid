package com.zev.wanandroid.mvp.ui.adapter;

public class ChapterBean {
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

}
