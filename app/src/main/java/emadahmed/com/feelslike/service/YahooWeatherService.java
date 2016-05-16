package emadahmed.com.feelslike.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import emadahmed.com.feelslike.data.Channel;

/**
 * Created by Emad Ahmed on 5/6/2016.
 */
public class YahooWeatherService {

    private WeatherServiceCallback callback;

    private String location;

    private Exception error;

    public YahooWeatherService(WeatherServiceCallback weatherServiceCallback)
    {
       this.callback=weatherServiceCallback;
    }


    public void refreshWeather(final String l) {
        this.location=l;
        new AsyncTask<String,Void,String>(){
            protected String doInBackground(String... strings) {

                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")",strings[0]);

                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line= reader.readLine())!=null) {
                        result.append(line);
                    }

                    return result.toString();

                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {

                if (s==null && error!=null) {
                    callback.serviceFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject queryResults = data.optJSONObject("query");

                    int count = queryResults.optInt("count");
                    if(count==0)
                    {
                        callback.serviceFailure(new locationWeatherException("No Weather information found for:" + location));
                        return;
                    }

                    Channel channel = new Channel();

                    channel.populate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.serviceSuccess(channel);

                }catch (JSONException e)
                {
                    callback.serviceFailure(e);
                }
            }
        }.execute(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public class locationWeatherException extends Exception{

        public locationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
