package com.onethousandprojects.appoeira.serverStuff.groupDetail;


public class ClientGroupDetailRequest {
    private String token;
    private Integer groupId;
    private Integer userId;

    public ClientGroupDetailRequest(String token, Integer groupId, Integer userId) {
        super();
        this.token = token;
        this.groupId = groupId;
        this.userId = userId;
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
}
