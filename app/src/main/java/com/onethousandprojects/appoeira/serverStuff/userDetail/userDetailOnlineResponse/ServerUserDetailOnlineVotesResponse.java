package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse;

public class ServerUserDetailOnlineVotesResponse {
    private Integer onlineId;
    private String onlineName;
    private String onlinePicUrl;
    private String onlineVote;
    private String onlineDate;

    public ServerUserDetailOnlineVotesResponse(Integer onlineId, String onlineName, String onlinePicUrl, String onlineVote, String onlineDate) {
        super();
        this.onlineId = onlineId;
        this.onlineName = onlineName;
        this.onlinePicUrl = onlinePicUrl;
        this.onlineVote = onlineVote;
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

    public String getOnlineVote() {
        return onlineVote;
    }

    public void setOnlineVote(String onlineVote) {
        this.onlineVote = onlineVote;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

}
