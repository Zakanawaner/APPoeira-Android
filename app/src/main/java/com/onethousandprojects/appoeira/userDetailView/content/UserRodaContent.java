package com.onethousandprojects.appoeira.userDetailView.content;

public class UserRodaContent {
    private Integer rodaId;
    private String rodaName;
    private String rodaPicUrl;
    private String rodaRole;

    public UserRodaContent(Integer rodaId, String rodaName, String rodaPicUrl, String rodaRole) {
        this.rodaId = rodaId;
        this.rodaName = rodaName;
        this.rodaPicUrl = rodaPicUrl;
        this.rodaRole = rodaRole;
    }

    public Integer getId() {
        return rodaId;
    }

    public void setId(Integer rodaId) {
        this.rodaId = rodaId;
    }

    public String getName() {
        return rodaName;
    }

    public void setName(String rodaName) {
        this.rodaName = rodaName;
    }

    public String getPicUrl() {
        return rodaPicUrl;
    }

    public void setPicUrl(String rodaPicUrl) {
        this.rodaPicUrl = rodaPicUrl;
    }

    public String getRole() {
        return rodaRole;
    }

    public void setRole(String rodaRole) {
        this.rodaRole = rodaRole;
    }
}