package com.onethousandprojects.appoeira.serverStuff.groupRatedByUser;

public class ClientGroupRatedByUserRequest {
    private Integer UserId;
    private Integer GroupId;
    private Integer Rating;

    public ClientGroupRatedByUserRequest(Integer UserId, Integer GroupId, Integer Rating) {
        super();
        this.UserId = UserId;
        this.GroupId = GroupId;
        this.Rating = Rating;
    }

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
