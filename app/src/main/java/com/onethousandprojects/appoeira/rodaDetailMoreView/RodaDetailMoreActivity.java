package com.onethousandprojects.appoeira.rodaDetailMoreView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.NewCommentFragment;
import com.onethousandprojects.appoeira.rodaDetailMoreView.adapter.MyRodaCommentsRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaDetailMoreView.adapter.MyRodaMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.BeenHereRatingRoda;
import com.onethousandprojects.appoeira.rodaDetailMoreView.fragments.JoinRodaFragment;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.methods.RodaDetailMoreServer;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class RodaDetailMoreActivity extends AppCompatActivity implements MyRodaMembersRecyclerViewAdapter.OnRodaDetailListener,
                                                                         MyRodaCommentsRecyclerViewAdapter.OnRodaDetailListener{
    public RodaDetailMoreServer rodaDetailMoreServer = new RodaDetailMoreServer();
    public Bundle fromRodaDetailActivity;
    public FloatingActionButton fbtnAdd;
    ImageView ivRodaStar1;
    ImageView ivRodaStar2;
    ImageView ivRodaStar3;
    ImageView ivRodaStar4;
    ImageView ivRodaStar5;
    TextView tvNumberComments;
    TextView tvRodaDetailVerification;
    public TextView tvNumberCommentsNum;
    public TextView tvJoinRoda;
    ConstraintLayout groupDetails;
    ConstraintLayout groupComments;
    public int NUM_PAGES = 0;
    public int NUM_MEMBERS = 0;
    static boolean onComments = false;
    String origin = "RodaDetailMoreActivity";
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
            RodaDetailMoreActivity.this,
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
                tvJoinRoda.setText(R.string.groupDetailMoreCommentGroup);
                groupDetails.setVisibility(View.GONE);
                groupComments.setVisibility(View.VISIBLE);
            } else {
                onComments = false;
                String numComments = "(" + NUM_PAGES + ")";
                tvNumberCommentsNum.setText(numComments);
                tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);if (fromRodaDetailActivity.getBoolean("member")) {
                    tvJoinRoda.setText(R.string.groupDetailMoreLeaveRoda);
                } else {
                    tvJoinRoda.setText(R.string.groupDetailMoreJoinRoda);
                }
                groupDetails.setVisibility(View.VISIBLE);
                groupComments.setVisibility(View.GONE);
            }
            killFragment();
        }
    };
    private View.OnClickListener joinRodaListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            killFragment();
            if (onComments) {
                getSupportFragmentManager().beginTransaction().add(R.id.newCommentFragment, new NewCommentFragment(), "NewCommentFragment").commit();
                groupComments.setVisibility(View.GONE);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("groupImage", fromRodaDetailActivity.getString("image"));
                bundle.putBoolean("member", fromRodaDetailActivity.getBoolean("member"));
                bundle.putBoolean("isOwner", fromRodaDetailActivity.getBoolean("isOwner"));
                JoinRodaFragment joinRodaFragment = new JoinRodaFragment();
                joinRodaFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.joinGroupFragment, joinRodaFragment, "JoinRodaFragment").commit();
                groupDetails.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener groupDetailVerification = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BeenHereRatingRoda beenHereRatingRoda = new BeenHereRatingRoda();
            getSupportFragmentManager().beginTransaction().add(R.id.beenHereFragment, beenHereRatingRoda, "BeenHereFragment").commit();
            groupDetails.setVisibility(View.GONE);
            groupComments.setVisibility(View.GONE);
        }
    };
    public View.OnClickListener groupModificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toRodaModification = new Intent(RodaDetailMoreActivity.this, RodaModificationActivity.class);
            toRodaModification.putExtra("details", fromRodaDetailActivity);
            toRodaModification.putExtra("users", (Parcelable) rodaDetailMoreServer.getMyResponse());
            startActivity(toRodaModification);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roda_detail_more);
        fromRodaDetailActivity = getIntent().getExtras();

        TextView tvRodaName = findViewById(R.id.groupDetailMoreName);
        ImageView ivRodaAvatar = findViewById(R.id.groupDetailMoreAvatar);
        TextView tvVotes = findViewById(R.id.groupDetailMoreVotes);
        tvNumberComments = findViewById(R.id.groupDetailMoreNumberOfComments);
        tvNumberCommentsNum = findViewById(R.id.groupDetailMoreNumberOfCommentsNum);
        tvJoinRoda = findViewById(R.id.groupDetailMoreJoinGroup);
        groupDetails = findViewById(R.id.SecondConstraint);
        groupComments = findViewById(R.id.ThirdConstraint);
        tvRodaDetailVerification = findViewById(R.id.groupDetailVerification);
        TextView tvRodaAbout = findViewById(R.id.groupDetailAbout);
        TextView tvRodaDate = findViewById(R.id.groupDetailDate);
        LinearLayout llRodaRating = findViewById(R.id.groupDetailMoreRating);
        TextView tvFrontLineTitle = findViewById(R.id.groupDetailMoreFronline);
        TextView tvStudentsTitle = findViewById(R.id.groupDetailMoreStudents);
        RelativeLayout rlStudentsFragment = findViewById(R.id.groupDetailStudentsLayout);
        TextView tvFriendsTitle = findViewById(R.id.groupDetailMoreFriends);
        RelativeLayout rlFriendsFragment = findViewById(R.id.groupDetailFriendsLayout);
        fbtnAdd = findViewById(R.id.addButton);

        ivRodaStar1 = findViewById(R.id.groupDetailMoreStar1);
        ivRodaStar2 = findViewById(R.id.groupDetailMoreStar2);
        ivRodaStar3 = findViewById(R.id.groupDetailMoreStar3);
        ivRodaStar4 = findViewById(R.id.groupDetailMoreStar4);
        ivRodaStar5 = findViewById(R.id.groupDetailMoreStar5);

        groupComments.setVisibility(View.GONE);
        if (!fromRodaDetailActivity.getString("image").equals("")) {
            Picasso.with(this).load(fromRodaDetailActivity.getString("image")).fit().into(ivRodaAvatar);
        }
        tvRodaName.setText(fromRodaDetailActivity.getString("name"));
        tvStudentsTitle.setVisibility(View.GONE);
        rlStudentsFragment.setVisibility(View.GONE);
        tvFriendsTitle.setVisibility(View.GONE);
        rlFriendsFragment.setVisibility(View.GONE);
        tvFrontLineTitle.setText(R.string.participants);
        tvNumberComments.setOnClickListener(showCommentsListener);
        tvNumberComments.setText(R.string.groupDetailMoreNumberOfComments);
        if (fromRodaDetailActivity.getBoolean("member")) {
            tvJoinRoda.setText(R.string.groupDetailMoreLeaveRoda);
        } else {
            tvJoinRoda.setText(R.string.groupDetailMoreJoinRoda);
        }
        tvJoinRoda.setOnClickListener(joinRodaListener);
        tvRodaDetailVerification.setOnClickListener(groupDetailVerification);

        if (fromRodaDetailActivity.getBoolean("verified")) {
            List<ImageView> stars = CommonMethods.SetStars(fromRodaDetailActivity.getDouble("rating"), ivRodaStar1, ivRodaStar2, ivRodaStar3, ivRodaStar4, ivRodaStar5);
            ivRodaStar1 = stars.get(0);
            ivRodaStar2 = stars.get(1);
            ivRodaStar3 = stars.get(2);
            ivRodaStar4 = stars.get(3);
            ivRodaStar5 = stars.get(4);
            String votes = "(" + fromRodaDetailActivity.getInt("votes") + ")";
            tvVotes.setText(votes);
        } else {
            llRodaRating.setVisibility(View.GONE);
        }
        if (fromRodaDetailActivity.getInt("voted") > 0) {
            tvRodaDetailVerification.setVisibility(View.GONE);
        }
        if (fromRodaDetailActivity.getString("description")==null) {
            tvRodaAbout.setVisibility(View.GONE);
        } else {
            tvRodaAbout.setVisibility(View.VISIBLE);
            tvRodaAbout.setText(fromRodaDetailActivity.getString("description"));
        }
        if (fromRodaDetailActivity.getString("date")==null) {
            tvRodaDate.setVisibility(View.GONE);
        } else {
            tvRodaDate.setVisibility(View.VISIBLE);
            tvRodaDate.setText(fromRodaDetailActivity.getString("date"));
        }

        rodaDetailMoreServer.getRodaDetailMore(RodaDetailMoreActivity.this, fromRodaDetailActivity.getInt("rodaId"));
        rodaDetailMoreServer.serverRodaCommentsResponse(RodaDetailMoreActivity.this, fromRodaDetailActivity.getInt("rodaId"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_roda_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    @Override
    public void OnRodaDetailClick(int userId) {
        Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
        toUserDetailActivity.putExtra("rodaId", userId);
        startActivity(toUserDetailActivity);
    }
    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }
    public void killFragment() {
        if (getSupportFragmentManager().findFragmentByTag("JoinRodaFragment") != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("JoinRodaFragment"))).commit();
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