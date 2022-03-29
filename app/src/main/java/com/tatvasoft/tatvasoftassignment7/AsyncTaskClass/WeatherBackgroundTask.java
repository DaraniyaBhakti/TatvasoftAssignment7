package com.tatvasoft.tatvasoftassignment7.AsyncTaskClass;

import android.util.Log;

import com.tatvasoft.tatvasoftassignment7.Fragment.CityFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherBackgroundTask extends BackgroundTask{

    @Override
    protected String doInBackground(String string) {
        return super.doInBackground(string);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String temp="",tempMax="",tempMin="",humidity="",pressure="",rain="",windDegree="",windSpeed="";

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject object = jsonObject.getJSONObject("main");
            temp = object.getString("temp");
            tempMax = object.getString("temp_max");
            tempMin = object.getString("temp_min");
            humidity = object.getString("humidity");
            pressure = object.getString("pressure");
            rain = jsonObject.getJSONObject("clouds").getString("all");
            windDegree = jsonObject.getJSONObject("wind").getString("speed");
            windSpeed = jsonObject.getJSONObject("wind").getString("deg");

            isCityPresent=true;

        } catch (JSONException e) {
            e.printStackTrace();
            isCityPresent = false;
            Log.d("Weather", String.valueOf(e));
        }


        CityFragment.temperature.setText(temp);
        CityFragment.tempMax.setText(tempMax);
        CityFragment.tempMin.setText(tempMin);
        CityFragment.humidity.setText(humidity);
        CityFragment.pressure.setText(pressure);
        CityFragment.rain.setText(rain);
        CityFragment.windDegree.setText(windDegree);
        CityFragment.windSpeed.setText(windSpeed);


    }

    @Override
    public void cancel() {
        super.cancel();
    }
}
