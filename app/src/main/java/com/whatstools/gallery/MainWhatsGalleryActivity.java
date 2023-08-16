package com.whatstools.gallery;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.MainActivity;
import com.whatstools.R;

public class MainWhatsGalleryActivity extends AppCompatActivity {
    Fragment fragment = null;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayouts;


    //Button Tab Layout Listner
    private class btnTabLayoutListner implements TabLayout.OnTabSelectedListener {
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    MainWhatsGalleryActivity.this.fragment = new VideoGallery();
                    break;
                case 1:
                    MainWhatsGalleryActivity.this.fragment = new ImageGallery();
                    break;
            }
            FragmentTransaction ft = MainWhatsGalleryActivity.this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.simpleFrameLayout, MainWhatsGalleryActivity.this.fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery_whats);
        getSupportActionBar().setTitle("WhatsApp gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }
        goToSecondFragment();
        this.simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
        this.tabLayouts = findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = this.tabLayouts.newTab();
        firstTab.setIcon(R.drawable.video);
        this.tabLayouts.addTab(firstTab);
        TabLayout.Tab secondTab = this.tabLayouts.newTab();
        secondTab.setIcon(R.drawable.image);
        this.tabLayouts.addTab(secondTab);
        this.tabLayouts.setOnTabSelectedListener(new btnTabLayoutListner());
    }

    public boolean onSupportNavigateUp() {
        finish();
        AdsCount();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //Ads Counter
    private void AdsCount() {
        if (MainActivity.countAds >= 6) {
            LoadAds();
            MainActivity.countAds = 0;
            return;
        }
        MainActivity.countAds++;
    }

    private void LoadAds() {
        StartAppAd.showAd(this);
    }

    private void goToSecondFragment() {
        this.fragment = new VideoGallery();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.simpleFrameLayout, this.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
