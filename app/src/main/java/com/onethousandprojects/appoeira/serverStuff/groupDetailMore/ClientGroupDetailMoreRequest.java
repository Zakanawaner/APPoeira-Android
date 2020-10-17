package com.onethousandprojects.appoeira.serverStuff.groupDetailMore;


public class ClientGroupDetailMoreRequest {
    private String token;
    private Integer groupId;

    public ClientGroupDetailMoreRequest(String token, Integer groupId) {
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
