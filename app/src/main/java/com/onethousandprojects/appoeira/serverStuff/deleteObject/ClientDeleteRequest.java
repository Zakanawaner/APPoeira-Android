package com.onethousandprojects.appoeira.serverStuff.deleteObject;


public class ClientDeleteRequest {
    private String token;
    private Integer objectId;
    private Integer userId;
    private Integer type;

    public ClientDeleteRequest(String token, Integer objectId, Integer userId) {
        super();
        this.token = token;
        this.objectId = objectId;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
