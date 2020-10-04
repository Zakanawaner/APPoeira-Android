package com.onethousandprojects.appoeira.serverStuff.comments;


public class ClientCommentsRequest {
    private Integer groupId;

    public ClientCommentsRequest(Integer groupId) {
        super();
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
