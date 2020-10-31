package com.onethousandprojects.appoeira.serverStuff.sendEmail;

public class ServerSendEmailResponse {
    private boolean ok;

    public ServerSendEmailResponse(boolean ok) {
        super();
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

}
