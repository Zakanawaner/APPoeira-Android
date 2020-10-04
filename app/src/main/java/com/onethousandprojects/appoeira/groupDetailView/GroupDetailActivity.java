package com.onethousandprojects.appoeira.groupDetailView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ClientGroupDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.groupDetail.ServerGroupDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public Double latitude;
    public Double longitude;
    public Bundle fromGroupListActivity;
    ImageView ivGroupStar1;
    ImageView ivGroupStar2;
    ImageView ivGroupStar3;
    ImageView ivGroupStar4;
    ImageView ivGroupStar5;
    Client Client;
    Server Server;
    private ServerGroupDetailResponse myResponse;
    String origin = "GroupDetailActivity";
    NavParams navParams = new NavParams(
            null,
            GroupDetailActivity.this,
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
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        fromGroupListActivity = getIntent().getExtras();

        retrofitinit();

        TextView tvGroupName = findViewById(R.id.detailName);
        TextView tvGroupUrl = findViewById(R.id.detailUrlOrCreator);
        TextView tvGroupPhone = findViewById(R.id.detailPhone);
        ImageView ivGroupAvatar = findViewById(R.id.detailAvatar);
        ImageView ivVerifiedGroup = findViewById(R.id.detailVerified);
        TextView tvGroupAddress = findViewById(R.id.detailAddress);
        TextView tvVotes = findViewById(R.id.detailVotes);
        TextView tvMore = findViewById(R.id.detailMore);
        LinearLayout llDetailRating = findViewById(R.id.detailRating);

        ivGroupStar1 = findViewById(R.id.detailStar1);
        ivGroupStar2 = findViewById(R.id.detailStar2);
        ivGroupStar3 = findViewById(R.id.detailStar3);
        ivGroupStar4 = findViewById(R.id.detailStar4);
        ivGroupStar5 = findViewById(R.id.detailStar5);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);

        ClientGroupDetailRequest clientGroupDetailRequest = new ClientGroupDetailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), fromGroupListActivity.getInt("id"), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerGroupDetailResponse> call = Server.post_group_detail(clientGroupDetailRequest);
        call.enqueue(new Callback<ServerGroupDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerGroupDetailResponse> call, @NonNull Response<ServerGroupDetailResponse> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    assert myResponse != null;
                    if (myResponse.getError().equals("")) {
                        Picasso.with(GroupDetailActivity.this).load(myResponse.getPicUrl()).fit().into(ivGroupAvatar);
                        tvGroupName.setText(myResponse.getName());
                        tvGroupUrl.setText(myResponse.getUrl());
                        tvGroupPhone.setText(myResponse.getPhone());
                        tvGroupAddress.setText(myResponse.getAddress());

                        if (myResponse.getVerified()) {
                            ivVerifiedGroup.setImageResource(R.drawable.verified);
                            List<ImageView> stars = CommonMethods.SetStars(myResponse.getRating(), ivGroupStar1, ivGroupStar2, ivGroupStar3, ivGroupStar4, ivGroupStar5);
                            ivGroupStar1 = stars.get(0);
                            ivGroupStar2 = stars.get(1);
                            ivGroupStar3 = stars.get(2);
                            ivGroupStar4 = stars.get(3);
                            ivGroupStar5 = stars.get(4);
                            String votes = "(" + myResponse.getVotes() + ")";
                            tvVotes.setText(votes);
                        } else {
                            llDetailRating.setVisibility(View.GONE);
                            tvVotes.setVisibility(View.GONE);
                        }
                        if (myResponse.getPhone()==null) {
                            tvGroupPhone.setVisibility(View.GONE);
                        }
                        if (myResponse.getUrl()==null) {
                            tvGroupUrl.setVisibility(View.GONE);
                        }
                        if (myResponse.getAddress()==null) {
                            tvGroupAddress.setVisibility(View.GONE);
                        }
                        latitude = myResponse.getLatitude();
                        longitude = myResponse.getLongitude();
                        mapFragment.getMapAsync(GroupDetailActivity.this);
                    } else {
                        Toast.makeText(GroupDetailActivity.this, myResponse.getError(), Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.clearValues();
                        Intent goToLogin = new Intent(GroupDetailActivity.this, LoginActivity.class);
                        startActivity(goToLogin);
                        finish();
                    }
                } else {
                    Toast.makeText(GroupDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerGroupDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(GroupDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetailMore = new Intent(GroupDetailActivity.this, GroupDetailMoreActivity.class);
                goToDetailMore.putExtra("id", myResponse.getId());
                goToDetailMore.putExtra("name", myResponse.getName());
                goToDetailMore.putExtra("image", myResponse.getPicUrl());
                goToDetailMore.putExtra("url", myResponse.getUrl());
                goToDetailMore.putExtra("phone", myResponse.getPhone());
                goToDetailMore.putExtra("verified", myResponse.getVerified());
                goToDetailMore.putExtra("rating", myResponse.getRating());
                goToDetailMore.putExtra("votes", myResponse.getVotes());
                goToDetailMore.putExtra("opening", myResponse.getOpening());
                goToDetailMore.putExtra("facebook", myResponse.getFacebook());
                goToDetailMore.putExtra("google", myResponse.getGoogle());
                goToDetailMore.putExtra("about", myResponse.getAbout());
                goToDetailMore.putExtra("address", myResponse.getAddress());
                goToDetailMore.putExtra("city", myResponse.getCity());
                goToDetailMore.putExtra("country", myResponse.getCountry());
                goToDetailMore.putExtra("latitude", myResponse.getLatitude());
                goToDetailMore.putExtra("longitude", myResponse.getLongitude());
                goToDetailMore.putExtra("member", myResponse.getIsMember());
                goToDetailMore.putExtra("voted", myResponse.getHasVoted());
                goToDetailMore.putExtra("comment", myResponse.getComment());
                startActivity(goToDetailMore);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_group_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }

    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }

    public ServerGroupDetailResponse getMyResponse() {
        return myResponse;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(myResponse.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
    }
}