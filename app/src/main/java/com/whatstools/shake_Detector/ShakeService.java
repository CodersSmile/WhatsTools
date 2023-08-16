package com.whatstools.shake_Detector;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

public class ShakeService extends Service {
    private appPreferences appPreferences;
    private Sensor sensor;
    private SensorManager sensorManager;
    private ShakeListener shakeListener;
    private ShakeOptions shakeOptions;

    public void onCreate() {
        this.appPreferences = new appPreferences(getBaseContext());
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.shakeOptions = new ShakeOptions().background(this.appPreferences.getBoolean("BACKGROUND", Boolean.valueOf(true))).sensibility(this.appPreferences.getFloat("SENSIBILITY", Float.valueOf(1.2f)).floatValue()).shakeCount(this.appPreferences.getInt("SHAKE_COUNT", Integer.valueOf(1)).intValue()).interval(this.appPreferences.getInt("SHAKE_INTERVAL", Integer.valueOf(2000)).intValue());
        startShakeService(getBaseContext());
        if (this.shakeOptions.isBackground()) {
            return START_STICKY;
        }
        return START_NOT_STICKY;
    }

    public void startShakeService(Context context) {
        this.shakeListener = new ShakeListener(this.shakeOptions, context);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(1);
        if (sensors.size() > 0) {
            this.sensor = sensors.get(0);
            this.sensorManager.registerListener(this.shakeListener, this.sensor, 1);
        }
    }

    public void onDestroy() {
        this.sensorManager.unregisterListener(this.shakeListener);
        super.onDestroy();
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
