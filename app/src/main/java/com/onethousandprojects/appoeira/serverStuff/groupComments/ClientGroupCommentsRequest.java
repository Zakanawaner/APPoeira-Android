package com.onethousandprojects.appoeira.serverStuff.groupComments;


public class ClientGroupCommentsRequest {
    private Integer groupId;

    public ClientGroupCommentsRequest(Integer groupId) {
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
