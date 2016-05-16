package emadahmed.com.feelslike;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import emadahmed.com.feelslike.data.Channel;
import emadahmed.com.feelslike.data.Wcmodel;
import emadahmed.com.feelslike.service.WeatherServiceCallback;
import emadahmed.com.feelslike.service.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    private EditText location;

    private String weatherLocation;

    private EditText edit_temp, edit_wind;
    Button button_get_windchill;
    TextView text_results;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        location = (EditText) findViewById(R.id.location);

        edit_temp = (EditText) findViewById(R.id.edit_temp);
        edit_wind = (EditText) findViewById(R.id.edit_windspeed);
        button_get_windchill = (Button)findViewById(R.id.button_get_windchill);
        text_results = (TextView)findViewById(R.id.text_results);

        button_get_windchill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String results= Wcmodel.calculateWindChill(edit_wind,edit_temp);

                text_results.setText(results);




            }
        });







    }

    public void showWeather(View view)
    {
        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();

        weatherLocation = location.getText().toString();

        service.refreshWeather(weatherLocation);
        String x = temperatureTextView.getText().toString();
        edit_temp.setText(temperatureTextView.getText().toString());
    }

    public void serviceSuccess(Channel channel) {

        dialog.hide();

        int resource = getResources().getIdentifier("drawable/icon" + channel.getItem().getCondition().getCode(),null, getPackageName());

        //@SuppressWarnings("deprecation")
        //Drawable weatherIconDrawable = getResources().getDrawable(resource);

        //weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(channel.getItem().getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());

        conditionTextView.setText(channel.getItem().getCondition().getDescription());


        locationTextView.setText(service.getLocation());


    }

    public void serviceFailure(Exception exception)
    {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();

    }

}
