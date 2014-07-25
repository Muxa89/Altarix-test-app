package com.altarix.test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mikhail on 22.07.14.
 */
public class Ware {

    private String name;
    private WareType type;
    private int price; // in kopiykas

    public Ware(String name, WareType type, int price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Ware(JSONObject object) throws JSONException {
        this.name = object.getString("name");
        this.type = WareType.getByJSONObject(object.getJSONObject("type"));
        this.price = object.getInt("price");
    }

    public String getName() {
        return name;
    }

    public WareType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("type", type.toJSONObject());
        object.put("price", price);
        return object;
    }

    @Override
    public String toString() {
        return "Ware{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ware ware = (Ware) o;

        if (price != ware.price) return false;
        if (name != null ? !name.equals(ware.name) : ware.name != null) return false;
        if (type != ware.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
