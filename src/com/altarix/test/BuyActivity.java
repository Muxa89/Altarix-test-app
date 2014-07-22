package com.altarix.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class BuyActivity extends Activity {

    private static TextView titleView;
    private static TextView typeView;
    private static TextView countView;
    private static Button buyButton;
    private static ScrollView scrollView;

    private List<Ware> wares;
    private Ware currentWare;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);

        initViews();

        loadWares();
    }

    private void initViews() {
        titleView = (TextView) findViewById(R.id.titleView);
        typeView = (TextView) findViewById(R.id.typeView);
        countView = (TextView) findViewById(R.id.countView);
        buyButton = (Button) findViewById(R.id.buyButton);
    }

    private void loadWares() {
        IWaresProvider provider = new FakeWaresProvider();
        wares = provider.getWares();

        if (wares != null && wares.size() > 0) {
            currentWare = wares.get(0);
            fillWare(currentWare);

        }
    }

    private void fillWare(Ware ware) {
        titleView.setText(ware.getName());
        typeView.setText(ware.getType().getName());
        countView.setText(Integer.toString(ware.getCount()));
    }
}
