package com.altarix.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.altarix.test.FakeWaresProvider;
import com.altarix.test.IWaresProvider;
import com.altarix.test.R;
import com.altarix.test.Ware;

import java.util.List;

public class BuyActivity extends Activity {

    private static TextView titleView;
    private static TextView typeView;
    private static TextView countView;
    private static Button buyButton;

    private List<Ware> wares;
    private Ware currentWare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.buy);

        initViews();

        loadWares();
    }

    private void initViews() {
        titleView = (TextView) findViewById(R.id.titleView);
        typeView = (TextView) findViewById(R.id.typeView);
        countView = (TextView) findViewById(R.id.countView);
        buyButton = (Button) findViewById(R.id.buyButton);
    }

    public void buyWare(View view) {
        if (currentWare == null) return;

        int count = currentWare.getCount();
        if (count > 1) {
            currentWare.setCount(currentWare.getCount() - 1);
        } else {
            wares.remove(currentWare);
            if (wares.size() > 0) {
                currentWare = wares.get(0);
            } else {
                currentWare = null;
            }
        }
        fill(currentWare);
    }

    public void toSellPage(View view) {
        Intent intent = new Intent(this, SellActivity.class);
        startActivityForResult(intent, 10);
    }

    private void loadWares() {
        IWaresProvider provider = new FakeWaresProvider();
        wares = provider.getWares();

        if (wares != null && wares.size() > 0) {
            currentWare = wares.get(0);
            fill(currentWare);
        }
    }

    private void fill(Ware ware) {
        if (ware != null) {
            titleView.setText(ware.getName());
            typeView.setText(ware.getType().getName());
            countView.setText(Integer.toString(ware.getCount()));
        } else {
            titleView.setText("Товар не выбран");
            typeView.setText("");
            countView.setText("");
        }
        buyButton.setEnabled(ware != null);
    }
}
