package com.onethousandprojects.appoeira.serverStuff.comments;


public class ClientNewCommentRequest {
    private Integer groupId;
    private Integer userId;
    private String comment;

    public ClientNewCommentRequest(Integer groupId, Integer userId, String comment) {
        super();
        this.groupId = groupId;
        this.userId = userId;
        this.comment = comment;
    }

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
