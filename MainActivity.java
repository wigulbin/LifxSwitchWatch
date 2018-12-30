package com.augment.golden.lifxswitchwatch;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.input.RotaryEncoder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends WearableActivity{

    private TextView mTextView;
    private boolean on = false;
    private AtomicInteger currentBrightness = new AtomicInteger(55);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        Switch button = (Switch)findViewById(R.id.switch1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch vSwitch = (Switch) v;
                new BulbThread().execute(LightInfo.changePower(vSwitch.isChecked()));
            }
        });


        setAmbientEnabled();
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent ev){
        if(ev.getAction() == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(ev)){
            float delta = -RotaryEncoder.getRotaryAxisValue(ev);
            currentBrightness.getAndAdd((int)(12 * delta));
            new BulbThread().execute(LightInfo.changeBrightness((int)(12 * delta), currentBrightness));
        }

        return super.onGenericMotionEvent(ev);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_STEM_1){
            LightInfo info = LightInfo.changePower(on);
            new BulbThread().execute(info);
            on = !on;
        }

        return super.onKeyDown(keyCode, event);
    }
}
