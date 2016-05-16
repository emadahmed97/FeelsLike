package emadahmed.com.feelslike.data;

import org.json.JSONObject;

/**
 * Created by Emad Ahmed on 5/6/2016.
 */
public class Channel implements JSONPopulator {

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    private Item item;
    private Units units;
    @Override
    public void populate(JSONObject data) {

        units = new Units();
        units.populate(data.optJSONObject("units"));

        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
