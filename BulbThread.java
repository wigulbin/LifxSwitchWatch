package com.augment.golden.lifxswitchwatch;

import android.os.AsyncTask;
import android.util.Log;

public class BulbThread extends AsyncTask<LightInfo, Void, String> {
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        Log.e("AsyncTask", "onPreExecute");
//    }

    protected String doInBackground(LightInfo... params) {
        LightInfo info = params[0];
        BulbClient client = new BulbClient();
        if(info.changePower){
            if(info.onOrOff)
                client.turnOnLight();
            else
                client.turnOffLight();
        }
        if(info.changeBrightness && info.currentBrightness.intValue() > -1){
            client.setBrightness(info.currentBrightness.intValue());

        }
        if(info.getBrightness){
            info.currentBrightness.getAndSet(client.getBrightness());
        }

        return "";
    }
    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
        System.out.println("Here");
    }

}
