package com.onethousandprojects.appoeira.serverStuff.areThereNews;


public class ClientAreThereNewsRequest {
    private String token;
    private Integer userId;

    public ClientAreThereNewsRequest(String token, Integer userId) {
        super();
        this.token = token;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
