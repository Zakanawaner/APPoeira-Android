package com.onethousandprojects.appoeira.searchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventDetailView.EventDetailActivity;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.newsView.NewsActivity;
import com.onethousandprojects.appoeira.onlineDetailView.OnlineDetailActivity;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.OnlineMembersInvitedFragment;
import com.onethousandprojects.appoeira.rodaDetailView.RodaDetailActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.searchView.adapters.MySearchRecyclerViewAdapter;
import com.onethousandprojects.appoeira.searchView.content.SearchContent;
import com.onethousandprojects.appoeira.searchView.fragment.SearchFragment;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.search.ClientSearchRequest;
import com.onethousandprojects.appoeira.serverStuff.search.ServerSearchResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchEventResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchOnlineResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchRodaResponse;
import com.onethousandprojects.appoeira.serverStuff.search.objects.ServerSearchUserResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.userDetailView.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements MySearchRecyclerViewAdapter.OnSearchListener {
    Client Client;
    Server Server;
    private String u = "0", g = "0", r = "0", e = "0", o = "0";
    private LinearLayout llSearch;
    private TextView tvSearchInstructions;
    private boolean createdFragment;
    private ServerSearchResponse serverSearchResponse;
    private String text = "";
    private SearchView sv;
    String origin = "SearchActivity";
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
            null,
            null,
            null,
            null,
            SearchActivity.this,
            null,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Client = Client.getInstance();
        Server = Client.getServer();

        Button btnUsers = findViewById(R.id.searchUsersBtn);
        Button btnGroups = findViewById(R.id.searchGroupsBtn);
        Button btnRoda = findViewById(R.id.searchRodaBtn);
        Button btnEvents = findViewById(R.id.searchEventBtn);
        Button btnOnline = findViewById(R.id.searchOnlineBtn);
        TextView tvSearchTitle = findViewById(R.id.searchTitle);
        sv = findViewById(R.id.searchUsers);
        llSearch = findViewById(R.id.searchUserLayout);
        tvSearchInstructions = findViewById(R.id.searchInstructions);

        tvSearchTitle.setVisibility(View.GONE);
        llSearch.setVisibility(View.GONE);

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u.equals("0")) {
                    u = "1";
                    btnUsers.setBackgroundColor(Color.parseColor("#ABFFA6"));
                } else {
                    u = "0";
                    btnUsers.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                manageVisibility();
                if (!text.equals("")) {
                    sendSearch();
                }
            }
        });
        btnGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (g.equals("0")) {
                    g = "1";
                    btnGroups.setBackgroundColor(Color.parseColor("#ABFFA6"));
                    tvSearchInstructions.setVisibility(View.INVISIBLE);
                } else {
                    g = "0";
                    btnGroups.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                manageVisibility();
                if (!text.equals("")) {
                    sendSearch();
                }
            }
        });
        btnRoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (r.equals("0")) {
                    r = "1";
                    btnRoda.setBackgroundColor(Color.parseColor("#ABFFA6"));
                } else {
                    r = "0";
                    btnRoda.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                manageVisibility();
                if (!text.equals("")) {
                    sendSearch();
                }
            }
        });
        btnEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (e.equals("0")) {
                    e = "1";
                    btnEvents.setBackgroundColor(Color.parseColor("#ABFFA6"));
                } else {
                    e = "0";
                    btnEvents.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                manageVisibility();
                if (!text.equals("")) {
                    sendSearch();
                }
            }
        });
        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (o.equals("0")) {
                    o = "1";
                    btnOnline.setBackgroundColor(Color.parseColor("#ABFFA6"));
                } else {
                    o = "0";
                    btnOnline.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                manageVisibility();
                if (!text.equals("")) {
                    sendSearch();
                }
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                text = s;
                if (!text.equals("")) {
                    sendSearch();
                } else {
                    if (createdFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ListFragment"))).commit();
                        createdFragment = false;
                    }
                }
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_search);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    private void sendSearch(){
        String mode = u + g + r + e + o;
        ClientSearchRequest clientSearchRequest = new ClientSearchRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), text, mode);
        Call<ServerSearchResponse> call = Server.post_search(clientSearchRequest);
        call.enqueue(new Callback<ServerSearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerSearchResponse> call, @NonNull Response<ServerSearchResponse> response) {
                if (response.isSuccessful()){
                    serverSearchResponse = response.body();
                    if (!createdFragment) {
                        createdFragment = true;
                        getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new SearchFragment(), "ListFragment").commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ListFragment"))).commit();
                        if (serverSearchResponse.getUserResponses().size() > 0 ||
                                serverSearchResponse.getGroupResponses().size() > 0 ||
                                serverSearchResponse.getRodaResponses().size() > 0 ||
                                serverSearchResponse.getEventResponses().size() > 0 ||
                                serverSearchResponse.getOnlineResponses().size() > 0) {
                            getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new SearchFragment(), "ListFragment").commit();
                        } else {
                            createdFragment = false;
                        }
                    }
                } else {
                    Toast.makeText(SearchActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerSearchResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void manageVisibility() {
        if (u.equals("1") || g.equals("1") || r.equals("1") || e.equals("1") || o.equals("1")) {
            llSearch.setVisibility(View.VISIBLE);
            tvSearchInstructions.setVisibility(View.GONE);
        } else {
            llSearch.setVisibility(View.GONE);
            tvSearchInstructions.setVisibility(View.VISIBLE);
            sv.setQuery("", false);
            text = "";
        }
    }
    public ServerSearchResponse getSearchResponse() {
        return serverSearchResponse;
    }
    @Override
    public void OnSearchClick(int id, int type) {
        if (CommonMethods.AmILogged()) {
            switch (type){
                case 1:
                    Intent toUserDetailActivity = new Intent(this, UserDetailActivity.class);
                    toUserDetailActivity.putExtra("id", id);
                    startActivity(toUserDetailActivity);
                    finish();
                    break;
                case 2:
                    Intent toGroupDetailActivity = new Intent(this, GroupDetailActivity.class);
                    toGroupDetailActivity.putExtra("id", id);
                    startActivity(toGroupDetailActivity);
                    finish();
                    break;
                case 3:
                    Intent toRodaDetailActivity = new Intent(this, RodaDetailActivity.class);
                    toRodaDetailActivity.putExtra("id", id);
                    startActivity(toRodaDetailActivity);
                    finish();
                    break;
                case 4:
                    Intent toEventDetailActivity = new Intent(this, EventDetailActivity.class);
                    toEventDetailActivity.putExtra("id", id);
                    startActivity(toEventDetailActivity);
                    finish();
                    break;
                case 5:
                    Intent toOnlineDetailActivity = new Intent(this, OnlineDetailActivity.class);
                    toOnlineDetailActivity.putExtra("id", id);
                    startActivity(toOnlineDetailActivity);
                    finish();
                    break;
                default:
                    break;
            }
        } else {
            Intent toLogin = new Intent(this, LoginActivity.class);
            startActivity(toLogin);
        }
    }
}