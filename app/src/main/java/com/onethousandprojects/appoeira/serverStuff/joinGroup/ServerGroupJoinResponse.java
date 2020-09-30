package com.onethousandprojects.appoeira.serverStuff.joinGroup;


public class ServerGroupJoinResponse {
    private boolean ok;

    public ServerGroupJoinResponse(boolean ok) {
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
