package com.onethousandprojects.appoeira.serverStuff.ratedByUser;

public class ServeRatedByUserResponse {
    private boolean ok;
    private Integer stars;
    private Double rating;

    public ServeRatedByUserResponse(boolean ok, Integer stars, Double rating) {
        super();
        this.ok = ok;
        this.stars = stars;
        this.rating = rating;
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

    public Double getRating () { return rating; }

    public void setRating (Double rating) { this.rating = rating; }

}
