package com.onethousandprojects.appoeira.userModificationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.userModificationView.fragments.ModifyAvatarFragment;
import com.onethousandprojects.appoeira.userModificationView.fragments.ModifyPasswordFragment;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.onethousandprojects.appoeira.serverStuff.userModification.ClientUserModificationRequest;
import com.onethousandprojects.appoeira.serverStuff.userModification.ServerUserModificationResponse;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileModificationActivity extends AppCompatActivity {
    public Client Client;
    public Server Server;
    private ModifyAvatarFragment modifyAvatarFragment;
    private ModifyPasswordFragment modifyPasswordFragment;
    private ServerUserModificationResponse myResponse;
    private String rank = "";
    public String pass = "";
    public String newPass = "";
    public ImageView ivUserAvatar;
    private Button btnSaveChanges;
    private int roleId;
    AdapterView.OnItemSelectedListener spinnerRank = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            roleId = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            roleId = 0;
        }
    };
    String origin = "ProfileModificationActivity";
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
            ProfileModificationActivity.this,
            null,
            null,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_modification);
        Bundle fromUserDetailActivity = getIntent().getExtras();

        ivUserAvatar = findViewById(R.id.userProfileAvatar);
        TextView tvModifyAvatar = findViewById(R.id.userModifyAvatar);
        EditText etApelhido = findViewById(R.id.userProfileApelhido);
        EditText etEmail = findViewById(R.id.userProfileEmail);
        EditText etName = findViewById(R.id.userProfileName);
        EditText etLastName = findViewById(R.id.userProfileApellido);
        btnSaveChanges = findViewById(R.id.modifyProfileSave);
        TextView tvChangePassword = findViewById(R.id.userProfileChangePassword);
        Spinner spRank = findViewById(R.id.userProfileRank);

        retrofitinit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_user);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).fit().into(ivUserAvatar);
        etApelhido.setText(SharedPreferencesManager.getStringValue(Constants.APELHIDO));
        assert fromUserDetailActivity != null;
        etEmail.setText(fromUserDetailActivity.getString("email"));
        etName.setText(fromUserDetailActivity.getString("name"));
        etLastName.setText(fromUserDetailActivity.getString("lastName"));
        spRank.setOnItemSelectedListener(spinnerRank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ranks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRank.setAdapter(adapter);
        spRank.setSelection(SharedPreferencesManager.getIntegerValue(Constants.RANK));
        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyAvatarFragment==null && modifyPasswordFragment==null) {
                    modifyAvatarFragment = new ModifyAvatarFragment();
                    btnSaveChanges.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyAvatarFragment, "ModifyAvatarFragment").commit();
                }
            }
        });
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyPasswordFragment==null && modifyAvatarFragment==null) {
                    modifyPasswordFragment = new ModifyPasswordFragment();
                    btnSaveChanges.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyPasswordFragment, modifyPasswordFragment, "ModifyPasswordFragment").commit();
                }
            }
        });
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientUserModificationRequest clientUserModificationRequest = new ClientUserModificationRequest(SharedPreferencesManager.getStringValue(Constants.PERF_TOKEN), SharedPreferencesManager.getIntegerValue(Constants.ID), String.valueOf(etName.getText()), String.valueOf(etLastName.getText()), String.valueOf(etApelhido.getText()), String.valueOf(etEmail.getText()), pass, newPass, roleId);
                Call<ServerUserModificationResponse> call = Server.post_user_update_profile(clientUserModificationRequest);
                call.enqueue(new Callback<ServerUserModificationResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ServerUserModificationResponse> call, @NonNull Response<ServerUserModificationResponse> response) {
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            myResponse = response.body();
                            manageResponse();
                            refreshActivity();
                        } else {
                            Toast.makeText(ProfileModificationActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<ServerUserModificationResponse> call, @NonNull Throwable t) {
                        Toast.makeText(ProfileModificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void manageResponse() {
        String errorBin = Integer.toBinaryString(myResponse.getError());
        if (errorBin.substring(0, 1).equals("0")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
        }
        if (errorBin.substring(1, 2).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.savedPass, Toast.LENGTH_SHORT).show();
        }
        if (errorBin.substring(3, 4).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.rankUpdated, Toast.LENGTH_SHORT).show();
            SharedPreferencesManager.setIntegerValue(Constants.RANK, myResponse.getRank());
        }
        if (errorBin.substring(4, 5).equals("1")) {
            //Toast.makeText(ProfileModificationActivity.this, "La contraseña no se guardó porque la antigua contraseña no coincide", Toast.LENGTH_SHORT).show();
        }
        if (errorBin.substring(5, 6).equals("1")) {
            //Toast.makeText(ProfileModificationActivity.this, "La contraseña no se guardó porque la antigua contraseña no coincide", Toast.LENGTH_SHORT).show();
        }
        if (errorBin.substring(7, 8).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.apelhidoUpdated, Toast.LENGTH_SHORT).show();
            SharedPreferencesManager.setStringValue(Constants.APELHIDO, myResponse.getApelhido());
        }
        if (errorBin.substring(8, 9).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.lastNameUpdated, Toast.LENGTH_SHORT).show();
            SharedPreferencesManager.setStringValue(Constants.LAST_NAME, myResponse.getLastName());
        }
        if (errorBin.substring(9).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.nameUpdated, Toast.LENGTH_SHORT).show();
            SharedPreferencesManager.setStringValue(Constants.FIRST_NAME, myResponse.getName());
        }
        if (errorBin.substring(6, 7).equals("1")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.emailUpdated, Toast.LENGTH_SHORT).show();
            Toast.makeText(ProfileModificationActivity.this, R.string.checkYourEmail, Toast.LENGTH_LONG).show();
            SharedPreferencesManager.setBooleanValue(Constants.EMAIL_VERIFIED, false);
        }
        SharedPreferencesManager.setStringValue(Constants.PERF_TOKEN, myResponse.getToken());
        SharedPreferencesManager.setIntegerValue(Constants.RANK, myResponse.getRank());
        SharedPreferencesManager.setStringValue(Constants.FIRST_NAME, myResponse.getName());
        SharedPreferencesManager.setStringValue(Constants.LAST_NAME, myResponse.getLastName());
        SharedPreferencesManager.setStringValue(Constants.APELHIDO, myResponse.getApelhido());
        SharedPreferencesManager.setStringValue(Constants.EMAIL, myResponse.getEmail());
    }

    public void killAvatarFragment() {
        if (modifyAvatarFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
            modifyAvatarFragment = null;
            btnSaveChanges.setVisibility(View.VISIBLE);
        }
    }
    public void killPasswordFragment() {
        if (modifyPasswordFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyPasswordFragment"))).commit();
            modifyPasswordFragment = null;
            btnSaveChanges.setVisibility(View.VISIBLE);
        }
    }
    public void refreshActivity() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    private void retrofitinit() {
        Client = Client.getInstance();
        Server = Client.getServer();
    }
}