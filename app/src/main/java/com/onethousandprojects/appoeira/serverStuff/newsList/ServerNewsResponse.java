package com.onethousandprojects.appoeira.serverStuff.newsList;

public class ServerNewsResponse {
    // te siguió
    // siguió a
    // se unió a
    // creó
    // modificó
    // te invitó
    // hay es el día de,
    private Integer originId;
    private Integer originType;
    private Integer mediumId;
    private Integer mediumType;
    private Integer destinationId;
    private Integer destinationType;


    public ServerNewsResponse(Integer originId, Integer originType, Integer mediumId,
                              Integer mediumType, Integer destinationId, Integer destinationType) {
        super();
        this.originId = originId;
        this.originType = originType;
        this.mediumId = mediumId;
        this.mediumType = mediumType;
        this.destinationId = destinationId;
        this.destinationType = destinationType;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public Integer getOriginType() {
        return originType;
    }

    public void setOriginType(Integer originType) {
        this.originType = originType;
    }

    public Integer getMediumId() {
        return mediumId;
    }

    public void setMediumId(Integer mediumId) {
        this.mediumId = mediumId;
    }

    public Integer getMediumType() {
        return mediumType;
    }

    public void setMediumType(Integer mediumType) {
        this.mediumType = mediumType;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Integer destinationType) {
        this.destinationType = destinationType;
    }
}
