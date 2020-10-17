package com.onethousandprojects.appoeira.serverStuff.onlineDetail;

public class ServerOnlineDetailResponse {
    private String error;
    private Integer id;
    private String name;
    private String picUrl;
    private String description;
    private String phone;
    private boolean verified;
    private Double rating;
    private Integer votes;
    private String platform;
    private boolean isMember;
    private Integer hasVoted;
    private boolean isOwner;


    public ServerOnlineDetailResponse(String error, Integer id, String name, String picUrl,
                                      String description, String phone,
                                      boolean verified, Double rating, Integer votes,
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
