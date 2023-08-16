package com.whatstools.statusSaver;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.whatstools.MainActivity;
import com.whatstools.R;

import java.io.File;
import java.io.IOException;

public class SavedImageViewer extends AppCompatActivity implements ViewPager.OnPageChangeListener, OnClickListener {
    FloatingActionButton btn_delete;
    FloatingActionButton btn_setas;
    FloatingActionButton btn_share;
    FloatingActionMenu materialDesignFAM;
    Bitmap myBitmap;
    int position;
    ViewPager viewPager;


    //Click event of Button Fab
    private class btnFamListner implements OnClickListener {
        public void onClick(View view) {
            if (SavedImageViewer.this.materialDesignFAM.isOpened()) {
                SavedImageViewer.this.materialDesignFAM.close(true);
            }
        }
    }

    //Dialog Yes Button Click event
    private class btnDialogYesListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            String imageFilePath = SavedImageGridRecycerAdapter.fileModelArrayList.get(SavedImageViewer.this.position).getImageFilePath();
            Uri imageConvertToUri = Uri.parse(imageFilePath);
            File fdelete = new File(imageConvertToUri.getPath());
            if (!fdelete.exists()) {
                return;
            }
            if (fdelete.delete()) {
                SavedImageGridRecycerAdapter.fileModelArrayList.remove(SavedImageViewer.this.position);
                SavedImageViewer.this.finish();
                Toast.makeText(SavedImageViewer.this, "Image Deleted Successfully....", Toast.LENGTH_SHORT).show();
                SavedImageViewer.this.addImageGallery(new File(imageFilePath));
                SavedImageViewer.this.AdsCount();
                return;
            }
            System.out.println("file not Deleted :" + imageConvertToUri.getPath());
        }
    }

    //Dialog NO Button Click event
    private class btnDialogNoListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_image_viewer);
        getSupportActionBar().setTitle("Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.materialDesignFAM = findViewById(R.id.material_design_android_floating_action_menu);
        this.btn_share = findViewById(R.id.share);
        this.btn_setas = findViewById(R.id.setas);
        this.btn_delete = findViewById(R.id.delete);
        this.btn_share.setOnClickListener(this);
        this.btn_setas.setOnClickListener(this);
        this.btn_delete.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.position = extras.getInt("Position");
            Log.d("Position get in Images", this.position + "");
            this.viewPager = findViewById(R.id.view_pager);
            this.viewPager.setAdapter(new SavedImageAdapter(this));
            this.viewPager.setCurrentItem(this.position);
            this.viewPager.addOnPageChangeListener(this);
        }
        this.materialDesignFAM.setOnClickListener(new btnFamListner());
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
    }

    public void onPageSelected(int position) {
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete:
                this.materialDesignFAM.close(true);
                DeleteActivity();
                return;
            case R.id.setas:
                this.materialDesignFAM.close(true);
                SetAsActivity();
                return;
            case R.id.share:
                this.materialDesignFAM.close(true);
                ShareActivity();
                return;
            default:
                return;
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

    private void DeleteActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure to delete the Video from your device's local storage?");
        alertDialog.setPositiveButton("YES", new btnDialogYesListner());
        alertDialog.setNegativeButton("NO", new btnDialogNoListner());
        alertDialog.show();
    }

    private void addImageGallery(File targetLocation) {
        String[] projection = new String[]{"_id"};
        String[] selectionArgs = new String[]{targetLocation.getAbsolutePath()};
        Uri queryUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(queryUri, projection, "_data = ?", selectionArgs, null);
        if (c.moveToFirst()) {
            contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, c.getLong(c.getColumnIndexOrThrow("_id"))), null, null);
        }
        c.close();
    }

    @SuppressLint({"LongLogTag"})
    private void SetAsActivity() {
        File imgFile = new File(SavedImageGridRecycerAdapter.fileModelArrayList.get(this.position).getImageFilePath());
        if (imgFile.exists()) {
            this.myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        try {
            WallpaperManager.getInstance(getApplicationContext()).setBitmap(this.myBitmap);
            Toast.makeText(this, "Wallpaper Set Successfully", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Wallpaper Set Unsuccessfully", Toast.LENGTH_LONG).show();
        }
    }


    //Share Activity Method
    private void ShareActivity() {
        Uri imageConvertToUri = FileProvider.getUriForFile(this, "com.whatstools.provider", new File(SavedImageGridRecycerAdapter.fileModelArrayList.get(this.position).getImageFilePath()));
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.STREAM", imageConvertToUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
        AdsCount();
    }

}
