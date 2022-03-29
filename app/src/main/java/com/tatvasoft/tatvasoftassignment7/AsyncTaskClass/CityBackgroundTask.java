package com.tatvasoft.tatvasoftassignment7.AsyncTaskClass;

import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.markerOptions;
import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.myGoogleMap;
import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.myLatLng;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment;
import com.tatvasoft.tatvasoftassignment7.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CityBackgroundTask extends BackgroundTask{

    private final Context context;

    public CityBackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String string) {
        return super.doInBackground(string);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String cityName;
        try {
            JSONObject jsonObject = new JSONObject(s);
            cityName=jsonObject.getString("name");

            markerOptions.position(myLatLng);
            myGoogleMap.addMarker(markerOptions);
            myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 7));
            GoogleMapFragment.bookmarkCity = cityName;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, R.string.select_proper_city,Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
