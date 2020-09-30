package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse;

public class ServerUserDetailEventsResponse {
    private Integer eventId;
    private String eventName;
    private String eventPicUrl;
    private String eventRole;
    private String eventDate;

    public ServerUserDetailEventsResponse(Integer eventId, String eventName, String eventPicUrl, String eventRole, String eventDate) {
        super();
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPicUrl = eventPicUrl;
        this.eventRole = eventRole;
        this.eventDate = eventDate;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPicUrl() {
        return eventPicUrl;
    }

    public void setEventPicUrl(String eventPicUrl) {
        this.eventPicUrl = eventPicUrl;
    }

    public String getEventRole() {
        return eventRole;
    }

    public void setEventRole(String eventRole) {
        this.eventRole = eventRole;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

}
