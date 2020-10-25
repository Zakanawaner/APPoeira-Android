package com.onethousandprojects.appoeira.rodaModificationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.onethousandprojects.appoeira.eventModificationView.EventModificationActivity;
import com.onethousandprojects.appoeira.onlineModificationView.OnlineModificationActivity;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;
import com.onethousandprojects.appoeira.rodaModificationView.adapters.MyRodaModificationInviteMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.DeleteRodaFragment;
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
    private DeleteRodaFragment deleteRodaFragment;
    public FloatingActionButton fbtnAdd;
    private Button btnDelete;
    public ImageView ivAvatar;
    public Bitmap imageBitmap;
    public Integer rodaId;
    public Boolean flagFirstFragment = false;
    private CustomScrollView nsvModifView;
    public RodaModificationServer rodaModificationServer = new RodaModificationServer();
    private List<Integer> ownerMembers = new ArrayList<>();
    public List<Integer> invitedMembers = new ArrayList<>();
    private Double latitude, longitude;
    public Bundle fromRodaDetail;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roda_modification);
        fromRodaDetail = getIntent().getExtras();

        assert fromRodaDetail != null;
        rodaId = fromRodaDetail.getInt("rodaId");
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
        fbtnAdd = findViewById(R.id.addButton);
        btnDelete = findViewById(R.id.modifySave);

        mapFragment.getMapAsync(RodaModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());

        ivAvatar.setImageResource(R.drawable.ic_add_plus);

        if (fromRodaDetail.getBoolean("modification")) {
            if (!Objects.equals(fromRodaDetail.getString("image"), "")) {
                Picasso.with(this).load(fromRodaDetail.getString("image")).into(ivAvatar);
            }
            etName.setText(fromRodaDetail.getString("name"));
            etDescription.setText(fromRodaDetail.getString("description"));
            etPhone.setText(fromRodaDetail.getString("phone"));
            String[] dateHour = Objects.requireNonNull(fromRodaDetail.getString("date")).split(" ");
            String[] date = dateHour[0].split("-");
            String[] hour = dateHour[1].split(":");
            dpDate.updateDate(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
            tpTime.setCurrentHour(Integer.parseInt(hour[0]));
            tpTime.setCurrentMinute(Integer.parseInt(hour[1]));
            rodaModificationServer.getRodaDetailMore(this, fromRodaDetail.getInt("rodaId"));
        }

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
        fbtnAdd.setImageResource(R.drawable.ic_check);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (fromRodaDetail.getBoolean("modification")) {
                    imageBitmap = ((BitmapDrawable)ivAvatar.getDrawable()).getBitmap();
                }
                if (imageBitmap != null) {
                    if (!String.valueOf(etName.getText()).equals("")) {
                        if (latitude != null) {
                            if (!String.valueOf(etPhone.getText()).equals("")) {
                                ownerMembers.clear();
                                ownerMembers.add(SharedPreferencesManager.getIntegerValue(Constants.ID));
                                try {
                                    rodaModificationServer.sendModificationsToServer(RodaModificationActivity.this,
                                            ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                                            createDate(dpDate.getYear(), dpDate.getMonth(), dpDate.getDayOfMonth(), tpTime.getHour(), tpTime.getMinute()),
                                            invitedMembers, latitude, longitude, String.valueOf(etPhone.getText()), imageBitmap, rodaId);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(RodaModificationActivity.this, R.string.selectPhonePlease, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RodaModificationActivity.this, R.string.selectPlacePlease, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RodaModificationActivity.this, R.string.selectNamePlease, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RodaModificationActivity.this, R.string.choseImagePlease, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteRodaFragment==null) {
                    deleteRodaFragment = new DeleteRodaFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.deleteFragment, deleteRodaFragment, "DeleteFragment").commit();
                }
            }
        });
    }
    public void killAvatarFragment() {
        if (modifyRodaAvatarFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
            modifyRodaAvatarFragment = null;
        }
        if (deleteRodaFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("DeleteFragment"))).commit();
            deleteRodaFragment = null;
        }
    }
    private String createDate(Integer year, Integer month, Integer day, Integer hour, Integer minute) {
        return year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }
    @Override
    public void OnRodaInviteMemberClick(int position) {
        invitedMembers.add(rodaModificationServer.getSearchResponse().getUserResponses().get(position).getId());
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
                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My roda"));
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
        if (fromRodaDetail.getBoolean("modification")) {
            latitude = fromRodaDetail.getDouble("latitude");
            longitude = fromRodaDetail.getDouble("longitude");
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
    public void toRodaList() {
        Intent toRodaList = new Intent(RodaModificationActivity.this, RodaListActivity.class);
        startActivity(toRodaList);
        finish();
    }
}