package com.onethousandprojects.appoeira.serverStuff.eventDetail;

public class ServerEventDetailResponse {
    private String error;
    private Integer id;
    private String name;
    private String picUrl;
    private String description;
    private String phone;
    private boolean verified;
    private Double rating;
    private Integer votes;
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String platform;
    private boolean isMember;
    private Integer hasVoted;
    private boolean isOwner;


    public ServerEventDetailResponse(String error, Integer id, String name, String picUrl, String description, String phone,
                                     boolean verified, Double rating, Integer votes, String address,
                                     String city, String country, Double latitude, Double longitude,
                                     String platform, boolean isMember, Integer hasVoted,
                                     boolean isOwner) {
        super();
        this.error = error;
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.phone = phone;
        this.verified = verified;
        this.rating = rating;
        this.votes = votes;
        this.address = address;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.platform = platform;
        this.isMember = isMember;
        this.hasVoted = hasVoted;
        this.isOwner = isOwner;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean getIsMember() {
        return isMember;
    }

    public void setMember(boolean isMember) {
        this.isMember = isMember;
    }

    public Integer getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(Integer hasVoted) {
        this.hasVoted = hasVoted;
    }

}
