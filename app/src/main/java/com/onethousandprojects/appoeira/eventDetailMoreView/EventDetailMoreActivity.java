package com.onethousandprojects.appoeira.eventDetailMoreView;

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
import com.onethousandprojects.appoeira.eventDetailMoreView.adapter.MyEventCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventDetailMoreView.adapter.MyEventMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.BeenHereRatingEvent;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.JoinEventFragment;
import com.onethousandprojects.appoeira.eventDetailMoreView.fragments.NewCommentFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.EventDetailMoreServer;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class EventDetailMoreActivity extends AppCompatActivity implements MyEventMembersRecyclerViewAdapter.OnEventDetailListener,
                                                                          MyEventCommentsRecyclerViewAdapter.OnEventDetailListener{

    public FloatingActionButton fbtnAdd;
    public EventDetailMoreServer eventDetailMoreServer = new EventDetailMoreServer();
    public Bundle fromEventDetailActivity;
    ImageView ivEventStar1;
    ImageView ivEventStar2;
    ImageView ivEventStar3;
    ImageView ivEventStar4;
    ImageView ivEventStar5;
    TextView tvNumberComments;
    TextView tvEventDetailVerification;
    public TextView tvNumberCommentsNum;
    public TextView tvJoinEvent;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    public int NUM_PAGES = 0;
    public int NUM_MEMBERS = 0;
    static boolean onComments = false;
    String origin = "EventDetailMoreActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            null,
            null,
            null,
            null,
            EventDetailMoreActivity.this,
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
                tvJoinEvent.setText(R.string.groupDetailMoreCommentGroup);
                groupDetails.setVisibility(View.GONE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                String numComments = "(" + NUM_PAGES + ")";
                tvNumberCommentsNum.setText(numComments);
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
                if (fromEventDetailActivity.getBoolean("member")) {
                    tvJoinEvent.setText(R.string.groupDetailMoreLeaveEvent);
                } else {
                    tvJoinEvent.setText(R.string.groupDetailMoreJoinEvent);
                }
                groupDetails.setVisibility(View.VISIBLE);
                groupComments.setVisibility(View.GONE);
            }
            killFragment();
        }
    };
    private View.OnClickListener joinEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            if (onComments) {
                getSupportFragmentManager().beginTransaction().add(R.id.newCommentFragment, new NewCommentFragment(), "NewCommentFragment").commit();
                groupComments.setVisibility(View.GONE);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("groupImage", fromEventDetailActivity.getString("image"));
                bundle.putBoolean("member", fromEventDetailActivity.getBoolean("member"));
                bundle.putBoolean("isOwner", fromEventDetailActivity.getBoolean("isOwner"));
                JoinEventFragment joinEventFragment = new JoinEventFragment();
                joinEventFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.joinGroupFragment, joinEventFragment, "JoinEventFragment").commit();
                groupDetails.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener groupDetailVerification = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            BeenHereRatingEvent beenHereRatingEvent = new BeenHereRatingEvent();
            getSupportFragmentManager().beginTransaction().add(R.id.beenHereFragment, beenHereRatingEvent, "BeenHereFragment").commit();
            groupDetails.setVisibility(View.GONE);
            groupComments.setVisibility(View.GONE);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail_more);
        fromEventDetailActivity = getIntent().getExtras();
        TextView tvEventName = findViewById(R.id.groupDetailMoreName);
        ImageView ivEventAvatar = findViewById(R.id.groupDetailMoreAvatar);
        TextView tvVotes = findViewById(R.id.groupDetailMoreVotes);
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinEvent = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);
        tvEventDetailVerification = findViewById(R.id.groupDetailVerification);
        TextView tvEventAbout = findViewById(R.id.groupDetailAbout);
        TextView tvEventDate = findViewById(R.id.groupDetailDate);
        LinearLayout llEventRating = findViewById(R.id.groupDetailMoreRating);
        TextView tvFrontLineTitle = findViewById(R.id.groupDetailMoreFronline);
        TextView tvStudentsTitle = findViewById(R.id.groupDetailMoreStudents);
        TextView tvFriendsTitle = findViewById(R.id.groupDetailMoreFriends);
        RelativeLayout rlFriendsFragment = findViewById(R.id.groupDetailFriendsLayout);
        fbtnAdd = findViewById(R.id.addButton);

        ivEventStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivEventStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivEventStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivEventStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivEventStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.GONE);
        if (!Objects.equals(fromEventDetailActivity.getString("image"), "")) {
            Picasso.with(this).load(fromEventDetailActivity.getString("image")).fit().into(ivEventAvatar);
        }
        tvEventName.setText(fromEventDetailActivity.getString("name"));
        tvEventAbout.setText(fromEventDetailActivity.getString("description"));
        tvFriendsTitle.setVisibility(View.GONE);
        rlFriendsFragment.setVisibility(View.GONE);
        tvFrontLineTitle.setText(R.string.convided);
        tvStudentsTitle.setText(R.string.participants);
        tvNumberComments.setOnClickListener(showCommentsListener);
        if (fromEventDetailActivity.getBoolean("member")) {
            tvJoinEvent.setText(R.string.groupDetailMoreLeaveEvent);
        } else {
            tvJoinEvent.setText(R.string.groupDetailMoreJoinEvent);
        }
        tvJoinEvent.setOnClickListener(joinEventListener);
        tvEventDetailVerification.setOnClickListener(groupDetailVerification);

        if (fromEventDetailActivity.getBoolean("verified")) {
            List<ImageView> stars = CommonMethods.SetStars(fromEventDetailActivity.getDouble("rating"), ivEventStar1, ivEventStar2, ivEventStar3, ivEventStar4, ivEventStar5);
            ivEventStar1 = stars.get(0);
            ivEventStar2 = stars.get(1);
            ivEventStar3 = stars.get(2);
            ivEventStar4 = stars.get(3);
            ivEventStar5 = stars.get(4);
            String votes = "(" + fromEventDetailActivity.getInt("votes") + ")";
            tvVotes.setText(votes);
        } else {
            llEventRating.setVisibility(View.GONE);
        }
        if (fromEventDetailActivity.getInt("voted") > 0) {
            tvEventDetailVerification.setVisibility(View.GONE);
        }
        if (fromEventDetailActivity.getString("description")==null) {
            tvEventAbout.setVisibility(View.GONE);
        } else {
            tvEventAbout.setVisibility(View.VISIBLE);
            tvEventAbout.setText(fromEventDetailActivity.getString("description"));
        }
        if (fromEventDetailActivity.getString("date")==null) {
            tvEventDate.setVisibility(View.GONE);
        } else {
            tvEventDate.setVisibility(View.VISIBLE);
            tvEventDate.setText(fromEventDetailActivity.getString("date"));
        }

        eventDetailMoreServer.getEventDetailMore(EventDetailMoreActivity.this, fromEventDetailActivity.getInt("eventId"));
        eventDetailMoreServer.serverEventCommentsResponse(EventDetailMoreActivity.this, fromEventDetailActivity.getInt("eventId"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_event_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(EventDetailMoreActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }
    }
    @Override
    public void OnEventDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("id", userId);
        startActivity(toUserDetailActivity);
    }
    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
        //this.recreate();
    }
    public void killFragment() {
        if (getSupportFragmentManager().findFragmentByTag("JoinEventFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("JoinEventFragment"))).commit();
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