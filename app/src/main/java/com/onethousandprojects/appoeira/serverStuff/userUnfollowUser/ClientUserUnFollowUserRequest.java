package com.onethousandprojects.appoeira.serverStuff.userUnfollowUser;


public class ClientUserUnFollowUserRequest {
    private String token;
    private Integer myId;
    private Integer userId;

    public ClientUserUnFollowUserRequest(String token, Integer myId, Integer userId) {
        super();
        this.token = token;
        this.myId = myId;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getGroupId() {
        return myId;
    }

    public void setGroupId(Integer myId) {
        this.myId = myId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
