package com.onethousandprojects.appoeira.userDetailView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailView.EventDetailActivity;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.AuthClient;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.AuthServer;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ClientUserDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.userDetail.ServerUserDetailResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailEventResponse.ServerUserDetailEventCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.userDetail.userDetailFollowResponse.ServerUserDetailFollowResponse;
import com.onethousandprojects.appoeira.serverStuff.userFollowUser.ClientUserFollowUserRequest;
import com.onethousandprojects.appoeira.serverStuff.userFollowUser.ServerUserFollowUserResponse;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ClientUserUnFollowUserRequest;
import com.onethousandprojects.appoeira.serverStuff.userUnfollowUser.ServerUserUnFollowUserResponse;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserActivityRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserEventRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserFollowerRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserGroupRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserOnlineRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.adapter.UserRodaRecyclerViewAdapter;
import com.onethousandprojects.appoeira.userDetailView.content.UserActivityContent;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserActivityListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserEventListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserGroupListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserOnlineListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserRodaListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserFollowedListFragment;
import com.onethousandprojects.appoeira.userDetailView.fragments.UserFollowersListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity implements UserGroupRecyclerViewAdapter.OnGroupDetailListener,
                                                                     UserEventRecyclerViewAdapter.OnEventDetailListener,
                                                                     UserRodaRecyclerViewAdapter.OnRodaDetailListener,
                                                                     UserOnlineRecyclerViewAdapter.OnOnlineDetailListener,
                                                                     UserActivityRecyclerViewAdapter.OnActivityDetailListener,
                                                                     UserFollowerRecyclerViewAdapter.OnUserDetailListener {
    Bundle fromGroupDetailMoreActivity;
    AuthClient AuthClient;
    AuthServer AuthServer;
    Client Client;
    Server Server;
    private boolean onProfile = false;
    private boolean onActivity = false;
    private List<ServerUserDetailFollowResponse> followers;
    private List<ServerUserDetailFollowResponse> followed;
    private List<UserActivityContent> activity = new ArrayList<>();
    private List<ServerUserDetailEventCommentResponse> eventComments;
    private ServerUserDetailResponse myResponse;
    private boolean alreadyFollowing = false;
    private ImageView ivFollow;
    private TextView tvModifyProfile;
    private TextView tvViewActivity;
    private TextView tvNumberActivity;
    private TextView tvUserFollowers;
    private TextView tvUserFollowed;
    private UserActivityListFragment userActivityListFragment;
    private UserGroupListFragment userGroupListFragment;
    private UserRodaListFragment userRodaListFragment;
    private UserEventListFragment userEventListFragment;
    private UserOnlineListFragment userOnlineListFragment;
    private UserFollowersListFragment userfollowersListFragment;
    private UserFollowedListFragment userfollowedListFragment;
    private ConstraintLayout detailsConstraint;
    private ConstraintLayout activityConstraint;
    private ConstraintLayout followersConstraint;
    private ConstraintLayout followedConstraint;
    String origin = "UserDetailActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            UserDetailActivity.this,
            null,
            null,
            null,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);
    int NUM_ACTIVITIES = 0;
    int NUM_DETAILS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        fromGroupDetailMoreActivity = getIntent().getExtras();
        if (fromGroupDetailMoreActivity.getInt("id") == SharedPreferencesManager.getIntegerValue(Constants.ID)) {
            onProfile = true;
        }

        ImageView ivUserAvatar = findViewById(R.id.userDetailAvatar);
        ivFollow = findViewById(R.id.userDetailFollowButton);
        TextView tvUserRank = findViewById(R.id.userDetailRank);
        tvViewActivity = findViewById(R.id.tvViewActivity);
        tvModifyProfile = findViewById(R.id.tvModifyProfile);
        TextView tvUserApelhido = findViewById(R.id.userDetailApelhido);
        tvUserFollowers = findViewById(R.id.userDetailNumberOfFollowers);
        tvUserFollowed = findViewById(R.id.userDetailNumberOfFollowed);
        TextView tvUserJoined = findViewById(R.id.userDetailDateJoin);
        tvNumberActivity = findViewById(R.id.userDetailActivityNum);
        detailsConstraint = findViewById(R.id.SecondConstraint);
        activityConstraint = findViewById(R.id.ThirdConstraint);
        followersConstraint = findViewById(R.id.FourthConstraint);
        followedConstraint = findViewById(R.id.FifthConstraint);
        ImageView ivUserPremium = findViewById(R.id.userDetailPremium);
        TextView tvLogOut = findViewById(R.id.userLogOut);

        ivUserPremium.setImageResource(R.drawable.ic_premium);
        activityConstraint.setVisibility(View.GONE);
        followersConstraint.setVisibility(View.GONE);
        followedConstraint.setVisibility(View.GONE);

        authretrofitinit();
        retrofitinit();

        ClientUserDetailRequest clientUserDetailRequest = new ClientUserDetailRequest(fromGroupDetailMoreActivity.getInt("id"));
        Call<ServerUserDetailResponse> call = Server.post_user_detail(clientUserDetailRequest);
        call.enqueue(new Callback<ServerUserDetailResponse>() {
            @Override
            public void onResponse(Call<ServerUserDetailResponse> call, Response<ServerUserDetailResponse> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    Picasso.with(UserDetailActivity.this).load(myResponse.getPicUrl()).fit().into(ivUserAvatar);
                    tvUserRank.setText(String.valueOf(myResponse.getRank()));
                    tvUserApelhido.setText(String.valueOf(myResponse.getApelhido()));
                    if (myResponse.getFollowers().size() == 1 && myResponse.getFollowers().get(0).getUserId() == null) {
                        tvUserFollowers.setText(String.valueOf(0));
                    } else {
                        tvUserFollowers.setText(String.valueOf(myResponse.getFollowers().size()));
                        followers = myResponse.getFollowers();
                        for (int i = 0; i < followers.size(); i++) {
                            activity.add(new UserActivityContent(followers.get(i).getUserId(),
                                    String.valueOf(followers.get(i).getUserApelhido()),
                                    String.valueOf(followers.get(i).getUserPicUrl()),
                                    String.valueOf(followers.get(i).getUserDate()),
                                    "Fue seguido por:"));
                            if (followers.get(i).getUserId().equals(SharedPreferencesManager.getIntegerValue(Constants.ID))) {
                                alreadyFollowing = true;
                            }
                        }
                        userfollowersListFragment = new UserFollowersListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.followersList, userfollowersListFragment, "UserFollowerListFragment").commit();
                    }
                    if (myResponse.getFollowed().size() == 1 && myResponse.getFollowed().get(0).getUserId() == null) {
                        tvUserFollowed.setText(String.valueOf(0));
                    } else {
                        tvUserFollowed.setText(String.valueOf(myResponse.getFollowed().size()));
                        followed = myResponse.getFollowed();
                        for (int i = 0; i < followed.size(); i++) {
                            activity.add(new UserActivityContent(followed.get(i).getUserId(),
                                    String.valueOf(followed.get(i).getUserApelhido()),
                                    String.valueOf(followed.get(i).getUserPicUrl()),
                                    String.valueOf(followed.get(i).getUserDate()),
                                    "Siguió a:"));
                        }
                        userfollowedListFragment = new UserFollowedListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.followedList, userfollowedListFragment, "UserFollowedListFragment").commit();
                    }
                    manageFollowButtons();
                    tvUserJoined.setText(String.valueOf(myResponse.getJoined().substring(0, 10)));
                    if (myResponse.isPremium()) {
                        ivUserPremium.setVisibility(View.VISIBLE);
                    } else {
                        ivUserPremium.setVisibility(View.GONE);
                    }
                    if (!(myResponse.getGroups().size() == 1 && myResponse.getGroups().get(0).getGroupId() == null)) {
                        NUM_DETAILS = NUM_DETAILS + myResponse.getGroups().size();
                        userGroupListFragment = new UserGroupListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.userDetailGroupsLayout, userGroupListFragment, "UserGroupListFragment").commit();
                        for (int i = 0; i < myResponse.getGroups().size(); i++) {
                            activity.add(new UserActivityContent(myResponse.getGroups().get(i).getGroupId(),
                                    String.valueOf(myResponse.getGroups().get(i).getGroupName()),
                                    String.valueOf(myResponse.getGroups().get(i).getGroupPicUrl()),
                                    String.valueOf(myResponse.getGroups().get(i).getGroupDate()),
                                    "Se unió al grupo:"));
                        }
                    }
                    if (!(myResponse.getEvents().size() == 1 && myResponse.getEvents().get(0).getEventId() == null)) {
                        NUM_DETAILS = NUM_DETAILS + myResponse.getEvents().size();
                        userEventListFragment = new UserEventListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.userDetailEventsLayout, userEventListFragment, "UserEventListFragment").commit();
                        for (int i = 0; i < myResponse.getEvents().size(); i++) {
                            activity.add(new UserActivityContent(myResponse.getEvents().get(i).getEventId(),
                                    String.valueOf(myResponse.getEvents().get(i).getEventName()),
                                    String.valueOf(myResponse.getEvents().get(i).getEventPicUrl()),
                                    String.valueOf(myResponse.getEvents().get(i).getEventDate()),
                                    "Se unió al evento:"));
                        }
                    }
                    if (!(myResponse.getRodas().size() == 1 && myResponse.getRodas().get(0).getRodaId() == null)) {
                        NUM_DETAILS = NUM_DETAILS + myResponse.getRodas().size();
                        userRodaListFragment = new UserRodaListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.userDetailRodasLayout, userRodaListFragment, "UserRodaListFragment").commit();
                        for (int i = 0; i < myResponse.getRodas().size(); i++) {
                            activity.add(new UserActivityContent(myResponse.getRodas().get(i).getRodaId(),
                                    String.valueOf(myResponse.getRodas().get(i).getRodaName()),
                                    String.valueOf(myResponse.getRodas().get(i).getRodaPicUrl()),
                                    String.valueOf(myResponse.getRodas().get(i).getRodaDate()),
                                    "Se unió a la roda:"));
                        }

                    }
                    if (!(myResponse.getOnlines().size() == 1 && myResponse.getOnlines().get(0).getOnlineId() == null)) {
                        NUM_DETAILS = NUM_DETAILS + myResponse.getOnlines().size();
                        userOnlineListFragment = new UserOnlineListFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.userDetailOnlinesLayout, userOnlineListFragment, "UserOnlineListFragment").commit();
                        for (int i = 0; i < myResponse.getOnlines().size(); i++) {
                            activity.add(new UserActivityContent(myResponse.getOnlines().get(i).getOnlineId(),
                                    String.valueOf(myResponse.getOnlines().get(i).getOnlineName()),
                                    String.valueOf(myResponse.getOnlines().get(i).getOnlinePicUrl()),
                                    String.valueOf(myResponse.getOnlines().get(i).getOnlineDate()),
                                    "Se unió a la clase online:"));
                        }
                    }
                    if (!(activity.size() == 1 && activity.get(0).getId() == null)) {
                        NUM_ACTIVITIES = activity.size();
                    }
                    tvNumberActivity.setText("(" + NUM_ACTIVITIES + ")");
                    userActivityListFragment = new UserActivityListFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.activityList, userActivityListFragment, "UserActivityListFragment").commit();
                } else {
                    Toast.makeText(UserDetailActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerUserDetailResponse> call, Throwable t) {
                Toast.makeText(UserDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_user_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDetailActivity.this, LoginActivity.class);
                SharedPreferencesManager.clearValues();
                startActivity(intent);
                finish();
            }
        });
    }

    private void manageFollowButtons() {
        if (!onProfile) {
            tvModifyProfile.setVisibility(View.GONE);
            if (!alreadyFollowing) {
                ivFollow.setImageResource(R.drawable.ic_follow);
                ivFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClientUserFollowUserRequest clientUserFollowUserRequest = new ClientUserFollowUserRequest(SharedPreferencesManager.getIntegerValue(Constants.ID), fromGroupDetailMoreActivity.getInt("id"));
                        Call<ServerUserFollowUserResponse> call = Server.post_user_follow_user(clientUserFollowUserRequest);
                        call.enqueue(new Callback<ServerUserFollowUserResponse>() {
                            @Override
                            public void onResponse(Call<ServerUserFollowUserResponse> call, Response<ServerUserFollowUserResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().isOk()) {
                                        Toast.makeText(UserDetailActivity.this, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                                        refreshActivity();
                                    } else {
                                        Toast.makeText(UserDetailActivity.this, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(UserDetailActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ServerUserFollowUserResponse> call, Throwable t) {
                                Toast.makeText(UserDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } else {
                ivFollow.setImageResource(R.drawable.ic_unfollow);
                ivFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClientUserUnFollowUserRequest clientUserUnFollowUserRequest = new ClientUserUnFollowUserRequest(SharedPreferencesManager.getIntegerValue(Constants.ID), fromGroupDetailMoreActivity.getInt("id"));
                        Call<ServerUserUnFollowUserResponse> call = Server.post_user_unfollow_user(clientUserUnFollowUserRequest);
                        call.enqueue(new Callback<ServerUserUnFollowUserResponse>() {
                            @Override
                            public void onResponse(Call<ServerUserUnFollowUserResponse> call, Response<ServerUserUnFollowUserResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().isOk()) {
                                        Toast.makeText(UserDetailActivity.this, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                                        refreshActivity();
                                    } else {
                                        Toast.makeText(UserDetailActivity.this, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(UserDetailActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ServerUserUnFollowUserResponse> call, Throwable t) {
                                Toast.makeText(UserDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

        } else {
            ivFollow.setVisibility(View.GONE);
            tvModifyProfile.setVisibility(View.VISIBLE);
            tvModifyProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toModifyProfileActivity = new Intent(UserDetailActivity.this, ProfileModificationActivity.class);
                    toModifyProfileActivity.putExtra("rank", String.valueOf(myResponse.getRank()));
                    toModifyProfileActivity.putExtra("email", String.valueOf(myResponse.getEmail()));
                    toModifyProfileActivity.putExtra("school", String.valueOf(myResponse.getSchool()));
                    toModifyProfileActivity.putExtra("name", String.valueOf(myResponse.getName()));
                    toModifyProfileActivity.putExtra("lastName", String.valueOf(myResponse.getLastName()));
                    startActivity(toModifyProfileActivity);
                }
            });
        }
        tvViewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!onActivity) {
                    onActivity = true;
                    tvViewActivity.setText(R.string.ViewUserInfo);
                    tvNumberActivity.setText("(" + NUM_DETAILS + ")");
                    detailsConstraint.setVisibility(View.GONE);
                    activityConstraint.setVisibility(View.VISIBLE);
                    followersConstraint.setVisibility(View.GONE);
                    followedConstraint.setVisibility(View.GONE);
                } else {
                    onActivity = false;
                    tvViewActivity.setText(R.string.ViewUserActivity);
                    tvNumberActivity.setText("(" + NUM_ACTIVITIES + ")");
                    detailsConstraint.setVisibility(View.VISIBLE);
                    activityConstraint.setVisibility(View.GONE);
                    followersConstraint.setVisibility(View.GONE);
                    followedConstraint.setVisibility(View.GONE);
                }
            }
        });
        tvUserFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsConstraint.setVisibility(View.GONE);
                activityConstraint.setVisibility(View.GONE);
                followersConstraint.setVisibility(View.VISIBLE);
                followedConstraint.setVisibility(View.GONE);
            }
        });
        tvUserFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsConstraint.setVisibility(View.GONE);
                activityConstraint.setVisibility(View.GONE);
                followersConstraint.setVisibility(View.GONE);
                followedConstraint.setVisibility(View.VISIBLE);
            }
        });
    }

    private void authretrofitinit() {
        AuthClient = AuthClient.getInstance();
        AuthServer = AuthClient.getAuthServer();
    }

    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public ServerUserDetailResponse getMyResponse() {
        return myResponse;
    }

    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }
    @Override
    public void OnGroupDetailClick(int groupId) {
        Intent toGroupDetailActivity = new Intent(this, GroupDetailActivity.class);
        toGroupDetailActivity.putExtra("id", groupId);
        startActivity(toGroupDetailActivity);
    }
    @Override
    public void OnUserDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("id", userId);
        startActivity(toUserDetailActivity);
    }
    @Override
    public void OnEventDetailClick(int eventId) {
        Intent toEventDetailActivity = new Intent(this, EventDetailActivity.class);
        toEventDetailActivity.putExtra("id", eventId);
        startActivity(toEventDetailActivity);
    }
    @Override
    public void OnRodaDetailClick(int rodaId) {
        Intent toRodaDetailActivity = new Intent(this, RodaDetailActivity.class);
        toRodaDetailActivity.putExtra("id", rodaId);
        startActivity(toRodaDetailActivity);
    }
    @Override
    public void OnOnlineDetailClick(int onlineId) {
        Intent toOnlineDetailActivity = new Intent(this, OnlineDetailActivity.class);
        toOnlineDetailActivity.putExtra("id", onlineId);
        startActivity(toOnlineDetailActivity);
    }

    @Override
    public void OnActivityDetailClick(int position) {

    }

    public List<UserActivityContent> getActivity() {
        return activity;
    }
    public List<ServerUserDetailFollowResponse> getFollowers(){
        return followers;
    }
    public List<ServerUserDetailFollowResponse> getFollowed(){
        return followed;
    }
}