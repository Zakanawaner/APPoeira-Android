package com.onethousandprojects.appoeira.newsView.content;

public class NewsContent {

    private Integer originId;
    private String originName;
    private Integer originType;
    private Integer mediumId;
    private String mediumName;
    private Integer mediumType;
    private Integer destinationId;
    private String destinationName;
    private Integer destinationType;
    private Integer newsType;
    private String newsDate;

    public NewsContent(Integer originId, String originName, Integer originType,
                       Integer mediumId, String mediumName, Integer mediumType,
                       Integer destinationId, String destinationName, Integer destinationType,
                       Integer newsType, String newsDate) {

        this.originId = originId;
        this.originName = originName;
        this.originType = originType;
        this.mediumId = mediumId;
        this.mediumName = mediumName;
        this.mediumType = mediumType;
        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.destinationType = destinationType;
        this.newsType = newsType;
        this.newsDate = newsDate;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
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

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
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

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public Integer getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(Integer destinationType) {
        this.destinationType = destinationType;
    }

    public Integer getNewsType() {
        return newsType;
    }

    public void setNewsType(Integer newsType) {
        this.newsType = newsType;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}