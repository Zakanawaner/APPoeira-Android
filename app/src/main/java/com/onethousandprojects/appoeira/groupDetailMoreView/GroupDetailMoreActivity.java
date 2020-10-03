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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    ImageView ivGroupStar1;
    ImageView ivGroupStar2;
    ImageView ivGroupStar3;
    ImageView ivGroupStar4;
    ImageView ivGroupStar5;
    TextView tvNumberComments;
    TextView tvGroupDetailVerification;
    public TextView tvNumberCommentsNum;
    TextView tvJoinGroup;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    public int NUM_PAGES = 0;
    public int NUM_MEMBERS = 0;
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
                groupDetails.setVisibility(View.GONE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                tvNumberCommentsNum.setText("(" + NUM_PAGES + ")");
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
                tvJoinGroup.setText(R.string.groupDetailMoreJoinGroup);
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
            BeenHereRatingGroup beenHereRatingGroup = new BeenHereRatingGroup();
            getSupportFragmentManager().beginTransaction().add(R.id.beenHereFragment, beenHereRatingGroup, "BeenHereFragment").commit();
            groupDetails.setVisibility(View.GONE);
            groupComments.setVisibility(View.GONE);
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
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinGroup = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);
        tvGroupDetailVerification = findViewById(R.id.groupDetailVerification);

        ivGroupStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivGroupStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivGroupStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivGroupStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivGroupStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.GONE);
        Picasso.with(this).load(fromGroupDetailActivity.getString("image")).fit().into(ivGroupAvatar);
        tvGroupName.setText(fromGroupDetailActivity.getString("name"));
        tvNumberComments.setOnClickListener(showCommentsListener);
        tvJoinGroup.setOnClickListener(joinGroupListener);
        tvGroupDetailVerification.setOnClickListener(groupDetailVerification);

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
            tvGroupDetailVerification.setVisibility(View.GONE);
        }

        groupDetailMoreServer.getGroupDetailMore(GroupDetailMoreActivity.this, fromGroupDetailActivity.getInt("id"));
        groupDetailMoreServer.serverGroupCommentsResponse(GroupDetailMoreActivity.this, fromGroupDetailActivity.getInt("id"));

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