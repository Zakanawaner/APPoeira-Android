package com.onethousandprojects.appoeira.userDetailView.content;

public class UserFollowersContent {
    private Integer userId;
    private String userApelhido;
    private String userPicUrl;
    private String userDate;

    public UserFollowersContent(Integer userId, String userApelhido, String userPicUrl, String userDate) {
        this.userId = userId;
        this.userApelhido = userApelhido;
        this.userPicUrl = userPicUrl;
        this.userDate = userDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserApelhido() {
        return userApelhido;
    }

    public void setUserApelhido(String userApelhido) {
        this.userApelhido = userApelhido;
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }
}