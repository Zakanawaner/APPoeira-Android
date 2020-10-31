package com.onethousandprojects.appoeira.rodaDetailView;

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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.rodaDetailMoreView.RodaDetailMoreActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ClientRodaDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.rodaDetail.ServerRodaDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RodaDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public Double latitude;
    public Double longitude;
    public Bundle fromRodaListActivity;
    public FloatingActionButton fbtnAdd;
    ImageView ivRodaStar1;
    ImageView ivRodaStar2;
    ImageView ivRodaStar3;
    ImageView ivRodaStar4;
    ImageView ivRodaStar5;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private ServerRodaDetailResponse myResponse;
    String origin = "RodaDetailActivity";
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
            RodaDetailActivity.this,
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
        setContentView(R.layout.activity_roda_detail);
        fromRodaListActivity = getIntent().getExtras();

        retrofitinit();

        TextView tvRodaName = findViewById(R.id.detailName);
        TextView tvRodaUrl = findViewById(R.id.detailUrlOrCreator);
        TextView tvRodaPhone = findViewById(R.id.detailPhone);
        ImageView ivRodaAvatar = findViewById(R.id.detailAvatar);
        ImageView ivVerifiedRoda = findViewById(R.id.detailVerified);
        TextView tvRodaAddress = findViewById(R.id.detailAddress);
        TextView tvVotes = findViewById(R.id.detailVotes);
        TextView tvMore = findViewById(R.id.detailMore);
        LinearLayout llDetailRating = findViewById(R.id.detailRating);
        fbtnAdd = findViewById(R.id.addButton);

        ivRodaStar1 = findViewById(R.id.detailStar1);
        ivRodaStar2 = findViewById(R.id.detailStar2);
        ivRodaStar3 = findViewById(R.id.detailStar3);
        ivRodaStar4 = findViewById(R.id.detailStar4);
        ivRodaStar5 = findViewById(R.id.detailStar5);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);

        ClientRodaDetailRequest clientRodaDetailRequest = new ClientRodaDetailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), fromRodaListActivity.getInt("rodaId"), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerRodaDetailResponse> call = Server.post_roda_detail(clientRodaDetailRequest);
        call.enqueue(new Callback<ServerRodaDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerRodaDetailResponse> call, @NonNull Response<ServerRodaDetailResponse> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    assert myResponse != null;
                    if (myResponse.getError().equals("")) {
                        if (!myResponse.getPicUrl().equals("")) {
                            Picasso.with(RodaDetailActivity.this).load(myResponse.getPicUrl()).fit().into(ivRodaAvatar);
                        }
                        tvRodaName.setText(myResponse.getName());
                        String url = fromRodaListActivity.getString("ownerRank") + " " + fromRodaListActivity.getString("owner");
                        tvRodaUrl.setText(url);
                        tvRodaPhone.setText(myResponse.getPhone());
                        tvRodaAddress.setText(myResponse.getAddress());
                        if (myResponse.isOwner()) {
                            fbtnAdd.setImageResource(R.drawable.ic_edit);
                            fbtnAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent toCreateRoda = new Intent(RodaDetailActivity.this, RodaModificationActivity.class);
                                    toCreateRoda.putExtra("rodaId", myResponse.getId());
                                    toCreateRoda.putExtra("modification", true);
                                    toCreateRoda.putExtra("name", myResponse.getName());
                                    toCreateRoda.putExtra("image", myResponse.getPicUrl());
                                    toCreateRoda.putExtra("description", myResponse.getDescription());
                                    toCreateRoda.putExtra("phone", myResponse.getPhone());
                                    toCreateRoda.putExtra("verified", myResponse.getVerified());
                                    toCreateRoda.putExtra("rating", myResponse.getRating());
                                    toCreateRoda.putExtra("votes", myResponse.getVotes());
                                    toCreateRoda.putExtra("address", myResponse.getAddress());
                                    toCreateRoda.putExtra("city", myResponse.getCity());
                                    toCreateRoda.putExtra("country", myResponse.getCountry());
                                    toCreateRoda.putExtra("latitude", myResponse.getLatitude());
                                    toCreateRoda.putExtra("longitude", myResponse.getLongitude());
                                    toCreateRoda.putExtra("member", myResponse.getIsMember());
                                    toCreateRoda.putExtra("voted", myResponse.getHasVoted());
                                    toCreateRoda.putExtra("isOwner", myResponse.isOwner());
                                    toCreateRoda.putExtra("date", fromRodaListActivity.getString("date"));
                                    startActivity(toCreateRoda);
                                }
                            });
                        }

                        if (myResponse.getVerified()) {
                            ivVerifiedRoda.setImageResource(R.drawable.verified);
                            List<ImageView> stars = CommonMethods.SetStars(myResponse.getRating(), ivRodaStar1, ivRodaStar2, ivRodaStar3, ivRodaStar4, ivRodaStar5);
                            ivRodaStar1 = stars.get(0);
                            ivRodaStar2 = stars.get(1);
                            ivRodaStar3 = stars.get(2);
                            ivRodaStar4 = stars.get(3);
                            ivRodaStar5 = stars.get(4);
                            String votes = "(" + myResponse.getVotes() + ")";
                            tvVotes.setText(votes);
                        } else {
                            llDetailRating.setVisibility(View.GONE);
                            tvVotes.setVisibility(View.GONE);
                        }
                        if (myResponse.getPhone()==null) {
                            tvRodaPhone.setVisibility(View.GONE);
                        }
                        if (myResponse.getAddress()==null) {
                            tvRodaAddress.setVisibility(View.GONE);
                        }
                        latitude = myResponse.getLatitude();
                        longitude = myResponse.getLongitude();
                        mapFragment.getMapAsync(RodaDetailActivity.this);
                    } else {
                        Toast.makeText(RodaDetailActivity.this, myResponse.getError(), Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.clearValues();
                        Intent goToLogin = new Intent(RodaDetailActivity.this, LoginActivity.class);
                        startActivity(goToLogin);
                        finish();
                    }
                } else {
                    Toast.makeText(RodaDetailActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerRodaDetailResponse> call, @NonNull Throwable t) {
                Toast.makeText(RodaDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetailMore = new Intent(RodaDetailActivity.this, RodaDetailMoreActivity.class);
                goToDetailMore.putExtra("rodaId", myResponse.getId());
                goToDetailMore.putExtra("name", myResponse.getName());
                goToDetailMore.putExtra("image", myResponse.getPicUrl());
                goToDetailMore.putExtra("description", myResponse.getDescription());
                goToDetailMore.putExtra("phone", myResponse.getPhone());
                goToDetailMore.putExtra("verified", myResponse.getVerified());
                goToDetailMore.putExtra("rating", myResponse.getRating());
                goToDetailMore.putExtra("votes", myResponse.getVotes());
                goToDetailMore.putExtra("address", myResponse.getAddress());
                goToDetailMore.putExtra("city", myResponse.getCity());
                goToDetailMore.putExtra("country", myResponse.getCountry());
                goToDetailMore.putExtra("latitude", myResponse.getLatitude());
                goToDetailMore.putExtra("longitude", myResponse.getLongitude());
                goToDetailMore.putExtra("member", myResponse.getIsMember());
                goToDetailMore.putExtra("voted", myResponse.getHasVoted());
                goToDetailMore.putExtra("isOwner", myResponse.isOwner());
                goToDetailMore.putExtra("date", fromRodaListActivity.getString("date"));
                startActivity(goToDetailMore);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_roda_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(RodaDetailActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public ServerRodaDetailResponse getMyResponse() {
        return myResponse;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(myResponse.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
    }
}