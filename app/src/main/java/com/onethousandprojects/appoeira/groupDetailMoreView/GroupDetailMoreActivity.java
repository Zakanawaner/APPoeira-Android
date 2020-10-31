package com.onethousandprojects.appoeira.groupDetailMoreView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.BeenHereRatingGroup;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.JoinGroupFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.fragments.NewCommentFragment;
import com.onethousandprojects.appoeira.groupDetailMoreView.adapter.MyGroupMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.serverStuff.methods.GroupDetailMoreServer;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class GroupDetailMoreActivity extends AppCompatActivity implements MyGroupMembersRecyclerViewAdapter.OnGroupDetailListener,
                                                                          MyGroupCommentsRecyclerViewAdapter.OnGroupDetailListener {
    public GroupDetailMoreServer groupDetailMoreServer = new GroupDetailMoreServer();
    public Bundle fromGroupDetailActivity;
    public FloatingActionButton fbtnAdd;
    ImageView ivGroupStar1;
    ImageView ivGroupStar2;
    ImageView ivGroupStar3;
    ImageView ivGroupStar4;
    ImageView ivGroupStar5;
    TextView tvNumberComments;
    TextView tvGroupDetailVerification;
    public TextView tvNumberCommentsNum;
    public TextView tvJoinGroup;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    public int NUM_PAGES = 0;
    public int NUM_MEMBERS = 0;
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
                String numComments = "(" + NUM_MEMBERS + ")";
                tvNumberCommentsNum.setText(numComments);
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfMembers);
                tvJoinGroup.setText(R.string.groupDetailMoreCommentGroup);
                groupDetails.setVisibility(View.GONE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                String numComments = "(" + NUM_PAGES + ")";
                tvNumberCommentsNum.setText(numComments);
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
                if (fromGroupDetailActivity.getBoolean("member")) {
                    tvJoinGroup.setText(R.string.groupDetailMoreLeaveGroup);
                } else {
                    tvJoinGroup.setText(R.string.groupDetailMoreJoinGroup);
                }
                groupDetails.setVisibility(View.VISIBLE);
                groupComments.setVisibility(View.GONE);
            }
            killFragment();
        }
    };
    private View.OnClickListener joinGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            if (onComments) {
                getSupportFragmentManager().beginTransaction().add(R.id.newCommentFragment, new NewCommentFragment(), "NewCommentFragment").commit();
                groupComments.setVisibility(View.GONE);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("groupImage", fromGroupDetailActivity.getString("image"));
                bundle.putBoolean("member", fromGroupDetailActivity.getBoolean("member"));
                bundle.putBoolean("isOwner", fromGroupDetailActivity.getBoolean("isOwner"));
                JoinGroupFragment joinGroupFragment = new JoinGroupFragment();
                joinGroupFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.joinGroupFragment, joinGroupFragment, "JoinGroupFragment").commit();
                groupDetails.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener groupDetailVerification = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            BeenHereRatingGroup beenHereRatingGroup = new BeenHereRatingGroup();
            getSupportFragmentManager().beginTransaction().add(R.id.beenHereFragment, beenHereRatingGroup, "BeenHereFragment").commit();
            groupDetails.setVisibility(View.GONE);
            groupComments.setVisibility(View.GONE);
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail_more);
        fromGroupDetailActivity = getIntent().getExtras();

        TextView tvGroupName = findViewById(R.id.groupDetailMoreName);
        ImageView ivGroupAvatar = findViewById(R.id.groupDetailMoreAvatar);
        TextView tvVotes = findViewById(R.id.groupDetailMoreVotes);
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinGroup = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);
        tvGroupDetailVerification = findViewById(R.id.groupDetailVerification);
        TextView tvGroupAbout = findViewById(R.id.groupDetailAbout);
        LinearLayout llGroupRating = findViewById(R.id.groupDetailMoreRating);
        fbtnAdd = findViewById(R.id.addButton);

        ivGroupStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivGroupStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivGroupStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivGroupStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivGroupStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.GONE);
        if (!Objects.equals(fromGroupDetailActivity.getString("image"), "")) {
            Picasso.with(this).load(fromGroupDetailActivity.getString("image")).fit().into(ivGroupAvatar);
        }
        tvGroupName.setText(fromGroupDetailActivity.getString("name"));
        tvGroupAbout.setText(fromGroupDetailActivity.getString("about"));
        tvNumberComments.setOnClickListener(showCommentsListener);
        if (fromGroupDetailActivity.getBoolean("member")) {
            tvJoinGroup.setText(R.string.groupDetailMoreLeaveGroup);
        } else {
            tvJoinGroup.setText(R.string.groupDetailMoreJoinGroup);
        }
        tvJoinGroup.setOnClickListener(joinGroupListener);
        tvGroupDetailVerification.setOnClickListener(groupDetailVerification);

        if (fromGroupDetailActivity.getBoolean("verified")) {
            List<ImageView> stars = CommonMethods.SetStars(fromGroupDetailActivity.getDouble("rating"), ivGroupStar1, ivGroupStar2, ivGroupStar3, ivGroupStar4, ivGroupStar5);
            ivGroupStar1 = stars.get(0);
            ivGroupStar2 = stars.get(1);
            ivGroupStar3 = stars.get(2);
            ivGroupStar4 = stars.get(3);
            ivGroupStar5 = stars.get(4);
            String votes = "(" + fromGroupDetailActivity.getInt("votes") + ")";
            tvVotes.setText(votes);
        } else {
            llGroupRating.setVisibility(View.GONE);
        }
        if (fromGroupDetailActivity.getInt("voted") > 0) {
            tvGroupDetailVerification.setVisibility(View.GONE);
        }
        if (fromGroupDetailActivity.getString("about")==null) {
            tvGroupAbout.setVisibility(View.GONE);
        }

        groupDetailMoreServer.getGroupDetailMore(GroupDetailMoreActivity.this, fromGroupDetailActivity.getInt("groupId"));
        groupDetailMoreServer.serverGroupCommentsResponse(GroupDetailMoreActivity.this, fromGroupDetailActivity.getInt("groupId"));

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_group_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(GroupDetailMoreActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);
    }
    @Override
    public void OnGroupDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("id", userId);
        startActivity(toUserDetailActivity);
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
        if (getSupportFragmentManager().findFragmentByTag("BeenHereFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("BeenHereFragment"))).commit();
        }
        if (!onComments) {
            groupDetails.setVisibility(View.VISIBLE);
        } else {
            groupComments.setVisibility(View.VISIBLE);
        }
    }
}