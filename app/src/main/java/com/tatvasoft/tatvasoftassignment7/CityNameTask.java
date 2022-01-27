package com.tatvasoft.tatvasoftassignment7;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class CityNameTask extends AsyncTask<String,String,String> {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_ID = "fae7190d7e6433ec3a45285ffcf55c86";
    HttpURLConnection connection;
    InputStream inputStream;

    static boolean isCityPresent = true;
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
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            jsonObject.getString("name");
            isCityPresent = true;
        } catch (JSONException e) {
            e.printStackTrace();
            isCityPresent = false;

        }

    }
}
