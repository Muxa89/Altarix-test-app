package com.altarix.test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 22.07.14.
 */
public enum WareType {
    APPLIANCES("Бытовая техника"), PHONE("Телефон"), CAR("Автомобиль");

    private String title;

    WareType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static WareType findByTitle(String title) {
        for (WareType wareType : values()) {
            if (wareType.title.equals(title)) {
                return wareType;
            }
        }
        return null;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", name());
        object.put("title", title);
        return object;
    }

    public static WareType getByJSONObject(JSONObject object) throws JSONException {
        return valueOf(object.getString("name"));
    }
}
