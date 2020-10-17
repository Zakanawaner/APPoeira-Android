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
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ClientRodaModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ServerRodaModificationResponse;
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

public class RodaModificationServer {
    private ServerRodaModificationResponse serverRodaModificationResponse;
    private ServerSearchResponse serverSearchResponse;
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
        ClientRodaModificationRequest clientRodaModificationRequest = new ClientRodaModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, date, invited, latitude, longitude, phone);
        Call<ServerRodaModificationResponse> call = Server.post_roda_update(clientRodaModificationRequest, CommonMethods.fromBitmapToFile(RodaModificationActivity, imageBitmap, "roda", "avatar", rodaId, 0));
        call.enqueue(new Callback<ServerRodaModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerRodaModificationResponse> call, @NonNull Response<ServerRodaModificationResponse> response) {
                if (response.isSuccessful()){
                    serverRodaModificationResponse = response.body();
                    Intent toRodaDetailActivity = new Intent(RodaModificationActivity, RodaDetailActivity.class);
                    toRodaDetailActivity.putExtra("id", serverRodaModificationResponse.getId());
                    toRodaDetailActivity.putExtra("ownerRank", CommonMethods.fromRankIdToRankName(SharedPreferencesManager.getIntegerValue(Constants.RANK), RodaModificationActivity));
                    toRodaDetailActivity.putExtra("owner", SharedPreferencesManager.getStringValue(Constants.APELHIDO));
                    RodaModificationActivity.startActivity(toRodaDetailActivity);
                    RodaModificationActivity.finish();
                } else {
                    Toast.makeText(RodaModificationActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(RodaModificationActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
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
}
