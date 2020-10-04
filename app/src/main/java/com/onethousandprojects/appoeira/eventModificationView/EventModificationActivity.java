package com.onethousandprojects.appoeira.eventModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
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
import com.onethousandprojects.appoeira.eventDetailMoreView.EventDetailMoreActivity;
import com.onethousandprojects.appoeira.eventModificationView.adapters.MyEventModificationConvideMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventModificationView.adapters.MyEventModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventModificationView.fragments.ModifyEventAvatarFragment;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.adapters.MyRodaModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.ModifyRodaAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.EventModificationServer;
import com.onethousandprojects.appoeira.serverStuff.methods.RodaModificationServer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class EventModificationActivity extends AppCompatActivity implements MyEventModificationInviteMembersRecyclerViewAdapter.OnEventModificationInviteMembersListener,
        MyEventModificationConvideMembersRecyclerViewAdapter.OnEventModificationConvideMembersListener,
        OnMapReadyCallback {
    private ModifyEventAvatarFragment modifyEventAvatarFragment;
    public String newUrl = "";
    public ImageView ivAvatar;
    private Button btnSave;
    private CustomScrollView nsvModifView;
    public EventModificationServer eventModificationServer = new EventModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    private List<Integer> invitedMembers = new ArrayList<>();
    private List<Integer> convidedMembers = new ArrayList<>();
    private LinearLayout llMapFragment;
    private Double latitude, longitude;
    private TextView address;
    Geocoder geocoder;
    private GoogleMap myGoogleMap;
    private Integer platform;
    AdapterView.OnItemSelectedListener spinnerPlatform = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            platform = i;
            if (platform == 6) {
                llMapFragment.setVisibility(View.VISIBLE);
            } else {
                llMapFragment.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            platform = 0;
        }
    };
    String origin = "EventModificationActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            EventModificationActivity.this,
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
        setContentView(R.layout.activity_event_modification);

        nsvModifView = findViewById(R.id.modifScrollView);
        ivAvatar = findViewById(R.id.avatar);
        TextView tvModifyAvatar = findViewById(R.id.modifyAvatar);
        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.description);
        EditText etPhone = findViewById(R.id.phone);
        EditText etKey = findViewById(R.id.key);
        DatePicker dpDate = findViewById(R.id.date);
        TimePicker tpTime = findViewById(R.id.hour);
        SearchView svUsers = findViewById(R.id.searchUsers);
        SearchView svConvided = findViewById(R.id.searchConvided);
        llMapFragment = findViewById(R.id.mapFragmentLayout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);
        Spinner spinner = findViewById(R.id.platformSpinner);
        spinner.setOnItemSelectedListener(spinnerPlatform);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.platforms_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        address = findViewById(R.id.locationAddress);
        btnSave = findViewById(R.id.modifySave);

        llMapFragment.setVisibility(View.GONE);

        mapFragment.getMapAsync(EventModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_event);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).
                    transform(new CommonMethods.CircleTransform()).
                    into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyEventAvatarFragment==null) {
                    modifyEventAvatarFragment = new ModifyEventAvatarFragment();
                    btnSave.setVisibility(View.GONE);
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyEventAvatarFragment, "ModifyAvatarFragment").commit();
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
                    eventModificationServer.sendUserSearchToServer(EventModificationActivity.this, s, false);
                } else {
                    if (eventModificationServer.createdFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("UserListFragment"))).commit();
                        eventModificationServer.createdFragment = false;
                    }
                }
                return false;
            }
        });
        svConvided.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.equals("")) {
                    eventModificationServer.sendUserSearchToServer(EventModificationActivity.this, s, true);
                } else {
                    if (eventModificationServer.createdFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ConvidedListFragment"))).commit();
                        eventModificationServer.createdFragment = false;
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
                eventModificationServer.sendModificationsToServer(EventModificationActivity.this,
                        ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                        createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                        newUrl, invitedMembers, latitude, longitude, String.valueOf(etPhone.getText()), convidedMembers,
                        String.valueOf(etKey.getText()), platform);
            }
        });
    }
    public void killAvatarFragment() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
        modifyEventAvatarFragment = null;
        btnSave.setVisibility(View.VISIBLE);
    }
    public void chargeImage(){
        Picasso.with(this).load(newUrl).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatarImage(ivAvatar));
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    @Override
    public void OnEventInviteMemberClick(int position) {
        invitedMembers.add(eventModificationServer.getUserSearchResponse().get(position).getId());
    }
    @Override
    public void OnEventConvideMemberClick(int position) {
        convidedMembers.add(eventModificationServer.getUserSearchResponse().get(position).getId());
    }
    public void addInvited(Integer id, boolean membersConv) {
        if (!membersConv) {
            invitedMembers.add(id);
        } else {
            convidedMembers.add(id);
        }
    }
    public void deleteInvited(Integer id, boolean membersConv) {
        if (!membersConv) {
            for (int i = 0; i < invitedMembers.size(); i++) {
                if (invitedMembers.get(i).equals(id)) {
                    invitedMembers.remove(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < convidedMembers.size(); i++) {
                if (convidedMembers.get(i).equals(id)) {
                    convidedMembers.remove(i);
                    break;
                }
            }
        }
    }
    public boolean checkMembers(Integer id, boolean membersConv) {
        if (!membersConv) {
            for (int i = 0; i < invitedMembers.size(); i++) {
                if (invitedMembers.get(i).equals(id)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < convidedMembers.size(); i++) {
                if (convidedMembers.get(i).equals(id)) {
                    return true;
                }
            }
            return false;
        }
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
                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My event"));
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