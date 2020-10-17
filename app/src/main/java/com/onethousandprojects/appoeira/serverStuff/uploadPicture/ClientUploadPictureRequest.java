package com.onethousandprojects.appoeira.serverStuff.uploadPicture;

public class ClientUploadPictureRequest {
    private String token;

    public ClientUploadPictureRequest(String token) {
        super();
        this.token = token;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }
}
