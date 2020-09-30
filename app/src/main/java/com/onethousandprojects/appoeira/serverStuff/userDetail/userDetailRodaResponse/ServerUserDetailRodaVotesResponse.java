package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse;

public class ServerUserDetailRodaVotesResponse {
    private Integer rodaId;
    private String rodaName;
    private String rodaPicUrl;
    private String rodaVote;
    private String rodaDate;

    public ServerUserDetailRodaVotesResponse(Integer rodaId, String rodaName, String rodaPicUrl, String rodaVote, String rodaDate) {
        super();
        this.rodaId = rodaId;
        this.rodaName = rodaName;
        this.rodaPicUrl = rodaPicUrl;
        this.rodaVote = rodaVote;
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

    public String getRodaVote() {
        return rodaVote;
    }

    public void setRodaVote(String rodaVote) {
        this.rodaVote = rodaVote;
    }

    public String getRodaDate() {
        return rodaDate;
    }

    public void setRodaDate(String rodaDate) {
        this.rodaDate = rodaDate;
    }
}
