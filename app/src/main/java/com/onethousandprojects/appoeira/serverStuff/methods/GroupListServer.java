package com.onethousandprojects.appoeira.serverStuff.methods;

import android.location.Location;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupFragment;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupMapsFragment;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupListServer {
    private List<ServerLocationGroupResponse> serverLocationGroupResponse;
    private List<ServerLocationGroupResponse> serverLocationGroupMapsResponse;
    private boolean createdFragment = false;
    Client Client;
    Server Server;

    public GroupListServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void sendLocationToServer(GroupListActivity GroupListActivity, Location location, Integer distance) {
        ClientLocationGroupsRequest clientLocationGroupsRequest = new ClientLocationGroupsRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), distance);
        Call<List<ServerLocationGroupResponse>> call = Server.post_location_groups(clientLocationGroupsRequest);
        call.enqueue(new Callback<List<ServerLocationGroupResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerLocationGroupResponse>> call, @NonNull Response<List<ServerLocationGroupResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationGroupResponse = response.body();
                    assert serverLocationGroupResponse != null;
                    Collections.sort(serverLocationGroupResponse, new Comparator<ServerLocationGroupResponse>() {
                        @Override
                        public int compare(ServerLocationGroupResponse lhs, ServerLocationGroupResponse rhs) {
                            return lhs.getDistance().compareTo(rhs.getDistance());
                        }
                    });
                    if (!createdFragment) {
                        createdFragment = true;
                        GroupListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupFragment(), "GroupListFragment").commit();
                    } else {
                        GroupListActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(GroupListActivity.getSupportFragmentManager().findFragmentByTag("GroupListFragment"))).commit();
                        GroupListActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupFragment(), "GroupListFragment").commit();
                    }
                    GroupListActivity.srGroupList.setRefreshing(false);
                } else {
                    Toast.makeText(GroupListActivity,"Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerLocationGroupResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(GroupListActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public List<ServerLocationGroupResponse> getServerLocationGroupResponse() {
        return serverLocationGroupResponse;
    }
    public void sendMapsLocationToServer(GroupMapsFragment groupMapsFragment, Location location, Integer distance) {
        ClientLocationGroupsRequest clientLocationGroupsRequest = new ClientLocationGroupsRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), distance);
        Call<List<ServerLocationGroupResponse>> call = Server.post_location_groups(clientLocationGroupsRequest);
        call.enqueue(new Callback<List<ServerLocationGroupResponse>>() {
            @Override
            public void onResponse(Call<List<ServerLocationGroupResponse>> call, Response<List<ServerLocationGroupResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationGroupMapsResponse = response.body();
                    for(int i = 0; i < serverLocationGroupMapsResponse.size(); i++) {
                        // googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(image)).position(marker).title(myResponse.get(i).getName()));
                        Marker marker = groupMapsFragment.groupsMap.addMarker(new MarkerOptions().position(new LatLng(serverLocationGroupMapsResponse.get(i).getLatitude(), serverLocationGroupMapsResponse.get(i).getLongitude())).title(serverLocationGroupMapsResponse.get(i).getName()));
                        Pair<String, Integer> pair = new Pair<> (serverLocationGroupMapsResponse.get(i).getPicUrl(), serverLocationGroupMapsResponse.get(i).getId());
                        marker.setTag(pair);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ServerLocationGroupResponse>> call, Throwable t) {
            }
        });
    }
    public List<ServerLocationGroupResponse> getServerLocationGroupMapsResponse() {
        return serverLocationGroupMapsResponse;
    }
}
