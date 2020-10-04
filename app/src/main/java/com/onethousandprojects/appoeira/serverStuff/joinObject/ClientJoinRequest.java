package com.onethousandprojects.appoeira.serverStuff.joinObject;


public class ClientJoinRequest {
    private Integer groupId;
    private Integer userId;
    private Integer roleId;

    public ClientJoinRequest(Integer groupId, Integer userId, Integer roleId) {
        super();
        this.groupId = groupId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}