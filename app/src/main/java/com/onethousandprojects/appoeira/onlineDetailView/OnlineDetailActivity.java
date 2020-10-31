package com.onethousandprojects.appoeira.onlineDetailView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineDetailMoreView.OnlineDetailMoreActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.onlineDetail.ClientOnlineDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.onlineDetail.ServerOnlineDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnlineDetailActivity extends AppCompatActivity {

    public FloatingActionButton fbtnAdd;
    public Bundle fromOnlineListActivity;
    ImageView ivOnlineStar1;
    ImageView ivOnlineStar2;
    ImageView ivOnlineStar3;
    ImageView ivOnlineStar4;
    ImageView ivOnlineStar5;
    ImageView ivPlatform;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private ServerOnlineDetailResponse myResponse;
    String origin = "OnlineDetailActivity";
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
            OnlineDetailActivity.this,
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
        setContentView(R.layout.activity_online_detail);
        fromOnlineListActivity = getIntent().getExtras();

        retrofitinit();

        TextView tvOnlineName = findViewById(R.id.detailName);
        TextView tvOnlineUrl = findViewById(R.id.detailUrlOrCreator);
        TextView tvOnlinePhone = findViewById(R.id.detailPhone);
        ImageView ivOnlineAvatar = findViewById(R.id.detailAvatar);
        ImageView ivVerifiedOnline = findViewById(R.id.detailVerified);
        TextView tvOnlineAddress = findViewById(R.id.detailAddress);
        TextView tvVotes = findViewById(R.id.detailVotes);
        TextView tvMore = findViewById(R.id.detailMore);
        View maps = findViewById(R.id.detailMap);
        ivPlatform = findViewById(R.id.detailPlatform);
        LinearLayout llDetailRating = findViewById(R.id.detailRating);
        fbtnAdd = findViewById(R.id.addButton);

        ivOnlineStar1 = findViewById(R.id.detailStar1);
        ivOnlineStar2 = findViewById(R.id.detailStar2);
        ivOnlineStar3 = findViewById(R.id.detailStar3);
        ivOnlineStar4 = findViewById(R.id.detailStar4);
        ivOnlineStar5 = findViewById(R.id.detailStar5);

        ClientOnlineDetailRequest clientOnlineDetailRequest = new ClientOnlineDetailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), fromOnlineListActivity.getInt("onlineId"), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerOnlineDetailResponse> call = Server.post_online_detail(clientOnlineDetailRequest);
        call.enqueue(new Callback<ServerOnlineDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerOnlineDetailResponse> call, @NonNull Response<ServerOnlineDetailResponse> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    assert myResponse != null;
                    if (myResponse.getError().equals("")) {
                        if (!myResponse.getPicUrl().equals("")) {
                            Picasso.with(OnlineDetailActivity.this).load(myResponse.getPicUrl()).fit().into(ivOnlineAvatar);
                        }
                        tvOnlineName.setText(myResponse.getName());
                        String onlineUrl = fromOnlineListActivity.getString("ownerRank") + " " + fromOnlineListActivity.getString("owner");
                        tvOnlineUrl.setText(onlineUrl);
                        tvOnlinePhone.setText(myResponse.getPhone());
                        if (myResponse.isOwner()) {
                            fbtnAdd.setImageResource(R.drawable.ic_edit);
                            fbtnAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent toCreateOnline = new Intent(OnlineDetailActivity.this, OnlineModificationActivity.class);
                                    toCreateOnline.putExtra("onlineId", myResponse.getId());
                                    toCreateOnline.putExtra("modification", true);
                                    toCreateOnline.putExtra("name", myResponse.getName());
                                    toCreateOnline.putExtra("image", myResponse.getPicUrl());
                                    toCreateOnline.putExtra("description", myResponse.getDescription());
                                    toCreateOnline.putExtra("phone", myResponse.getPhone());
                                    toCreateOnline.putExtra("verified", myResponse.getVerified());
                                    toCreateOnline.putExtra("rating", myResponse.getRating());
                                    toCreateOnline.putExtra("votes", myResponse.getVotes());
                                    toCreateOnline.putExtra("member", myResponse.getIsMember());
                                    toCreateOnline.putExtra("voted", myResponse.getHasVoted());
                                    toCreateOnline.putExtra("isOwner", myResponse.isOwner());
                                    toCreateOnline.putExtra("platform", myResponse.getPlatform());
                                    toCreateOnline.putExtra("date", fromOnlineListActivity.getString("date"));
                                    startActivity(toCreateOnline);
                                }
                            });
                        }

                        if (myResponse.getVerified()) {
                            ivVerifiedOnline.setImageResource(R.drawable.verified);
                            List<ImageView> stars = CommonMethods.SetStars(myResponse.getRating(), ivOnlineStar1, ivOnlineStar2, ivOnlineStar3, ivOnlineStar4, ivOnlineStar5);
                            ivOnlineStar1 = stars.get(0);
                            ivOnlineStar2 = stars.get(1);
                            ivOnlineStar3 = stars.get(2);
                            ivOnlineStar4 = stars.get(3);
                            ivOnlineStar5 = stars.get(4);
                            String votes = "(" + myResponse.getVotes() + ")";
                            tvVotes.setText(votes);
                        } else {
                            llDetailRating.setVisibility(View.GONE);
                            tvVotes.setVisibility(View.GONE);
                        }
                        if (myResponse.getPhone()==null) {
                            tvOnlinePhone.setVisibility(View.GONE);
                        }
                        tvOnlineAddress.setVisibility(View.GONE);
                        maps.setVisibility(View.GONE);
                        CommonMethods.fromPlatformNameToPlatformLogo(myResponse.getPlatform(), OnlineDetailActivity.this, ivPlatform);
                        ivPlatform.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(OnlineDetailActivity.this, myResponse.getError(), Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.clearValues();
                        Intent goToLogin = new Intent(OnlineDetailActivity.this, LoginActivity.class);
                        startActivity(goToLogin);
                        finish();
                    }
                } else {
                    Toast.makeText(OnlineDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerOnlineDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(OnlineDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetailMore = new Intent(OnlineDetailActivity.this,
                        OnlineDetailMoreActivity.class);
                goToDetailMore.putExtra("onlineId", myResponse.getId());
                goToDetailMore.putExtra("name", myResponse.getName());
                goToDetailMore.putExtra("image", myResponse.getPicUrl());
                goToDetailMore.putExtra("description", myResponse.getDescription());
                goToDetailMore.putExtra("phone", myResponse.getPhone());
                goToDetailMore.putExtra("verified", myResponse.getVerified());
                goToDetailMore.putExtra("rating", myResponse.getRating());
                goToDetailMore.putExtra("votes", myResponse.getVotes());
                goToDetailMore.putExtra("member", myResponse.getIsMember());
                goToDetailMore.putExtra("voted", myResponse.getHasVoted());
                goToDetailMore.putExtra("isOwner", myResponse.isOwner());
                goToDetailMore.putExtra("platform", myResponse.getPlatform());
                goToDetailMore.putExtra("date", fromOnlineListActivity.getString("date"));
                startActivity(goToDetailMore);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_online_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(OnlineDetailActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public ServerOnlineDetailResponse getMyResponse() {
        return myResponse;
    }
}