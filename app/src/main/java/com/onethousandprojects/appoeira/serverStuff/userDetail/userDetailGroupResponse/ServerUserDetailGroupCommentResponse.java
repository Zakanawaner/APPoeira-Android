package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse;

public class ServerUserDetailGroupCommentResponse {
    private Integer groupId;
    private String groupName;
    private String groupPicUrl;
    private String groupComment;
    private String groupDate;

    public ServerUserDetailGroupCommentResponse(Integer groupId, String groupName, String groupPicUrl, String groupComment, String groupDate) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPicUrl = groupPicUrl;
        this.groupComment = groupComment;
        this.groupDate = groupDate;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupPicUrl() {
        return groupPicUrl;
    }

    public void setGroupPicUrl(String groupPicUrl) {
        this.groupPicUrl = groupPicUrl;
    }

    public String getGroupComment() {
        return groupComment;
    }

    public void setGroupComment(String groupComment) {
        this.groupComment = groupComment;
    }

    public String getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(String groupDate) {
        this.groupDate = groupDate;
    }

}
