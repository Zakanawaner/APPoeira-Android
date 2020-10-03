package com.onethousandprojects.appoeira.rodaModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.CustomScrollView;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.rodaModificationView.adapters.MyRodaModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.ModifyRodaAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.RodaModificationServer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RodaModificationActivity extends AppCompatActivity implements MyRodaModificationInviteMembersRecyclerViewAdapter.OnRodaModificationInviteMembersListener,
        OnMapReadyCallback {
    private ModifyRodaAvatarFragment modifyRodaAvatarFragment;
    public String newUrl = "";
    public ImageView ivAvatar;
    private Button btnSave;
    private CustomScrollView nsvModifView;
    public RodaModificationServer rodaModificationServer = new RodaModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    private List<Integer> invitedMembers = new ArrayList<>();
    private Double latitude, longitude;
    private TextView address;
    Geocoder geocoder;
    private GoogleMap myGoogleMap;
    String origin = "RodaModificationActivity";
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
            RodaModificationActivity.this,
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
        setContentView(R.layout.activity_roda_modification);

        nsvModifView = findViewById(R.id.modifScrollView);
        ivAvatar = findViewById(R.id.avatar);
        TextView tvModifyAvatar = findViewById(R.id.modifyAvatar);
        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.description);
        EditText etPhone = findViewById(R.id.phone);
        DatePicker dpDate = findViewById(R.id.date);
        TimePicker tpTime = findViewById(R.id.hour);
        SearchView svUsers = findViewById(R.id.searchUsers);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);
        address = findViewById(R.id.locationAddress);
        btnSave = findViewById(R.id.modifySave);

        mapFragment.getMapAsync(RodaModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_roda);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }
        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyRodaAvatarFragment==null) {
                    modifyRodaAvatarFragment = new ModifyRodaAvatarFragment();
                    btnSave.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyRodaAvatarFragment, "ModifyAvatarFragment").commit();
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
                    rodaModificationServer.sendUserSearchToServer(RodaModificationActivity.this, s);
                } else {
                    if (rodaModificationServer.createdFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        rodaModificationServer.createdFragment = false;
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
                rodaModificationServer.sendModificationsToServer(RodaModificationActivity.this,
                        ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                        createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                        newUrl, invitedMembers, latitude, longitude, String.valueOf(etPhone.getText()));
            }
        });
    }
    public void killAvatarFragment() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
        modifyRodaAvatarFragment = null;
        btnSave.setVisibility(View.VISIBLE);
    }
    public void chargeImage(){
        Picasso.with(this).load(newUrl).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatarImage(ivAvatar));
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    @Override
    public void OnRodaInviteMemberClick(int position) {
        invitedMembers.add(rodaModificationServer.getUserSearchResponse().get(position).getId());
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        myGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                myGoogleMap.clear();
                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Mi roda"));
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        address.setText(addresses.get(0).getAddressLine(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        myGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                nsvModifView.setEnableScrolling(false);
            }
        });
        myGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                nsvModifView.setEnableScrolling(true);
            }
        });
    }
}