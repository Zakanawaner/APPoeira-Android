package com.onethousandprojects.appoeira.groupDetailMoreView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberFriendsFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberOwnersFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberStudentsFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.JoinGroupFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.NewCommentFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.popUp.GroupBeenHereRatingPopUp;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.CommentFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.GroupMemberFrontlineFragment;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupCommentsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ClientGroupNewCommentRequest;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupCommentsResponse;
import com.onethousandprojects.appoeira.serverStuff.groupComments.ServerGroupNewCommentResponse;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ClientGroupDetailMoreRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetailMore.ServerGroupDetailMoreResponse;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ClientGroupJoinRequest;
import com.onethousandprojects.appoeira.serverStuff.joinGroup.ServerGroupJoinResponse;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailMoreActivity extends AppCompatActivity implements MyGroupMembersRecyclerViewAdapter.OnGroupDetailListener,
                                                                          MyGroupCommentsRecyclerViewAdapter.OnGroupDetailListener {
    Client Client;
    Server Server;
    private List<ServerGroupDetailMoreResponse> myResponse;
    private List<ServerGroupCommentsResponse> myResponseComments;
    Bundle fromGroupDetailActivity;
    ImageView ivGroupStar1;
    ImageView ivGroupStar2;
    ImageView ivGroupStar3;
    ImageView ivGroupStar4;
    ImageView ivGroupStar5;
    TextView tvNumberComments;
    TextView tvNumberCommentsNum;
    TextView tvJoinGroup;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    int NUM_PAGES = 0;
    int NUM_MEMBERS = 0;
    private PagerAdapter pagerAdapter;
    static boolean onComments = false;
    String origin = "GroupDetailMoreActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            GroupDetailMoreActivity.this,
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
            null,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);
    private View.OnClickListener showCommentsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!onComments) {
                onComments = true;
                tvNumberCommentsNum.setText("(" + NUM_MEMBERS + ")");
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfMembers);
                tvJoinGroup.setText(R.string.groupDetailMoreCommentGroup);
                groupDetails.setVisibility(View.INVISIBLE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                tvNumberCommentsNum.setText("(" + NUM_PAGES + ")");
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
                tvJoinGroup.setText(R.string.groupDetailMoreJoinGroup);
                groupDetails.setVisibility(View.VISIBLE);
                groupComments.setVisibility(View.INVISIBLE);
            }
            killFragment();
        }
    };
    private View.OnClickListener joinGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onComments) {
                getSupportFragmentManager().beginTransaction().add(R.id.newCommentFragment, new NewCommentFragment(), "NewCommentFragment").commit();
                groupComments.setVisibility(View.INVISIBLE);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("groupImage", fromGroupDetailActivity.getString("image"));
                JoinGroupFragment joinGroupFragment = new JoinGroupFragment();
                joinGroupFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.joinGroupFragment, joinGroupFragment, "JoinGroupFragment").commit();
                groupDetails.setVisibility(View.INVISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail_more);
        fromGroupDetailActivity = getIntent().getExtras();

        TextView tvGroupName = findViewById(R.id.groupDetailMoreName);
        ImageView ivGroupAvatar = findViewById(R.id.groupDetailMoreAvatar);
        TextView tvVotes = findViewById(R.id.groupDetailMoreVotes);
        TextView tvHaveYouBeenHere = findViewById(R.id.groupDetailVerification);
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinGroup = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);

        ivGroupStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivGroupStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivGroupStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivGroupStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivGroupStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.INVISIBLE);
        Picasso.with(this).load(fromGroupDetailActivity.getString("image")).fit().into(ivGroupAvatar);
        tvGroupName.setText(fromGroupDetailActivity.getString("name"));
        tvNumberComments.setOnClickListener(showCommentsListener);
        tvJoinGroup.setOnClickListener(joinGroupListener);
        if (fromGroupDetailActivity.getBoolean("verified")) {
            List<ImageView> stars = CommonMethods.SetStars(fromGroupDetailActivity.getDouble("rating"), ivGroupStar1, ivGroupStar2, ivGroupStar3, ivGroupStar4, ivGroupStar5);
            ivGroupStar1 = stars.get(0);
            ivGroupStar2 = stars.get(1);
            ivGroupStar3 = stars.get(2);
            ivGroupStar4 = stars.get(3);
            ivGroupStar5 = stars.get(4);
            tvVotes.setText("(" + fromGroupDetailActivity.getInt("votes") + ")");

        }
        if (fromGroupDetailActivity.getInt("voted") > 0) {
            tvHaveYouBeenHere.setVisibility(View.INVISIBLE);
        }

        retrofitinit();
        ClientGroupDetailMoreRequest clientGroupDetailMoreRequest = new ClientGroupDetailMoreRequest(fromGroupDetailActivity.getInt("id"));
        Call<List<ServerGroupDetailMoreResponse>> call = Server.post_group_detail_more(clientGroupDetailMoreRequest);
        call.enqueue(new Callback<List<ServerGroupDetailMoreResponse>>() {
            @Override
            public void onResponse(Call<List<ServerGroupDetailMoreResponse>> call, Response<List<ServerGroupDetailMoreResponse>> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    assert myResponse != null;
                    NUM_MEMBERS = myResponse.size();
                    getSupportFragmentManager().beginTransaction().add(R.id.groupDetailOwnerLayout, new GroupMemberOwnersFragment(), "GroupMemberOwnersFragment").commit();
                    getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFrontlineLayout, new GroupMemberFrontlineFragment(), "GroupMemberFrontlineFragment").commit();
                    getSupportFragmentManager().beginTransaction().add(R.id.groupDetailStudentsLayout, new GroupMemberStudentsFragment(), "GroupMemberStudentsFragment").commit();
                    getSupportFragmentManager().beginTransaction().add(R.id.groupDetailFriendsLayout, new GroupMemberFriendsFragment(), "GroupMemberFriendsFragment").commit();
                } else {
                    Toast.makeText(GroupDetailMoreActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ServerGroupDetailMoreResponse>> call, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ClientGroupCommentsRequest clientGroupCommentsRequest = new ClientGroupCommentsRequest(fromGroupDetailActivity.getInt("id"));
        Call<List<ServerGroupCommentsResponse>> call1 = Server.post_group_comments(clientGroupCommentsRequest);
        call1.enqueue(new Callback<List<ServerGroupCommentsResponse>>() {
            @Override
            public void onResponse(Call<List<ServerGroupCommentsResponse>> callComments, Response<List<ServerGroupCommentsResponse>> response) {
                if (response.isSuccessful()){
                    myResponseComments = response.body();
                    assert myResponseComments != null;
                    NUM_PAGES = myResponseComments.size();
                    tvNumberCommentsNum.setText("(" + NUM_PAGES + ")");
                    getSupportFragmentManager().beginTransaction().add(R.id.groupCommentsLayout, new CommentFragment(), "GroupCommentsFragment").commit();
               } else {
                    Toast.makeText(GroupDetailMoreActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<ServerGroupCommentsResponse>> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_group_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public List<ServerGroupDetailMoreResponse> getMyResponse() {
        return myResponse;
    }
    public List<ServerGroupCommentsResponse> getMyResponseComments() {
        return myResponseComments;
    }
    public void postComment(String comment) {
        ClientGroupNewCommentRequest clientGroupNewCommentRequest = new ClientGroupNewCommentRequest(fromGroupDetailActivity.getInt("id"), SharedPreferencesManager.getIntegerValue(Constants.ID), comment);
        Call<ServerGroupNewCommentResponse> call = Server.post_new_comment(clientGroupNewCommentRequest);
        call.enqueue(new Callback<ServerGroupNewCommentResponse>() {
            @Override
            public void onResponse(Call<ServerGroupNewCommentResponse> callComments, Response<ServerGroupNewCommentResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isOk()) {
                        Toast.makeText(GroupDetailMoreActivity.this, "TU comentario se envió correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GroupDetailMoreActivity.this, "TU comentario no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupDetailMoreActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ServerGroupNewCommentResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinGroup(Integer roleId) {
        ClientGroupJoinRequest clientGroupJoinRequest = new ClientGroupJoinRequest(fromGroupDetailActivity.getInt("id"), SharedPreferencesManager.getIntegerValue(Constants.ID), roleId);
        Call<ServerGroupJoinResponse> call = Server.post_join_group(clientGroupJoinRequest);
        call.enqueue(new Callback<ServerGroupJoinResponse>() {
            @Override
            public void onResponse(Call<ServerGroupJoinResponse> callComments, Response<ServerGroupJoinResponse> response) {
                if (response.isSuccessful()){
                    if (response.body().isOk()) {
                        Toast.makeText(GroupDetailMoreActivity.this, "Tu petición se envió correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GroupDetailMoreActivity.this, "Tu petición no pudo guardarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupDetailMoreActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerGroupJoinResponse> callComments, Throwable t) {
                Toast.makeText(GroupDetailMoreActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void OnGroupDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("id", userId);
        startActivity(toUserDetailActivity);
    }
    public void groupDetailVerification(View view) {
        String message = "Si conoces este grupo, confirma que has estado aquí. Esto nos hará la vida más fácil.";
        GroupBeenHereRatingPopUp popUpClass = new GroupBeenHereRatingPopUp();
        popUpClass.showPopupWindow(view, message, false, fromGroupDetailActivity.getInt("id"), SharedPreferencesManager.getIntegerValue(Constants.ID));
    }
    public static void groupDetailBeenHere(View view, Integer group_id, Integer user_id) {
        String message = "¿Te gustaría valorar tu experiencia?";
        GroupBeenHereRatingPopUp popUpClass = new GroupBeenHereRatingPopUp();
        popUpClass.showPopupWindow(view, message, true, group_id, user_id);
    }
    public static void groupDetailBeenHereStarsGiven(View view, Integer stars, boolean ok) {
        if (ok) {
            Toast.makeText(view.getContext(), "Has dado " + stars + " estrellas a este grupo", Toast.LENGTH_SHORT).show();
            GroupDetailMoreActivity groupDetailMoreActivity = new GroupDetailMoreActivity();
            groupDetailMoreActivity.refreshActivity();
        } else {
            Toast.makeText(view.getContext(), "Ya valoraste este grupo con " + stars + " estrellas", Toast.LENGTH_SHORT).show();
        }
    }
    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }
    public void killFragment() {
        if (getSupportFragmentManager().findFragmentByTag("JoinGroupFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("JoinGroupFragment"))).commit();
        }
        if (getSupportFragmentManager().findFragmentByTag("NewCommentFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("NewCommentFragment"))).commit();
        }
        if (!onComments) {
            groupDetails.setVisibility(View.VISIBLE);
        } else {
            groupComments.setVisibility(View.VISIBLE);
        }
    }
}