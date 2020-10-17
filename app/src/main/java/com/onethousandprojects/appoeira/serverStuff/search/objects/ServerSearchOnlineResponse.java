package com.onethousandprojects.appoeira.serverStuff.search.objects;


public class ServerSearchOnlineResponse {
    private Integer id;
    private String name;
    private String picUrl;
    private boolean verified;
    private String ownerRank;
    private String owner;

    public ServerSearchOnlineResponse(Integer id, String name,
                                      String picUrl, boolean verified,
                                      String ownerRank, String owner) {
        super();
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.verified = verified;
        this.ownerRank = ownerRank;
        this.owner = owner;
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

    public String getOwnerRank() {
        return ownerRank;
    }

    public void setOwnerRank(String ownerRank) {
        this.ownerRank = ownerRank;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
