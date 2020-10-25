package com.onethousandprojects.appoeira.serverStuff.areThereNews;

public class ServerAreThereNewsResponse {
    private boolean response;

    public ServerAreThereNewsResponse(boolean response) {
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
