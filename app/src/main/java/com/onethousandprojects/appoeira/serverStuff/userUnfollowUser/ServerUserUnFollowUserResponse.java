package com.onethousandprojects.appoeira.serverStuff.userUnfollowUser;


public class ServerUserUnFollowUserResponse {
    private boolean ok;

    public ServerUserUnFollowUserResponse(boolean ok) {
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
