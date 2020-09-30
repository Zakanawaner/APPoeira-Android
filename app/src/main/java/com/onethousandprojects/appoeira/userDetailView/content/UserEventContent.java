package com.onethousandprojects.appoeira.userDetailView.content;

public class UserEventContent {
    private Integer eventId;
    private String eventName;
    private String eventPicUrl;
    private String eventRole;

    public UserEventContent(Integer eventId, String eventName, String eventPicUrl, String eventRole) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPicUrl = eventPicUrl;
        this.eventRole = eventRole;
    }

    public Integer getId() {
        return eventId;
    }

    public void setId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return eventName;
    }

    public void setName(String eventName) {
        this.eventName = eventName;
    }

    public String getPicUrl() {
        return eventPicUrl;
    }

    public void setPicUrl(String eventPicUrl) {
        this.eventPicUrl = eventPicUrl;
    }

    public String getRole() {
        return eventRole;
    }

    public void setRole(String eventRole) {
        this.eventRole = eventRole;
    }
}