package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.RodaMembersInvitedFragment;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ClientDeleteRequest;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ServerDeleteResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ClientRodaDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ServerRodaDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ClientRodaModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ServerRodaModificationResponse;
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

public class RodaModificationServer {
    private ServerRodaModificationResponse serverRodaModificationResponse;
    private List<ServerRodaDetailMoreResponse> serverRodaDetailMoreResponse;
    private ServerSearchResponse serverSearchResponse;
    private ServerDeleteResponse serverDeleteResponse;
    public boolean createdFragment = false;
    Client Client;
    Server Server;

    public RodaModificationServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendModificationsToServer(RodaModificationActivity RodaModificationActivity,
                                          List<Integer> owners, String name, String description,
                                          String date, List<Integer> invited,
                                          Double latitude, Double longitude, String phone,
                                          Bitmap imageBitmap, Integer rodaId) throws IOException {
        ClientRodaModificationRequest clientRodaModificationRequest = new ClientRodaModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, date, invited, latitude, longitude, phone, rodaId);
        Call<ServerRodaModificationResponse> call = Server.post_roda_update(clientRodaModificationRequest, CommonMethods.fromBitmapToFile(RodaModificationActivity, imageBitmap, "roda", "avatar", rodaId, 0));
        call.enqueue(new Callback<ServerRodaModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerRodaModificationResponse> call, @NonNull Response<ServerRodaModificationResponse> response) {
                if (response.isSuccessful()){
                    serverRodaModificationResponse = response.body();
                    Intent toRodaDetailActivity = new Intent(RodaModificationActivity, RodaDetailActivity.class);
                    toRodaDetailActivity.putExtra("rodaId", serverRodaModificationResponse.getId());
                    toRodaDetailActivity.putExtra("ownerRank", CommonMethods.fromRankIdToRankName(SharedPreferencesManager.getIntegerValue(Constants.RANK), RodaModificationActivity));
                    toRodaDetailActivity.putExtra("owner", SharedPreferencesManager.getStringValue(Constants.APELHIDO));
                    toRodaDetailActivity.putExtra("date", RodaModificationActivity.fromRodaDetail.getString("date"));
                    RodaModificationActivity.startActivity(toRodaDetailActivity);
                    RodaModificationActivity.finish();
                } else {
                    Toast.makeText(RodaModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerRodaModificationResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(RodaModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerRodaModificationResponse getServerRodaModificationResponse() {
        return serverRodaModificationResponse;
    }
    public void sendUserSearchToServer(RodaModificationActivity RodaModificationActivity,
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
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(RodaModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            RodaModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(RodaModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSearchResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(RodaModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerSearchResponse getSearchResponse() {
        return serverSearchResponse;
    }
    public void getRodaDetailMore(RodaModificationActivity RodaModificationActivity, Integer groupId) {
        ClientRodaDetailMoreRequest clientRodaDetailMoreRequest = new ClientRodaDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerRodaDetailMoreResponse>> call = Server.post_roda_detail_more(clientRodaDetailMoreRequest);
        call.enqueue(new Callback<List<ServerRodaDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerRodaDetailMoreResponse>> call, @NonNull Response<List<ServerRodaDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverRodaDetailMoreResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        assert serverRodaDetailMoreResponse != null;
                        for (int i = 0; i < serverRodaDetailMoreResponse.size(); i++) {
                            if (serverRodaDetailMoreResponse.get(i).getUserId().equals(SharedPreferencesManager.getIntegerValue(Constants.ID))) {
                                serverRodaDetailMoreResponse.remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < serverRodaDetailMoreResponse.size(); i++) {
                            RodaModificationActivity.invitedMembers.add(serverRodaDetailMoreResponse.get(i).getUserId());
                        }
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(RodaModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            RodaModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMembersInvitedFragment(), "UserListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(RodaModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerRodaDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(RodaModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerRodaDetailMoreResponse> getMyResponse() {
        return serverRodaDetailMoreResponse;
    }
    public void deleteRoda(RodaModificationActivity RodaModificationActivity, Integer rodaId) {
        ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), rodaId, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerDeleteResponse> call = Server.post_roda_delete(clientDeleteRequest);
        call.enqueue(new Callback<ServerDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerDeleteResponse> call, @NonNull Response<ServerDeleteResponse> response) {
                if (response.isSuccessful()){
                    serverDeleteResponse = response.body();
                    assert serverDeleteResponse != null;
                    if (serverDeleteResponse.isOk()) {
                        Toast.makeText(RodaModificationActivity, R.string.rodaDeleted, Toast.LENGTH_SHORT).show();
                        RodaModificationActivity.toRodaList();
                    } else {
                        Toast.makeText(RodaModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerDeleteResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(RodaModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
