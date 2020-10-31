package com.onethousandprojects.appoeira.serverStuff.amIVerified;

public class ServerAmIVerifiedResponse {
    private boolean response;

    public ServerAmIVerifiedResponse(boolean response) {
        super();
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}
