package com.zev.wanandroid.mvp.model.entity;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;

public class Chapter {
    private boolean canEdit;
    private int chapterId;
    private int id;
    private int superChapterId;
    private String author;
    private String chapterName;
    private String superChapterName;
    private boolean collect;
    private int courseId;
    private String desc;
    private String descMd;
    private String envelopePic;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private String shareUser;
    private String title;
    private int userId;
    private int originId;
    private int visible;
    private int zan;
    private long publishTime;
    private long shareDate;
    private int selfVisible;
    private boolean fresh; // 是否新文章
    private List<Tags> tags;

    public List<Tags> getTags() {
        return tags;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public int getChapterId() {
        return chapterId;
    }

    public int getId() {
        return id;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescMd() {
        return descMd;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public String getLink() {
        return link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public String getOrigin() {
        return origin;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public String getTitle() {
        return title;
    }

    public int getUserId() {
        return userId;
    }

    public int getVisible() {
        return visible;
    }

    public int getZan() {
        return zan;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public long getShareDate() {
        return shareDate;
    }

    public int getSelfVisible() {
        return selfVisible;
    }


    public int getOriginId() {
        return originId;
    }

    public boolean isFresh() {
        return fresh;
    }

    public String getAuthor() {
        if (ObjectUtils.isEmpty(author)) {
            return shareUser;
        }
        return author;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "canEdit=" + canEdit +
                ", chapterId=" + chapterId +
                ", id=" + id +
                ", superChapterId=" + superChapterId +
                ", author=" + author +
                ", chapterName='" + chapterName + '\'' +
                ", superChapterName='" + superChapterName + '\'' +
                ", collect=" + collect +
                ", courseId=" + courseId +
                ", desc='" + desc + '\'' +
                ", descMd='" + descMd + '\'' +
                ", envelopePic='" + envelopePic + '\'' +
                ", link='" + link + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", niceShareDate='" + niceShareDate + '\'' +
                ", origin='" + origin + '\'' +
                ", prefix='" + prefix + '\'' +
                ", projectLink='" + projectLink + '\'' +
                ", shareUser='" + shareUser + '\'' +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", visible=" + visible +
                ", zan=" + zan +
                ", publishTime=" + publishTime +
                ", shareDate=" + shareDate +
                ", selfVisible=" + selfVisible +
                ", fresh=" + fresh +
                ", tags=" + tags +
                '}';
    }

    public class Tags {
        public String name;
        public String url;
    }
}
