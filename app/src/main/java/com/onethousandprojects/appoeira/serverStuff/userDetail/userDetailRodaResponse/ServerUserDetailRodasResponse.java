package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailRodaResponse;

public class ServerUserDetailRodasResponse {
    private Integer rodaId;
    private String rodaName;
    private String rodaPicUrl;
    private String rodaRole;
    private String rodaDate;

    public ServerUserDetailRodasResponse(Integer rodaId, String rodaName, String rodaPicUrl, String rodaRole, String rodaDate) {
        super();
        this.rodaId = rodaId;
        this.rodaName = rodaName;
        this.rodaPicUrl = rodaPicUrl;
        this.rodaRole = rodaRole;
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

    public String getRodaRole() {
        return rodaRole;
    }

    public void setRodaRole(String rodaRole) {
        this.rodaRole = rodaRole;
    }

    public String getRodaDate() {
        return rodaDate;
    }

    public void setRodaDate(String rodaDate) {
        this.rodaDate = rodaDate;
    }
}
