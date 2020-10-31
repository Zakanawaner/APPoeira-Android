package com.onethousandprojects.appoeira.serverStuff.methods;

import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventListView.fragments.EventFragment;
import com.onethousandprojects.appoeira.serverStuff.eventList.ClientLocationEventRequest;
import com.onethousandprojects.appoeira.serverStuff.eventList.ServerLocationEventResponse;
import com.onethousandprojects.appoeira.serverStuff.sendEmail.ClientSendEmailRequest;
import com.onethousandprojects.appoeira.serverStuff.sendEmail.ServerSendEmailResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListServer {
    private List<ServerLocationEventResponse> serverLocationEventResponse;
    private ServerSendEmailResponse serverSendEmailResponse;
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
                    if (!createdFragment) {
                        createdFragment = true;
                        EventListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventFragment(), "EventListFragment").commit();
                    } else {
                        EventListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(EventListActivity.getSupportFragmentManager().findFragmentByTag("EventListFragment"))).commit();
                        EventListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventFragment(), "EventListFragment").commit();
                    }
                    EventListActivity.srEventList.setRefreshing(false);
                } else {
                    Toast.makeText(EventListActivity,R.string.failed, Toast.LENGTH_SHORT).show();
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
    public void sendVerificationEmail(EventListActivity EventListActivity, Integer type, Integer firstId, Integer secondId) {
        ClientSendEmailRequest clientSendEmailRequest = new ClientSendEmailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), type, firstId, secondId);
        Call<ServerSendEmailResponse> call = Server.post_send_email(clientSendEmailRequest);
        call.enqueue(new Callback<ServerSendEmailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSendEmailResponse> call, @NonNull Response<ServerSendEmailResponse> response) {
                if (response.isSuccessful()){
                    serverSendEmailResponse = response.body();
                    assert serverSendEmailResponse != null;
                    if (serverSendEmailResponse.isOk()) {
                        Toast.makeText(EventListActivity,R.string.checkYourEmail, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventListActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSendEmailResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(EventListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
