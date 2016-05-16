package emadahmed.com.feelslike.service;

import emadahmed.com.feelslike.data.Channel;

/**
 * Created by Emad Ahmed on 5/6/2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailure(Exception exception);



}
