package com.onethousandprojects.appoeira.serverStuff.groupModification;

import java.util.List;

public class ClientGroupModificationRequest {
    private String token;
    private List<Integer> owners;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String phone;

    public ClientGroupModificationRequest(String token, List<Integer> owners, String name,
                                          String description, Double latitude, Double longitude,
                                          String phone) {
        super();
        this.token = token;
        this.owners = owners;
        this.name = name;
        this.description = description;
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
