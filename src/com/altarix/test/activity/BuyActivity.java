package com.altarix.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.altarix.test.*;
import org.json.JSONArray;
import org.json.JSONException;

public class BuyActivity extends Activity {

    public static final int SELL_ACTIVITY_CODE = 10;
    public static final String CURRENT_WARE_INDEX = "currentWareIndex";
    public static final String WARES = "wares";
    public static final String SOLD_WARES = "soldWares";

    private static TextView titleView;
    private static TextView typeView;
    private static TextView countView;
    private static Button buyButton;
    private static SeekBar position;
    private static LinearLayout buyPageContainer;

    private WareStorage wareStorage;
    private Ware currentWare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buy);

        initViews();

        initWareStorage(savedInstanceState);
    }

    private void initViews() {
        titleView = (TextView) findViewById(R.id.titleView);
        typeView = (TextView) findViewById(R.id.typeView);
        countView = (TextView) findViewById(R.id.countView);
        buyButton = (Button) findViewById(R.id.buyButton);
        buyPageContainer = (LinearLayout) findViewById(R.id.buyPageContainer);
        position = (SeekBar) findViewById(R.id.seekBar);

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
                        if (Math.abs(finishX - startX) > 10) {
                            scrollWareList(finishX > startX);
                        }
                        break;
                }
                return true;
            }
        });

        position.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    currentWare = wareStorage.get(i);
                    fill(currentWare);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    private void initWareStorage(Bundle savedInstanceState) {
        Integer wareIndex = 0;
        if (savedInstanceState != null) {
            try {
                JSONArray jsonArray = new JSONArray(savedInstanceState.getString(WARES));
                wareStorage = new WareStorage(jsonArray);
                wareIndex = savedInstanceState.getInt(CURRENT_WARE_INDEX, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            IWaresProvider provider = new FakeWaresProvider();
            wareStorage = provider.getWareStorage();
        }

        if (wareStorage != null && wareStorage.size() > 0) {
            currentWare = wareStorage.get(wareIndex);
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
            buyButton.setText(String.format(getString(R.string.buyButtonFormat), ware.getPrice() / 100, ware.getPrice() % 100));
        } else {
            titleView.setText(getString(R.string.buyNullWareTitle));
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
                    JSONArray array = new JSONArray(data.getExtras().getString(SOLD_WARES));
                    WareStorage newWares = new WareStorage(array);
                    wareStorage.add(newWares);
                    fill(null);
                    if (currentWare == null && newWares.size() > 0) {
                        currentWare = wareStorage.get(0);
                    }
                    fill(currentWare);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try {
            outState.putString(WARES, wareStorage.toJSONArray().toString());
            if (currentWare != null) {
                outState.putInt(CURRENT_WARE_INDEX, wareStorage.indexOf(currentWare));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
