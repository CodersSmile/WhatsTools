package com.whatstools.asciiFaces;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.startapp.android.publish.ads.banner.Banner;
import com.whatstools.Internetconnection;
import com.whatstools.R;

import java.util.ArrayList;
import java.util.List;

public class AsciiFacesMainActivity extends AppCompatActivity {

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList();
        private final List<String> mFragmentTitleList = new ArrayList();

        public ViewPagerAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
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
        setContentView(R.layout.activity_main_asciifaces);
        getSupportActionBar().setTitle("Ascii Faces");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!Internetconnection.checkConnection(this)) {
            Banner banner = findViewById(R.id.startAppBanner);
            banner.hideBanner();
        }
        ViewPager viewPager = findViewById(R.id.viewpager);
        addTabs(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter1 = new ViewPagerAdapter(getSupportFragmentManager());
        adapter1.addFrag(new HappyFragment(), "HAPPY");
        adapter1.addFrag(new AngryFragment(), "ANGRY");
        adapter1.addFrag(new OtherFragment(), "OTHER");
        viewPager.setAdapter(adapter1);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
