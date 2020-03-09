package com.example.androidlabs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastQuery extends AsyncTask< String, Integer, String>
{
    public String currentTemp;
    public String minTemp;
    public String maxTemp;
    public String uv;
    public Bitmap bm;

    protected String doInBackground(String ... args)
    {
        try {

            URL url = new URL(args[0]);

            //open the connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //wait for data:
            InputStream response = urlConnection.getInputStream();


            //JSON reading:
            //Build the entire string response:
            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            String result = sb.toString(); //result is the whole string


            // convert string to JSON:
            JSONObject uvReport = new JSONObject(result);

            //get the double associated with "value"
            double uvRating = uvReport.getDouble("value");

            Log.i("MainActivity", "The uv is now: " + uvRating) ;

        }
        catch (Exception e)
        {

        }

        return "Done";
    }

    //Type 2
    public void onProgressUpdate(Integer ... args)
    {

    }
    //Type3
    public void onPostExecute(String fromDoInBackground)
    {
        Log.i("HTTP", fromDoInBackground);
    }
}