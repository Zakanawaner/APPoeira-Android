package com.onethousandprojects.appoeira.serverStuff.search;


public class ClientSearchRequest {
    private String token;
    private String search;
    private String mode;

    public ClientSearchRequest(String token, String search, String mode) {
        super();
        this.token = token;
        this.search = search;
        this.mode = mode;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
