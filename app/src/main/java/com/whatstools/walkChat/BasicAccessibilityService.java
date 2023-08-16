package com.whatstools.walkChat;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;

import com.whatstools.R;

public class BasicAccessibilityService extends AccessibilityService {
    public static Context context;
    public static View view;
    private final AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
    ActivityManager objActivityMang;
    LayoutInflater layoutInflater;
    private LayoutParams layoutParams;
    private View view1;
    private WindowManager windowManager;

    @SuppressLint({"ClickableViewAccessibility"})
    public void onServiceConnected() {
        this.windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        this.accessibilityServiceInfo.eventTypes = -1;
        this.accessibilityServiceInfo.feedbackType = 16;
        this.accessibilityServiceInfo.notificationTimeout = 100;
        setServiceInfo(this.accessibilityServiceInfo);
        context = this;
        this.objActivityMang = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        this.layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view1 = this.layoutInflater.inflate(R.layout.service_transnlat_text, null);
        this.layoutParams = new LayoutParams(50, 50, 2003, 40, -3);
        this.layoutParams.gravity = 51;
        this.layoutParams.x = 0;
        this.layoutParams.y = 0;
        AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
        accessibilityServiceInfo.eventTypes = 32;
        accessibilityServiceInfo.feedbackType = 16;
        accessibilityServiceInfo.flags = 2;
        setServiceInfo(accessibilityServiceInfo);
    }

    private ActivityInfo m18198a(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    @SuppressLint({"NewApi"})
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Object obj = null;
        try {
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && accessibilityEvent.getPackageName() != null && accessibilityEvent.getClassName() != null) {
                ComponentName componentName = new ComponentName(accessibilityEvent.getPackageName().toString(), accessibilityEvent.getClassName().toString());
                if (m18198a(componentName) != null) {
                    obj = 1;
                }
                if (obj == null) {
                    return;
                }
                if (componentName.getPackageName().equals("com.whatsapp")) {
                    if (WalkMainActivity.isWalk) {
                        view = CameraOverlay.methOverlayCheck(this);
                        if (view != null) {
                            view.setAlpha(0.5f);
                        }
                    }
                } else if (view != null) {
                    CameraOverlay.methWinManager();
                }
            }
        } catch (Exception ignored) {
        }
    }

    public void onInterrupt() {
        Log.e("Service", "Interupted");
    }
}
