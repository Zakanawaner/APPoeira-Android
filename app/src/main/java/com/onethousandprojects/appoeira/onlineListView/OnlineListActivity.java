package com.onethousandprojects.appoeira.onlineListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineListView.fragments.VerifyEmailFragment;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineListView.adapter.MyOnlineListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.methods.OnlineListServer;
import com.onethousandprojects.appoeira.serverStuff.onlineList.ServerLocationOnlineResponse;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class OnlineListActivity extends AppCompatActivity implements MyOnlineListRecyclerViewAdapter.OnOnlineListener {

    public FloatingActionButton fbtnAdd, fbtnDistance;
    public OnlineListServer onlineListServer = new OnlineListServer();
    private VerifyEmailFragment verifyEmailFragment;
    public SwipeRefreshLayout srOnlineList;
    public TextView tvDistance;
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            onlineListServer.sendLocationToServer(OnlineListActivity.this);
        }
    };
    String origin = "OnlineListActivity";
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
            OnlineListActivity.this,
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_list);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.onlines_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_online_list);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(OnlineListActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }
        onlineListServer.sendLocationToServer(OnlineListActivity.this);

        fbtnDistance = findViewById(R.id.distanceButton);
        fbtnDistance.setVisibility(View.GONE);
        tvDistance = findViewById(R.id.tvDistanceInKm);
        tvDistance.setVisibility(View.GONE);
        fbtnAdd = findViewById(R.id.addButton);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonMethods.AmILogged()) {
                    if (CommonMethods.AmIVerified()) {
                        Intent toNewOnlineActivity = new Intent(OnlineListActivity.this, OnlineModificationActivity.class);
                        toNewOnlineActivity.putExtra("onlineId", 0);
                        toNewOnlineActivity.putExtra("modification", false);
                        startActivity(toNewOnlineActivity);
                    } else {
                        if (verifyEmailFragment==null) {
                            verifyEmailFragment = new VerifyEmailFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.veryfyEmailFragment, verifyEmailFragment, "VerifyEmailFragment").commit();
                        }
                    }
                } else {
                    Intent toLogin = new Intent(OnlineListActivity.this, LoginActivity.class);
                    startActivity(toLogin);
                }
            }
        });
        srOnlineList = findViewById(R.id.swipeRefreshList);
        srOnlineList.setOnRefreshListener(onRefreshListener);
    }
    @Override
    public void OnOnlineClick(int position) {
        if (CommonMethods.AmILogged()) {
            if (CommonMethods.AmIVerified()) {
                ServerLocationOnlineResponse online = onlineListServer.getServerLocationOnlineResponse().get(position);
                Intent toOnlineDetailActivity = new Intent(this, OnlineDetailActivity.class);
                toOnlineDetailActivity.putExtra("onlineId", online.getId());
                toOnlineDetailActivity.putExtra("date", online.getDate());
                toOnlineDetailActivity.putExtra("owner", online.getOwnerApelhido());
                toOnlineDetailActivity.putExtra("ownerRank", online.getOwnerRank());
                startActivity(toOnlineDetailActivity);
            } else {
                if (verifyEmailFragment==null) {
                    verifyEmailFragment = new VerifyEmailFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.veryfyEmailFragment, verifyEmailFragment, "VerifyEmailFragment").commit();
                }
            }
        } else {
            Intent toLogin = new Intent(this, LoginActivity.class);
            startActivity(toLogin);
        }
    }
    public void killVerifyFragment() {
        if (verifyEmailFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("VerifyEmailFragment"))).commit();
        }
        verifyEmailFragment = null;
    }
}