package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.location.Location;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupFragment;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaFragment;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaMapsFragment;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.RodaMembersInvitedFragment;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ClientLocationRodasRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ServerLocationRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ClientRodaModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaModification.ServerRodaModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ClientUserSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.userSearch.ServerUserSearchResponse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RodaModificationServer {
    private ServerRodaModificationResponse serverRodaModificationResponse;
    private List<ServerUserSearchResponse> serverUserSearchResponse;
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
                                          String date, String picUrl, List<Integer> invited,
                                          Double latitude, Double longitude) {
        ClientRodaModificationRequest clientRodaModificationRequest = new ClientRodaModificationRequest(owners, name, description, date, picUrl, invited, latitude, longitude);
        Call<ServerRodaModificationResponse> call = Server.post_roda_update(clientRodaModificationRequest);
        call.enqueue(new Callback<ServerRodaModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerRodaModificationResponse> call, @NonNull Response<ServerRodaModificationResponse> response) {
                if (response.isSuccessful()){
                    serverRodaModificationResponse = response.body();
                    Intent toRodaDetailActivity = new Intent(RodaModificationActivity, RodaDetailActivity.class);
                    toRodaDetailActivity.putExtra("id", serverRodaModificationResponse.getId());
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
        ClientUserSearchRequest clientUserSearchRequest = new ClientUserSearchRequest(search);
        Call<List<ServerUserSearchResponse>> call = Server.post_user_search(clientUserSearchRequest);
        call.enqueue(new Callback<List<ServerUserSearchResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerUserSearchResponse>> call, @NonNull Response<List<ServerUserSearchResponse>> response) {
                if (response.isSuccessful()){
                    serverUserSearchResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaMembersInvitedFragment(), "UserListFragment").commit();
                    } else {
                        RodaModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(RodaModificationActivity.getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        if (serverUserSearchResponse.get(0).getId() != null) {
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
            public void onFailure(@NonNull Call<List<ServerUserSearchResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(RodaModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerUserSearchResponse> getUserSearchResponse() {
        return serverUserSearchResponse;
    }
}