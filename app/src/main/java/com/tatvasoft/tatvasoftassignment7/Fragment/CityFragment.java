package com.tatvasoft.tatvasoftassignment7.Fragment;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tatvasoft.tatvasoftassignment7.BroadcastReceiver.BatteryReceiver;
import com.tatvasoft.tatvasoftassignment7.R;
import com.tatvasoft.tatvasoftassignment7.AsyncTaskClass.WeatherTask;
import com.tatvasoft.tatvasoftassignment7.Utils.Constant;

public class CityFragment extends Fragment {

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.title_city_weather));

        return view;
    }

    public static TextView temperature;
    public static TextView tempMin;
    public static TextView tempMax;
    public static TextView humidity;
    public static TextView pressure;
    public static TextView rain;
    public static TextView windDegree;
    public static TextView windSpeed;
    public static TextView batteryPercent;
    public static ImageView batteryImg;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        assert bundle != null;
        String cityName = bundle.getString(Constant.CITY_NAME);

        requireActivity().registerReceiver(new BatteryReceiver(),new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        TextView tvCityName = view.findViewById(R.id.tvDetailCityName);
        temperature = view.findViewById(R.id.tvDetailTemperature);
        tempMax = view.findViewById(R.id.tvDetailTempMax);
        tempMin = view.findViewById(R.id.tvDetailTempMin);
        humidity = view.findViewById(R.id.tvDetailHumidity);
        pressure = view.findViewById(R.id.tvDetailPressure);
        rain = view.findViewById(R.id.tvDetailRain);
        windDegree = view.findViewById(R.id.tvDetailWindDegree);
        windSpeed = view.findViewById(R.id.tvDetailWindSpeed);
        batteryPercent = view.findViewById(R.id.tvBattery);
        batteryImg = view.findViewById(R.id.batteryImg);
        tvCityName.setText(cityName);
        new WeatherTask().execute(cityName);
    }

}