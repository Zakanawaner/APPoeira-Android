package com.onethousandprojects.appoeira.serverStuff.eventModification;

import java.util.List;

public class ClientEventModificationRequest {
    private List<Integer> owners;
    private String name;
    private String description;
    private String date;
    private String picUrl;
    private List<Integer> invited;
    private Double latitude;
    private Double longitude;
    private String phone;
    private List<Integer> convided;
    private String key;
    private Integer platform;

    public ClientEventModificationRequest(List<Integer> owners, String name, String description,
                                          String date, String picUrl, List<Integer> invited,
                                          Double latitude, Double longitude, String phone,
                                          List<Integer> convided, String key, Integer platform) {
        super();
        this.owners = owners;
        this.name = name;
        this.description = description;
        this.date = date;
        this.picUrl = picUrl;
        this.invited = invited;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.convided = convided;
        this.key = key;
        this.platform = platform;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getConvided() {
        return convided;
    }

    public void setConvided(List<Integer> convided) {
        this.convided = convided;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }
}
