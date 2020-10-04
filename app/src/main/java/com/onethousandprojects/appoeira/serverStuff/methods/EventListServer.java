package com.onethousandprojects.appoeira.serverStuff.methods;

import android.location.Location;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventListView.fragments.EventFragment;
import com.onethousandprojects.appoeira.serverStuff.eventList.ClientLocationEventRequest;
import com.onethousandprojects.appoeira.serverStuff.eventList.ServerLocationEventResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListServer {
    private List<ServerLocationEventResponse> serverLocationEventResponse;
    private boolean createdFragment = false;
    Client Client;
    Server Server;

    public EventListServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendLocationToServer(EventListActivity EventListActivity, Location location, Integer distance) {
        ClientLocationEventRequest clientLocationEventsRequest = new ClientLocationEventRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), distance);
        Call<List<ServerLocationEventResponse>> call = Server.post_location_event(clientLocationEventsRequest);
        call.enqueue(new Callback<List<ServerLocationEventResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerLocationEventResponse>> call, @NonNull Response<List<ServerLocationEventResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationEventResponse = response.body();
                    assert serverLocationEventResponse != null;
                    Collections.sort(serverLocationEventResponse, new Comparator<ServerLocationEventResponse>() {
                        @Override
                        public int compare(ServerLocationEventResponse lhs, ServerLocationEventResponse rhs) {
                            return lhs.getDistance().compareTo(rhs.getDistance());
                        }
                    });
                    if (!createdFragment) {
                        createdFragment = true;
                        EventListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventFragment(), "EventListFragment").commit();
                    } else {
                        EventListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(EventListActivity.getSupportFragmentManager().findFragmentByTag("EventListFragment"))).commit();
                        EventListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventFragment(), "EventListFragment").commit();
                    }
                    EventListActivity.srEventList.setRefreshing(false);
                } else {
                    Toast.makeText(EventListActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerLocationEventResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(EventListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerLocationEventResponse> getServerLocationEventResponse() {
        return serverLocationEventResponse;
    }
}
