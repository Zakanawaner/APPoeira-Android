package com.onethousandprojects.appoeira.serverStuff.onlineDetail;


public class ClientOnlineDetailRequest {
    private String token;
    private Integer onlineId;
    private Integer userId;

    public ClientOnlineDetailRequest(String token, Integer onlineId, Integer userId) {
        super();
        this.token = token;
        this.onlineId = onlineId;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
