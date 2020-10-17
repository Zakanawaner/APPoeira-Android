package com.onethousandprojects.appoeira.serverStuff.rodaModification;

import android.widget.DatePicker;

import java.util.List;

public class ClientRodaModificationRequest {
    private String token;
    private List<Integer> owners;
    private String name;
    private String description;
    private String date;
    private List<Integer> invited;
    private Double latitude;
    private Double longitude;
    private String phone;

    public ClientRodaModificationRequest(String token, List<Integer> owners, String name,
                                         String description, String date,
                                         List<Integer> invited, Double latitude, Double longitude,
                                         String phone) {
        super();
        this.token = token;
        this.owners = owners;
        this.name = name;
        this.description = description;
        this.date = date;
        this.invited = invited;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

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
}
