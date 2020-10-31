package com.onethousandprojects.appoeira.groupModificationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.SphericalUtil;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.CustomScrollView;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.groupModificationView.adapters.MyGroupModificationFrontlineMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupModificationView.adapters.MyGroupModificationStudentsMembersRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupModificationView.fragments.DeleteGroupFragment;
import com.onethousandprojects.appoeira.groupModificationView.fragments.ModifyGroupAvatarFragment;
import com.onethousandprojects.appoeira.serverStuff.methods.GroupModificationServer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GroupModificationActivity extends AppCompatActivity implements MyGroupModificationFrontlineMembersRecyclerViewAdapter.OnGroupModificationFrontlineMembersListener,
        MyGroupModificationStudentsMembersRecyclerViewAdapter.OnGroupModificationStundentMembersListener,
        OnMapReadyCallback {

    private static final String TAG = "MyLogTag";
    private FusedLocationProviderClient fusedLocationClient;
    private ModifyGroupAvatarFragment modifyGroupAvatarFragment;
    public ImageView ivAvatar;
    public Bitmap imageBitmap;
    public Integer groupId;
    public Bundle fromGroupDetail;
    public Boolean flagFirstFragment = false;
    public boolean flagSecondFragment = false;
    private DeleteGroupFragment deleteGroupFragment;
    public FloatingActionButton fbtnAdd;
    private CustomScrollView nsvModifView;
    private Double latitude, longitude;
    private List<Integer> ownerMembers = new ArrayList<>();
    public List<Integer> frontlineMembers = new ArrayList<>();
    public List<Integer> studentMembers = new ArrayList<>();
    private TextView address;
    Geocoder geocoder;
    private GoogleMap myGoogleMap;
    public GroupModificationServer groupModificationServer = new GroupModificationServer();
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
            groupModificationServer.getListOfGroupsFromMap(GroupModificationActivity.this, location, distance);
        }
    };
    GoogleMap.OnInfoWindowClickListener mapListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Pair<String, Integer> pair = (Pair<String, Integer>) marker.getTag();
            Intent toGroupDetailActivity = new Intent(GroupModificationActivity.this, GroupDetailActivity.class);
            assert pair != null;
            toGroupDetailActivity.putExtra("groupId", pair.second);
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
        fromGroupDetail = getIntent().getExtras();

        assert fromGroupDetail != null;
        groupId = fromGroupDetail.getInt("groupId");
        nsvModifView = findViewById(R.id.modifScrollView);
        ivAvatar = findViewById(R.id.avatar);
        TextView tvModifyAvatar = findViewById(R.id.modifyAvatar);
        EditText etName = findViewById(R.id.name);
        EditText etDescription = findViewById(R.id.description);
        EditText etUrl = findViewById(R.id.groupUrl);
        EditText etPhone = findViewById(R.id.phone);
        EditText etKey = findViewById(R.id.key);SearchView svUsers = findViewById(R.id.searchUsers);
        SearchView svConvided = findViewById(R.id.searchConvided);
        MapFragment mapFragment2 = (MapFragment) getFragmentManager().findFragmentById(R.id.detailMap);
        address = findViewById(R.id.locationAddress);
        Button btnDelete = findViewById(R.id.modifySave);
        Button btnNewGroup = findViewById(R.id.newGroupBtn);
        fbtnAdd = findViewById(R.id.addButton);

        ConstraintLayout findGroupLayout = findViewById(R.id.findGroupLayout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        mapFragment2.getMapAsync(GroupModificationActivity.this);
        geocoder = new Geocoder(this, Locale.getDefault());
        etKey.setVisibility(View.GONE);
        etUrl.setVisibility(View.VISIBLE);
        ivAvatar.setImageResource(R.drawable.ic_add_plus);

        if (fromGroupDetail.getBoolean("modification")) {
            if (fromGroupDetail.getString("image") != null) {
                Picasso.with(this).load(fromGroupDetail.getString("image")).fit().into(ivAvatar);
            }
            etName.setText(fromGroupDetail.getString("name"));
            etDescription.setText(fromGroupDetail.getString("description"));
            etUrl.setText(fromGroupDetail.getString("url"));
            etPhone.setText(fromGroupDetail.getString("phone"));
            nsvModifView.setVisibility(View.VISIBLE);
            findGroupLayout.setVisibility(View.GONE);
            groupModificationServer.getGroupDetailMore(this, fromGroupDetail.getInt("groupId"));
        }

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_update_group);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
            CommonMethods.NewsVariable bv = Constants.newsVariable;
            bv.setListener(new CommonMethods.NewsVariable.ChangeListener() {
                @Override
                public void onChange() {
                    if (bv.gotNews) {
                        topNavigationView.getMenu().getItem(2).setIcon(ContextCompat.getDrawable(GroupModificationActivity.this, R.drawable.ic_circle));
                    }
                }
            });
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.addItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        btnNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nsvModifView.setVisibility(View.VISIBLE);
                findGroupLayout.setVisibility(View.GONE);
            }
        });
        tvModifyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modifyGroupAvatarFragment==null) {
                    modifyGroupAvatarFragment = new ModifyGroupAvatarFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.modifyAvatarFragment, modifyGroupAvatarFragment, "ModifyAvatarFragment").commit();
                }
                if (deleteGroupFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("DeleteFragment"))).commit();
                    deleteGroupFragment = null;
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
                    groupModificationServer.sendUserSearchToServer(GroupModificationActivity.this, s, false);
                } else {
                    if (groupModificationServer.createdStudentsFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("StudentListFragment"))).commit();
                        groupModificationServer.createdStudentsFragment = false;
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
                    groupModificationServer.sendUserSearchToServer(GroupModificationActivity.this, s, true);
                } else {
                    if (groupModificationServer.createdFrontlineFragment) {
                        getSupportFragmentManager().beginTransaction().remove(Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("<frontlineListFragment"))).commit();
                        groupModificationServer.createdFrontlineFragment = false;
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
                if (fromGroupDetail.getBoolean("modification")) {
                    imageBitmap = ((BitmapDrawable)ivAvatar.getDrawable()).getBitmap();
                }
                if (imageBitmap != null) {
                    if (!String.valueOf(etName.getText()).equals("")) {
                        if (latitude != null) {
                            if (!String.valueOf(etPhone.getText()).equals("")) {
                                ownerMembers.clear();
                                ownerMembers.add(SharedPreferencesManager.getIntegerValue(Constants.ID));
                                try {
                                    groupModificationServer.sendModificationsToServer(GroupModificationActivity.this,
                                            ownerMembers, String.valueOf(etName.getText()), String.valueOf(etDescription.getText()),
                                            latitude, longitude, String.valueOf(etPhone.getText()),
                                            imageBitmap, groupId, String.valueOf(etUrl.getText()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(GroupModificationActivity.this, R.string.selectPhonePlease, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(GroupModificationActivity.this, R.string.selectPlacePlease, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(GroupModificationActivity.this, R.string.selectNamePlease, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GroupModificationActivity.this, R.string.choseImagePlease, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteGroupFragment==null) {
                    deleteGroupFragment = new DeleteGroupFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.deleteFragment, deleteGroupFragment, "DeleteFragment").commit();
                }
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
        modifyGroupAvatarFragment = null;
    }
    @Override
    public void OnGroupStudentMemberClick(int position) {
        studentMembers.add(groupModificationServer.getSearchResponse().getUserResponses().get(position).getId());
    }
    @Override
    public void OnGroupFrontlineMemberClick(int position) {
        frontlineMembers.add(groupModificationServer.getSearchResponse().getUserResponses().get(position).getId());
    }
    public void addInvited(Integer id, boolean studentsFrontline) {
        if (!studentsFrontline) {
            studentMembers.add(id);
        } else {
            frontlineMembers.add(id);
        }
    }
    public void deleteInvited(Integer id, boolean studentsFrontline) {
        if (!studentsFrontline) {
            for (int i = 0; i < studentMembers.size(); i++) {
                if (studentMembers.get(i).equals(id)) {
                    studentMembers.remove(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < frontlineMembers.size(); i++) {
                if (frontlineMembers.get(i).equals(id)) {
                    frontlineMembers.remove(i);
                    break;
                }
            }
        }
    }
    public boolean checkMembers(Integer id, boolean studentsFrontline) {
        if (!studentsFrontline) {
            for (int i = 0; i < studentMembers.size(); i++) {
                if (studentMembers.get(i).equals(id)) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 0; i < frontlineMembers.size(); i++) {
                if (frontlineMembers.get(i).equals(id)) {
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
                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("My group"));
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
        if (fromGroupDetail.getBoolean("modification") && !Objects.equals(fromGroupDetail.getString("address"), "")) {
            latitude = fromGroupDetail.getDouble("latitude");
            longitude = fromGroupDetail.getDouble("longitude");
            myGoogleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My group"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));
            try {
                List<Address> addressesToModify = geocoder.getFromLocation(latitude, longitude, 1);
                address.setText(addressesToModify.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void toGroupList() {
        Intent toGroupList = new Intent(GroupModificationActivity.this, GroupListActivity.class);
        startActivity(toGroupList);
        finish();
    }
}