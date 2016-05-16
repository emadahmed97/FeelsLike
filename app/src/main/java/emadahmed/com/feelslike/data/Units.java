package emadahmed.com.feelslike.data;

import org.json.JSONObject;

/**
 * Created by Emad Ahmed on 5/6/2016.
 */
public class Units implements JSONPopulator {

    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
