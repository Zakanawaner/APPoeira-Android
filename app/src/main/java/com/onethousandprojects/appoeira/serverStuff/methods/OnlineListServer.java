package com.onethousandprojects.appoeira.serverStuff.methods;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineListView.fragments.OnlineFragment;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ClientLocationOnlineRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ServerLocationOnlineResponse;
import com.onethousandprojects.appoeira.serverStuff.sendEmail.ClientSendEmailRequest;
import com.onethousandprojects.appoeira.serverStuff.sendEmail.ServerSendEmailResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineListServer {
    private List<ServerLocationOnlineResponse> serverLocationOnlineResponse;
    private ServerSendEmailResponse serverSendEmailResponse;
    private boolean createdFragment = false;
    Client Client;
    Server Server;

    public OnlineListServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendLocationToServer(OnlineListActivity OnlineListActivity) {
        ClientLocationOnlineRequest clientLocationOnlinesRequest = new ClientLocationOnlineRequest();
        Call<List<ServerLocationOnlineResponse>> call = Server.post_location_online(clientLocationOnlinesRequest);
        call.enqueue(new Callback<List<ServerLocationOnlineResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerLocationOnlineResponse>> call, @NonNull Response<List<ServerLocationOnlineResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationOnlineResponse = response.body();
                    assert serverLocationOnlineResponse != null;
                    if (!createdFragment) {
                        createdFragment = true;
                        OnlineListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineFragment(), "OnlineListFragment").commit();
                    } else {
                        OnlineListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(OnlineListActivity.getSupportFragmentManager().findFragmentByTag("OnlineListFragment"))).commit();
                        OnlineListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineFragment(), "OnlineListFragment").commit();
                    }
                    OnlineListActivity.srOnlineList.setRefreshing(false);
                } else {
                    Toast.makeText(OnlineListActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerLocationOnlineResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerLocationOnlineResponse> getServerLocationOnlineResponse() {
        return serverLocationOnlineResponse;
    }
    public void sendVerificationEmail(OnlineListActivity OnlineListActivity, Integer type, Integer firstId, Integer secondId) {
        ClientSendEmailRequest clientSendEmailRequest = new ClientSendEmailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), type, firstId, secondId);
        Call<ServerSendEmailResponse> call = Server.post_send_email(clientSendEmailRequest);
        call.enqueue(new Callback<ServerSendEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSendEmailResponse> call, @NonNull Response<ServerSendEmailResponse> response) {
                if (response.isSuccessful()){
                    serverSendEmailResponse = response.body();
                    assert serverSendEmailResponse != null;
                    if (serverSendEmailResponse.isOk()) {
                        Toast.makeText(OnlineListActivity,R.string.checkYourEmail, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineListActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSendEmailResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
