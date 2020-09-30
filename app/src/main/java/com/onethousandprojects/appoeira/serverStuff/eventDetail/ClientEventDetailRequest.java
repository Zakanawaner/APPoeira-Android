package com.onethousandprojects.appoeira.serverStuff.eventDetail;


public class ClientEventDetailRequest {
    private String token;
    private Integer eventId;
    private Integer userId;

    public ClientEventDetailRequest(String token, Integer eventId, Integer userId) {
        super();
        this.token = token;
        this.eventId = eventId;
        this.userId = userId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
