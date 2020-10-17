package com.onethousandprojects.appoeira.serverStuff.methods;

import androidx.annotation.NonNull;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.serverStuff.news.ClientAreThereNewsRequest;
import com.onethousandprojects.appoeira.serverStuff.news.ServerAreThereNewsResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsServer {
    private ServerAreThereNewsResponse serverAreThereNewsResponse;
    Client Client;
    Server Server;

    public NewsServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void sendNewsRequest() {
        ClientAreThereNewsRequest clientAreThereNewsRequest = new ClientAreThereNewsRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerAreThereNewsResponse> call = Server.post_are_there_news(clientAreThereNewsRequest);
        call.enqueue(new Callback<ServerAreThereNewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerAreThereNewsResponse> call, @NonNull Response<ServerAreThereNewsResponse> response) {
                if (response.isSuccessful()){
                    serverAreThereNewsResponse = response.body();
                    assert serverAreThereNewsResponse != null;
                    Constants.newsVariable.setGotNews(serverAreThereNewsResponse.isResponse());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerAreThereNewsResponse> call, @NonNull Throwable t) {
            }
        });
    }
}
