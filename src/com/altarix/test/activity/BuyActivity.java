package com.altarix.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.altarix.test.FakeWaresProvider;
import com.altarix.test.IWaresProvider;
import com.altarix.test.R;
import com.altarix.test.Ware;

import java.util.ArrayList;
import java.util.List;

public class BuyActivity extends Activity {

    private static TextView titleView;
    private static TextView typeView;
    private static TextView countView;
    private static Button buyButton;
    private static ProgressBar position;
    private static LinearLayout buyPageContainer;

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
        position = (ProgressBar) findViewById(R.id.position);
        buyPageContainer = (LinearLayout) findViewById(R.id.buyPageContainer);

        buyPageContainer.setOnTouchListener(new View.OnTouchListener() {
            private float startX = 0.0f;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float finishX = motionEvent.getX();
                        scrollWareList(finishX > startX);
                        break;
                }
                return true;
            }
        });
    }

    private void scrollWareList(boolean scrolledToLeft) {
        if (wares.size() == 0) return;

        int currentPosition = wares.indexOf(currentWare);

        if ((currentPosition == 0 && scrolledToLeft) || (currentPosition == wares.size() - 1 && !scrolledToLeft))
            return;

        if (scrolledToLeft) {
            currentPosition--;
        } else {
            currentPosition++;
        }

        currentWare = wares.get(currentPosition);
        fill(currentWare);
    }

    public void buyWare(View view) {
        if (currentWare == null) return;

        int count = currentWare.getCount();
        if (count > 1) {
            currentWare.setCount(currentWare.getCount() - 1);
        } else {
            int index = wares.indexOf(currentWare);
            wares.remove(currentWare);
            if (wares.size() > 0) {
                currentWare = wares.get(index == 0 ? 0 : index - 1);
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
        } else {
            fill(null);
        }
    }

    private void fill(Ware ware) {
        if (wares != null && wares.size() > 1) {
            position.setVisibility(View.VISIBLE);
            position.setMax(wares.size() - 1);
            position.setProgress(wares.indexOf(ware));
        } else {
            position.setVisibility(View.INVISIBLE);
        }

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
