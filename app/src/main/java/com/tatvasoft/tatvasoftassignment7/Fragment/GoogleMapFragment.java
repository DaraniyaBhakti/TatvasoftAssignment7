package com.tatvasoft.tatvasoftassignment7.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tatvasoft.tatvasoftassignment7.AsyncTaskClass.CityBackgroundTask;
import com.tatvasoft.tatvasoftassignment7.DatabaseHelper.Database;
import com.tatvasoft.tatvasoftassignment7.R;

import java.util.List;
import java.util.Locale;


public class GoogleMapFragment extends Fragment {

    public static String bookmarkCity="";
    public static String cityName="";
    private Database database;
    public static GoogleMap myGoogleMap;
    public static LatLng myLatLng;
    public static MarkerOptions markerOptions = new MarkerOptions();

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
        return inflater.inflate(R.layout.fragment_google_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button bookmarkButton = view.findViewById(R.id.bookMarkButton);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(supportMapFragment!=null)
        {
            supportMapFragment.getMapAsync(googleMap -> {
                myGoogleMap = googleMap;
                myGoogleMap.clear();
                myGoogleMap.setOnMapClickListener((LatLng latLng) -> {
                    myLatLng = latLng;
                    Geocoder geocoder = new Geocoder(GoogleMapFragment.this.getContext(), Locale.getDefault());
                    List<Address> addressList;
                    try {
                        if (geocoder.getFromLocation(myLatLng.latitude, myLatLng.longitude, 1) != null) {
                            addressList = geocoder.getFromLocation(myLatLng.latitude, myLatLng.longitude, 1);
                            if (addressList.size() > 0 && (addressList.get(0).getLocality()) != null) {
                                cityName = addressList.get(0).getLocality();
                                CityBackgroundTask cityBackgroundTask = new CityBackgroundTask(getContext());
                                cityBackgroundTask.execute(cityName);

                            } else {
                                Toast.makeText(GoogleMapFragment.this.getContext(), GoogleMapFragment.this.getString(R.string.toast_select_proper_location), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(GoogleMapFragment.this.getContext(), GoogleMapFragment.this.getString(R.string.toast_select_proper_area), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(GoogleMapFragment.this.getContext(), GoogleMapFragment.this.getString(R.string.toast_enable_permission), Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }else
        {
            Log.d("map","null");
        }

        bookmarkButton.setOnClickListener(view1 -> {
            if (!bookmarkCity.isEmpty()) {
                database.insertData(bookmarkCity + "");
                Toast.makeText(getContext(), String.format("%s is bookmarked", bookmarkCity), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GoogleMapFragment.this.getContext(), R.string.toast_bookmark_null, Toast.LENGTH_SHORT).show();
            }
            bookmarkCity = "";

        });

    }


}