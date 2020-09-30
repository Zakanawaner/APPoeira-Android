package com.onethousandprojects.appoeira.serverStuff.rodaList;

public class ClientLocationRodasRequest {
    private String latitude;
    private String longitude;
    private Integer distance;

    public ClientLocationRodasRequest(String latitude, String longitude, Integer distance) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public String getlatitude() {
        return latitude;
    }

    public void setlatitude(String latitud) {
        this.latitude = latitud;
    }

    public String getlongitude() {
        return longitude;
    }

    public void setlongitude(String longitud) {
        this.longitude = longitud;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
