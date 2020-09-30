package com.onethousandprojects.appoeira.serverStuff.serverAndClient;

import com.onethousandprojects.appoeira.commonThings.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthClient {
    private static AuthClient instance = null;
    private AuthServer authServer;
    private Retrofit retrofit;

    public AuthClient(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient authClient = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(Constants.API_APPOEIRA_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(authClient).build();
        authServer = retrofit.create(AuthServer.class);
    }
    // Patrón singleton
    public static AuthClient getInstance() {
        if (instance == null){
            instance = new AuthClient();
        }
        return instance;
    }
    public AuthServer getAuthServer() {
        return authServer;
    }
}
