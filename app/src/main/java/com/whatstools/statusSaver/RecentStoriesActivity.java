package com.whatstools.statusSaver;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.MainActivity;
import com.whatstools.R;

public class RecentStoriesActivity extends AppCompatActivity {
    Fragment fragment = null;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;


    //Tab layout Listener
    private class btnTabLayoutListner implements TabLayout.OnTabSelectedListener {
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    RecentStoriesActivity.this.fragment = new VideoFragment();
                    break;
                case 1:
                    RecentStoriesActivity.this.fragment = new ImagesFragment();
                    break;
            }
            FragmentTransaction ft = RecentStoriesActivity.this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.simpleFrameLayout, RecentStoriesActivity.this.fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        public void onTabUnselected(TabLayout.Tab tab) {
        }

        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(Color.parseColor("#3f5b48"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_stories);
        getSupportActionBar().setTitle("Recent Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        goToSecondFragment();
        this.simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
        this.tabLayout = findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = this.tabLayout.newTab();
        firstTab.setText("Video");
        this.tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = this.tabLayout.newTab();
        secondTab.setText("Images");
        this.tabLayout.addTab(secondTab);
        this.tabLayout.setOnTabSelectedListener(new btnTabLayoutListner());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        AdsCount();
        finish();
    }

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
        this.fragment = new VideoFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.simpleFrameLayout, this.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
