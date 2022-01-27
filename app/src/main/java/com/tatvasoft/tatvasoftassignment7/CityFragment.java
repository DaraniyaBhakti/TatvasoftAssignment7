package com.tatvasoft.tatvasoftassignment7;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    static TextView temperature;
    static TextView tempMin;
    static TextView tempMax;
    static TextView humidity;
    static TextView pressure;
    static TextView rain;
    static TextView windDegree;
    static TextView windSpeed;
    static TextView batteryPercent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.title_city_weather));
        Bundle bundle = getArguments();
        assert bundle != null;
        String cityName = bundle.getString("CityName");

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

        tvCityName.setText(cityName);
        new WeatherTask().execute(cityName);
    }

}