package com.onethousandprojects.appoeira.rodaDetailMoreView.content;

public class RodaCommentsContent {
    private Integer userId;
    private String picUrl;
    private String userApelhido;
    private String comment;
    private String date;

    public RodaCommentsContent(Integer userId, String picUrl, String userApelhido, String comment, String date) {
        this.userId = userId;
        this.picUrl = picUrl;
        this.userApelhido = userApelhido;
        this.comment = comment;
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUserApelhido() {
        return userApelhido;
    }

    public void setUserApelhido(String userApelhido) {
        this.userApelhido = userApelhido;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}