package com.onethousandprojects.appoeira.serverStuff.ratedByUser;

public class ServeRatedByUserResponse {
    private boolean ok;
    private Integer stars;

    public ServeRatedByUserResponse(boolean ok, Integer stars) {
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
