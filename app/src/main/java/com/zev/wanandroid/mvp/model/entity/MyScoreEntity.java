package com.zev.wanandroid.mvp.model.entity;

import java.util.List;

public class MyScoreEntity {

    public int curPage;
    public List<Score> datas;
    public int pageCount;
    public int size;
    public int total;
    public boolean over;


    public class Score {
        private int coinCount;
        private int userId;
        private String username;
        private String desc;
        private long date;
        private int id;
        private int type;
        private String reason;

        public int getCoinCount() {
            return coinCount;
        }

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getDesc() {
            return desc;
        }

        public long getDate() {
            return date;
        }

        public int getId() {
            return id;
        }

        public int getType() {
            return type;
        }

        public String getReason() {
            return reason;
        }
    }


}
