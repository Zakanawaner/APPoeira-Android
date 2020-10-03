package com.onethousandprojects.appoeira.serverStuff.onlineModification;

import java.util.List;

public class ClientOnlineModificationRequest {
    private List<Integer> owners;
    private String name;
    private String description;
    private String date;
    private String picUrl;
    private List<Integer> invited;
    private Integer platform;
    private String phone;
    private String key;

    public ClientOnlineModificationRequest(List<Integer> owners, String name, String description,
                                           String date, String picUrl, List<Integer> invited,
                                           Integer platform, String phone, String key) {
        super();
        this.owners = owners;
        this.name = name;
        this.description = description;
        this.date = date;
        this.picUrl = picUrl;
        this.invited = invited;
        this.platform = platform;
        this.phone = phone;
        this.key = key;
    }

    public List<Integer> getOwners() {
        return owners;
    }

    public void setOwners(List<Integer> owners) {
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<Integer> getInvited() {
        return invited;
    }

    public void setInvited(List<Integer> invited) {
        this.invited = invited;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
