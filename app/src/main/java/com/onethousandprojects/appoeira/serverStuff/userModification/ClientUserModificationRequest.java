package com.onethousandprojects.appoeira.serverStuff.userModification;

public class ClientUserModificationRequest {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String apelhido;
    private String email;
    private String password;
    private String newPassword;
    private Integer rank;
    private String picUrl;

    public ClientUserModificationRequest(Integer userId, String firstName, String lastName, String apelhido,
                                         String email, String password, String newPassword, Integer rank, String picUrl) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.apelhido = apelhido;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.rank = rank;
        this.picUrl = picUrl;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
