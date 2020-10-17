package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.commonThings.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static Client instance = null;
    private Server Server;
    private Retrofit retrofit;

    public Client(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().baseUrl(Constants.API_APPOEIRA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
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
