package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse;

public class ServerUserDetailRodaCommentResponse {
    private Integer rodaId;
    private String rodaName;
    private String rodaPicUrl;
    private String rodaComment;
    private String rodaDate;

    public ServerUserDetailRodaCommentResponse(Integer rodaId, String rodaName, String rodaPicUrl, String rodaComment, String rodaDate) {
        super();
        this.rodaId = rodaId;
        this.rodaName = rodaName;
        this.rodaPicUrl = rodaPicUrl;
        this.rodaComment = rodaComment;
        this.rodaDate = rodaDate;
    }

    public Integer getRodaId() {
        return rodaId;
    }

    public void setRodaId(Integer rodaId) {
        this.rodaId = rodaId;
    }

    public String getRodaName() {
        return rodaName;
    }

    public void setRodaName(String rodaName) {
        this.rodaName = rodaName;
    }

    public String getRodaPicUrl() {
        return rodaPicUrl;
    }

    public void setRodaPicUrl(String rodaPicUrl) {
        this.rodaPicUrl = rodaPicUrl;
    }

    public String getRodaComment() {
        return rodaComment;
    }

    public void setRodaComment(String rodaComment) {
        this.rodaComment = rodaComment;
    }

    public String getRodaDate() {
        return rodaDate;
    }

    public void setRodaDate(String rodaDate) {
        this.rodaDate = rodaDate;
    }

}
