package com.onethousandprojects.appoeira.searchView.content;

import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchEventResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchOnlineResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchUserResponse;

import java.util.List;

public class SearchContent {
    private Integer type;
    private Integer id;
    private String name;
    private String picUrl;
    private boolean verified;
    private String rank;
    private Double rating;
    private String ownerRank;
    private String owner;

    public SearchContent(Integer type, Integer id, String name,
                         String picUrl, boolean verified,
                         String rank, Double rating,
                         String ownerRank, String owner) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.picUrl = picUrl;
        this.verified = verified;
        this.rank = rank;
        this.rating = rating;
        this.ownerRank = ownerRank;
        this.owner = owner;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
