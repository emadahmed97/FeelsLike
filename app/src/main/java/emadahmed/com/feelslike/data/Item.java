package emadahmed.com.feelslike.data;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Emad Ahmed on 5/6/2016.
 */
public class Item implements JSONPopulator {

    public Condition getCondition() {
        return condition;
    }

    private Condition condition;

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
