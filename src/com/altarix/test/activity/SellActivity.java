package com.altarix.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import com.altarix.test.R;

/**
 * Created by mikhail on 22.07.14.
 */
public class SellActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.sell);


    }

    public void toBuyPage(View view) {
        finish();
    }

    public void sellWare(View view) {

    }
}
