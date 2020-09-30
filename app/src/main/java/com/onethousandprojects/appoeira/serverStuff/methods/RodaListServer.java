package com.onethousandprojects.appoeira.serverStuff.methods;

import android.location.Location;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaFragment;
import com.onethousandprojects.appoeira.rodaListView.fragments.RodaMapsFragment;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ClientLocationRodasRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaList.ServerLocationRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RodaListServer {
    private List<ServerLocationRodaResponse> serverLocationRodaResponse;
    private List<ServerLocationRodaResponse> serverLocationRodaMapsResponse;
    private boolean createdFragment = false;
    Client Client;
    Server Server;

    public RodaListServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendLocationToServer(RodaListActivity RodaListActivity, Location location, Integer distance) {
        ClientLocationRodasRequest clientLocationRodasRequest = new ClientLocationRodasRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), distance);
        Call<List<ServerLocationRodaResponse>> call = Server.post_location_rodas(clientLocationRodasRequest);
        call.enqueue(new Callback<List<ServerLocationRodaResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerLocationRodaResponse>> call, @NonNull Response<List<ServerLocationRodaResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationRodaResponse = response.body();
                    assert serverLocationRodaResponse != null;
                    Collections.sort(serverLocationRodaResponse, new Comparator<ServerLocationRodaResponse>() {
                        @Override
                        public int compare(ServerLocationRodaResponse lhs, ServerLocationRodaResponse rhs) {
                            return lhs.getDistance().compareTo(rhs.getDistance());
                        }
                    });
                    if (!createdFragment) {
                        createdFragment = true;
                        RodaListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaFragment(), "RodaListFragment").commit();
                    } else {
                        RodaListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(RodaListActivity.getSupportFragmentManager().findFragmentByTag("RodaListFragment"))).commit();
                        RodaListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new RodaFragment(), "RodaListFragment").commit();
                    }
                    RodaListActivity.srRodaList.setRefreshing(false);
                } else {
                    Toast.makeText(RodaListActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerLocationRodaResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(RodaListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerLocationRodaResponse> getServerLocationRodaResponse() {
        return serverLocationRodaResponse;
    }
    public void sendMapsLocationToServer(RodaMapsFragment rodaMapsFragment, Location location, Integer distance) {
        ClientLocationRodasRequest clientLocationRodasRequest = new ClientLocationRodasRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), distance);
        Call<List<ServerLocationRodaResponse>> call = Server.post_location_rodas(clientLocationRodasRequest);
        call.enqueue(new Callback<List<ServerLocationRodaResponse>>() {
            @Override
            public void onResponse(Call<List<ServerLocationRodaResponse>> call, Response<List<ServerLocationRodaResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationRodaMapsResponse = response.body();
                    for(int i = 0; i < serverLocationRodaMapsResponse.size(); i++) {
                        // googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(image)).position(marker).title(myResponse.get(i).getName()));
                        Marker marker = rodaMapsFragment.rodasMap.addMarker(new MarkerOptions().position(new LatLng(serverLocationRodaMapsResponse.get(i).getLatitude(), serverLocationRodaMapsResponse.get(i).getLongitude())).title(serverLocationRodaMapsResponse.get(i).getName()));
                        Pair<String, Integer> pair = new Pair<> (serverLocationRodaMapsResponse.get(i).getPicUrl(), serverLocationRodaMapsResponse.get(i).getId());
                        marker.setTag(pair);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ServerLocationRodaResponse>> call, Throwable t) {
            }
        });
    }
    public List<ServerLocationRodaResponse> getServerLocationRodaMapsResponse() {
        return serverLocationRodaMapsResponse;
    }
}
