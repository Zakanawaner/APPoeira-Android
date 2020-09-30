package com.onethousandprojects.appoeira.serverStuff.signupTransaction;

public class ServerSignupResponse {
    private Integer id;
    private Integer rank;
    private String token;
    private String name;
    private String lastName;
    private String picUrl;
    private String apelhido;
    private boolean emailVerified;
    private String email;

    public ServerSignupResponse(Integer id, Integer rank, String token,
                                String name, String lastName, String picUrl,
                                String apelhido, boolean emailVerified, String email) {
        super();
        this.id = id;
        this.rank = rank;
        this.token = token;
        this.name = name;
        this.lastName = lastName;
        this.picUrl = picUrl;
        this.apelhido = apelhido;
        this.emailVerified = emailVerified;
        this.email = email;

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
