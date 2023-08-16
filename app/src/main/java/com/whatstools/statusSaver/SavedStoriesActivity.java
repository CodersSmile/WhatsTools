package com.whatstools.statusSaver;

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

public class SavedStoriesActivity extends AppCompatActivity {
    Fragment fragment = null;
    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;


    //Tab Layout Listener
    private class btnTabLayoutListner implements TabLayout.OnTabSelectedListener {
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    SavedStoriesActivity.this.fragment = new VideoSavedFragment();
                    break;
                case 1:
                    SavedStoriesActivity.this.fragment = new ImagesSavedFragment();
                    break;
            }
            FragmentTransaction ft = SavedStoriesActivity.this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.simpleFrameLayout, SavedStoriesActivity.this.fragment);
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
        setContentView(R.layout.activity_saved_stories);
        getSupportActionBar().setTitle("Saved Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.simpleFrameLayout = findViewById(R.id.simpleFrameLayout);
        this.tabLayout = findViewById(R.id.simpleTabLayout);
        TabLayout.Tab firstTab = this.tabLayout.newTab();
        firstTab.setText("Videos");
        this.tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab = this.tabLayout.newTab();
        secondTab.setText("Images");
        this.tabLayout.addTab(secondTab);
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("callingactivity").equals("maincall")) {
            goToSecondFragment();
        } else if (bundle.getString("callingactivity").equals("secondcall")) {
            goToImagesSavedFragment();
        }
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

    public void goToImagesSavedFragment() {
        this.fragment = new ImagesSavedFragment();
        this.tabLayout.getTabAt(1).select();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.simpleFrameLayout, this.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    private void goToSecondFragment() {
        this.fragment = new VideoSavedFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.simpleFrameLayout, this.fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
