package com.onethousandprojects.appoeira.serverStuff.comments;


public class ClientNewCommentRequest {
    private String token;
    private Integer groupId;
    private Integer userId;
    private String comment;

    public ClientNewCommentRequest(String token, Integer groupId, Integer userId, String comment) {
        super();
        this.token = token;
        this.groupId = groupId;
        this.userId = userId;
        this.comment = comment;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
