package com.onethousandprojects.appoeira.serverStuff.methods;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.CommentFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberFriendsFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberFrontlineFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberOwnersFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberStudentsFragment;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ClientNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.comments.ServerNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ClientRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.ratedByUser.ServeRatedByUserResponse;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ClientJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinObject.ServerJoinResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailMoreServer {
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private List<ServerGroupDetailMoreResponse> serverGroupDetailMoreResponse;
    private List<ServerCommentsResponse> serverCommentsResponse;
    private ServeRatedByUserResponse serveRatedByUserResponse;

    public GroupDetailMoreServer() {
        super();
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void getGroupDetailMore(GroupDetailMoreActivity GroupDetailMoreActivity, Integer groupId) {
        ClientGroupDetailMoreRequest clientGroupDetailMoreRequest = new ClientGroupDetailMoreRequest(groupId);
        Call<List<ServerGroupDetailMoreResponse>> call = Server.post_group_detail_more(clientGroupDetailMoreRequest);
        call.enqueue(new Callback<List<ServerGroupDetailMoreResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerGroupDetailMoreResponse>> call, @NonNull Response<List<ServerGroupDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    serverGroupDetailMoreResponse = response.body();
                    assert serverGroupDetailMoreResponse != null;
                    GroupDetailMoreActivity.NUM_MEMBERS = serverGroupDetailMoreResponse.size();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new GroupMemberOwnersFragment(), "RodaMemberOwnersFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new GroupMemberFrontlineFragment(), "GroupMemberFrontlineFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailStudentsLayout, new GroupMemberStudentsFragment(), "GroupMemberStudentsFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFriendsLayout, new GroupMemberFriendsFragment(), "RodaMemberMembersFragment").commit();
                } else {
                    Toast.makeText(GroupDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerGroupDetailMoreResponse>> call, @NonNull Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerGroupDetailMoreResponse> getMyResponse() {
        return serverGroupDetailMoreResponse;
    }
    public void serverGroupCommentsResponse(GroupDetailMoreActivity GroupDetailMoreActivity, Integer groupId) {
        ClientCommentsRequest clientGroupCommentsRequest = new ClientCommentsRequest(groupId);
        Call<List<ServerCommentsResponse>> call1 = Server.post_group_comments(clientGroupCommentsRequest);
        call1.enqueue(new Callback<List<ServerCommentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerCommentsResponse>> callComments, @NonNull Response<List<ServerCommentsResponse>> response) {
                if (response.isSuccessful()){
                    serverCommentsResponse = response.body();
                    assert serverCommentsResponse != null;
                    GroupDetailMoreActivity.NUM_PAGES = serverCommentsResponse.size();
                    GroupDetailMoreActivity.tvNumberCommentsNum.setText("(" + GroupDetailMoreActivity.NUM_PAGES + ")");
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "GroupCommentsFragment").commit();
                } else {
                    Toast.makeText(GroupDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerCommentsResponse>> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerCommentsResponse> getMyResponseComments() {
        return serverCommentsResponse;
    }
    public void postComment(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, String comment) {
        ClientNewCommentRequest clientNewCommentRequest = new ClientNewCommentRequest(group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerNewCommentResponse> call = Server.post_new_comment_group(clientNewCommentRequest);
        call.enqueue(new Callback<ServerNewCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerNewCommentResponse> callComments, @NonNull Response<ServerNewCommentResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(GroupDetailMoreActivity, "TU comentario se envió correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GroupDetailMoreActivity, "TU comentario no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinGroup(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, Integer roleId) {
        ClientJoinRequest clientJoinRequest = new ClientJoinRequest(group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerJoinResponse> call = Server.post_join_group(clientJoinRequest);
        call.enqueue(new Callback<ServerJoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerJoinResponse> callComments, @NonNull Response<ServerJoinResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().isOk()) {
                        Toast.makeText(GroupDetailMoreActivity, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GroupDetailMoreActivity, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerJoinResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendRating(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, Integer stars) {
        ClientRatedByUserRequest clientRatedByUserRequest = new ClientRatedByUserRequest(SharedPreferencesManager.getIntegerValue(Constants.ID), group_id, stars);
        Call<ServeRatedByUserResponse> call = Server.post_user_rated_group(clientRatedByUserRequest);
        call.enqueue(new Callback<ServeRatedByUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Response<ServeRatedByUserResponse> response) {
                if (response.isSuccessful()){
                    serveRatedByUserResponse = response.body();
                    assert serveRatedByUserResponse != null;
                    if (serveRatedByUserResponse.isOk()) {
                        Toast.makeText(GroupDetailMoreActivity, "Has dado " + stars + " estrellas a este grupo", Toast.LENGTH_SHORT).show();
                        GroupDetailMoreActivity.killFragment();
                        GroupDetailMoreActivity.refreshActivity();
                    } else {
                        Toast.makeText(GroupDetailMoreActivity, "Ya valoraste este grupo con " + stars + " estrellas", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupDetailMoreActivity, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServeRatedByUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
