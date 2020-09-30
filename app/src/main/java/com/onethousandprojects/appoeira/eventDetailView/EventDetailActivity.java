package com.onethousandprojects.appoeira.eventDetailView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.serverStuff.eventDetail.ClientEventDetailRequest;
import com.onethousandprojects.appoeira.serverStuff.eventDetail.ServerEventDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public Double latitude;
    public Double longitude;
    public Bundle fromEventListActivity;
    ImageView ivEventStar1;
    ImageView ivEventStar2;
    ImageView ivEventStar3;
    ImageView ivEventStar4;
    ImageView ivEventStar5;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client Client;
    com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server Server;
    private ServerEventDetailResponse myResponse;
    String origin = "EventDetailActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            null,
            null,
            null,
            EventDetailActivity.this,
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
        setContentView(R.layout.activity_event_detail);
        fromEventListActivity = getIntent().getExtras();

        retrofitinit();

        TextView tvEventName = findViewById(R.id.detailName);
        TextView tvEventUrl = findViewById(R.id.detailUrlOrCreator);
        TextView tvEventPhone = findViewById(R.id.detailPhone);
        ImageView ivEventAvatar = findViewById(R.id.detailAvatar);
        ImageView ivVerifiedEvent = findViewById(R.id.detailVerified);
        TextView tvEventAddress = findViewById(R.id.detailAddress);
        TextView tvVotes = findViewById(R.id.detailVotes);
        TextView tvMore = findViewById(R.id.detailMore);

        ivEventStar1 = findViewById(R.id.detailStar1);
        ivEventStar2 = findViewById(R.id.detailStar2);
        ivEventStar3 = findViewById(R.id.detailStar3);
        ivEventStar4 = findViewById(R.id.detailStar4);
        ivEventStar5 = findViewById(R.id.detailStar5);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);

        ClientEventDetailRequest clientEventDetailRequest = new ClientEventDetailRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), fromEventListActivity.getInt("id"), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<ServerEventDetailResponse> call = Server.post_event_detail(clientEventDetailRequest);
        call.enqueue(new Callback<ServerEventDetailResponse>() {
            @Override
            public void onResponse(Call<ServerEventDetailResponse> call, Response<ServerEventDetailResponse> response) {
                if (response.isSuccessful()){
                    myResponse = response.body();
                    assert myResponse != null;
                    if (myResponse.getError().equals("")) {
                        if (!myResponse.getPicUrl().equals("")) {
                            Picasso.with(EventDetailActivity.this).load(myResponse.getPicUrl()).fit().into(ivEventAvatar);
                        }
                        tvEventName.setText(myResponse.getName());
                        tvEventUrl.setText(fromEventListActivity.getString("ownerRank") + " " + fromEventListActivity.getString("owner"));
                        tvEventPhone.setText(myResponse.getPhone());
                        tvEventAddress.setText(myResponse.getAddress());

                        if (myResponse.getVerified()) {
                            ivVerifiedEvent.setImageResource(R.drawable.verified);
                            List<ImageView> stars = CommonMethods.SetStars(myResponse.getRating(), ivEventStar1, ivEventStar2, ivEventStar3, ivEventStar4, ivEventStar5);
                            ivEventStar1 = stars.get(0);
                            ivEventStar2 = stars.get(1);
                            ivEventStar3 = stars.get(2);
                            ivEventStar4 = stars.get(3);
                            ivEventStar5 = stars.get(4);
                            tvVotes.setText("(" + myResponse.getVotes() + ")");
                        }

                        latitude = myResponse.getLatitude();
                        longitude = myResponse.getLongitude();
                        mapFragment.getMapAsync(EventDetailActivity.this);
                    } else {
                        Toast.makeText(EventDetailActivity.this, myResponse.getError(), Toast.LENGTH_SHORT).show();
                        SharedPreferencesManager.clearValues();
                        Intent goToLogin = new Intent(EventDetailActivity.this, LoginActivity.class);
                        startActivity(goToLogin);
                        finish();
                    }
                } else {
                    Toast.makeText(EventDetailActivity.this, "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ServerEventDetailResponse> call, Throwable t) {
                Toast.makeText(EventDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToDetailMore = new Intent(EventDetailActivity.this, EventDetailMoreActivity.class);
                goToDetailMore.putExtra("id", myResponse.getId());
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
                startActivity(goToDetailMore);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_event_detail);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public ServerEventDetailResponse getMyResponse() {
        return myResponse;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
    }
}