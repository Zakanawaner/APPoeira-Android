package com.onethousandprojects.appoeira.serverStuff.eventDetailMore;


public class ClientEventDetailMoreRequest {
    private String token;
    private Integer groupId;

    public ClientEventDetailMoreRequest(String token, Integer groupId) {
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
