package com.onethousandprojects.appoeira.serverStuff.userSearch;


public class ClientUserSearchRequest {
    private String search;

    public ClientUserSearchRequest(String search) {
        super();
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
