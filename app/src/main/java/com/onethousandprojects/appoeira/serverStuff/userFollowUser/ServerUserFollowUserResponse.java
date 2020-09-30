package com.onethousandprojects.appoeira.serverStuff.userFollowUser;


public class ServerUserFollowUserResponse {
    private boolean ok;

    public ServerUserFollowUserResponse(boolean ok) {
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
