package com.tatvasoft.tatvasoftassignment7;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GoogleMapFragment extends Fragment {

    private ArrayList<String> cityNamesList = new ArrayList<>();
    private FusedLocationProviderClient client;
    private String bookmarkCity="";
    private String cityName="";
    double currentLatitude;
    double currentLongitude;
    private Database database;

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_map, container, false);
        getActivity().setTitle(getString(R.string.title_map));
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
        return view;
    }
    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManagerCompat = (LocationManager)getActivity().
                getSystemService(Context.LOCATION_SERVICE);
        if(locationManagerCompat.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManagerCompat.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                if(location != null){
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                }else {
                    LocationRequest locationRequest = new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(1000)
                            .setNumUpdates(1);

                    LocationCallback locationCallback = new LocationCallback(){
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            currentLatitude = location1.getLatitude();
                            currentLongitude = location1.getLongitude();
                        }
                    };
                    client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                }
            });

        }else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bookmarkButton = view.findViewById(R.id.bookMarkButton);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(supportMapFragment!=null)
        {
            supportMapFragment.getMapAsync(googleMap -> {
                 googleMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng currentLatLng = new LatLng(20.5937,78.9629);
                markerOptions.position(currentLatLng);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,5));
                googleMap.setOnMapClickListener(latLng -> {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addressList = null;
                    try {
                        if(geocoder.getFromLocation(latLng.latitude,latLng.longitude,1)!=null) {
                            addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                            if (addressList.size() > 0 && (addressList.get(0).getLocality()) != null) {
                                cityName = addressList.get(0).getLocality();
                                new CityNameTask().execute(cityName);
                                if(CityNameTask.isCityPresent){
                                    markerOptions.position(latLng);
                                    googleMap.addMarker(markerOptions);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
                                    bookmarkCity = cityName;
                                }else {
                                    Toast.makeText(getContext(), getString(R.string.toast_select_proper_city),Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getContext(), getString(R.string.toast_select_proper_location), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(),getString(R.string.toast_select_proper_area), Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), getString(R.string.toast_enable_permission), Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }else
        {
            Log.d("map","null");
        }

        bookmarkButton.setOnClickListener(view1 -> {
            cityNamesList.add(bookmarkCity);
            database.insertData(bookmarkCity+"");
            Toast.makeText(getContext(), String.format("%s is bookmarked", bookmarkCity),Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 && (grantResults.length>0) &&
                (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            getCurrentLocation();
        }else {
            Toast.makeText(getActivity(), getString(R.string.toast_permission_denied),Toast.LENGTH_SHORT).show();
        }
    }

}