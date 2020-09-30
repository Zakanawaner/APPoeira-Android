package com.onethousandprojects.appoeira.serverStuff.groupDetail;

public class ServerGroupDetailResponse {
    private String error;
    private Integer id;
    private String name;
    private String picUrl;
    private String url;
    private String phone;
    private boolean verified;
    private Double rating;
    private Integer votes;
    private String opening;
    private String about;
    private String address;
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String facebook;
    private String google;
    private boolean isMember;
    private Integer hasVoted;
    private String comment;


    public ServerGroupDetailResponse(String error, Integer id, String name, String picUrl, String url, String phone,
                                     boolean verified, Double rating, Integer votes, String opening, String about, String address,
                                     String city, String country, Double latitude, Double longitude,
                                     String facebook, String google, boolean isMember, Integer hasVoted, String comment) {
        super();
        this.error = error;
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.url = url;
        this.phone = phone;
        this.verified = verified;
        this.rating = rating;
        this.votes = votes;
        this.opening = opening;
        this.about = about;
        this.address = address;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.facebook = facebook;
        this.google = google;
        this.isMember = isMember;
        this.hasVoted = hasVoted;
        this.comment = comment;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
