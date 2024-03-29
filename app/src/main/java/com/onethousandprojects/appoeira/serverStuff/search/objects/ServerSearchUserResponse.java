package com.onethousandprojects.appoeira.serverStuff.search.objects;


public class ServerSearchUserResponse {
    private Integer id;
    private String apelhido;
    private String picUrl;
    private boolean premium;
    private String rank;

    public ServerSearchUserResponse(Integer id, String apelhido,
                                    String picUrl, boolean premium,
                                    String rank) {
        super();
        this.id = id;
        this.apelhido = apelhido;
        this.picUrl = picUrl;
        this.premium = premium;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApelhido() {
        return apelhido;
    }

    public void setApelhido(String apelhido) {
        this.apelhido = apelhido;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
