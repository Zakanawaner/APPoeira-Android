package com.onethousandprojects.appoeira.authView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.onethousandprojects.appoeira.serverStuff.signupTransaction.ClientSignupRequest;
import com.onethousandprojects.appoeira.serverStuff.signupTransaction.ServerSignupResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Client Client;
    Server Server;
    String origin = "SignUpActivity";
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
            null,
            SignUpActivity.this);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    private Integer rankSelected;
    AdapterView.OnItemSelectedListener spinnerRank = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            rankSelected = i;
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            rankSelected = 0;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        retrofitinit();
        Spinner spinner = (Spinner) findViewById(R.id.rankSpinner);
        spinner.setOnItemSelectedListener(spinnerRank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ranks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_sign_up);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
    }
    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
    public void sendSignUp(View view) {
        EditText etName = (EditText) findViewById(R.id.editTextName);
        EditText etLastName = (EditText) findViewById(R.id.editTextApellido);
        EditText etApelhido = (EditText) findViewById(R.id.editTextApelhido);
        EditText etEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText etPassword = (EditText) findViewById(R.id.editTextPassword);
        EditText etPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        if (!String.valueOf(etApelhido.getText()).equals("")) {
            if (!TextUtils.isEmpty(String.valueOf(etEmail.getText())) && Patterns.EMAIL_ADDRESS.matcher(String.valueOf(etEmail.getText())).matches()) {
                if (String.valueOf(etPassword.getText()).equals(String.valueOf(etPassword2.getText())) && !String.valueOf(etPassword.getText()).equals("")) {
                    if (rankSelected > 0) {
                        ClientSignupRequest clientSignupRequest = new ClientSignupRequest(String.valueOf(etName.getText()),
                                String.valueOf(etLastName.getText()),
                                String.valueOf(etApelhido.getText()),
                                String.valueOf(etEmail.getText()),
                                String.valueOf(etPassword.getText()),
                                rankSelected);
                        Call<ServerSignupResponse> call = Server.post_sign_up(clientSignupRequest);
                        call.enqueue(new Callback<ServerSignupResponse>() {
                            @Override
                            public void onResponse(Call<ServerSignupResponse> call, Response<ServerSignupResponse> response) {
                                if (response.isSuccessful()) {
                                    assert response.body() != null;
                                    if (response.body().getToken() == null) {
                                        Toast.makeText(SignUpActivity.this, R.string.signupEmailExists, Toast.LENGTH_SHORT).show();
                                    } else {
                                        saveSharedPreferences(response.body());
                                        toGroupList();
                                    }
                                } else {
                                    Toast.makeText(SignUpActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ServerSignupResponse> call, Throwable t) {
                                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else { Toast.makeText(SignUpActivity.this, R.string.signupSelectYourRank, Toast.LENGTH_SHORT).show(); }
                } else { Toast.makeText(SignUpActivity.this, R.string.signupIncorrectPassword, Toast.LENGTH_SHORT).show(); }
            } else { Toast.makeText(SignUpActivity.this, R.string.signupValidEmail, Toast.LENGTH_SHORT).show(); }
        } else { Toast.makeText(SignUpActivity.this, R.string.signupValidApelhido, Toast.LENGTH_SHORT).show(); }
    }
    private void saveSharedPreferences(ServerSignupResponse body) {
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
        Intent goToGroupList = new Intent(SignUpActivity.this, GroupListActivity.class);
        startActivity(goToGroupList);
        finish();
    }
}