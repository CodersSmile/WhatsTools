package com.whatstools.statusSaver;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.MainActivity;
import com.whatstools.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class ImageViewer extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    File imageRoot = new File(Environment.getExternalStorageDirectory() + "/Whatsapp/Media/.Statuses/");
    File imageRoot1 = new File(Environment.getExternalStorageDirectory() + "/Status Saver/StatusImages/");
    int position;
    ArrayList<FileModel> saveimages = ImageGridRecycerAdapter.fileModelArrayList;


    //Button click event of download image
    private class btnImgDownloadListner implements OnClickListener {
        public void onClick(View view) {
            try {
                ImageViewer.this.copyDirectory(ImageViewer.this.imageRoot, ImageViewer.this.imageRoot1);
                Toast.makeText(ImageViewer.this.getApplicationContext(), "Successfully downloaded", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(ImageViewer.this.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            ImageViewer.this.AdsCount();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_viewer);
        getSupportActionBar().setTitle("Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton img_download = findViewById(R.id.img_download);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.position = extras.getInt("Position");
            Log.d("Position get in Images", this.position + "");
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(new ImageAdapter(this));
            viewPager.setCurrentItem(this.position);
            viewPager.addOnPageChangeListener(this);
        }
        img_download.setOnClickListener(new btnImgDownloadListner());
    }

    private void AdsCount() {
        if (MainActivity.countAds >= 3) {
            LoadAds();
            MainActivity.countAds = 0;
            return;
        }
        MainActivity.countAds++;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void LoadAds() {
        StartAppAd.showAd(this);
    }

    public void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }
            for (int i = 0; i < this.saveimages.size(); i++) {
                copyDirectory(new File(sourceLocation, this.saveimages.get(this.position).getImageFileName()), new File(targetLocation, this.saveimages.get(this.position).getImageFileName()));
            }
            return;
        }
        InputStream in = new FileInputStream(sourceLocation);
        OutputStream out = new FileOutputStream(targetLocation);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                addImageGallery(targetLocation);
                return;
            }
        }
    }

    private void addImageGallery(File targetLocation) {
        ContentValues values = new ContentValues();
        values.put("title", getString(R.string.picture_title));
        values.put("description", getString(R.string.picture_description));
        values.put("datetaken", System.currentTimeMillis());
        values.put("bucket_id", targetLocation.toString().toLowerCase(Locale.US).hashCode());
        values.put("bucket_display_name", targetLocation.getName().toLowerCase(Locale.US));
        values.put("_data", targetLocation.getAbsolutePath());
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
    }
}
