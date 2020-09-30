package com.onethousandprojects.appoeira.userDetailView.content;

public class UserGroupContent {
    private Integer groupId;
    private String groupName;
    private String groupPicUrl;
    private String groupRole;

    public UserGroupContent(Integer groupId, String groupName, String groupPicUrl, String groupRole) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPicUrl = groupPicUrl;
        this.groupRole = groupRole;
    }

    public Integer getId() {
        return groupId;
    }

    public void setId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return groupName;
    }

    public void setName(String groupName) {
        this.groupName = groupName;
    }

    public String getPicUrl() {
        return groupPicUrl;
    }

    public void setPicUrl(String groupPicUrl) {
        this.groupPicUrl = groupPicUrl;
    }

    public String getRole() {
        return groupRole;
    }

    public void setRole(String groupRole) {
        this.groupRole = groupRole;
    }
}