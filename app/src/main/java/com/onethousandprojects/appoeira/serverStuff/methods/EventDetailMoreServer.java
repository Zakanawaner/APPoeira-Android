package com.onethousandprojects.appoeira.serverStuff.methods;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.EventMemberConvidedFragment;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.EventMemberMembersFragment;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.EventMemberOwnersFragment;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.CommentFragment;
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ClientEventDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetailMore.ServerEventDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ClientJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ServerJoinResponse;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ClientLeaveRequest;
import com.onethousandprojects.appoeira.serverStuff.leaveObject.ServerLeaveResponse;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ClientRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ServeRatedByUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailMoreServer {
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private List<ServerEventDetailMoreResponse> serverEventDetailMoreResponse;
    private List<ServerCommentsResponse> serverEventCommentsResponse;
    private ServeRatedByUserResponse serveEventRatedByUserResponse;

    public EventDetailMoreServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void getEventDetailMore(EventDetailMoreActivity EventDetailMoreActivity, Integer groupId) {
        ClientEventDetailMoreRequest clientEventDetailMoreRequest = new ClientEventDetailMoreRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerEventDetailMoreResponse>> call = Server.post_event_detail_more(clientEventDetailMoreRequest);
        call.enqueue(new Callback<List<ServerEventDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerEventDetailMoreResponse>> call, @NonNull Response<List<ServerEventDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverEventDetailMoreResponse = response.body();
                    assert serverEventDetailMoreResponse != null;
                    if (EventDetailMoreActivity.fromEventDetailActivity.getBoolean("isOwner")) {
                        EventDetailMoreActivity.fbtnAdd.setImageResource(R.drawable.ic_edit);
                        EventDetailMoreActivity.fbtnAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toCreateEvent = new Intent(EventDetailMoreActivity, EventModificationActivity.class);
                                toCreateEvent.putExtra("eventId", EventDetailMoreActivity.fromEventDetailActivity.getInt("eventId"));
                                toCreateEvent.putExtra("modification", true);
                                toCreateEvent.putExtra("name", EventDetailMoreActivity.fromEventDetailActivity.getString("name"));
                                toCreateEvent.putExtra("image", EventDetailMoreActivity.fromEventDetailActivity.getString("image"));
                                toCreateEvent.putExtra("description", EventDetailMoreActivity.fromEventDetailActivity.getString("description"));
                                toCreateEvent.putExtra("phone", EventDetailMoreActivity.fromEventDetailActivity.getString("phone"));
                                toCreateEvent.putExtra("verified", EventDetailMoreActivity.fromEventDetailActivity.getBoolean("verified"));
                                toCreateEvent.putExtra("rating", EventDetailMoreActivity.fromEventDetailActivity.getDouble("rating"));
                                toCreateEvent.putExtra("votes", EventDetailMoreActivity.fromEventDetailActivity.getInt("votes"));
                                toCreateEvent.putExtra("address", EventDetailMoreActivity.fromEventDetailActivity.getString("address"));
                                toCreateEvent.putExtra("city", EventDetailMoreActivity.fromEventDetailActivity.getString("city"));
                                toCreateEvent.putExtra("country", EventDetailMoreActivity.fromEventDetailActivity.getString("country"));
                                toCreateEvent.putExtra("latitude", EventDetailMoreActivity.fromEventDetailActivity.getDouble("latitude"));
                                toCreateEvent.putExtra("longitude", EventDetailMoreActivity.fromEventDetailActivity.getDouble("longitude"));
                                toCreateEvent.putExtra("member", EventDetailMoreActivity.fromEventDetailActivity.getBoolean("member"));
                                toCreateEvent.putExtra("voted", EventDetailMoreActivity.fromEventDetailActivity.getInt("voted"));
                                toCreateEvent.putExtra("isOwner", EventDetailMoreActivity.fromEventDetailActivity.getBoolean("isOwner"));
                                toCreateEvent.putExtra("platform", EventDetailMoreActivity.fromEventDetailActivity.getString("platform"));
                                toCreateEvent.putExtra("date", EventDetailMoreActivity.fromEventDetailActivity.getString("date"));
                                EventDetailMoreActivity.startActivity(toCreateEvent);
                            }
                        });
                    }
                    EventDetailMoreActivity.NUM_MEMBERS = serverEventDetailMoreResponse.size();
                    EventDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new EventMemberOwnersFragment(), "EventMemberOwnersFragment").commit();
                    EventDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new EventMemberConvidedFragment(), "EventMemberInvitedFragment").commit();
                    EventDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailStudentsLayout, new EventMemberMembersFragment(), "EventMemberMembersFragment").commit();
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerEventDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerEventDetailMoreResponse> getMyResponse() {
        return serverEventDetailMoreResponse;
    }
    public void serverEventCommentsResponse(EventDetailMoreActivity EventDetailMoreActivity, Integer groupId) {
        ClientCommentsRequest clientEventCommentsRequest = new ClientCommentsRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), groupId);
        Call<List<ServerCommentsResponse>> call1 = Server.post_event_comments(clientEventCommentsRequest);
        call1.enqueue(new Callback<List<ServerCommentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Response<List<ServerCommentsResponse>> response) {
                if (response.isSuccessful()){
                    serverEventCommentsResponse = response.body();
                    assert serverEventCommentsResponse != null;
                    EventDetailMoreActivity.NUM_PAGES = serverEventCommentsResponse.size();
                    EventDetailMoreActivity.tvNumberCommentsNum.setText("(" + EventDetailMoreActivity.NUM_PAGES + ")");
                    EventDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "EventCommentsFragment").commit();
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerCommentsResponse> getMyResponseComments() {
        return serverEventCommentsResponse;
    }
    public void postComment(EventDetailMoreActivity EventDetailMoreActivity, Integer group_id, String comment) {
        ClientNewCommentRequest clientEventNewCommentRequest = new ClientNewCommentRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerNewCommentResponse> call = Server.post_new_comment_event(clientEventNewCommentRequest);
        call.enqueue(new Callback<ServerNewCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerNewCommentResponse> callComments, @NonNull Response<ServerNewCommentResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(EventDetailMoreActivity, R.string.commentSent, Toast.LENGTH_SHORT).show();
                        EventDetailMoreActivity.killFragment();
                        EventDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(EventDetailMoreActivity, R.string.commentNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinEvent(EventDetailMoreActivity EventDetailMoreActivity, Integer group_id, Integer roleId) {
        ClientJoinRequest clientEventJoinRequest = new ClientJoinRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerJoinResponse> call = Server.post_join_event(clientEventJoinRequest);
        call.enqueue(new Callback<ServerJoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerJoinResponse> callComments, @NonNull Response<ServerJoinResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(EventDetailMoreActivity, R.string.requestSent, Toast.LENGTH_SHORT).show();
                        EventDetailMoreActivity.getIntent().removeExtra("member");
                        EventDetailMoreActivity.getIntent().putExtra("member", true);
                        EventDetailMoreActivity.killFragment();
                        EventDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(EventDetailMoreActivity, R.string.requestNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerJoinResponse> callComments, Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void leaveEvent(EventDetailMoreActivity EventDetailMoreActivity, Integer group_id) {
        ClientLeaveRequest clientLeaveRequest = new ClientLeaveRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), group_id, SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerLeaveResponse> call = Server.post_leave_event(clientLeaveRequest);
        call.enqueue(new Callback<ServerLeaveResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerLeaveResponse> callComments, @NonNull Response<ServerLeaveResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(EventDetailMoreActivity, R.string.requestSent, Toast.LENGTH_SHORT).show();
                        EventDetailMoreActivity.getIntent().removeExtra("member");
                        EventDetailMoreActivity.getIntent().putExtra("member", false);
                        EventDetailMoreActivity.killFragment();
                        EventDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(EventDetailMoreActivity, R.string.requestNotSaved, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerLeaveResponse> callComments, Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendRating(EventDetailMoreActivity EventDetailMoreActivity, Integer group_id, Integer stars) {
        ClientRatedByUserRequest clientEventRatedByUserRequest = new ClientRatedByUserRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID), group_id, stars);
        Call<ServeRatedByUserResponse> call = Server.post_user_rated_event(clientEventRatedByUserRequest);
        call.enqueue(new Callback<ServeRatedByUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Response<ServeRatedByUserResponse> response) {
                if (response.isSuccessful()){
                    serveEventRatedByUserResponse = response.body();
                    assert serveEventRatedByUserResponse != null;
                    if (serveEventRatedByUserResponse.isOk()) {
                        Toast.makeText(EventDetailMoreActivity, R.string.youHaveGiven + serveEventRatedByUserResponse.getStars() + R.string.starsToThisEvent, Toast.LENGTH_SHORT).show();
                        EventDetailMoreActivity.killFragment();
                        EventDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(EventDetailMoreActivity, R.string.youAlreadyRatedEvent + serveEventRatedByUserResponse.getStars() + R.string.stars, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(EventDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
