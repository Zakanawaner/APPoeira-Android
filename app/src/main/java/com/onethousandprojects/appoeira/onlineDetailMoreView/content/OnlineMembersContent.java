package com.onethousandprojects.appoeira.onlineDetailMoreView.content;

public class OnlineMembersContent {
    private String groupSchool;
    private Integer userId;
    private String userApelhido;
    private String userPicUrl;
    private boolean userPremium;
    private String userRank;
    private String userGroupRole;

    public OnlineMembersContent(String groupSchool, Integer userId, String userApelhido, String userPicUrl,
                                boolean userPremium, String userRank, String userGroupRole) {
        this.groupSchool = groupSchool;
        this.userId = userId;
        this.userApelhido = userApelhido;
        this.userPicUrl = userPicUrl;
        this.userPremium = userPremium;
        this.userRank = userRank;
        this.userGroupRole = userGroupRole;
    }

    public String getGroupSchool() {
        return groupSchool;
    }

    public void setGroupSchool(String groupSchool) {
        this.groupSchool = groupSchool;
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

    public boolean isUserPremium() {
        return userPremium;
    }

    public void setUserPremium(boolean userPremium) {
        this.userPremium = userPremium;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }

    public String getUserGroupRole() {
        return userGroupRole;
    }

    public void setUserGroupRole(String userGroupRole) {
        this.userGroupRole = userGroupRole;
    }
}