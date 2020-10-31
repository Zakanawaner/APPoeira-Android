package com.onethousandprojects.appoeira.serverStuff.sendEmail;

public class ClientSendEmailRequest {
    private String token;
    private Integer type;
    private Integer firstId;
    private Integer secondId;

    public ClientSendEmailRequest(String token, Integer type, Integer firstId, Integer secondId) {
        super();
        this.token = token;
        this.type = type;
        this.firstId = firstId;
        this.secondId = secondId;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFirstId() {
        return firstId;
    }

    public void setFirstId(Integer firstId) {
        this.firstId = firstId;
    }

    public Integer getSecondId() {
        return secondId;
    }

    public void setSecondId(Integer secondId) {
        this.secondId = secondId;
    }
}
