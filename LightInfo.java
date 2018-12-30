package com.augment.golden.lifxswitchwatch;

import java.util.concurrent.atomic.AtomicInteger;

public class LightInfo {
    boolean changePower;
    boolean onOrOff;

    boolean changeBrightness;
    int brightnessAmount;

    boolean getBrightness;
    AtomicInteger currentBrightness;

    private void LightInfo(){

    }

    public static LightInfo changePower(boolean onOrOff){
        LightInfo info = new LightInfo();
        info.changePower = true;
        info.onOrOff = onOrOff;

        return info;
    }

    public static LightInfo changeBrightness(int amount, AtomicInteger currentBrightness){
        LightInfo info = new LightInfo();
        info.changeBrightness = true;
        info.brightnessAmount = amount;
        info.currentBrightness = currentBrightness;
        currentBrightness.getAndAdd(amount);

        return info;
    }

    public static LightInfo getBrightness(AtomicInteger currentBrightness){
        LightInfo info = new LightInfo();
        info.getBrightness = true;
        info.currentBrightness = currentBrightness;

        return info;
    }
}
