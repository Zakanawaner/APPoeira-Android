package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse;

public class ServerUserDetailEventVotesResponse {
    private Integer eventId;
    private String eventName;
    private String eventPicUrl;
    private String eventVote;
    private String eventDate;

    public ServerUserDetailEventVotesResponse(Integer eventId, String eventName, String eventPicUrl, String eventVote, String eventDate) {
        super();
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPicUrl = eventPicUrl;
        this.eventVote = eventVote;
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

    public String getEventVote() {
        return eventVote;
    }

    public void setEventVote(String eventVote) {
        this.eventVote = eventVote;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

}
