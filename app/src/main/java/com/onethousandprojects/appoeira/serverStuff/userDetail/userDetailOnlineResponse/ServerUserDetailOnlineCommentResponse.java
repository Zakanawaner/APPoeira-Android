package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailOnlineResponse;

public class ServerUserDetailOnlineCommentResponse {
    private Integer onlineId;
    private String onlineName;
    private String onlinePicUrl;
    private String onlineComment;
    private String onlineDate;

    public ServerUserDetailOnlineCommentResponse(Integer onlineId, String onlineName, String onlinePicUrl, String onlineComment, String onlineDate) {
        super();
        this.onlineId = onlineId;
        this.onlineName = onlineName;
        this.onlinePicUrl = onlinePicUrl;
        this.onlineComment = onlineComment;
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

    public String getOnlineComment() {
        return onlineComment;
    }

    public void setOnlineComment(String onlineComment) {
        this.onlineComment = onlineComment;
    }

    public String getOnlineDate() {
        return onlineDate;
    }

    public void setOnlineDate(String onlineDate) {
        this.onlineDate = onlineDate;
    }

}
