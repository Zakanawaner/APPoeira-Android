package com.onethousandprojects.appoeira.userDetailView.content;

public class UserActivityContent {
    private Integer Id;
    private String Name;
    private String PicUrl;
    private String Date;
    private String description;

    public UserActivityContent(Integer Id, String Name, String PicUrl, String Date, String description) {
        this.Id = Id;
        this.Name = Name;
        this.PicUrl = PicUrl;
        this.Date = Date;
        this.description = description;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}