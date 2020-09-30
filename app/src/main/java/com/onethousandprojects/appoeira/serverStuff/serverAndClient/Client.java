package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.commonThings.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance = null;
    private Server Server;
    private Retrofit retrofit;

    public Client(){
        retrofit = new Retrofit.Builder().baseUrl(Constants.API_APPOEIRA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Server = retrofit.create(Server.class);
    }
    // Patrón singleton
    public static Client getInstance() {
        if (instance == null){
            instance = new Client();
        }
        return instance;
    }
    public Server getServer() {
        return Server;
    }
}
