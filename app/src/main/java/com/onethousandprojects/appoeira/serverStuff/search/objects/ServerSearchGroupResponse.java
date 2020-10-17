package com.onethousandprojects.appoeira.serverStuff.search.objects;


public class ServerSearchGroupResponse {
    private Integer id;
    private String name;
    private String picUrl;
    private boolean verified;
    private Double rating;

    public ServerSearchGroupResponse(Integer id, String name,
                                     String picUrl, boolean verified,
                                     Double rating) {
        super();
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.verified = verified;
        this.rating = rating;
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

    public boolean isVerified() {
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
}
