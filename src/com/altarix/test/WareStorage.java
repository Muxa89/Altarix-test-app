package com.altarix.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by mikhail on 24.07.14.
 */
public class WareStorage {

    private LinkedHashMap<Ware, Integer> wares;

    public WareStorage() {
        wares = new LinkedHashMap<Ware, Integer>();
    }

    public WareStorage(JSONArray jsonArray) throws JSONException {
        wares = new LinkedHashMap<Ware, Integer>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            Ware ware = new Ware(object.getJSONObject("ware"));
            int count = object.getInt("count");
            add(ware, count);
        }
    }

    public void add(Ware ware, int count) {
        if (!wares.containsKey(ware)) {
            wares.put(ware, 0);
        }
        wares.put(ware, wares.get(ware) + count);
    }

    public void add(WareStorage storage) {
        for (Ware ware : storage.wares.keySet()) {
            add(ware, storage.getCount(ware));
        }
    }

    public void remove(Ware ware, int count) {
        if (!wares.containsKey(ware)) return;
        int oldCount = wares.get(ware);
        int newCount = oldCount - count;
        if (newCount <= 0) {
            wares.remove(ware);
        } else {
            wares.put(ware, newCount);
        }
    }

    public int indexOf(Ware ware) {
        int index = 0;
        for (Ware currentWare : wares.keySet()) {
            if (currentWare.equals(ware)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int size() {
        return wares.size();
    }

    public Ware get(int i) {
        return (Ware) wares.keySet().toArray()[i];
    }

    public int getCount(Ware ware) {
        if (!wares.containsKey(ware)) {
            return 0;
        } else {
            return wares.get(ware);
        }
    }

    public JSONArray toJSONArray() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Ware ware : wares.keySet()) {
            JSONObject entry = new JSONObject();
            entry.put("ware", ware.toJSONObject());
            entry.put("count", getCount(ware));
            jsonArray.put(entry);
        }
        return jsonArray;
    }

    @Override
    public String toString() {
        return "WareStorage{" +
                "wares=" + wares +
                '}';
    }
}
