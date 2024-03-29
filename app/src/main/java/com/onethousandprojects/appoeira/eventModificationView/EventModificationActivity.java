package com.onethousandprojects.appoeira.eventModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.CustomScrollView;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.eventListView.EventListActivity;
import com.onethousandprojects.appoeira.eventModificationView.adapters.MyEventModificationConvideMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventModificationView.adapters.MyEventModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.eventModificationView.fragments.DeleteEventFragment;
import com.onethousandprojects.appoeira.eventModificationView.fragments.ModifyEventAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.EventModificationServer;
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
    public ImageView ivAvatar;
    public Bundle fromEventDetail;
    public Bitmap imageBitmap;
    public Integer eventId;
    public Boolean flagFirstFragment = false;
    public Boolean flagSecondFragment = false;
    private DeleteEventFragment deleteEventFragment;
    public FloatingActionButton fbtnAdd;
    private CustomScrollView nsvModifView;
    public EventModificationServer eventModificationServer = new EventModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    public List<Integer> invitedMembers = new ArrayList<>();
    public List<Integer> convidedMembers = new ArrayList<>();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_modification);
        fromEventDetail = getIntent().getExtras();

        assert fromEventDetail != null;
        eventId = fromEventDetail.getInt("eventId");
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
        fbtnAdd = findViewById(R.id.addButton);
        Button btnDelete = findViewById(R.id.modifySave);

        llMapFragment.setVisibility(View.GONE);

        mapFragment.getMapAsync(EventModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());

        ivAvatar.setImageResource(R.drawable.ic_add_plus);

        if (fromEventDetail.getBoolean("modification")) {
            if (!Objects.equals(fromEventDetail.getString("image"), "")) {
                Picasso.with(this).load(fromEventDetail.getString("image")).into(ivAvatar);
            }
            etName.setText(fromEventDetail.getString("name"));
            etDescription.setText(fromEventDetail.getString("description"));
            etPhone.setText(fromEventDetail.getString("phone"));
            String[] dateHour = Objects.requireNonNull(fromEventDetail.getString("date")).split(" ");
            String[] date = dateHour[0].split("-");
            String[] hour = dateHour[1].split(":");
            dpDate.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            tpTime.setCurrentHour(Integer.parseInt(hour[0]));
            tpTime.setCurrentMinute(Integer.parseInt(hour[1]));
            spinner.setSelection(CommonMethods.fromPlatformNameToPlatformId(Objects.requireNonNull(fromEventDetail.getString("platform")), EventModificationActivity.this));
            if (CommonMethods.fromPlatformNameToPlatformId(Objects.requireNonNull(fromEventDetail.getString("platform")), EventModificationActivity.this).equals(6)) {
                llMapFragment.setVisibility(View.VISIBLE);
            }
            eventModificationServer.getEventDetailMore(this, fromEventDetail.getInt("eventId"));
        }

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
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(EventModificationActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }

        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyEventAvatarFragment==null) {
                    modifyEventAvatarFragment = new ModifyEventAvatarFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyEventAvatarFragment, "ModifyAvatarFragment").commit();
                }
                if (deleteEventFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("DeleteFragment"))).commit();
                    deleteEventFragment = null;
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
        fbtnAdd.setImageResource(R.drawable.ic_check);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (fromEventDetail.getBoolean("modification")) {
                    imageBitmap = ((BitmapDrawable)ivAvatar.getDrawable()).getBitmap();
                }
                if (imageBitmap != null) {
                    if (!String.valueOf(etName.getText()).equals("")) {
                        if (platform > 0) {
                            if ((platform == 6 && latitude != null) || platform < 6) {
                                if (!String.valueOf(etPhone.getText()).equals("")) {
                                    ownerMembers.clear();
                                    ownerMembers.add(SharedPreferencesManager.getIntegerValue(Constants.ID));
                                    try {
                                        eventModificationServer.sendModificationsToServer(EventModificationActivity.this,
                                                ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                                                createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                                                invitedMembers, latitude, longitude, String.valueOf(etPhone.getText()), convidedMembers,
                                                String.valueOf(etKey.getText()), platform, imageBitmap, eventId);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(EventModificationActivity.this, R.string.selectPhonePlease, Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(EventModificationActivity.this, R.string.selectPlacePlease, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EventModificationActivity.this, R.string.selectPlatformPlease, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EventModificationActivity.this, R.string.selectNamePlease, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EventModificationActivity.this, R.string.choseImagePlease, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteEventFragment==null) {
                    deleteEventFragment = new DeleteEventFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.deleteFragment, deleteEventFragment, "DeleteFragment").commit();
                }
            }
        });
    }
    public void killAvatarFragment() {
        if (modifyEventAvatarFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
            modifyEventAvatarFragment = null;
        }
        if (deleteEventFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("DeleteFragment"))).commit();
            deleteEventFragment = null;
        }
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    @Override
    public void OnEventInviteMemberClick(int position) {
        invitedMembers.add(eventModificationServer.getSearchResponse().getUserResponses().get(position).getId());
    }
    @Override
    public void OnEventConvideMemberClick(int position) {
        convidedMembers.add(eventModificationServer.getSearchResponse().getUserResponses().get(position).getId());
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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        if (fromEventDetail.getBoolean("modification") && !Objects.equals(fromEventDetail.getString("address"), "")) {
            latitude = fromEventDetail.getDouble("latitude");
            longitude = fromEventDetail.getDouble("longitude");
            myGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My roda"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
            try {
                List<Address> addressesToModify = geocoder.getFromLocation(latitude, longitude, 1);
                address.setText(addressesToModify.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void toEventList() {
        Intent toEventList = new Intent(EventModificationActivity.this, EventListActivity.class);
        startActivity(toEventList);
        finish();
    }
}