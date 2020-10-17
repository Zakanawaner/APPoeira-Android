package com.onethousandprojects.appoeira.serverStuff.uploadPicture;

public class ServerUploadPictureResponse {
    private boolean ok;
    private String picUrl;

    public ServerUploadPictureResponse(boolean ok, String picUrl) {
        super();
        this.ok = ok;
        this.picUrl = picUrl;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getPicUrl() { return picUrl; }

    public void setPicUrl(String picUrl) { this.picUrl = picUrl; }
}
