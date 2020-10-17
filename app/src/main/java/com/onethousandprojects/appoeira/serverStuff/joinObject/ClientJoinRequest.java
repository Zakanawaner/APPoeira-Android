package com.onethousandprojects.appoeira.serverStuff.joinObject;


public class ClientJoinRequest {
    private String token;
    private Integer groupId;
    private Integer userId;
    private Integer roleId;

    public ClientJoinRequest(String token, Integer groupId, Integer userId, Integer roleId) {
        super();
        this.token = token;
        this.groupId = groupId;
        this.userId = userId;
        this.roleId = roleId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

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
