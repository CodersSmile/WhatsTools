package com.whatstools.shakeShortcut;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;
import com.whatstools.shake_Detector.ShakeCallback;
import com.whatstools.shake_Detector.ShakeDetector;
import com.whatstools.shake_Detector.ShakeOptions;

public class ShakeMain extends AppCompatActivity {
    public static boolean ShakeCheck = true;
    ImageView ShakeButton;
    ImageView ShakeDemo;
    private ShakeDetector shakeDetector;


    //Method of shake listener
    private class btnShakeListner implements OnClickListener {
        public void onClick(View view) {
            if (ShakeMain.ShakeCheck) {
                ShakeMain.ShakeCheck = false;
                ShakeMain.this.ShakeButton.setImageResource(R.drawable.offs);
                ShakeMain.this.shakeDetector.stopShakeDetector(ShakeMain.this.getBaseContext());
            } else {
                ShakeMain.ShakeCheck = true;
                ShakeMain.this.ShakeButton.setImageResource(R.drawable.ons);
                ShakeMain.this.shakeDetector.startShakeService(ShakeMain.this.getBaseContext());
            }
        }
    }


    //Method of when shake is detect
    private class shakeDetectListner implements ShakeCallback {
        public void onShake() {
            ShakeMain.this.startActivity(ShakeMain.this.getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_shake);
        getSupportActionBar().setTitle("WhatsApp Shortcut");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }
        this.ShakeButton = findViewById(R.id.btnShake);
        this.ShakeDemo = findViewById(R.id.shake);
        this.ShakeButton.setOnClickListener(new btnShakeListner());
        this.shakeDetector = new ShakeDetector(new ShakeOptions().background(Boolean.FALSE).interval(1000).shakeCount(2).sensibility(2.0f)).start(this, new shakeDetectListner());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        this.shakeDetector.destroy(getBaseContext());
        super.onDestroy();
    }
}
