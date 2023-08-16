package com.whatstools.shakeShortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShakeReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals("shake.detector")) {
            Log.d("my receiver", ">>>>>> my receiver <<<<<");
        }
    }
}
