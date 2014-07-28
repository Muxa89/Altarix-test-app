package com.altarix.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.altarix.test.R;
import com.altarix.test.Ware;
import com.altarix.test.WareStorage;
import com.altarix.test.WareType;
import org.json.JSONException;

import java.util.*;

/**
 * Created by mikhail on 22.07.14.
 */
public class SellActivity extends Activity {

    private static TextView titleView;
    private static Spinner typesSpinner;
    private static TextView countView;
    private static TextView priceView;

    private static interface Validator<T> {
        T validate(String text) throws Exception;
    }

    private static Validator<String> titleValidator = new Validator<String>() {
        @Override
        public String validate(String text) throws Exception {
            return "".equals(text) ? null : text;
        }
    };

    private static Validator<Integer> countValidator = new Validator<Integer>() {
        @Override
        public Integer validate(String text) throws Exception {
            Integer count = Integer.valueOf(text);
            if (count <= 0) {
                count = null;
            }
            return count;
        }
    };

    private static Validator<Double> priceValidator = new Validator<Double>() {
        @Override
        public Double validate(String text) throws Exception {
            Double price = Double.valueOf(priceView.getText().toString());
            if (price <= 0) {
                price = null;
            }
            return price;
        }
    };

    private WareStorage soldWares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        priceView = (TextView) findViewById(R.id.priceView);
    }

    public void sellWare(View view) {
        String title = validateAndGetValueFromView(titleView, "Введите название", titleValidator);
        WareType wareType = WareType.findByTitle(typesSpinner.getSelectedItem().toString());
        Integer count = validateAndGetValueFromView(countView, "Неверное количество", countValidator);
        Double price = validateAndGetValueFromView(priceView, "Неверная стоимость", priceValidator);

        if (title != null && price != null && count != null) {
            price *= 100;
            soldWares.add(new Ware(title, wareType, price.intValue()), count);
        }
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

    /**
     * @return null if not valid
     */
    private static <T> T validateAndGetValueFromView(TextView view, String errorText, Validator<T> validator) {
        T result;
        try {
            result = (T) validator.validate(view.getText().toString().trim());
        } catch (Exception e) {
            result = null;
        }

        if (result == null) {
            view.setError(errorText);
            view.requestFocus();
        }
        return result;
    }
}
