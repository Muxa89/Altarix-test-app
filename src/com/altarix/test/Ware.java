package com.altarix.test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 22.07.14.
 */
public class Ware {

    private String name;
    private WareType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WareType getType() {
        return type;
    }

    public void setType(WareType type) {
        this.type = type;
    }

    public Ware(String name, WareType type) {
        this.name = name;
        this.type = type;
    }

    public Ware(JSONObject object) throws JSONException {
        this.name = object.getString("name");
        this.type = WareType.getByJSONObject(object.getJSONObject("type"));
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("type", type.toJSONObject());
        return object;
    }

    @Override
    public String toString() {
        return "Ware{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ware ware = (Ware) o;

        if (name != null ? !name.equals(ware.name) : ware.name != null) return false;
        if (type != ware.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
