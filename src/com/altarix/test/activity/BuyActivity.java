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
import com.altarix.test.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class BuyActivity extends Activity {

    public static final int SELL_ACTIVITY_CODE = 10;
    private static TextView titleView;
    private static TextView typeView;
    private static TextView countView;
    private static Button buyButton;
    private static ProgressBar position;
    private static LinearLayout buyPageContainer;

    private WareStorage wareStorage;
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
        if (wareStorage.size() == 0) return;

        int currentPosition = wareStorage.indexOf(currentWare);

        if ((currentPosition == 0 && scrolledToLeft) || (currentPosition == wareStorage.size() - 1 && !scrolledToLeft))
            return;

        if (scrolledToLeft) {
            currentPosition--;
        } else {
            currentPosition++;
        }

        currentWare = wareStorage.get(currentPosition);
        fill(currentWare);
    }

    public void buyWare(View view) {
        if (currentWare == null) return;
        int index = wareStorage.indexOf(currentWare);
        wareStorage.remove(currentWare, 1);
        if (wareStorage.getCount(currentWare) == 0) {
            if (wareStorage.size() > 0) {
                currentWare = wareStorage.get(index == 0 ? 0 : index - 1);
            } else {
                currentWare = null;
            }
        }

        fill(currentWare);
    }

    public void toSellPage(View view) {
        Intent intent = new Intent(this, SellActivity.class);
        startActivityForResult(intent, SELL_ACTIVITY_CODE);
    }

    private void loadWares() {
        IWaresProvider provider = new FakeWaresProvider();
        wareStorage = provider.getWareStorage();

        if (wareStorage != null && wareStorage.size() > 0) {
            currentWare = wareStorage.get(0);
            fill(currentWare);
        } else {
            fill(null);
        }
    }

    private void fill(Ware ware) {
        if (wareStorage != null && wareStorage.size() > 1) {
            position.setVisibility(View.VISIBLE);
            position.setMax(wareStorage.size() - 1);
            position.setProgress(wareStorage.indexOf(ware));
        } else {
            position.setVisibility(View.INVISIBLE);
        }

        if (ware != null) {
            titleView.setText(ware.getName());
            typeView.setText(ware.getType().getTitle());
            countView.setText(Integer.toString(wareStorage.getCount(ware)));
            buyButton.setText(String.format("Купить за %d.%02d руб.", ware.getPrice() / 100, ware.getPrice() % 100));
        } else {
            titleView.setText("Товар не выбран");
            typeView.setText("");
            countView.setText("");
        }

        buyButton.setVisibility(ware != null ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELL_ACTIVITY_CODE:
                try {
                    JSONArray array = new JSONArray(data.getExtras().getString("soldWares"));
                    WareStorage newWares = new WareStorage(array);
                    wareStorage.add(newWares);
                    fill(null);
                    fill(currentWare);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;
        }
    }
}
