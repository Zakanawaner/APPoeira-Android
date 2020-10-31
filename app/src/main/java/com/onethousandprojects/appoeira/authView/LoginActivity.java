package com.onethousandprojects.appoeira.authView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ClientLoginRequest;
import com.onethousandprojects.appoeira.serverStuff.loginTransaction.ServerLoginResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Client Client;
    Server Server;
    String origin = "LoginActivity";
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
            null,
            null,
            LoginActivity.this,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        retrofitinit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_login);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    private void retrofitinit() {
        Client = com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client.getInstance();
        Server = Client.getServer();
    }
    public void sendLogin(View view) {
        EditText etEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText etPassword = (EditText) findViewById(R.id.editTextPassword);
        ClientLoginRequest clientLoginRequest = new ClientLoginRequest(String.valueOf(etEmail.getText()), String.valueOf(etPassword.getText()));
        Call<ServerLoginResponse> call = Server.post_login(clientLoginRequest);
        call.enqueue(new Callback<ServerLoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerLoginResponse> call, @NonNull Response<ServerLoginResponse> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().getId() != null) {
                        saveSharedPreferences(response.body());
                        toGroupList();
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.loginFailed, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ServerLoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveSharedPreferences(ServerLoginResponse body) {
        SharedPreferencesManager.setStringValue(Constants.PERF_TOKEN, body.getToken());
        SharedPreferencesManager.setIntegerValue(Constants.ID, body.getId());
        SharedPreferencesManager.setIntegerValue(Constants.RANK, body.getRank());
        SharedPreferencesManager.setStringValue(Constants.FIRST_NAME, body.getName());
        SharedPreferencesManager.setStringValue(Constants.LAST_NAME, body.getLastName());
        SharedPreferencesManager.setStringValue(Constants.APELHIDO, body.getApelhido());
        SharedPreferencesManager.setStringValue(Constants.PIC_URL, body.getPicUrl());
        SharedPreferencesManager.setStringValue(Constants.EMAIL, body.getEmail());
        SharedPreferencesManager.setBooleanValue(Constants.EMAIL_VERIFIED, body.isEmailVerified());
    }
    private void toGroupList() {
        Intent goToGroupList = new Intent(LoginActivity.this, GroupListActivity.class);
        startActivity(goToGroupList);
        finish();
    }
    public void goToSignUp(View view) {
        Intent goToSignUp = new Intent(view.getContext(), SignUpActivity.class);
        startActivity(goToSignUp);
        finish();
    }
}