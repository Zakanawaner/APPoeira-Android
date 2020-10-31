package com.onethousandprojects.appoeira.serverStuff.groupModification;

import java.util.List;

public class ClientGroupModificationRequest {

    private String token;
    private List<Integer> owners;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String phone;
    private List<Integer> frontline;
    private List<Integer> students;
    private String url;
    private Integer id;

    public ClientGroupModificationRequest(String token, List<Integer> owners, String name,
                                          String description, Double latitude, Double longitude,
                                          String phone, List<Integer> frontline, List<Integer> students,
                                          String url, Integer id) {
        super();
        this.token = token;
        this.owners = owners;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.frontline = frontline;
        this.students = students;
        this.url = url;
        this.id = id;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public List<Integer> getOwners() {
        return owners;
    }

    public void setOwners(List<Integer> owners) {
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Integer> getFrontline() {
        return frontline;
    }

    public void setFrontline(List<Integer> frontline) {
        this.frontline = frontline;
    }

    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
