package com.whatstools.statusSaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.Internetconnection;
import com.whatstools.R;

public class StatusSaverMainActivity extends AppCompatActivity implements OnClickListener {
    ImageView recent_stories;
    ImageView saved_stories;

    @SuppressLint({"WrongViewCast"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_statussaver);
        getSupportActionBar().setTitle("Status Saver");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getPermission();
        if (!Internetconnection.checkConnection(this)) {
            Mrec banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        this.recent_stories = findViewById(R.id.recent_story);
        this.saved_stories = findViewById(R.id.saved_stories);
        this.recent_stories.setOnClickListener(this);
        this.saved_stories.setOnClickListener(this);
    }

    private void getPermission() {
        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recent_story:
                LoadAdsRcent();
                return;
            case R.id.saved_stories:
                LoadAdsRSave();
                return;
            default:
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void LoadAdsRcent() {
        startActivity(new Intent(this, RecentStoriesActivity.class));
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        StartAppAd.showAd(this);

    }

    private void LoadAdsRSave() {
        Intent intent = new Intent(this, SavedStoriesActivity.class);
        intent.putExtra("callingactivity", "maincall");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        StartAppAd.showAd(this);
    }
}
