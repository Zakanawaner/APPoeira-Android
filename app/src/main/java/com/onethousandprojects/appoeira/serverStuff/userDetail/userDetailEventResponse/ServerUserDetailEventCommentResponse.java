package com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse;

public class ServerUserDetailEventCommentResponse {
    private Integer eventId;
    private String eventName;
    private String eventPicUrl;
    private String eventComment;
    private String eventDate;

    public ServerUserDetailEventCommentResponse(Integer eventId, String eventName, String eventPicUrl, String eventComment, String eventDate) {
        super();
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventPicUrl = eventPicUrl;
        this.eventComment = eventComment;
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

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

}
