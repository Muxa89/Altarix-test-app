package com.altarix.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.altarix.test.R;
import com.altarix.test.Ware;
import com.altarix.test.WareStorage;
import com.altarix.test.WareType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by mikhail on 22.07.14.
 */
public class SellActivity extends Activity {

    private static TextView titleView;
    private static Spinner typesSpinner;
    private static TextView countView;

    private WareStorage soldWares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        soldWares = new WareStorage();

        setContentView(R.layout.sell);

        initViews();
    }

    private void initViews() {
        titleView = (TextView) findViewById(R.id.titleView);

        typesSpinner = (Spinner) findViewById(R.id.typesSpinner);
        ArrayList<String> typeNames = new ArrayList<String>();
        for (WareType wareType : WareType.values()) {
            typeNames.add(wareType.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellActivity.this, android.R.layout.simple_spinner_item, typeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(adapter);

        countView = (TextView) findViewById(R.id.countView);
    }

    public void sellWare(View view) {
        String title = titleView.getText().toString();
        WareType wareType = WareType.findByTitle(typesSpinner.getSelectedItem().toString());
        Integer count = Integer.valueOf(countView.getText().toString());
        soldWares.add(new Ware(title, wareType), count);
    }

    public void toBuyPage(View view) {
        try {
            getIntent().putExtra("soldWares", soldWares.toJSONArray().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setResult(RESULT_OK, getIntent());
        finish();
    }

    private String generateJSON(List<Ware> wares) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Ware ware : wares) {
            jsonArray.put(ware.toJSONObject());
        }
        return jsonArray.toString();
    }
}
