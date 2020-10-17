package com.onethousandprojects.appoeira.serverStuff.comments;


public class ClientCommentsRequest {
    private String token;
    private Integer groupId;

    public ClientCommentsRequest(String token, Integer groupId) {
        super();
        this.token = token;
        this.groupId = groupId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
