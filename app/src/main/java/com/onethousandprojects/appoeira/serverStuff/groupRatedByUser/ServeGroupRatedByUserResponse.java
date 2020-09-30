package com.onethousandprojects.appoeira.serverStuff.groupRatedByUser;

public class ServeGroupRatedByUserResponse {
    private boolean ok;
    private Integer stars;

    public ServeGroupRatedByUserResponse(boolean ok, Integer stars) {
        super();
        this.ok = ok;
        this.stars = stars;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk (boolean ok) {
        this.ok = ok;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars (Integer stars) {
        this.stars = stars;
    }

}
