package com.onethousandprojects.appoeira.userModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import com.onethousandprojects.appoeira.serverStuff.methods.ProfileModificationServer;
import com.onethousandprojects.appoeira.userModificationView.fragments.ModifyAvatarFragment;
import com.onethousandprojects.appoeira.userModificationView.fragments.ModifyPasswordFragment;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Objects;

public class ProfileModificationActivity extends AppCompatActivity {
    private ModifyAvatarFragment modifyAvatarFragment;
    private ModifyPasswordFragment modifyPasswordFragment;
    private ProfileModificationServer profileModificationServer = new ProfileModificationServer();
    public Bitmap imageBitmap;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_user);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(ProfileModificationActivity.this, R.drawable.ic_circle));
                    }
                }
            });
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
                imageBitmap = ((BitmapDrawable)ivUserAvatar.getDrawable()).getBitmap();
                if (imageBitmap != null) {
                    try {
                        profileModificationServer.sendModificationsToServer(ProfileModificationActivity.this, String.valueOf(etName.getText()), String.valueOf(etLastName.getText()), String.valueOf(etApelhido.getText()), String.valueOf(etEmail.getText()), pass, newPass, roleId, imageBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ProfileModificationActivity.this, R.string.choseImagePlease, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void manageResponse() {
        String errorBin = Integer.toBinaryString(profileModificationServer.getServerUserModificationResponse().getError());
        if (errorBin.substring(0, 1).equals("0")) {
            Toast.makeText(ProfileModificationActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (errorBin.substring(1, 2).equals("0")) {
                        Toast.makeText(ProfileModificationActivity.this, R.string.failedToSavePassword, Toast.LENGTH_SHORT).show();
                        if (errorBin.substring(6, 7).equals("0")) {
                            Toast.makeText(ProfileModificationActivity.this, R.string.failedToSaveEmail, Toast.LENGTH_LONG).show();
                        } else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Toast.makeText(ProfileModificationActivity.this, R.string.emailUpdated, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ProfileModificationActivity.this, R.string.checkYourEmail, Toast.LENGTH_LONG).show();
                                    SharedPreferencesManager.setBooleanValue(Constants.EMAIL_VERIFIED, false);
                                }
                            }, 1000);
                        }
                    } else if (errorBin.substring(6, 7).equals("0")) {
                        Toast.makeText(ProfileModificationActivity.this, R.string.failedToSaveEmail, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileModificationActivity.this, R.string.profileUpdated, Toast.LENGTH_SHORT).show();
                        if (!profileModificationServer.getServerUserModificationResponse().getEmail().equals(SharedPreferencesManager.getStringValue(Constants.EMAIL))) {
                            Toast.makeText(ProfileModificationActivity.this, R.string.checkYourEmail, Toast.LENGTH_LONG).show();
                            SharedPreferencesManager.setBooleanValue(Constants.EMAIL_VERIFIED, false);
                            SharedPreferencesManager.setStringValue(Constants.EMAIL, profileModificationServer.getServerUserModificationResponse().getEmail());
                        }
                    }
                }
            }, 1000);
            SharedPreferencesManager.setStringValue(Constants.PIC_URL, profileModificationServer.getServerUserModificationResponse().getPicUrl());
            SharedPreferencesManager.setStringValue(Constants.PERF_TOKEN, profileModificationServer.getServerUserModificationResponse().getToken());
            SharedPreferencesManager.setIntegerValue(Constants.RANK, profileModificationServer.getServerUserModificationResponse().getRank());
            SharedPreferencesManager.setStringValue(Constants.FIRST_NAME, profileModificationServer.getServerUserModificationResponse().getName());
            SharedPreferencesManager.setStringValue(Constants.LAST_NAME, profileModificationServer.getServerUserModificationResponse().getLastName());
            SharedPreferencesManager.setStringValue(Constants.APELHIDO, profileModificationServer.getServerUserModificationResponse().getApelhido());
        }
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
}