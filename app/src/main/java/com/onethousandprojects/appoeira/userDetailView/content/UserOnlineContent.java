package com.onethousandprojects.appoeira.userDetailView.content;

public class UserOnlineContent {
    private Integer onlineId;
    private String onlineName;
    private String onlinePicUrl;
    private String onlineRole;

    public UserOnlineContent(Integer onlineId, String onlineName, String onlinePicUrl, String onlineRole) {
        this.onlineId = onlineId;
        this.onlineName = onlineName;
        this.onlinePicUrl = onlinePicUrl;
        this.onlineRole = onlineRole;
    }

    public Integer getId() {
        return onlineId;
    }

    public void setId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public String getName() {
        return onlineName;
    }

    public void setName(String onlineName) {
        this.onlineName = onlineName;
    }

    public String getPicUrl() {
        return onlinePicUrl;
    }

    public void setPicUrl(String onlinePicUrl) {
        this.onlinePicUrl = onlinePicUrl;
    }

    public String getRole() {
        return onlineRole;
    }

    public void setRole(String onlineRole) {
        this.onlineRole = onlineRole;
    }
}