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
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ClientGroupRatedByUserRequest;
import com.onethousandprojects.appoeira.serverStuff.groupRatedByUser.ServeGroupRatedByUserResponse;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ClientGroupJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ServerGroupJoinResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailMoreServer {
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private List<ServerGroupDetailMoreResponse> serverGroupDetailMoreResponse;
    private List<ServerGroupCommentsResponse> serverGroupCommentsResponse;
    private ServeGroupRatedByUserResponse serveGroupRatedByUserResponse;

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
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new GroupMemberOwnersFragment(), "GroupMemberOwnersFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new GroupMemberFrontlineFragment(), "GroupMemberFrontlineFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailStudentsLayout, new GroupMemberStudentsFragment(), "GroupMemberStudentsFragment").commit();
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFriendsLayout, new GroupMemberFriendsFragment(), "GroupMemberFriendsFragment").commit();
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
        ClientGroupCommentsRequest clientGroupCommentsRequest = new ClientGroupCommentsRequest(groupId);
        Call<List<ServerGroupCommentsResponse>> call1 = Server.post_group_comments(clientGroupCommentsRequest);
        call1.enqueue(new Callback<List<ServerGroupCommentsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerGroupCommentsResponse>> callComments, @NonNull Response<List<ServerGroupCommentsResponse>> response) {
                if (response.isSuccessful()){
                    serverGroupCommentsResponse = response.body();
                    assert serverGroupCommentsResponse != null;
                    GroupDetailMoreActivity.NUM_PAGES = serverGroupCommentsResponse.size();
                    GroupDetailMoreActivity.tvNumberCommentsNum.setText("(" + GroupDetailMoreActivity.NUM_PAGES + ")");
                    GroupDetailMoreActivity.getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "GroupCommentsFragment").commit();
                } else {
                    Toast.makeText(GroupDetailMoreActivity, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ServerGroupCommentsResponse>> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<ServerGroupCommentsResponse> getMyResponseComments() {
        return serverGroupCommentsResponse;
    }
    public void postComment(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, String comment) {
        ClientGroupNewCommentRequest clientGroupNewCommentRequest = new ClientGroupNewCommentRequest(group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerGroupNewCommentResponse> call = Server.post_new_comment(clientGroupNewCommentRequest);
        call.enqueue(new Callback<ServerGroupNewCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerGroupNewCommentResponse> callComments, @NonNull Response<ServerGroupNewCommentResponse> response) {
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
            public void onFailure(@NonNull Call<ServerGroupNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinGroup(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, Integer roleId) {
        ClientGroupJoinRequest clientGroupJoinRequest = new ClientGroupJoinRequest(group_id, SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerGroupJoinResponse> call = Server.post_join_group(clientGroupJoinRequest);
        call.enqueue(new Callback<ServerGroupJoinResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerGroupJoinResponse> callComments, @NonNull Response<ServerGroupJoinResponse> response) {
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
            public void onFailure(@NonNull Call<ServerGroupJoinResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void sendRating(GroupDetailMoreActivity GroupDetailMoreActivity, Integer group_id, Integer stars) {
        ClientGroupRatedByUserRequest clientGroupRatedByUserRequest = new ClientGroupRatedByUserRequest(SharedPreferencesManager.getIntegerValue(Constants.ID), group_id, stars);
        Call<ServeGroupRatedByUserResponse> call = Server.post_user_rated_group(clientGroupRatedByUserRequest);
        call.enqueue(new Callback<ServeGroupRatedByUserResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServeGroupRatedByUserResponse> call, @NonNull Response<ServeGroupRatedByUserResponse> response) {
                if (response.isSuccessful()){
                    serveGroupRatedByUserResponse = response.body();
                    assert serveGroupRatedByUserResponse != null;
                    if (serveGroupRatedByUserResponse.isOk()) {
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
            public void onFailure(@NonNull Call<ServeGroupRatedByUserResponse> call, @NonNull Throwable t) {
                Toast.makeText(GroupDetailMoreActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
