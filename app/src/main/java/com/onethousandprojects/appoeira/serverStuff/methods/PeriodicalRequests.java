package com.onethousandprojects.appoeira.serverStuff.methods;

import androidx.annotation.NonNull;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.serverStuff.amIVerified.ClientAmIVerifiedRequest;
import com.onethousandprojects.appoeira.serverStuff.amIVerified.ServerAmIVerifiedResponse;
import com.onethousandprojects.appoeira.serverStuff.areThereNews.ClientAreThereNewsRequest;
import com.onethousandprojects.appoeira.serverStuff.areThereNews.ServerAreThereNewsResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PeriodicalRequests {
    private ServerAreThereNewsResponse serverAreThereNewsResponse;
    Client Client;
    Server Server;

    public PeriodicalRequests() {
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
    public void checkForVerifiedEmail() {
        ClientAmIVerifiedRequest clientAmIVerifiedRequest = new ClientAmIVerifiedRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerAmIVerifiedResponse> call = Server.post_am_i_verified(clientAmIVerifiedRequest);
        call.enqueue(new Callback<ServerAmIVerifiedResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerAmIVerifiedResponse> call, @NonNull Response<ServerAmIVerifiedResponse> response) {
                if (response.isSuccessful()){
                    ServerAmIVerifiedResponse serverAmIVerifiedResponse = response.body();
                    assert serverAmIVerifiedResponse != null;
                    if (serverAmIVerifiedResponse.isResponse()) {
                        SharedPreferencesManager.setBooleanValue(Constants.EMAIL_VERIFIED, true);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerAmIVerifiedResponse> call, @NonNull Throwable t) {
            }
        });
    }
}
