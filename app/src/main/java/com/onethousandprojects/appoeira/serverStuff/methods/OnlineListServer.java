package com.onethousandprojects.appoeira.serverStuff.methods;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineListView.fragments.OnlineFragment;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ClientLocationOnlineRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ServerLocationOnlineResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineListServer {
    private List<ServerLocationOnlineResponse> serverLocationOnlineResponse;
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
                    Toast.makeText(OnlineListActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
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
}
