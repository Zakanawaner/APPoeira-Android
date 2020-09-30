package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse;

public class ServerUserDetailGroupVotesResponse {
    private Integer groupId;
    private String groupName;
    private String groupPicUrl;
    private String groupVote;
    private String groupDate;

    public ServerUserDetailGroupVotesResponse(Integer groupId, String groupName, String groupPicUrl, String groupVote, String groupDate) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPicUrl = groupPicUrl;
        this.groupVote = groupVote;
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

    public String getGroupVote() {
        return groupVote;
    }

    public void setGroupVote(String groupVote) {
        this.groupVote = groupVote;
    }

    public String getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(String groupDate) {
        this.groupDate = groupDate;
    }

}
