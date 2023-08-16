package com.whatstools.shake_Detector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

public class ShakeListener implements SensorEventListener {
    private Context context;
    private int mShakeCount;
    private long mShakeTimestamp;
    private ShakeOptions shakeOptions;

    public interface OnShakeListener {
        void onShake(int i);
    }

    public ShakeListener(ShakeOptions shakeOptions) {
        this.shakeOptions = shakeOptions;
    }

    public ShakeListener(ShakeOptions shakeOptions, Context context) {
        this.shakeOptions = shakeOptions;
        this.context = context;
    }

    public ShakeListener(ShakeOptions shakeOptions, Context context, ShakeCallback callback) {
        this.shakeOptions = shakeOptions;
        this.context = context;
    }

    public void resetShakeCount() {
        this.mShakeCount = 0;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float gX = x / 9.80665f;
        float gY = event.values[1] / 9.80665f;
        float gZ = event.values[2] / 9.80665f;
        float gForce = (float) Math.sqrt((double) (((gX * gX) + (gY * gY)) + (gZ * gZ)));
        if (gForce > this.shakeOptions.getSensibility()) {
            Log.d("LISTENER", "force: " + gForce + " count: " + this.mShakeCount);
            long now = System.currentTimeMillis();
            if (this.mShakeTimestamp + 500 <= now) {
                if (this.mShakeTimestamp + ((long) this.shakeOptions.getInterval()) < now) {
                    this.mShakeCount = 0;
                }
                this.mShakeTimestamp = now;
                this.mShakeCount++;
                if (this.shakeOptions.getShakeCounts() != this.mShakeCount) {
                    return;
                }
                if (this.shakeOptions.isBackground()) {
                    sendToBroadCasts(this.context);
                } else {
                    sendToPrivateBroadCasts(this.context);
                }
            }
        }
    }

    private void sendToBroadCasts(Context context) {
        Intent locationIntent = new Intent();
        locationIntent.setAction("shake.detector");
        context.sendBroadcast(locationIntent);
    }

    private void sendToPrivateBroadCasts(Context context) {
        Intent locationIntent = new Intent();
        locationIntent.setAction("private.shake.detector");
        context.sendBroadcast(locationIntent);
    }
}
