package com.tatvasoft.tatvasoftassignment7.AsyncTaskClass;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment;
import com.tatvasoft.tatvasoftassignment7.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.markerOptions;
import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.myGoogleMap;
import static com.tatvasoft.tatvasoftassignment7.Fragment.GoogleMapFragment.myLatLng;

public class CityNameTask extends AsyncTask<String,String,String> {

    private final static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String API_ID = "fae7190d7e6433ec3a45285ffcf55c86";
    HttpURLConnection connection;
    InputStream inputStream;

    private final WeakReference<Context> contextRef;

    public CityNameTask(Context context) {
        contextRef = new WeakReference<Context>(context);
    }

    @Override
    protected String doInBackground(String... strings) {
        connection = null;
        inputStream = null;

        try {
            URL url = new URL(String.format("%s%s&APPID=%s", BASE_URL, strings[0], API_ID));
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuilder stringBuilder = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;
            while ((line = bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line).append("\n");
            }
            inputStream.close();
            connection.disconnect();

            return stringBuilder.toString();

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return "";
    }

    @Override
    protected void onPostExecute(String s) {

        String cityName;
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            cityName=jsonObject.getString("name");

            markerOptions.position(myLatLng);
            myGoogleMap.addMarker(markerOptions);
            myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 7));
            GoogleMapFragment.bookmarkCity = cityName;

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(contextRef.get(), R.string.select_proper_city,Toast.LENGTH_SHORT).show();

        }

    }

}
