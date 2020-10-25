package com.onethousandprojects.appoeira.onlineDetailMoreView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineDetailMoreView.adapter.MyOnlineCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineDetailMoreView.adapter.MyOnlineMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.BeenHereRatingOnline;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.JoinOnlineFragment;
import com.onethousandprojects.appoeira.onlineDetailMoreView.fragments.NewCommentFragment;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.methods.OnlineDetailMoreServer;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class OnlineDetailMoreActivity extends AppCompatActivity implements MyOnlineMembersRecyclerViewAdapter.OnOnlineDetailListener,
                                                                           MyOnlineCommentsRecyclerViewAdapter.OnOnlineDetailListener{
    public FloatingActionButton fbtnAdd;
    public OnlineDetailMoreServer  onlineDetailMoreServer = new OnlineDetailMoreServer();
    public Bundle fromOnlineDetailActivity;
    ImageView ivOnlineStar1;
    ImageView ivOnlineStar2;
    ImageView ivOnlineStar3;
    ImageView ivOnlineStar4;
    ImageView ivOnlineStar5;
    TextView tvNumberComments;
    TextView tvOnlineDetailVerification;
    public TextView tvNumberCommentsNum;
    public TextView tvJoinOnline;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    public int NUM_PAGES = 0;
    public int NUM_MEMBERS = 0;
    private PagerAdapter pagerAdapter;
    static boolean onComments = false;
    String origin = "OnlineDetailMoreActivity";
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
            OnlineDetailMoreActivity.this,
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
                tvJoinOnline.setText(R.string.groupDetailMoreCommentGroup);
                groupDetails.setVisibility(View.GONE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                String numComments = "(" + NUM_PAGES + ")";
                tvNumberCommentsNum.setText(numComments);
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
                if (fromOnlineDetailActivity.getBoolean("member")) {
                    tvJoinOnline.setText(R.string.groupDetailMoreLeaveOnline);
                } else {
                    tvJoinOnline.setText(R.string.groupDetailMoreJoinOnline);
                }
                groupDetails.setVisibility(View.VISIBLE);
                groupComments.setVisibility(View.GONE);
            }
            killFragment();
        }
    };
    private View.OnClickListener joinOnlineListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            if (onComments) {
                getSupportFragmentManager().beginTransaction().add(R.id.newCommentFragment, new NewCommentFragment(), "NewCommentFragment").commit();
                groupComments.setVisibility(View.GONE);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("groupImage", fromOnlineDetailActivity.getString("image"));
                bundle.putBoolean("member", fromOnlineDetailActivity.getBoolean("member"));
                bundle.putBoolean("isOwner", fromOnlineDetailActivity.getBoolean("isOwner"));
                JoinOnlineFragment joinOnlineFragment = new JoinOnlineFragment();
                joinOnlineFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.joinGroupFragment, joinOnlineFragment, "JoinOnlineFragment").commit();
                groupDetails.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener groupDetailVerification = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            BeenHereRatingOnline beenHereRatingOnline = new BeenHereRatingOnline();
            getSupportFragmentManager().beginTransaction().add(R.id.beenHereFragment, beenHereRatingOnline, "BeenHereFragment").commit();
            groupDetails.setVisibility(View.GONE);
            groupComments.setVisibility(View.GONE);
        }
    };
    public View.OnClickListener groupModificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toOnlineModification = new Intent(OnlineDetailMoreActivity.this, OnlineModificationActivity.class);
            toOnlineModification.putExtra("details", fromOnlineDetailActivity);
            toOnlineModification.putExtra("users", (Parcelable) onlineDetailMoreServer.getMyResponse());
            startActivity(toOnlineModification);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_detail_more);
        fromOnlineDetailActivity = getIntent().getExtras();

        TextView tvOnlineName = findViewById(R.id.groupDetailMoreName);
        ImageView ivOnlineAvatar = findViewById(R.id.groupDetailMoreAvatar);
        TextView tvVotes = findViewById(R.id.groupDetailMoreVotes);
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinOnline = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);
        tvOnlineDetailVerification = findViewById(R.id.groupDetailVerification);
        TextView tvOnlineAbout = findViewById(R.id.groupDetailAbout);
        TextView tvOnlineDate = findViewById(R.id.groupDetailDate);
        LinearLayout llOnlineRating = findViewById(R.id.groupDetailMoreRating);
        TextView tvFrontLineTitle = findViewById(R.id.groupDetailMoreFronline);
        TextView tvStudentsTitle = findViewById(R.id.groupDetailMoreStudents);
        RelativeLayout rlStudentsFragment = findViewById(R.id.groupDetailStudentsLayout);
        TextView tvFriendsTitle = findViewById(R.id.groupDetailMoreFriends);
        RelativeLayout rlFriendsFragment = findViewById(R.id.groupDetailFriendsLayout);
        fbtnAdd = findViewById(R.id.addButton);

        ivOnlineStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivOnlineStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivOnlineStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivOnlineStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivOnlineStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.GONE);
        if (!fromOnlineDetailActivity.getString("image").equals("")) {
            Picasso.with(this).load(fromOnlineDetailActivity.getString("image")).fit().into(ivOnlineAvatar);
        }
        tvOnlineName.setText(fromOnlineDetailActivity.getString("name"));
        String u = fromOnlineDetailActivity.getString("description");
        tvOnlineAbout.setText(fromOnlineDetailActivity.getString("description"));
        tvStudentsTitle.setVisibility(View.GONE);
        rlStudentsFragment.setVisibility(View.GONE);
        tvFriendsTitle.setVisibility(View.GONE);
        rlFriendsFragment.setVisibility(View.GONE);
        tvFrontLineTitle.setText(R.string.participants);
        tvNumberComments.setOnClickListener(showCommentsListener);
        if (fromOnlineDetailActivity.getBoolean("member")) {
            tvJoinOnline.setText(R.string.groupDetailMoreLeaveOnline);
        } else {
            tvJoinOnline.setText(R.string.groupDetailMoreJoinOnline);
        }
        tvJoinOnline.setOnClickListener(joinOnlineListener);
        tvOnlineDetailVerification.setOnClickListener(groupDetailVerification);

        if (fromOnlineDetailActivity.getBoolean("verified")) {
            List<ImageView> stars = CommonMethods.SetStars(fromOnlineDetailActivity.getDouble("rating"), ivOnlineStar1, ivOnlineStar2, ivOnlineStar3, ivOnlineStar4, ivOnlineStar5);
            ivOnlineStar1 = stars.get(0);
            ivOnlineStar2 = stars.get(1);
            ivOnlineStar3 = stars.get(2);
            ivOnlineStar4 = stars.get(3);
            ivOnlineStar5 = stars.get(4);
            String votes = "(" + fromOnlineDetailActivity.getInt("votes") + ")";
            tvVotes.setText(votes);
        } else {
            llOnlineRating.setVisibility(View.GONE);
        }
        if (fromOnlineDetailActivity.getInt("voted") > 0) {
            tvOnlineDetailVerification.setVisibility(View.GONE);
        }
        if (fromOnlineDetailActivity.getString("description")==null) {
            tvOnlineAbout.setVisibility(View.GONE);
        } else {
            tvOnlineAbout.setVisibility(View.VISIBLE);
            tvOnlineAbout.setText(fromOnlineDetailActivity.getString("description"));
        }
        if (fromOnlineDetailActivity.getString("date")==null) {
            tvOnlineDate.setVisibility(View.GONE);
        } else {
            tvOnlineDate.setVisibility(View.VISIBLE);
            tvOnlineDate.setText(fromOnlineDetailActivity.getString("date"));
        }

        onlineDetailMoreServer.getOnlineDetailMore(OnlineDetailMoreActivity.this, fromOnlineDetailActivity.getInt("onlineId"));
        onlineDetailMoreServer.serverOnlineCommentsResponse(OnlineDetailMoreActivity.this, fromOnlineDetailActivity.getInt("onlineId"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_online_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    @Override
    public void OnOnlineDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("onlineId", userId);
        startActivity(toUserDetailActivity);
    }
    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }
    public void killFragment() {
        if (getSupportFragmentManager().findFragmentByTag("JoinOnlineFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("JoinOnlineFragment"))).commit();
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