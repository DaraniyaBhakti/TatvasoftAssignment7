package com.tatvasoft.tatvasoftassignment7.AsyncTaskClass;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class BackgroundTask {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_ID = "fae7190d7e6433ec3a45285ffcf55c86";
    HttpURLConnection connection;
    InputStream inputStream;

    static boolean isCityPresent = true;
    protected Handler localHandler;
    protected Thread localThread;

    public void execute(String string){
        this.localHandler = new Handler(Looper.getMainLooper());
        this.onPreExecute();

        this.localThread = new Thread(() -> {
            String s = BackgroundTask.this.doInBackground(string);
            BackgroundTask.this.localHandler.post(() -> BackgroundTask.this.onPostExecute(s));
        });
        this.localThread.start();
    }

    public void cancel(){
        if(this.localThread.isAlive()){
            this.localThread.interrupt();
        }
    }

    protected void onPostExecute(String s) {
    }

    protected String doInBackground(String string) {
        connection = null;
        inputStream = null;

        try {
            URL url = new URL(String.format("%s%s&APPID=%s", BASE_URL, string, API_ID));
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuilder stringBuilder = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
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
        }
        return "";

    }

    private void onPreExecute() {
    }
}
