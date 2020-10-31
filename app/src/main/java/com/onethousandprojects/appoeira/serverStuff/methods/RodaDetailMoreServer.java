package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.CommentFragment;
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.RodaMemberMembersFragment;
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.RodaMemberOwnersFragment;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ClientJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ServerJoinResponse;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ClientLeaveRequest;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ServerLeaveResponse;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ClientRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ServeRatedByUserResponse;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ClientRodaDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetailMore.ServerRodaDetailMoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RodaDetailMoreServer {
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private List<ServerRodaDetailMoreResponse> serverRodaDetailMoreResponse;
    private List<ServerCommentsResponse> serverRodaCommentsResponse;
    private ServeRatedByUserResponse serveRodaRatedByUserResponse;

    public RodaDetailMoreServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void getRodaDetailMore(RodaDetailMoreActivity RodaDetailMoreActivity, Integer groupId) {
        ClientRodaDetailMoreRequest clientRodaDetailMoreRequest = new ClientRodaDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerRodaDetailMoreResponse>> call = Server.post_roda_detail_more(clientRodaDetailMoreRequest);
        call.enqueue(new Callback<List<ServerRodaDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerRodaDetailMoreResponse>> call, @NonNull Response<List<ServerRodaDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverRodaDetailMoreResponse = response.body();
                    assert serverRodaDetailMoreResponse != null;
                    if (RodaDetailMoreActivity.fromRodaDetailActivity.getBoolean("isOwner")) {
                        RodaDetailMoreActivity.fbtnAdd.setImageResource(R.drawable.ic_edit);
                        RodaDetailMoreActivity.fbtnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toCreateRoda = new Intent(RodaDetailMoreActivity, RodaModificationActivity.class);
                                toCreateRoda.putExtra("rodaId", RodaDetailMoreActivity.fromRodaDetailActivity.getInt("rodaId"));
                                toCreateRoda.putExtra("modification", true);
                                toCreateRoda.putExtra("name", RodaDetailMoreActivity.fromRodaDetailActivity.getString("name"));
                                toCreateRoda.putExtra("image", RodaDetailMoreActivity.fromRodaDetailActivity.getString("image"));
                                toCreateRoda.putExtra("description", RodaDetailMoreActivity.fromRodaDetailActivity.getString("description"));
                                toCreateRoda.putExtra("phone", RodaDetailMoreActivity.fromRodaDetailActivity.getString("phone"));
                                toCreateRoda.putExtra("verified", RodaDetailMoreActivity.fromRodaDetailActivity.getBoolean("verified"));
                                toCreateRoda.putExtra("rating", RodaDetailMoreActivity.fromRodaDetailActivity.getDouble("rating"));
                                toCreateRoda.putExtra("votes", RodaDetailMoreActivity.fromRodaDetailActivity.getInt("votes"));
                                toCreateRoda.putExtra("address", RodaDetailMoreActivity.fromRodaDetailActivity.getString("address"));
                                toCreateRoda.putExtra("city", RodaDetailMoreActivity.fromRodaDetailActivity.getString("city"));
                                toCreateRoda.putExtra("country", RodaDetailMoreActivity.fromRodaDetailActivity.getString("country"));
                                toCreateRoda.putExtra("latitude", RodaDetailMoreActivity.fromRodaDetailActivity.getDouble("latitude"));
                                toCreateRoda.putExtra("longitude", RodaDetailMoreActivity.fromRodaDetailActivity.getDouble("longitude"));
                                toCreateRoda.putExtra("member", RodaDetailMoreActivity.fromRodaDetailActivity.getBoolean("member"));
                                toCreateRoda.putExtra("voted", RodaDetailMoreActivity.fromRodaDetailActivity.getInt("voted"));
                                toCreateRoda.putExtra("isOwner", RodaDetailMoreActivity.fromRodaDetailActivity.getBoolean("isOwner"));
                                toCreateRoda.putExtra("date", RodaDetailMoreActivity.fromRodaDetailActivity.getString("date"));
                                RodaDetailMoreActivity.startActivity(toCreateRoda);
                            }
                        });
                    }
                    RodaDetailMoreActivity.NUM_MEMBERS = serverRodaDetailMoreResponse.size();
                    RodaDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new RodaMemberOwnersFragment(), "RodaMemberOwnersFragment").commit();
                    RodaDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new RodaMemberMembersFragment(), "RodaMemberMembersFragment").commit();
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerRodaDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerRodaDetailMoreResponse> getMyResponse() {
        return serverRodaDetailMoreResponse;
    }
    public void serverRodaCommentsResponse(RodaDetailMoreActivity RodaDetailMoreActivity, Integer groupId) {
        ClientCommentsRequest clientRodaCommentsRequest = new ClientCommentsRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerCommentsResponse>> call1 = Server.post_roda_comments(clientRodaCommentsRequest);
        call1.enqueue(new Callback<List<ServerCommentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Response<List<ServerCommentsResponse>> response) {
                if (response.isSuccessful()){
                    serverRodaCommentsResponse = response.body();
                    assert serverRodaCommentsResponse != null;
                    RodaDetailMoreActivity.NUM_PAGES = serverRodaCommentsResponse.size();
                    RodaDetailMoreActivity.tvNumberCommentsNum.setText("(" + RodaDetailMoreActivity.NUM_PAGES + ")");
                    RodaDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "RodaCommentsFragment").commit();
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerCommentsResponse> getMyResponseComments() {
        return serverRodaCommentsResponse;
    }
    public void postComment(RodaDetailMoreActivity RodaDetailMoreActivity, Integer group_id, String comment) {
        ClientNewCommentRequest clientRodaNewCommentRequest = new ClientNewCommentRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerNewCommentResponse> call = Server.post_new_comment_roda(clientRodaNewCommentRequest);
        call.enqueue(new Callback<ServerNewCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerNewCommentResponse> callComments, @NonNull Response<ServerNewCommentResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(RodaDetailMoreActivity, R.string.commentSent, Toast.LENGTH_SHORT).show();
                        RodaDetailMoreActivity.killFragment();
                        RodaDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(RodaDetailMoreActivity, R.string.commentNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinRoda(RodaDetailMoreActivity RodaDetailMoreActivity, Integer group_id, Integer roleId) {
        ClientJoinRequest clientRodaJoinRequest = new ClientJoinRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerJoinResponse> call = Server.post_join_roda(clientRodaJoinRequest);
        call.enqueue(new Callback<ServerJoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerJoinResponse> callComments, @NonNull Response<ServerJoinResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(RodaDetailMoreActivity, R.string.requestSent, Toast.LENGTH_SHORT).show();
                        RodaDetailMoreActivity.getIntent().removeExtra("member");
                        RodaDetailMoreActivity.getIntent().putExtra("member", true);
                        RodaDetailMoreActivity.killFragment();
                        RodaDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(RodaDetailMoreActivity, R.string.requestNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerJoinResponse> callComments, Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void leaveRoda(RodaDetailMoreActivity RodaDetailMoreActivity, Integer group_id) {
        ClientLeaveRequest clientLeaveRequest = new ClientLeaveRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerLeaveResponse> call = Server.post_leave_roda(clientLeaveRequest);
        call.enqueue(new Callback<ServerLeaveResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerLeaveResponse> callComments, @NonNull Response<ServerLeaveResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(RodaDetailMoreActivity, R.string.requestSent, Toast.LENGTH_SHORT).show();
                        RodaDetailMoreActivity.getIntent().removeExtra("member");
                        RodaDetailMoreActivity.getIntent().putExtra("member", false);
                        RodaDetailMoreActivity.killFragment();
                        RodaDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(RodaDetailMoreActivity, R.string.requestNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerLeaveResponse> callComments, Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendRating(RodaDetailMoreActivity RodaDetailMoreActivity, Integer group_id, Integer stars) {
        ClientRatedByUserRequest clientRodaRatedByUserRequest = new ClientRatedByUserRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID), group_id, stars);
        Call<ServeRatedByUserResponse> call = Server.post_user_rated_roda(clientRodaRatedByUserRequest);
        call.enqueue(new Callback<ServeRatedByUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Response<ServeRatedByUserResponse> response) {
                if (response.isSuccessful()){
                    serveRodaRatedByUserResponse = response.body();
                    assert serveRodaRatedByUserResponse != null;
                    if (serveRodaRatedByUserResponse.isOk()) {
                        Toast.makeText(RodaDetailMoreActivity, R.string.youHaveGiven + stars + R.string.starsToThisRoda, Toast.LENGTH_SHORT).show();
                        RodaDetailMoreActivity.killFragment();
                        RodaDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(RodaDetailMoreActivity, R.string.youAlreadyRatedRoda + stars + R.string.stars, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(RodaDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
