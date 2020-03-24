package com.zev.wanandroid.mvp.model.entity;

public class UserEntity {

//            "admin": false,
//            "chapterTops": [],
//            "collectIds": [],
//            "email": "",
//            "icon": "",
//            "id": 44945,
//            "nickname": "zevzhu",
//            "password": "",
//            "publicName": "zevzhu",
//            "token": "",
//            "type": 0,
//            "username": "zevzhu"


    private int id;
    private boolean admin;
    private String email;
    private String icon;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private int type;
    private String username;


    public int getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public String getIcon() {
        return icon;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPublicName() {
        return publicName;
    }

    public String getToken() {
        return token;
    }

    public int getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", admin=" + admin +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", publicName='" + publicName + '\'' +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}
