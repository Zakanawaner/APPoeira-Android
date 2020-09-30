package com.onethousandprojects.appoeira.serverStuff.userFollowUser;


public class ClientUserFollowUserRequest {
    private Integer myId;
    private Integer userId;

    public ClientUserFollowUserRequest(Integer myId, Integer userId) {
        super();
        this.myId = myId;
        this.userId = userId;
    }

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
