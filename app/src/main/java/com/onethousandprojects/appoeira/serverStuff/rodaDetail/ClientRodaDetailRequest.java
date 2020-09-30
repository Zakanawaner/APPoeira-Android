package com.onethousandprojects.appoeira.serverStuff.rodaDetail;


public class ClientRodaDetailRequest {
    private String token;
    private Integer rodaId;
    private Integer userId;

    public ClientRodaDetailRequest(String token, Integer rodaId, Integer userId) {
        super();
        this.token = token;
        this.rodaId = rodaId;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getGroupId() {
        return rodaId;
    }

    public void setGroupId(Integer rodaId) {
        this.rodaId = rodaId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
