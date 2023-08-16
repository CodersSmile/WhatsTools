package com.whatstools.fackChat;

import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.R;

import java.util.ArrayList;
import java.util.List;

public class MainFackChat extends AppCompatActivity {
    LinearLayout Camera;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    //Cam button click listener
    private class btnCamListner implements OnClickListener {
        public void onClick(View v) {
            MainFackChat.this.LoadAd();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fack_chat);
        GetPermission();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);
        this.viewPager = findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.Camera = findViewById(R.id.camera);
        this.Camera.setOnClickListener(new btnCamListner());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            LoadAd();
        } else if (item.getItemId() == R.id.more) {
            LoadAd();
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetPermission() {
        if (VERSION.SDK_INT >= 23) {
            CheckAccessPermission();
        }
    }

    @RequiresApi(api = 23)
    void CheckAccessPermission() {
        if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission", "Permission is granted");
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 555);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ChatsFragment(), "CHATS");
        adapter.addFrag(new StausFragment(), "STATUS");
        adapter.addFrag(new CallsFragment(), "CALLS");
        viewPager.setAdapter(adapter);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void LoadAd() {
        StartAppAd.showAd(this);
    }
}
