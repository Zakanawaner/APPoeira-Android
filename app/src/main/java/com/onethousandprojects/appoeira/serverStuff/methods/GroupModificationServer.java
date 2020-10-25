package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupModificationView.GroupModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.groupModification.ClientGroupModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.groupModification.ServerGroupModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupModificationServer {
    private ServerGroupModificationResponse serverGroupModificationResponse;
    private List<ServerLocationGroupResponse> serverLocationGroupMapsResponse;
    public boolean createdFragment = false;
    public boolean createdConvidedFragment = false;
    Client Client;
    public Server Server;

    public GroupModificationServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public void getListOfGroupsFromMap(GroupModificationActivity GroupModificationActivity,
                                       Location location, Double distance) {
        ClientLocationGroupsRequest clientLocationGroupsRequest = new ClientLocationGroupsRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), (int) Math.round(distance/1000));
        Call<List<ServerLocationGroupResponse>> call = Server.post_location_groups(clientLocationGroupsRequest);
        call.enqueue(new Callback<List<ServerLocationGroupResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerLocationGroupResponse>> call, @NonNull Response<List<ServerLocationGroupResponse>> response) {
                if (response.isSuccessful()){
                    serverLocationGroupMapsResponse = response.body();
                    assert serverLocationGroupMapsResponse != null;
                    for(int i = 0; i < serverLocationGroupMapsResponse.size(); i++) {
                        // googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(image)).position(marker).title(myResponse.get(i).getName()));
                        Marker marker = GroupModificationActivity.groupsMap.addMarker(new MarkerOptions().position(new LatLng(serverLocationGroupMapsResponse.get(i).getLatitude(), serverLocationGroupMapsResponse.get(i).getLongitude())).title(serverLocationGroupMapsResponse.get(i).getName()));
                        Pair<String, Integer> pair = new Pair<> (serverLocationGroupMapsResponse.get(i).getPicUrl(), serverLocationGroupMapsResponse.get(i).getId());
                        marker.setTag(pair);
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerLocationGroupResponse>> call, @NonNull Throwable t) {
            }
        });
    }

    public List<ServerLocationGroupResponse> getServerLocationGroupMapsResponse() {
        return serverLocationGroupMapsResponse;
    }

    public void sendModificationsToServer(GroupModificationActivity GroupModificationActivity,
                                          List<Integer> owners, String name, String description,
                                          Double latitude, Double longitude, String phone,
                                          Bitmap imageBitmap, Integer groupId) throws IOException {
        ClientGroupModificationRequest clientGroupModificationRequest = new ClientGroupModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, latitude, longitude, phone);
        Call<ServerGroupModificationResponse> call = Server.post_group_update(clientGroupModificationRequest, CommonMethods.fromBitmapToFile(GroupModificationActivity, imageBitmap, "group", "avatar", groupId, 0));
        call.enqueue(new Callback<ServerGroupModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerGroupModificationResponse> call, @NonNull Response<ServerGroupModificationResponse> response) {
                if (response.isSuccessful()){
                    serverGroupModificationResponse = response.body();
                    Intent toGroupDetailActivity = new Intent(GroupModificationActivity, GroupDetailActivity.class);
                    toGroupDetailActivity.putExtra("id", serverGroupModificationResponse.getId());
                    GroupModificationActivity.startActivity(toGroupDetailActivity);
                    GroupModificationActivity.finish();
                } else {
                    Toast.makeText(GroupModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerGroupModificationResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(GroupModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerGroupModificationResponse getServerGroupModificationResponse() {
        return serverGroupModificationResponse;
    }
}
