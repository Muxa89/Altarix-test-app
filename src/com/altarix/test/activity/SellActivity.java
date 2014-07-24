package com.altarix.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.altarix.test.R;
import com.altarix.test.WareType;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mikhail on 22.07.14.
 */
public class SellActivity extends Activity {

    private static Spinner typesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.sell);

        typesSpinner = (Spinner) findViewById(R.id.typesSpinner);
        ArrayList<String> typeNames = new ArrayList<String>();
        for (WareType wareType : WareType.values()) {
            typeNames.add(wareType.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SellActivity.this, android.R.layout.simple_spinner_item, typeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typesSpinner.setAdapter(adapter);
    }

    public void toBuyPage(View view) {
        finish();
    }

    public void sellWare(View view) {

    }
}
