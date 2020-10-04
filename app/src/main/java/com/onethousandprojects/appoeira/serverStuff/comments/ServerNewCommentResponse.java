package com.onethousandprojects.appoeira.serverStuff.comments;


public class ServerNewCommentResponse {
    private boolean ok;

    public ServerNewCommentResponse(boolean ok) {
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
