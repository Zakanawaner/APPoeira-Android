package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.OnlineMembersInvitedFragment;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ClientDeleteRequest;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ServerDeleteResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ClientOnlineDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ServerOnlineDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ClientOnlineModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineModification.ServerOnlineModificationResponse;
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

public class OnlineModificationServer {
    private ServerOnlineModificationResponse serverOnlineModificationResponse;
    private List<ServerOnlineDetailMoreResponse> serverOnlineDetailMoreResponse;
    private ServerSearchResponse serverSearchResponse;
    private ServerDeleteResponse serverDeleteResponse;
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
                                          String date, List<Integer> invited,
                                          Integer platform, String phone, String key,
                                          Bitmap imageBitmap, Integer onlineId) throws IOException {
        ClientOnlineModificationRequest clientOnlineModificationRequest = new ClientOnlineModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, date, invited, platform, phone, key, onlineId);
        Call<ServerOnlineModificationResponse> call = Server.post_online_update(clientOnlineModificationRequest, CommonMethods.fromBitmapToFile(OnlineModificationActivity, imageBitmap, "online", "avatar", onlineId, 0));
        call.enqueue(new Callback<ServerOnlineModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerOnlineModificationResponse> call, @NonNull Response<ServerOnlineModificationResponse> response) {
                if (response.isSuccessful()){
                    serverOnlineModificationResponse = response.body();
                    Intent toOnlineDetailActivity = new Intent(OnlineModificationActivity, OnlineDetailActivity.class);
                    toOnlineDetailActivity.putExtra("onlineId", serverOnlineModificationResponse.getId());
                    toOnlineDetailActivity.putExtra("ownerRank", CommonMethods.fromRankIdToRankName(SharedPreferencesManager.getIntegerValue(Constants.RANK), OnlineModificationActivity));
                    toOnlineDetailActivity.putExtra("owner", SharedPreferencesManager.getStringValue(Constants.APELHIDO));
                    toOnlineDetailActivity.putExtra("date", OnlineModificationActivity.fromOnlineDetail.getString("date"));
                    OnlineModificationActivity.startActivity(toOnlineDetailActivity);
                    OnlineModificationActivity.finish();
                } else {
                    Toast.makeText(OnlineModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
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
        ClientSearchRequest clientSearchRequest = new ClientSearchRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), search, "10000");
        Call<ServerSearchResponse> call = Server.post_search(clientSearchRequest);
        call.enqueue(new Callback<ServerSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSearchResponse> call, @NonNull Response<ServerSearchResponse> response) {
                if (response.isSuccessful()){
                    serverSearchResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(OnlineModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(OnlineModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSearchResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerSearchResponse getSearchResponse() {
        return serverSearchResponse;
    }
    public void getOnlineDetailMore(OnlineModificationActivity OnlineModificationActivity, Integer groupId) {
        ClientOnlineDetailMoreRequest clientOnlineDetailMoreRequest = new ClientOnlineDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerOnlineDetailMoreResponse>> call = Server.post_online_detail_more(clientOnlineDetailMoreRequest);
        call.enqueue(new Callback<List<ServerOnlineDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerOnlineDetailMoreResponse>> call, @NonNull Response<List<ServerOnlineDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverOnlineDetailMoreResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        assert serverOnlineDetailMoreResponse != null;
                        for (int i = 0; i < serverOnlineDetailMoreResponse.size(); i++) {
                            if (serverOnlineDetailMoreResponse.get(i).getUserId().equals(SharedPreferencesManager.getIntegerValue(Constants.ID))) {
                                serverOnlineDetailMoreResponse.remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < serverOnlineDetailMoreResponse.size(); i++) {
                            OnlineModificationActivity.invitedMembers.add(serverOnlineDetailMoreResponse.get(i).getUserId());
                        }
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        OnlineModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(OnlineModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            OnlineModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new OnlineMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(OnlineModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerOnlineDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(OnlineModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerOnlineDetailMoreResponse> getMyResponse() {
        return serverOnlineDetailMoreResponse;
    }
    public void deleteOnline(OnlineModificationActivity OnlineModificationActivity, Integer onlineId) {
        ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), onlineId, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerDeleteResponse> call = Server.post_online_delete(clientDeleteRequest);
        call.enqueue(new Callback<ServerDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerDeleteResponse> call, @NonNull Response<ServerDeleteResponse> response) {
                if (response.isSuccessful()){
                    serverDeleteResponse = response.body();
                    assert serverDeleteResponse != null;
                    if (serverDeleteResponse.isOk()) {
                        Toast.makeText(OnlineModificationActivity, R.string.onlineDeleted, Toast.LENGTH_SHORT).show();
                        OnlineModificationActivity.toOnlineList();
                    } else {
                        Toast.makeText(OnlineModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerDeleteResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(OnlineModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
