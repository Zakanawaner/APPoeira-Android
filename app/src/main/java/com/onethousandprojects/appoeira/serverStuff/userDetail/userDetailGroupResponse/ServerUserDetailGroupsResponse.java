package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailGroupResponse;

public class ServerUserDetailGroupsResponse {
    private Integer groupId;
    private String groupName;
    private String groupPicUrl;
    private String groupRole;
    private String groupDate;

    public ServerUserDetailGroupsResponse(Integer groupId, String groupName, String groupPicUrl, String groupRole, String groupDate) {
        super();
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPicUrl = groupPicUrl;
        this.groupRole = groupRole;
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

    public String getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(String groupRole) {
        this.groupRole = groupRole;
    }

    public String getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(String groupDate) {
        this.groupDate = groupDate;
    }

}
