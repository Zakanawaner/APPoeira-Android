package com.onethousandprojects.appoeira.serverStuff.rodaList;

public class ServerLocationRodaResponse {
    private Integer id;
    private String name;
    private String date;
    private String picUrl;
    private boolean verified;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private String ownerApelhido;
    private String ownerRank;


    public ServerLocationRodaResponse(Integer id, String name, String date, String picUrl,
                                      String ownerApelhido, String ownerRank, boolean verified,
                                      Double latitude, Double longitude, Double distance) {
        super();
        this.id = id;
        this.name = name;
        this.date  = date;
        this.picUrl = picUrl;
        this.verified = verified;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.ownerApelhido = ownerApelhido;
        this.ownerRank = ownerRank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOwnerApelhido() {
        return ownerApelhido;
    }

    public void setOwnerApelhido(String ownerApelhido) {
        this.ownerApelhido = ownerApelhido;
    }

    public String getOwnerRank() {
        return ownerRank;
    }

    public void setOwnerRank(String ownerApelhido) {
        this.ownerRank = ownerRank;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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
