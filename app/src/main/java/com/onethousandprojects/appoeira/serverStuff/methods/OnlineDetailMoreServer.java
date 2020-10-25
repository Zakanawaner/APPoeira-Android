package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.CommentFragment;
import com.onethousandprojects.appoeira.onlineDetailMoreView.OnlineDetailMoreActivity;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.OnlineMemberMembersFragment;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.OnlineMemberOwnersFragment;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ClientJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ServerJoinResponse;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ClientLeaveRequest;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ServerLeaveResponse;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ClientOnlineDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineDetailMore.ServerOnlineDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ClientRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ServeRatedByUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineDetailMoreServer {
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private List<ServerOnlineDetailMoreResponse> serverOnlineDetailMoreResponse;
    private List<ServerCommentsResponse> serverOnlineCommentsResponse;
    private ServeRatedByUserResponse serveOnlineRatedByUserResponse;

    public OnlineDetailMoreServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void getOnlineDetailMore(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer groupId) {
        ClientOnlineDetailMoreRequest clientOnlineDetailMoreRequest = new ClientOnlineDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerOnlineDetailMoreResponse>> call = Server.post_online_detail_more(clientOnlineDetailMoreRequest);
        call.enqueue(new Callback<List<ServerOnlineDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerOnlineDetailMoreResponse>> call, @NonNull Response<List<ServerOnlineDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverOnlineDetailMoreResponse = response.body();
                    assert serverOnlineDetailMoreResponse != null;
                    if (OnlineDetailMoreActivity.fromOnlineDetailActivity.getBoolean("isOwner")) {
                        OnlineDetailMoreActivity.fbtnAdd.setImageResource(R.drawable.ic_edit);
                        OnlineDetailMoreActivity.fbtnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toCreateRoda = new Intent(OnlineDetailMoreActivity, OnlineModificationActivity.class);
                                toCreateRoda.putExtra("onlineId", OnlineDetailMoreActivity.fromOnlineDetailActivity.getInt("onlineId"));
                                toCreateRoda.putExtra("modification", true);
                                toCreateRoda.putExtra("name", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("name"));
                                toCreateRoda.putExtra("image", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("image"));
                                toCreateRoda.putExtra("description", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("description"));
                                toCreateRoda.putExtra("phone", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("phone"));
                                toCreateRoda.putExtra("verified", OnlineDetailMoreActivity.fromOnlineDetailActivity.getBoolean("verified"));
                                toCreateRoda.putExtra("rating", OnlineDetailMoreActivity.fromOnlineDetailActivity.getDouble("rating"));
                                toCreateRoda.putExtra("votes", OnlineDetailMoreActivity.fromOnlineDetailActivity.getInt("votes"));
                                toCreateRoda.putExtra("member", OnlineDetailMoreActivity.fromOnlineDetailActivity.getBoolean("member"));
                                toCreateRoda.putExtra("voted", OnlineDetailMoreActivity.fromOnlineDetailActivity.getBoolean("voted"));
                                toCreateRoda.putExtra("isOwner", OnlineDetailMoreActivity.fromOnlineDetailActivity.getBoolean("isOwner"));
                                toCreateRoda.putExtra("platform", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("platform"));
                                toCreateRoda.putExtra("date", OnlineDetailMoreActivity.fromOnlineDetailActivity.getString("date"));
                                OnlineDetailMoreActivity.startActivity(toCreateRoda);
                            }
                        });
                    }
                    OnlineDetailMoreActivity.NUM_MEMBERS = serverOnlineDetailMoreResponse.size();
                    OnlineDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new OnlineMemberOwnersFragment(), "OnlineMemberOwnersFragment").commit();
                    OnlineDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new OnlineMemberMembersFragment(), "OnlineMemberMembersFragment").commit();
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerOnlineDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerOnlineDetailMoreResponse> getMyResponse() {
        return serverOnlineDetailMoreResponse;
    }
    public void serverOnlineCommentsResponse(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer groupId) {
        ClientCommentsRequest clientOnlineCommentsRequest = new ClientCommentsRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerCommentsResponse>> call1 = Server.post_online_comments(clientOnlineCommentsRequest);
        call1.enqueue(new Callback<List<ServerCommentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Response<List<ServerCommentsResponse>> response) {
                if (response.isSuccessful()){
                    serverOnlineCommentsResponse = response.body();
                    assert serverOnlineCommentsResponse != null;
                    OnlineDetailMoreActivity.NUM_PAGES = serverOnlineCommentsResponse.size();
                    OnlineDetailMoreActivity.tvNumberCommentsNum.setText("(" + OnlineDetailMoreActivity.NUM_PAGES + ")");
                    OnlineDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "OnlineCommentsFragment").commit();
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerCommentsResponse> getMyResponseComments() {
        return serverOnlineCommentsResponse;
    }
    public void postComment(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer group_id, String comment) {
        ClientNewCommentRequest clientOnlineNewCommentRequest = new ClientNewCommentRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerNewCommentResponse> call = Server.post_new_comment_online(clientOnlineNewCommentRequest);
        call.enqueue(new Callback<ServerNewCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerNewCommentResponse> callComments, @NonNull Response<ServerNewCommentResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(OnlineDetailMoreActivity, "TU comentario se envió correctamente", Toast.LENGTH_SHORT).show();
                        OnlineDetailMoreActivity.killFragment();
                        OnlineDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(OnlineDetailMoreActivity, "TU comentario no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinOnline(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer group_id, Integer roleId) {
        ClientJoinRequest clientOnlineJoinRequest = new ClientJoinRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerJoinResponse> call = Server.post_join_online(clientOnlineJoinRequest);
        call.enqueue(new Callback<ServerJoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerJoinResponse> callComments, @NonNull Response<ServerJoinResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(OnlineDetailMoreActivity, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                        OnlineDetailMoreActivity.getIntent().removeExtra("member");
                        OnlineDetailMoreActivity.getIntent().putExtra("member", true);
                        OnlineDetailMoreActivity.killFragment();
                        OnlineDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(OnlineDetailMoreActivity, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerJoinResponse> callComments, Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void leaveOnline(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer group_id) {
        ClientLeaveRequest clientLeaveRequest = new ClientLeaveRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerLeaveResponse> call = Server.post_leave_online(clientLeaveRequest);
        call.enqueue(new Callback<ServerLeaveResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerLeaveResponse> callComments, @NonNull Response<ServerLeaveResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(OnlineDetailMoreActivity, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                        OnlineDetailMoreActivity.getIntent().removeExtra("member");
                        OnlineDetailMoreActivity.getIntent().putExtra("member", false);
                        OnlineDetailMoreActivity.killFragment();
                        OnlineDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(OnlineDetailMoreActivity, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerLeaveResponse> callComments, Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendRating(OnlineDetailMoreActivity OnlineDetailMoreActivity, Integer group_id, Integer stars) {
        ClientRatedByUserRequest clientOnlineRatedByUserRequest = new ClientRatedByUserRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID), group_id, stars);
        Call<ServeRatedByUserResponse> call = Server.post_user_rated_online(clientOnlineRatedByUserRequest);
        call.enqueue(new Callback<ServeRatedByUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Response<ServeRatedByUserResponse> response) {
                if (response.isSuccessful()){
                    serveOnlineRatedByUserResponse = response.body();
                    assert serveOnlineRatedByUserResponse != null;
                    if (serveOnlineRatedByUserResponse.isOk()) {
                        Toast.makeText(OnlineDetailMoreActivity, "Has dado " + stars + " estrellas a este grupo", Toast.LENGTH_SHORT).show();
                        OnlineDetailMoreActivity.killFragment();
                        OnlineDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(OnlineDetailMoreActivity, "Ya valoraste este grupo con " + stars + " estrellas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(OnlineDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
