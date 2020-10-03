package com.onethousandprojects.appoeira.onlineModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineModificationView.adapters.MyOnlineModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.ModifyOnlineAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.OnlineModificationServer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineModificationActivity extends AppCompatActivity implements MyOnlineModificationInviteMembersRecyclerViewAdapter.OnOnlineModificationInviteMembersListener {
    private ModifyOnlineAvatarFragment modifyOnlineAvatarFragment;
    public String newUrl = "";
    public ImageView ivAvatar;
    private Button btnSave;
    public OnlineModificationServer onlineModificationServer = new OnlineModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    private List<Integer> invitedMembers = new ArrayList<>();
    private Integer platform;
    AdapterView.OnItemSelectedListener spinnerPlatform = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            platform = i;
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            platform = 0;
        }
    };
    String origin = "OnlineModificationActivity";
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
            OnlineModificationActivity.this,
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
        setContentView(R.layout.activity_online_modification);

        ivAvatar = findViewById(R.id.avatar);
        TextView tvModifyAvatar = findViewById(R.id.modifyAvatar);
        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.description);
        EditText etPhone = findViewById(R.id.phone);
        EditText etKey = findViewById(R.id.key);
        DatePicker dpDate = findViewById(R.id.date);
        TimePicker tpTime = findViewById(R.id.hour);
        SearchView svUsers = findViewById(R.id.searchUsers);
        Spinner spinner = findViewById(R.id.platformSpinner);
        spinner.setOnItemSelectedListener(spinnerPlatform);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.platforms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        btnSave = findViewById(R.id.modifySave);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_online);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyOnlineAvatarFragment==null) {
                    modifyOnlineAvatarFragment = new ModifyOnlineAvatarFragment();
                    btnSave.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyOnlineAvatarFragment, "ModifyAvatarFragment").commit();
                }
            }
        });
        svUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.equals("")) {
                    onlineModificationServer.sendUserSearchToServer(OnlineModificationActivity.this, s);
                } else {
                    if (onlineModificationServer.createdFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        onlineModificationServer.createdFragment = false;
                    }
                }
                return false;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                ownerMembers.clear();
                ownerMembers.add(SharedPreferencesManager.getIntegerValue(Constants.ID));
                onlineModificationServer.sendModificationsToServer(OnlineModificationActivity.this,
                        ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                        createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                        newUrl, invitedMembers, platform, String.valueOf(etPhone.getText()), String.valueOf(etKey.getText()));
            }
        });
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    public void killAvatarFragment() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
        modifyOnlineAvatarFragment = null;
        btnSave.setVisibility(View.VISIBLE);
    }
    public void chargeImage(){
        Picasso.with(this).load(newUrl).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatarImage(ivAvatar));
    }
    @Override
    public void OnOnlineInviteMemberClick(int position) {
        invitedMembers.add(onlineModificationServer.getUserSearchResponse().get(position).getId());
    }
    public void addInvited(Integer id) {
        invitedMembers.add(id);
    }
    public void deleteInvited(Integer id) {
        for (int i = 0; i < invitedMembers.size(); i++) {
            if (invitedMembers.get(i).equals(id)) {
                invitedMembers.remove(i);
                break;
            }
        }
    }
    public boolean checkMembers(Integer id) {
        for (int i = 0; i < invitedMembers.size(); i++) {
            if (invitedMembers.get(i).equals(id)) {
                return true;
            }
        }
        return false;
    }
}