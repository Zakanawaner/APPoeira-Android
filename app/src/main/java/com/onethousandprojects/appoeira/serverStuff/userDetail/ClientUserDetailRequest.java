package com.onethousandprojects.appoeira.serverStuff.userDetail;


public class ClientUserDetailRequest {
    private Integer userId;

    public ClientUserDetailRequest(Integer userId) {
        super();
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
