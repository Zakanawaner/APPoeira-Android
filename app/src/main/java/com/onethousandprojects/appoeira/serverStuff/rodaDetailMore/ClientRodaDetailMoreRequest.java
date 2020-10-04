package com.onethousandprojects.appoeira.serverStuff.rodaDetailMore;


public class ClientRodaDetailMoreRequest {
    private Integer groupId;

    public ClientRodaDetailMoreRequest(Integer groupId) {
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
