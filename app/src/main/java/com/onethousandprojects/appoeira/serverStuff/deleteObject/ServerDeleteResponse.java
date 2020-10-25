package com.onethousandprojects.appoeira.serverStuff.deleteObject;

public class ServerDeleteResponse {
    private boolean ok;

    public ServerDeleteResponse(boolean ok) {
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
