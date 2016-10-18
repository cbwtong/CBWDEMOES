package com.cbw.example.blur;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by cbw on 2016/8/9.
 */
public
class
NewMainActivity extends AppCompatActivity {
    /**
     * basic btn
     */
    private Button mBasicBtn;

    /**
     * weather btn
     */
    private Button mWeatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmain_activity);

        mBasicBtn = (Button) findViewById(R.id.basic_blur_btn);
        mWeatherBtn = (Button) findViewById(R.id.weather_blur_btn);

        mBasicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMainActivity.this, BlurredViewBasicActivity.class));
            }
        });

        mWeatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewMainActivity.this, WeatherActivity.class));
            }
        });
    }
}
