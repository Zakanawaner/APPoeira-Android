package com.onethousandprojects.appoeira.serverStuff.rodaModification;

import android.widget.DatePicker;

import java.util.List;

public class ClientRodaModificationRequest {
    private List<Integer> owners;
    private String name;
    private String description;
    private String date;
    private String picUrl;
    private List<Integer> invited;
    private Double latitude;
    private Double longitude;

    public ClientRodaModificationRequest(List<Integer> owners, String name, String description,
                                         String date, String picUrl, List<Integer> invited,
                                         Double latitude, Double longitude) {
        super();
        this.owners = owners;
        this.name = name;
        this.description = description;
        this.date = date;
        this.picUrl = picUrl;
        this.invited = invited;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
