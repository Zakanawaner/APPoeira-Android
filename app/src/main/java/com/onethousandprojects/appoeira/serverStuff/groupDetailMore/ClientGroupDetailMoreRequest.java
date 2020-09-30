package com.onethousandprojects.appoeira.serverStuff.groupDetailMore;


public class ClientGroupDetailMoreRequest {
    private Integer groupId;

    public ClientGroupDetailMoreRequest(Integer groupId) {
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
