package com.onethousandprojects.appoeira.serverStuff.joinObject;


public class ServerJoinResponse {
    private boolean ok;

    public ServerJoinResponse(boolean ok) {
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
