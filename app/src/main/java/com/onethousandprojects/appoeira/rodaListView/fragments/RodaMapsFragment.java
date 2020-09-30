package com.onethousandprojects.appoeira.rodaListView.fragments;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.VisibleRegion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;
import com.onethousandprojects.appoeira.R;
import com.onethousandprojects.appoeira.groupDetailView.GroupDetailActivity;
import com.onethousandprojects.appoeira.groupListView.GroupListActivity;
import com.onethousandprojects.appoeira.rodaListView.RodaListActivity;

public class RodaMapsFragment extends Fragment {
    private static final String TAG = "MyLogTag";
    private FusedLocationProviderClient fusedLocationClient;
    private Double distance;
    public GoogleMap rodasMap;
    GoogleMap.OnCameraIdleListener onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLongitude(rodasMap.getCameraPosition().target.longitude);
            location.setLatitude(rodasMap.getCameraPosition().target.latitude);
            distance = SphericalUtil.computeDistanceBetween(rodasMap.getProjection().getVisibleRegion().farLeft, rodasMap.getCameraPosition().target);
            RodaListActivity rodaListActivity = (RodaListActivity) requireActivity();
            rodaListActivity.rodaListServer.sendMapsLocationToServer(RodaMapsFragment.this, location, (int) Math.round(distance/1000));
        }
    };
    GoogleMap.OnInfoWindowClickListener mapListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Pair<String, Integer> pair = (Pair<String, Integer>) marker.getTag();
            Intent toGroupDetailActivity = new Intent(getContext(), GroupDetailActivity.class);
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
            TextView tvGroupName = v.findViewById(R.id.groupMarkerName);
            ImageView ivGroupAvatar = v.findViewById(R.id.groupMarkerAvatar);
            // TODO no carga la imagen
            //Picasso.with(v.getContext()).load(myResponse.get(pair.second).getPicUrl()).fit().into(ivGroupAvatar);
            tvGroupName.setText(marker.getTitle());
            return v;
        }
    };
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            rodasMap = googleMap;
            rodasMap.setOnInfoWindowClickListener(mapListener);
            rodasMap.setInfoWindowAdapter(infoWindowAdapter);
            rodasMap.setOnCameraIdleListener(onCameraIdleListener);
            get_location();
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_maps, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    private void get_location() {
        Task<Location> locationTask = fusedLocationClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //We have a location
                    rodasMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));
                    VisibleRegion visibleRegion = rodasMap.getProjection().getVisibleRegion();
                    distance = SphericalUtil.computeDistanceBetween(visibleRegion.farLeft, rodasMap.getCameraPosition().target);
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
}