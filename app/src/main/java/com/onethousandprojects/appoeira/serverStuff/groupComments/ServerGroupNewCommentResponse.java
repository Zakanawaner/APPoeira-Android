package com.onethousandprojects.appoeira.serverStuff.groupComments;


public class ServerGroupNewCommentResponse {
    private boolean ok;

    public ServerGroupNewCommentResponse(boolean ok) {
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
