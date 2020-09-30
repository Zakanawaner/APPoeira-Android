package com.onethousandprojects.appoeira.groupListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.authView.LoginActivity;
import com.onethousandprojects.appoeira.commonThings.NavParams;
import com.onethousandprojects.appoeira.commonThings.CommonMethods;
import com.onethousandprojects.appoeira.commonThings.Constants;
import com.onethousandprojects.appoeira.commonThings.SharedPreferencesManager;
import com.onethousandprojects.appoeira.groupDetailMoreView.GroupDetailMoreActivity;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupListView.adapter.MyGroupListRecyclerViewAdapter;
import com.onethousandprojects.appoeira.groupModificationView.GroupModificationActivity;
import com.onethousandprojects.appoeira.serverStuff.methods.GroupListServer;
import com.onethousandprojects.appoeira.serverStuff.groupList.ServerLocationGroupResponse;
import com.onethousandprojects.appoeira.getPermissionsView.GetPermissionsActivity;
import com.squareup.picasso.Picasso;

public class GroupListActivity extends AppCompatActivity implements MyGroupListRecyclerViewAdapter.OnGroupListener {
    private FusedLocationProviderClient fusedLocationClient;
    public GroupListServer groupListServer = new GroupListServer();
    public SwipeRefreshLayout srGroupList;
    public SeekBar sbDistance;
    public FloatingActionButton fbtnDistance, fbtnAdd;
    public TextView tvDistance;
    private boolean showingDistance = false;
    public boolean toggle_menu = false;
    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            get_location();
        }
    };
    String origin = "GroupListActivity";
    NavParams navParams = new NavParams(
            GroupListActivity.this,
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
            null,
            null,
            null,
            null);
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = CommonMethods.BottomNavigationMenuHandler(origin, navParams);
    private MaterialToolbar.OnMenuItemClickListener topNavListener = CommonMethods.TopNavigationMenuHandler(origin, navParams);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        sbDistance = findViewById(R.id.distanceBar);
        sbDistance.setProgress(10);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            get_location();
        } else {
            Bundle fromGetPermissionsActivity = getIntent().getExtras();
            if (fromGetPermissionsActivity == null) {
                Intent toGetPermissionsActivity = new Intent(this, GetPermissionsActivity.class);
                startActivity(toGetPermissionsActivity);
            } else {
                ask_location_permission();
            }
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.groups_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener);

        ActionMenuItemView ivTopMenuLogin = (ActionMenuItemView) findViewById(R.id.login);
        MaterialToolbar topNavigationView = findViewById(R.id.topMenu);
        topNavigationView.setTitle(R.string.page_title_group_list);
        topNavigationView.setOnMenuItemClickListener(topNavListener);
        if (CommonMethods.AmILogged()) {
            Picasso.with(this).load(SharedPreferencesManager.getStringValue(Constants.PIC_URL)).transform(new CommonMethods.CircleTransform()).into(CommonMethods.GetTarGetForAvatar(ivTopMenuLogin));
        }

        fbtnDistance = findViewById(R.id.distanceButton);
        tvDistance = findViewById(R.id.tvDistanceInKm);
        sbDistance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tvDistance.setText(i + "Km");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                get_location();
            }
        });
        fbtnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showingDistance) {
                    showingDistance = true;
                    sbDistance.setVisibility(View.VISIBLE);
                    tvDistance.setVisibility(View.VISIBLE);
                    fbtnDistance.setImageDrawable(null);
                    tvDistance.setText(String.valueOf(sbDistance.getProgress()) + "Km");
                } else {
                    showingDistance = false;
                    sbDistance.setVisibility(View.INVISIBLE);
                    tvDistance.setVisibility(View.INVISIBLE);
                    fbtnDistance.setImageResource(R.drawable.ic_road);
                }
            }
        });
        fbtnAdd = findViewById(R.id.addButton);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCreateGroup = new Intent(GroupListActivity.this, GroupModificationActivity.class);
                startActivity(toCreateGroup);
            }
        });
        srGroupList = findViewById(R.id.swipeRefreshList);
        srGroupList.setOnRefreshListener(onRefreshListener);
    }
    private void get_location() {
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //We have a location
                    groupListServer.sendLocationToServer(GroupListActivity.this, location, sbDistance.getProgress());
                } else {
                    Toast.makeText(GroupListActivity.this, "Problema al obtener la localización", Toast.LENGTH_SHORT).show();
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(Constants.TAG, "onFailure: " + e.getLocalizedMessage());
            }
        });
    }
    private void ask_location_permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_REQUEST_CODE);
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_REQUEST_CODE);
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission granted
                get_location();
            }
        }
    }
    @Override
    public void OnGroupClick(int position) {
        if (CommonMethods.AmILogged()) {
            ServerLocationGroupResponse group = groupListServer.getServerLocationGroupResponse().get(position);
            Intent toGroupDetailActivity = new Intent(this, GroupDetailActivity.class);
            toGroupDetailActivity.putExtra("id", group.getId());
            startActivity(toGroupDetailActivity);
        } else {
            Intent toLogin = new Intent(this, LoginActivity.class);
            startActivity(toLogin);
        }
    }
}
