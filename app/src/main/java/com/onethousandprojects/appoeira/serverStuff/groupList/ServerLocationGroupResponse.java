package com.onethousandprojects.appoeira.serverStuff.groupList;

public class ServerLocationGroupResponse {
    private Integer id;
    private String name;
    private String picUrl;
    private String phone;
    private boolean verified;
    private Double rating;
    private Integer votes;
    private Double latitude;
    private Double longitude;
    private Double distance;


    public ServerLocationGroupResponse(Integer id, String name, String picUrl, String phone,
                                       boolean verified, Double rating, Integer votes,
                                       Double latitude, Double longitude, Double distance) {
        super();
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.phone = phone;
        this.verified = verified;
        this.rating = rating;
        this.votes = votes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
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
