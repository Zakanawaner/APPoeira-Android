package com.onethousandprojects.appoeira.serverStuff.ratedByUser;

public class ClientRatedByUserRequest {
    private String token;
    private Integer UserId;
    private Integer GroupId;
    private Integer Rating;

    public ClientRatedByUserRequest(String token, Integer UserId, Integer GroupId, Integer Rating) {
        super();
        this.token = token;
        this.UserId = UserId;
        this.GroupId = GroupId;
        this.Rating = Rating;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId (Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getGroupId() {
        return GroupId;
    }

    public void setGroupId (Integer GroupId) {
        this.GroupId = GroupId;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating (Integer Rating) {
        this.Rating = Rating;
    }
}
