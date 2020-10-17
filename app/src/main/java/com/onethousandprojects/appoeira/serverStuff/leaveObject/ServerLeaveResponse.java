package com.onethousandprojects.appoeira.serverStuff.leaveObject;


public class ServerLeaveResponse {
    private boolean ok;

    public ServerLeaveResponse(boolean ok) {
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
