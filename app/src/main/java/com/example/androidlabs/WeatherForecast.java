package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WeatherForecast extends AppCompatActivity {

//    https://www.androdocs.com/java/creating-an-android-weather-app-using-java.html

//    https://www.concretepage.com/android/android-asynctask-example-with-progress-bar

    ProgressBar progressBar;

    TextView currentTempText;
    TextView minTempText;
    TextView maxTempText;

    private static final String TAG = "WeatherForecast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        currentTempText = (TextView) findViewById(R.id.currentTempText);
        minTempText = (TextView) findViewById(R.id.minTempText);
        maxTempText = (TextView) findViewById(R.id.maxTempText);


        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");




//        ForecastQuery uvreq = new ForecastQuery();
//        uvreq.execute("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");


    }

    public void startAsyncTask(View v) {

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        public String currentTemp;
        public String minTemp;
        public String maxTemp;
        public String uv;
        public Bitmap bm;


        private static final String TAG = "ForecastQuery";

        protected void onPreExecute() {
            currentTempText.setVisibility(View.GONE);
            minTempText.setVisibility(View.GONE);
            maxTempText.setVisibility(View.GONE);
        }

        protected String doInBackground(String ... args)
        {

            try {

                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                String parameter = null;
                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_TAG) {
                        if(xpp.getName().equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null,    "value");
                            minTemp = xpp.getAttributeValue(null,    "min");
                            maxTemp = xpp.getAttributeValue(null, "max");
                        }
                        else if(xpp.getName().equals("lastupdate"))
                        {
                            xpp.next();
                            parameter = xpp.getText(); // this will return  20
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }


                //JSON reading:
                //Build the entire string response:
//            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null)
//            {
//                sb.append(line + "\n");
//            }
//            String result = sb.toString(); //result is the whole string
//

//            JSONObject uvReport = new JSONObject(result);
//
//            double uvRating = uvReport.getDouble("value");
//            Log.i("MainActivity", "The uv is now: " + uvRating) ;

            }
            catch (Exception e) {
            }

            return null;
        }

        //Type 2
        public void onProgressUpdate(Integer ... args) {
            progressBar.setVisibility(View.VISIBLE);

        }
        //Type3
        public void onPostExecute(String fromDoInBackground) {

            currentTempText.setVisibility(View.VISIBLE);
            minTempText.setVisibility(View.VISIBLE);
            maxTempText.setVisibility(View.VISIBLE);

            minTempText.setText(minTemp);
            maxTempText.setText(maxTemp);
            currentTempText.setText(currentTemp);

            progressBar.setVisibility(View.GONE);
        }
    }


}
