package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailView.EventDetailActivity;
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.eventModificationView.fragments.EventMembersConvidedFragment;
import com.onethousandprojects.appoeira.eventModificationView.fragments.EventMembersInvitedFragment;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ClientEventModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.eventModification.ServerEventModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchUserResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.search.ClientSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.search.ServerSearchResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventModificationServer {
    private ServerEventModificationResponse serverEventModificationResponse;
    private ServerSearchResponse serverSearchResponse;
    public boolean createdFragment = false;
    public boolean createdConvidedFragment = false;
    Client Client;
    public Server Server;

    public EventModificationServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendModificationsToServer(EventModificationActivity EventModificationActivity,
                                          List<Integer> owners, String name, String description,
                                          String date, List<Integer> invited,
                                          Double latitude, Double longitude, String phone,
                                          List<Integer> convided, String key, Integer platform,
                                          Bitmap imageBitmap, Integer eventId) throws IOException {
        ClientEventModificationRequest clientEventModificationRequest = new ClientEventModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, date, invited, latitude, longitude, phone, convided, key, platform);
        Call<ServerEventModificationResponse> call = Server.post_event_update(clientEventModificationRequest, CommonMethods.fromBitmapToFile(EventModificationActivity, imageBitmap, "event", "avatar", eventId, 0));
        call.enqueue(new Callback<ServerEventModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerEventModificationResponse> call, @NonNull Response<ServerEventModificationResponse> response) {
                if (response.isSuccessful()){
                    serverEventModificationResponse = response.body();
                    Intent toEventDetailActivity = new Intent(EventModificationActivity, EventDetailActivity.class);
                    toEventDetailActivity.putExtra("id", serverEventModificationResponse.getId());
                    toEventDetailActivity.putExtra("ownerRank", CommonMethods.fromRankIdToRankName(SharedPreferencesManager.getIntegerValue(Constants.RANK), EventModificationActivity));
                    toEventDetailActivity.putExtra("owner", SharedPreferencesManager.getStringValue(Constants.APELHIDO));
                    EventModificationActivity.startActivity(toEventDetailActivity);
                    EventModificationActivity.finish();
                } else {
                    Toast.makeText(EventModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerEventModificationResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(EventModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerEventModificationResponse getServerEventModificationResponse() {
        return serverEventModificationResponse;
    }
    public void sendUserSearchToServer(EventModificationActivity EventModificationActivity,
                                       String search, boolean invConv) {
        ClientSearchRequest clientSearchRequest = new ClientSearchRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), search, "10000");
        Call<ServerSearchResponse> call = Server.post_search(clientSearchRequest);
        call.enqueue(new Callback<ServerSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSearchResponse> call, @NonNull Response<ServerSearchResponse> response) {
                if (response.isSuccessful()){
                    serverSearchResponse = response.body();
                    if (!invConv) {
                        if (!createdFragment) {
                            createdFragment = true;
                            EventModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            EventModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(EventModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                            if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                                EventModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new EventMembersInvitedFragment(), "UserListFragment").commit();
                            } else {
                                createdFragment = false;
                            }
                        }
                    } else {
                        if (!createdConvidedFragment) {
                            createdConvidedFragment = true;
                            EventModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new EventMembersConvidedFragment(), "ConvidedListFragment").commit();
                        } else {
                            EventModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(EventModificationActivity.getSupportFragmentManager().findFragmentByTag("ConvidedListFragment"))).commit();
                            if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                                EventModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new EventMembersConvidedFragment(), "ConvidedListFragment").commit();
                            } else {
                                createdConvidedFragment = false;
                            }
                        }
                    }
                } else {
                    Toast.makeText(EventModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSearchResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(EventModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerSearchResponse getSearchResponse() {
        return serverSearchResponse;
    }
}
