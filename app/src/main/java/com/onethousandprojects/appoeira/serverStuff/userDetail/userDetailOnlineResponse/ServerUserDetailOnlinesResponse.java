package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse;

public class ServerUserDetailOnlinesResponse {
    private Integer onlineId;
    private String onlineName;
    private String onlinePicUrl;
    private String onlineRole;
    private String onlineDate;

    public ServerUserDetailOnlinesResponse(Integer onlineId, String onlineName, String onlinePicUrl, String onlineRole, String onlineDate) {
        super();
        this.onlineId = onlineId;
        this.onlineName = onlineName;
        this.onlinePicUrl = onlinePicUrl;
        this.onlineRole = onlineRole;
        this.onlineDate = onlineDate;
    }

    public Integer getOnlineId() {
        return onlineId;
    }

    public void setOnlineId(Integer onlineId) {
        this.onlineId = onlineId;
    }

    public String getOnlineName() {
        return onlineName;
    }

    public void setOnlineName(String onlineName) {
        this.onlineName = onlineName;
    }

    public String getOnlinePicUrl() {
        return onlinePicUrl;
    }

    public void setOnlinePicUrl(String onlinePicUrl) {
        this.onlinePicUrl = onlinePicUrl;
    }

    public String getOnlineRole() {
        return onlineRole;
    }

    public void setOnlineRole(String onlineRole) {
        this.onlineRole = onlineRole;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

}
