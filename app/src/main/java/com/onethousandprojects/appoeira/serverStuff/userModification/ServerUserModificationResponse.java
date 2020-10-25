package com.onethousandprojects.appoeira.serverStuff.userModification;

public class ServerUserModificationResponse {
    private String picUrl;
    private String apelhido;
    private String name;
    private String lastName;
    private Integer rank;
    private Integer error;
    private String email;
    private String token;

    public ServerUserModificationResponse(String picUrl,
                                          String apelhido,
                                          String name,
                                          String lastName,
                                          Integer rank,
                                          Integer error,
                                          String email,
                                          String token) {
        super();
        this.picUrl = picUrl;
        this.apelhido = apelhido;
        this.name = name;
        this.lastName = lastName;
        this.rank = rank;
        this.error = error;
        this.email = email;
        this.token = token;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
