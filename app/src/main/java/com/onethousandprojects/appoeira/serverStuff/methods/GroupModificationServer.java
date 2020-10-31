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
import com.onethousandprojects.appoeira.groupModificationView.fragments.GroupMembersFrontlineFragment;
import com.onethousandprojects.appoeira.groupModificationView.fragments.GroupMembersStudentsFragment;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ClientDeleteRequest;
import com.onethousandprojects.appoeira.serverStuff.deleteObject.ServerDeleteResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.groupModification.ClientGroupModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.groupModification.ServerGroupModificationResponse;
import com.onethousandprojects.appoeira.serverStuff.search.ClientSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.search.ServerSearchResponse;
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
    private List<ServerGroupDetailMoreResponse> serverGroupDetailMoreResponse;
    private ServerSearchResponse serverSearchResponse;
    private ServerDeleteResponse serverDeleteResponse;
    public boolean createdFrontlineFragment = false;
    public boolean createdStudentsFragment = false;
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
                                          Bitmap imageBitmap, Integer groupId, String url) throws IOException {
        ClientGroupModificationRequest clientGroupModificationRequest = new ClientGroupModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), owners, name, description, latitude, longitude, phone, GroupModificationActivity.frontlineMembers, GroupModificationActivity.studentMembers, url, groupId);
        Call<ServerGroupModificationResponse> call = Server.post_group_update(clientGroupModificationRequest, CommonMethods.fromBitmapToFile(GroupModificationActivity, imageBitmap, "group", "avatar", groupId, 0));
        call.enqueue(new Callback<ServerGroupModificationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerGroupModificationResponse> call, @NonNull Response<ServerGroupModificationResponse> response) {
                if (response.isSuccessful()){
                    serverGroupModificationResponse = response.body();
                    Intent toGroupDetailActivity = new Intent(GroupModificationActivity, GroupDetailActivity.class);
                    toGroupDetailActivity.putExtra("groupId", serverGroupModificationResponse.getId());
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
    public void sendUserSearchToServer(GroupModificationActivity GroupModificationActivity,
                                       String search, boolean studentsFrontline) {
        ClientSearchRequest clientSearchRequest = new ClientSearchRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), search, "10000");
        Call<ServerSearchResponse> call = Server.post_search(clientSearchRequest);
        call.enqueue(new Callback<ServerSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSearchResponse> call, @NonNull Response<ServerSearchResponse> response) {
                if (response.isSuccessful()){
                    serverSearchResponse = response.body();
                    if (!studentsFrontline) {
                        if (!createdStudentsFragment) {
                            createdStudentsFragment = true;
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupMembersStudentsFragment(), "StudentListFragment").commit();
                        } else {
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(GroupModificationActivity.getSupportFragmentManager().findFragmentByTag("StudentListFragment"))).commit();
                            if (serverSearchResponse.getUserResponses().size() > 0) {
                                GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupMembersStudentsFragment(), "StudentListFragment").commit();
                            } else {
                                createdStudentsFragment = false;
                            }
                        }
                    } else {
                        if (!createdFrontlineFragment) {
                            createdFrontlineFragment = true;
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new GroupMembersFrontlineFragment(), "FrontlineListFragment").commit();
                        } else {
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(GroupModificationActivity.getSupportFragmentManager().findFragmentByTag("FrontlineListFragment"))).commit();
                            if (serverSearchResponse.getUserResponses().size() > 0) {
                                GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new GroupMembersFrontlineFragment(), "FrontlineListFragment").commit();
                            } else {
                                createdFrontlineFragment = false;
                            }
                        }
                    }
                } else {
                    Toast.makeText(GroupModificationActivity,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerSearchResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(GroupModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    public ServerSearchResponse getSearchResponse() {
        return serverSearchResponse;
    }
    public void getGroupDetailMore(GroupModificationActivity GroupModificationActivity, Integer groupId) {
        ClientGroupDetailMoreRequest clientGroupDetailMoreRequest = new ClientGroupDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerGroupDetailMoreResponse>> call = Server.post_group_detail_more(clientGroupDetailMoreRequest);
        call.enqueue(new Callback<List<ServerGroupDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerGroupDetailMoreResponse>> call, @NonNull Response<List<ServerGroupDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverGroupDetailMoreResponse = response.body();
                    if (!createdStudentsFragment) {
                        createdStudentsFragment = true;
                        assert serverGroupDetailMoreResponse != null;
                        for (int i = 0; i < serverGroupDetailMoreResponse.size(); i++) {
                            if (serverGroupDetailMoreResponse.get(i).getUserId().equals(SharedPreferencesManager.getIntegerValue(Constants.ID))) {
                                serverGroupDetailMoreResponse.remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < serverGroupDetailMoreResponse.size(); i++) {
                            if (serverGroupDetailMoreResponse.get(i).getUserGroupRole().equals("member")) {
                                GroupModificationActivity.studentMembers.add(serverGroupDetailMoreResponse.get(i).getUserId());
                            }
                        }
                        GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupMembersStudentsFragment(), "StudentListFragment").commit();
                    } else {
                        GroupModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(GroupModificationActivity.getSupportFragmentManager().findFragmentByTag("StudentListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new GroupMembersStudentsFragment(), "StudentListFragment").commit();
                        } else {
                            createdStudentsFragment = false;
                        }
                    }
                    if (!createdFrontlineFragment) {
                        createdFrontlineFragment = true;
                        assert serverGroupDetailMoreResponse != null;
                        for (int i = 0; i < serverGroupDetailMoreResponse.size(); i++) {
                            if (serverGroupDetailMoreResponse.get(i).getUserId().equals(SharedPreferencesManager.getIntegerValue(Constants.ID))) {
                                serverGroupDetailMoreResponse.remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < serverGroupDetailMoreResponse.size(); i++) {
                            if (serverGroupDetailMoreResponse.get(i).getUserGroupRole().equals("frontline")) {
                                GroupModificationActivity.frontlineMembers.add(serverGroupDetailMoreResponse.get(i).getUserId());
                            }
                        }
                        GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new GroupMembersFrontlineFragment(), "FrontlineListFragment").commit();
                    } else {
                        GroupModificationActivity.getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(GroupModificationActivity.getSupportFragmentManager().findFragmentByTag("FrontlineListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().get(0).getId() != null) {
                            GroupModificationActivity.getSupportFragmentManager().beginTransaction().add(R.id.ListConvidedLayout, new GroupMembersFrontlineFragment(), "FrontlineListFragment").commit();
                        } else {
                            createdFrontlineFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(GroupModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerGroupDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(GroupModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerGroupDetailMoreResponse> getMyResponse() {
        return serverGroupDetailMoreResponse;
    }
    public void deleteGroup(GroupModificationActivity GroupModificationActivity, Integer groupId) {
        ClientDeleteRequest clientDeleteRequest = new ClientDeleteRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerDeleteResponse> call = Server.post_group_delete(clientDeleteRequest);
        call.enqueue(new Callback<ServerDeleteResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerDeleteResponse> call, @NonNull Response<ServerDeleteResponse> response) {
                if (response.isSuccessful()){
                    serverDeleteResponse = response.body();
                    assert serverDeleteResponse != null;
                    if (serverDeleteResponse.isOk()) {
                        Toast.makeText(GroupModificationActivity, R.string.groupDeleted, Toast.LENGTH_SHORT).show();
                        GroupModificationActivity.toGroupList();
                    } else {
                        Toast.makeText(GroupModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupModificationActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerDeleteResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(GroupModificationActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
