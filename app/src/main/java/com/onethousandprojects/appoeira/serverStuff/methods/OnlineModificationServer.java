package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.OnlineMembersInvitedFragment;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ClientOnlineModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ServerOnlineModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ClientUserSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ServerUserSearchResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineModificationServer {
    private ServerOnlineModificationResponse serverOnlineModificationResponse;
    private List<ServerUserSearchResponse> serverUserSearchResponse;
    public boolean createdFragment = false;
    Client Client;
    Server Server;

    public OnlineModificationServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendModificationsToServer(OnlineModificationActivity OnlineModificationActivity,
                                          List<Integer> owners, String name, String description,
                                          String date, String picUrl, List<Integer> invited,
                                          Integer platform, String phone, String key) {
        ClientOnlineModificationRequest clientOnlineModificationRequest = new ClientOnlineModificationRequest(owners, name, description, date, picUrl, invited, platform, phone, key);
        Call<ServerOnlineModificationResponse> call = Server.post_online_update(clientOnlineModificationRequest);
        call.enqueue(new Callback<ServerOnlineModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerOnlineModificationResponse> call, @NonNull Response<ServerOnlineModificationResponse> response) {
                if (response.isSuccessful()){
                    serverOnlineModificationResponse = response.body();
                    Intent toOnlineDetailActivity = new Intent(OnlineModificationActivity, OnlineDetailActivity.class);
                    toOnlineDetailActivity.putExtra("id", serverOnlineModificationResponse.getId());
                    toOnlineDetailActivity.putExtra("ownerRank", CommonMethods.fromRankIdToRankName(SharedPreferencesManager.getIntegerValue(Constants.RANK), OnlineModificationActivity));
                    toOnlineDetailActivity.putExtra("owner", SharedPreferencesManager.getStringValue(Constants.APELHIDO));
                    OnlineModificationActivity.startActivity(toOnlineDetailActivity);
                    OnlineModificationActivity.finish();
                } else {
                    Toast.makeText(OnlineModificationActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerOnlineModificationResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerOnlineModificationResponse getServerOnlineModificationResponse() {
        return serverOnlineModificationResponse;
    }
    public void sendUserSearchToServer(OnlineModificationActivity OnlineModificationActivity,
                                          String search) {
        ClientUserSearchRequest clientUserSearchRequest = new ClientUserSearchRequest(search);
        Call<List<ServerUserSearchResponse>> call = Server.post_user_search(clientUserSearchRequest);
        call.enqueue(new Callback<List<ServerUserSearchResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerUserSearchResponse>> call, @NonNull Response<List<ServerUserSearchResponse>> response) {
                if (response.isSuccessful()){
                    serverUserSearchResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(OnlineModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverUserSearchResponse.get(0).getId() != null) {
                            OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(OnlineModificationActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerUserSearchResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerUserSearchResponse> getUserSearchResponse() {
        return serverUserSearchResponse;
    }
}
