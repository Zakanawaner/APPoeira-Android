package com.onethousandprojects.appoeira.onlineModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.onlineListView.OnlineListActivity;
import com.onethousandprojects.appoeira.onlineModificationView.adapters.MyOnlineModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.DeleteOnlineFragment;
import com.onethousandprojects.appoeira.onlineModificationView.fragments.ModifyOnlineAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.OnlineModificationServer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OnlineModificationActivity extends AppCompatActivity implements MyOnlineModificationInviteMembersRecyclerViewAdapter.OnOnlineModificationInviteMembersListener {
    private ModifyOnlineAvatarFragment modifyOnlineAvatarFragment;
    private DeleteOnlineFragment deleteOnlineFragment;
    public FloatingActionButton fbtnAdd;
    public Bundle fromOnlineDetail;
    private Button btnDelete;
    public ImageView ivAvatar;
    public Bitmap imageBitmap;
    public Integer onlineId;
    public Boolean flagFirstFragment = false;
    public OnlineModificationServer onlineModificationServer = new OnlineModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    public List<Integer> invitedMembers = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_modification);
        fromOnlineDetail = getIntent().getExtras();

        assert fromOnlineDetail != null;
        onlineId = fromOnlineDetail.getInt("onlineId");
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
        fbtnAdd = findViewById(R.id.addButton);
        btnDelete = findViewById(R.id.modifySave);

        ivAvatar.setImageResource(R.drawable.ic_add_plus);

        if (fromOnlineDetail.getBoolean("modification")) {
            if (!Objects.equals(fromOnlineDetail.getString("image"), "")) {
                Picasso.with(this).load(fromOnlineDetail.getString("image")).into(ivAvatar);
            }
            etName.setText(fromOnlineDetail.getString("name"));
            etDescription.setText(fromOnlineDetail.getString("description"));
            etPhone.setText(fromOnlineDetail.getString("phone"));
            String[] dateHour = Objects.requireNonNull(fromOnlineDetail.getString("date")).split(" ");
            String[] date = dateHour[0].split("-");
            String[] hour = dateHour[1].split(":");
            dpDate.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            tpTime.setCurrentHour(Integer.parseInt(hour[0]));
            tpTime.setCurrentMinute(Integer.parseInt(hour[1]));
            spinner.setSelection(CommonMethods.fromPlatformNameToPlatformId(Objects.requireNonNull(fromOnlineDetail.getString("platform")), OnlineModificationActivity.this));
            onlineModificationServer.getOnlineDetailMore(this, fromOnlineDetail.getInt("onlineId"));
        }

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
        fbtnAdd.setImageResource(R.drawable.ic_check);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (fromOnlineDetail.getBoolean("modification")) {
                    imageBitmap = ((BitmapDrawable)ivAvatar.getDrawable()).getBitmap();
                }
                if (imageBitmap != null) {
                    if (!String.valueOf(etName.getText()).equals("")) {
                        if (platform > 0) {
                            if (platform != 6) {
                                if (!String.valueOf(etPhone.getText()).equals("")) {
                                    ownerMembers.clear();
                                    ownerMembers.add(SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    try {
                                        onlineModificationServer.sendModificationsToServer(OnlineModificationActivity.this,
                                                ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                                                createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                                                invitedMembers, platform, String.valueOf(etPhone.getText()), String.valueOf(etKey.getText()), imageBitmap, onlineId);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(OnlineModificationActivity.this, R.string.selectPhonePlease, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(OnlineModificationActivity.this, R.string.selectNotPresentialPlease, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(OnlineModificationActivity.this, R.string.selectPlatformPlease, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(OnlineModificationActivity.this, R.string.selectNamePlease, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OnlineModificationActivity.this, R.string.choseImagePlease, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteOnlineFragment==null) {
                    deleteOnlineFragment = new DeleteOnlineFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.deleteFragment, deleteOnlineFragment, "DeleteFragment").commit();
                }
            }
        });
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    public void killAvatarFragment() {
        if (modifyOnlineAvatarFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
            modifyOnlineAvatarFragment = null;
        }
        if (deleteOnlineFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("DeleteFragment"))).commit();
            deleteOnlineFragment = null;
        }
    }
    @Override
    public void OnOnlineInviteMemberClick(int position) {
        invitedMembers.add(onlineModificationServer.getSearchResponse().getUserResponses().get(position).getId());
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
    public void toOnlineList() {
        Intent toOnlineList = new Intent(OnlineModificationActivity.this, OnlineListActivity.class);
        startActivity(toOnlineList);
        finish();
    }
}