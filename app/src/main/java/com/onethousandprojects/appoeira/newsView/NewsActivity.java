package com.onethousandprojects.appoeira.newsView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupListView.fragments.GroupFragment;
import com.onethousandprojects.appoeira.newsView.fragments.NewsFragment;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.newsList.ClientNewsRequest;
import com.onethousandprojects.appoeira.serverStuff.newsList.ServerNewsResponse;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.userModificationView.ProfileModificationActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
    private boolean createdFragment;
    public List<ServerNewsResponse> serverNewsResponse;
    Client Client;
    Server Server;
    public FloatingActionButton fbtnAdd;
    String origin = "NewsActivity";
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
            null,
            NewsActivity.this,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        retrofitInit();

        fbtnAdd = findViewById(R.id.addButton);
        fbtnAdd.setBackgroundResource(R.drawable.ic_empty);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_news);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
        getNews();
    }

    private void getNews() {
        ClientNewsRequest clientNewsRequest = new ClientNewsRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID));
        Call<List<ServerNewsResponse>> call = Server.post_news(clientNewsRequest);
        call.enqueue(new Callback<List<ServerNewsResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ServerNewsResponse>> call, @NonNull Response<List<ServerNewsResponse>> response) {
                if (response.isSuccessful()){
                    serverNewsResponse = response.body();
                    assert serverNewsResponse != null;
                    if (!createdFragment) {
                        createdFragment = true;
                        getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new NewsFragment(), "NewsListFragment").commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("NewsListFragment"))).commit();
                        getSupportFragmentManager().beginTransaction().add(R.id.ListLayout, new NewsFragment(), "NewsListFragment").commit();
                    }
                } else {
                    Toast.makeText(NewsActivity.this,R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ServerNewsResponse>> call,
                                  @NonNull Throwable t) {
                Toast.makeText(NewsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void retrofitInit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
}