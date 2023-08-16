package com.whatstools.shake_Detector;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class ShakeDetector {
    private appPreferences appPreferences;
    private ShakeBroadCastReceiver shakeBroadCastReceiver;
    private ShakeCallback shakeCallback;
    private ShakeOptions shakeOptions;

    public ShakeDetector(ShakeOptions shakeOptions) {
        this.shakeOptions = shakeOptions;
    }

    public ShakeDetector start(Context context, ShakeCallback shakeCallback) {
        this.shakeCallback = shakeCallback;
        this.shakeBroadCastReceiver = new ShakeBroadCastReceiver(shakeCallback);
        registerPrivateBroadCast(context);
        saveOptionsInStorage(context);
        startShakeService(context);
        return this;
    }

    public ShakeDetector start(Context context) {
        saveOptionsInStorage(context);
        startShakeService(context);
        return this;
    }

    public void destroy(Context context) {
        if (this.shakeBroadCastReceiver != null) {
            context.unregisterReceiver(this.shakeBroadCastReceiver);
        }
    }

    public void stopShakeDetector(Context context) {
        context.stopService(new Intent(context, ShakeService.class));
    }

    public void startShakeService(Context context) {
        context.startService(new Intent(context, ShakeService.class));
    }


    private void saveOptionsInStorage(Context context) {
        this.appPreferences = new appPreferences(context);
        this.appPreferences.putBoolean("BACKGROUND", this.shakeOptions.isBackground());
        this.appPreferences.putInt("SHAKE_COUNT", this.shakeOptions.getShakeCounts());
        this.appPreferences.putInt("SHAKE_INTERVAL", this.shakeOptions.getInterval());
        this.appPreferences.putFloat("SENSIBILITY", this.shakeOptions.getSensibility());
    }

    private void registerPrivateBroadCast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("shake.detector");
        filter.addAction("private.shake.detector");
        context.registerReceiver(this.shakeBroadCastReceiver, filter);
    }
}
