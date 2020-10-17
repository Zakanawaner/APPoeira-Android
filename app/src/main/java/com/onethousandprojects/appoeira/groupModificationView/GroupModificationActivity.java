package com.onethousandprojects.appoeira.groupModificationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.android.SphericalUtil;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.CustomScrollView;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.rodaModificationView.RodaModificationActivity;
import com.onethousandprojects.appoeira.rodaModificationView.fragments.ModifyRodaAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.groupList.ClientLocationGroupsRequest;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.serverStuff.methods.RodaModificationServer;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Client;
import com.onethousandprojects.appoeira.serverStuff.serverAndClient.Server;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupModificationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MyLogTag";
    private FusedLocationProviderClient fusedLocationClient;

    private List<ServerLocationGroupResponse> serverLocationGroupMapsResponse;
    public Client Client;
    public Server Server;

    private ModifyRodaAvatarFragment modifyRodaAvatarFragment;
    public ImageView ivAvatar;
    public Bitmap imageBitmap;
    public Integer groupId;
    private Button btnSave;
    private CustomScrollView nsvModifView;
    private MapFragment mapFragment;
    private Double latitude, longitude;
    private TextView address;
    Geocoder geocoder;
    private GoogleMap myGoogleMap;
    public RodaModificationServer rodaModificationServer = new RodaModificationServer();
    private Double distance;
    public GoogleMap groupsMap;
    String origin = "GroupModificationActivity";
    NavParams navParams = new NavParams(
            null,
            null,
            null,
            GroupModificationActivity.this,
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
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);
    GoogleMap.OnCameraIdleListener onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLongitude(groupsMap.getCameraPosition().target.longitude);
            location.setLatitude(groupsMap.getCameraPosition().target.latitude);
            distance = SphericalUtil.computeDistanceBetween(groupsMap.getProjection().getVisibleRegion().farLeft, groupsMap.getCameraPosition().target);
            ClientLocationGroupsRequest clientLocationGroupsRequest = new ClientLocationGroupsRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), (int) Math.round(distance/1000));
            Call<List<ServerLocationGroupResponse>> call = Server.post_location_groups(clientLocationGroupsRequest);
            call.enqueue(new Callback<List<ServerLocationGroupResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ServerLocationGroupResponse>> call, @NonNull Response<List<ServerLocationGroupResponse>> response) {
                    if (response.isSuccessful()){
                        serverLocationGroupMapsResponse = response.body();
                        assert serverLocationGroupMapsResponse != null;
                        for(int i = 0; i < serverLocationGroupMapsResponse.size(); i++) {
                            // googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(image)).position(marker).title(myResponse.get(i).getName()));
                            Marker marker = groupsMap.addMarker(new MarkerOptions().position(new LatLng(serverLocationGroupMapsResponse.get(i).getLatitude(), serverLocationGroupMapsResponse.get(i).getLongitude())).title(serverLocationGroupMapsResponse.get(i).getName()));
                            Pair<String, Integer> pair = new Pair<> (serverLocationGroupMapsResponse.get(i).getPicUrl(), serverLocationGroupMapsResponse.get(i).getId());
                            marker.setTag(pair);
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<List<ServerLocationGroupResponse>> call, @NonNull Throwable t) {
                }
            });
        }
    };
    GoogleMap.OnInfoWindowClickListener mapListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Pair<String, Integer> pair = (Pair<String, Integer>) marker.getTag();
            Intent toGroupDetailActivity = new Intent(GroupModificationActivity.this, GroupDetailActivity.class);
            assert pair != null;
            toGroupDetailActivity.putExtra("id", pair.second);
            startActivity(toGroupDetailActivity);
        }
    };
    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.adapter_info_window_marker, null);
            Pair<String, Integer> pair = (Pair<String, Integer>) marker.getTag();
            TextView tvGroupName = v.findViewById(R.id.markerName);
            ImageView ivGroupAvatar = v.findViewById(R.id.markerAvatar);
            // TODO no carga la imagen
            assert pair != null;
            Picasso.with(v.getContext()).load(pair.first).fit().into(ivGroupAvatar);
            tvGroupName.setText(marker.getTitle());
            return v;
        }
    };
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            groupsMap = googleMap;
            groupsMap.setOnInfoWindowClickListener(mapListener);
            groupsMap.setInfoWindowAdapter(infoWindowAdapter);
            groupsMap.setOnCameraIdleListener(onCameraIdleListener);
            get_location();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_modification);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Bundle fromGroupDetail = getIntent().getExtras();

        Client = Client.getInstance();
        Server = Client.getServer();

        assert fromGroupDetail != null;
        groupId = fromGroupDetail.getInt("groupId");
        nsvModifView = findViewById(R.id.modifScrollView);
        ivAvatar = findViewById(R.id.avatar);
        TextView tvModifyAvatar = findViewById(R.id.modifyAvatar);
        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.description);
        EditText etPhone = findViewById(R.id.phone);
        DatePicker dpDate = findViewById(R.id.date);
        TimePicker tpTime = findViewById(R.id.hour);
        SearchView svUsers = findViewById(R.id.searchUsers);
        MapFragment mapFragment2 = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);
        address = findViewById(R.id.locationAddress);
        btnSave = findViewById(R.id.modifySave);
        Button etNewGroupURL = findViewById(R.id.newGroupBtn);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        mapFragment2.getMapAsync(GroupModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_group);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        etNewGroupURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nsvModifView.setVisibility(View.VISIBLE);
                mapFragment.onDestroy();
            }
        });
    }
    private void get_location() {
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //We have a location
                    groupsMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));
                    VisibleRegion visibleRegion = groupsMap.getProjection().getVisibleRegion();
                    distance = SphericalUtil.computeDistanceBetween(visibleRegion.farLeft, groupsMap.getCameraPosition().target);
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }
    public void killAvatarFragment() {
        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("ModifyAvatarFragment"))).commit();
        modifyRodaAvatarFragment = null;
        btnSave.setVisibility(View.VISIBLE);
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
    }
}